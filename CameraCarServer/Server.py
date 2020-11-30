import base64
import pickle
import socket

from _thread import *
import threading
import sys
import cv2
from multiprocessing import shared_memory


class Server:

    def threaded(self, connection, shared_command, atomic_lock):
        while True:

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
                cmd_split = cmd.split(" ")
                distance = cmd_split[1]
                angle = cmd_split[2]
                print('distance: ' + distance, 'angle: ' + angle)
                # Input is a vector with value 0-1 and angle -180 to 180

                distance_byte = max(255, min(0, distance*255))
                positive_angle_byte = max(255, min(0, angle))
                negative_angle_byte = max(255, min(0, -angle))

                with atomic_lock:

                    shared_command[0] = 1
                    shared_command[1] = distance_byte
                    shared_command[2] = positive_angle_byte
                    shared_command[3] = negative_angle_byte

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