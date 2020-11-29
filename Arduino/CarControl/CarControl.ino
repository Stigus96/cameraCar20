/*
   Notes:

   Outputs when requesting from wire is a single char
   - s for stop
   - e for emergency stopped
   - f for forward
   - r for reverse

   Inputs from i2c must be a character with order, followed by a number to control speed
   - f is forward
   - s is stop
   - r is reverse

   DEBUG switches between *serial window or *serial communication to motorController.
   - true uses serial
   - false attaches motor controller
*/

#include <Servo.h>
#include <SoftwareSerial.h>
#include "RoboClaw.h"
#include <Wire.h>
#define I2C_SLAVE_ADDRESS 0x44 // i2c address for arduino
#define address 0x80 //Roboclaw address


bool DEBUG = false;
volatile char controllerStatus = 'i';


// Setup for Roboclaw
SoftwareSerial serial(0, 1);
RoboClaw roboclaw(&serial, 10000);

// Setup for servo
Servo Servo1;


// Setup for buttons
const int STOP_PIN = 2;

// motor Variables
volatile int motorSpeed = 0;
const int MAXSPEED = 64;
const int MINSPEED = -64;

volatile int wheelAngle = 90; // 90 degrees is forward
const int MAXANGLE = 150;
const int MINANGLE = 30;


// Global timer variables
unsigned long lockTime = millis();
bool locked = false;

void setup()
{
  pinMode(STOP_PIN, INPUT);
  attachInterrupt(STOP_PIN, emergencyStop, LOW);

  if (DEBUG) {
    Serial.begin(9600);
  } else {
    // Communicate with roboclaw at 38400bps - basicmicro robostudio
    roboclaw.begin(38400);
    roboclaw.ForwardM1(address, 0);
  }

  Wire.begin(I2C_SLAVE_ADDRESS);
  Wire.onRequest(requestEvents);
  Wire.onReceive(receiveEvents);
  delay(200);
  if (DEBUG) {
    Serial.print("---------- Arduino slave with address -");
    Serial.print(I2C_SLAVE_ADDRESS);
    Serial.println("- ready ---------------------");
  }
}

// Empty loop, activated by interrupt from stop button or listener on TX2
void loop() {

}

// Returns controllerStatus when requested.
void requestEvents() {
  if (DEBUG) {
    Serial.println("<-- recieved request from TX2");
    Serial.print("Returning current Status:");
    Serial.println(controllerStatus);
  }
  Wire.write(controllerStatus);
}

// Getting order from TX2
void receiveEvents(int numberOfBytes) {

  // Input begines with a line we need to ignore, use Wire.read to clear it;
  Wire.read();
  if (numberOfBytes > 1)
  {
    if (DEBUG) {
      Serial.println("--> incoming message from TX2: ");
    }
    String lineString = "";
    int line = 0;

    char command = Wire.read();

    while (0 < Wire.available()) {
      char c = Wire.read();
      lineString.concat(c);
    }

    // Does not throw exception, returns 0 if String is not convertible
    line = lineString.toInt();

    if (DEBUG) {

      Serial.print("-command: '");
      Serial.print(command);
      Serial.print("'  -With value: '");
      Serial.print(lineString);
      Serial.print("'  -Converted to int: '");
      Serial.print(line);
      Serial.println("'");
    }

    switch (command) {
      //order a for angle
      case 'a':
      case 'A':
        int newAngle = line;
        turnAngle(newAngle);

      //order f for forward
      case 'f':
      case 'F':
        controllerStatus = 'f';
        motorSpeed = line;
        break;

      //order r for reverse
      case 'r':
      case 'R':
        if (motorSpeed > 0) {
          motorSpeed = -line;
          controllerStatus = 'r';
        }
        else {
          motorSpeed = 0;
          controllerStatus = 'i';
        }
        break;

      //default:
      // Go to stop case

      // order s for stop
      case 's':
      case 'S':
        motorSpeed = 0;
        controllerStatus = 'i';
        break;
    }
    motorSetSpeed();
  }
}

void emergencyStop() {
  motorSpeed = 0;
  if (DEBUG) {
    Serial.println("Emergency button Pressed");
    Serial.println("Motor Stopped");
  } else {
    motorSetSpeed();
  }
  lockTimer(10000);
}

void motorSetSpeed() {
  // Force speed between min and max
  motorSpeed = max(motorSpeed, MINSPEED);
  motorSpeed = min(motorSpeed, MAXSPEED);

  if (0 > motorSpeed) {
    int motorRSpeed = -motorSpeed;
    if (DEBUG) {
      Serial.print("Speed: ");
      Serial.print(motorRSpeed);
      Serial.println(" reverse");
    }
    else {
      roboclaw.BackwardM1(address, motorRSpeed);
    }
  }
  else {
    if (DEBUG) {
      Serial.print("Speed: ");
      Serial.print(motorSpeed);
      Serial.println(" forward  ");
    }
    else {
      roboclaw.ForwardM1(address, motorSpeed);
    }
  }
}

void turnAngle(int angle) {
  // Change to adjust for 0
  int newAngle = angle + 90;
  newAngle = min(max(newAngle, MINANGLE), MAXANGLE);
  wheelAngle = newAngle;
  Servo1.write(newAngle);
}


// use 0 as input to use timer without setting delay
bool lockTimer(int timeToWait) {
  bool timerDone = false;

  if (timeToWait > 0) {
    lockTime = max(lockTime, (millis() + timeToWait));
  }
  if (millis() > lockTime) {
    timerDone = true;
  }
  else if (DEBUG) {
    Serial.print("locked for: ");+
    Serial.print(lockTime - millis());
    Serial.println(" ms");
  }
  return timerDone;
}


/*Previously used
  const int STOP = 0;
  const int REVERSE = 1;
  const int FORWARD = 2;
  bool buttonStates[] = {false, false, false};
  bool lastButtonStates[] = {false, false, false};

  //Setup for pins
  const int buttonPins[] = {2, 3, 4};
  void readButton(int Pin) {
  lastButtonStates[Pin] = buttonStates[Pin];
  buttonStates[Pin] = digitalRead(buttonPins[Pin]);
  }

  void loop()
  {
  readButton(STOP);
  readButton(FORWARD);
  readButton(REVERSE);

  if (HIGH == buttonStates[STOP]) {
    motorSpeed = 0;
    // Serial.println("Motor Stopped");
    motorSetSpeed();
    lockTimer(10000);
    delay(1000);


  }
  else {
    if (lockTimer) {

      if ((HIGH == buttonStates[FORWARD]) && (LOW == lastButtonStates[FORWARD]))
      {
        motorSpeed++;
        motorSetSpeed();

      }
      if ((HIGH == buttonStates[BACKWARD]) && (LOW == lastButtonStates[REVERSE]))
      {
        motorSpeed--;
        motorSetSpeed();
      }
    }
  }
  }
*/
