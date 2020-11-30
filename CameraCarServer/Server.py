import base64
import pickle
import socket

from _thread import *
import threading
import sys
import cv2
from multiprocessing import shared_memory



class Server:

    def threaded(self, connection, result):
        while True:
            result[0] = "empty"
            try:
                data = connection.recv(100)
            except:
                print("cannot receive data, client has probably forcibly closed connection")
                break
            cmd = data.decode("utf-8")
            print('received ' + cmd)
            cmd = cmd.strip()
            if cmd.startswith("VECTOR"):
                # incoming string on form "VECTOR speed angle"
                cmdSplit = cmd.split(" ")
                distance = cmdSplit[1]
                angle = cmdSplit[2]
                print('distance: ' + distance, 'angle: ' + angle)
                result[0] = distance + " " + angle



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

        elif cmd == "SENSOR_OFF":
            # turn sensor off
            print('turning sensor off')

        elif cmd == "SENSOR_ON":
            # turn sensor on
            print('turning sensor on')

        else:
            print('command not supported?')

    def __send(self, msg, connection):
        try:
            connection.sendall(bytes(msg, 'utf-8'))
        except TimeoutError:
            print('failed to send msg')