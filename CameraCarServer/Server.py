import base64
import pickle
import socket

from _thread import *
import threading
import sys
import cv2


class Server:

    def threaded(self, connection):
        while True:
            try:
                data = connection.recv(100)
            except:
                print("cannot receive data, client has probably forcibly closed connection")
                break
            cmd = data.decode("utf-8")
            print('received ' + cmd)
            cmd = cmd.strip()
            self.handleCommands(cmd, connection)

        connection.close()

    def handleCommands(self, cmd, connection):
        if cmd == "START":
            print('starting stuff')

        elif cmd.startswith("VECTOR"):
            # incoming string on form "VECTOR speed angle"
            cmdSplit = cmd.split(" ")
            speed = cmdSplit[1]
            angle = cmdSplit[2]
            print('Speed: ' + speed, 'angle: ' + angle)
            self.__send('vectors successfully received', connection)

        elif cmd == "SENSOR_OFF":
            # turn sensor off
            print('turning sensor off')
            self.__send('sensor turned off', connection)

        elif cmd == "SENSOR_ON":
            # turn sensor on
            print('turning sensor on')
            self.__send('sensor turned on', connection)

        else:
            print('command not supported?')
            self.__send('command not supported', connection)

    def __send(self, msg, connection):
        try:
            connection.sendall(bytes(msg, 'utf-8'))
        except TimeoutError:
            print('failed to send msg')
