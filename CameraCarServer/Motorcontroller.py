# -*- coding: utf-8 -*-
"""
Created on Tue Nov  3 16:37:01 2020
"""
# Library
# import sys
import smbus2 as smbus
import time
from datetime import datetime


I2C_Slave_address = 0
I2CBus = smbus.SMBbus(1)

class motorController():

    ## I2C_Slave_address =  0x44;
    def __init__(self, slaveAddress):
        self.I2C_Slave_address = slaveAddress
    # Do nothing

    # Get current time
    def GetTime():   
            now = datetime.now()
            current_time = now.strftime("%H:%M:%S")
            return current_time


    # Convert string to array of bytes
    def convertStringToBytes(self,src):
        converted = []
        for letter in src:
            converted.append(ord(letter))
        return converted

        
    def sendData(self,command):
        bytes_to_send = self.convertStringToBytes(command)
        try:
            print("Trying to send: " + str(command))
            print(bytes_to_send)
            self.I2CBus.write_i2c_block_data(I2C_Slave_address,
                                             0x00, bytes_to_send)
            # I2CBus.write_word_data(I2C_Slave_address, 0x00, 15, force=True)
            print(bytes_to_send)
            print("Success!")

        except IOError:
            print(self.GetTime(), "Remote I/O error")
        except TimeoutError:
            print("Connection timed out")

        # Create i2cbu
        
    def receiveData(self):
        dataReceived = []
        try:
            dataReceived = I2CBus.read_byte_data(I2C_Slave_address,0x00)
            print("Received from slave: ")
            print(dataReceived)
        except:
            print(self.getTime(), "Remote I/O error")
            time.sleep(2)
            
        return dataReceived
