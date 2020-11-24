# -*- coding: utf-8 -*-
"""
Created on Tue Nov  3 16:37:01 2020
"""
# import sys
from MotorController import MotorController
from MovementOrder import MovementOrder
i2c_slave_address = 0x44


class CarControl:

    def __init__(self):  # Do nothing

        value = 1
        self.controller1 = MotorController(i2c_slave_address)

    def forward(self, speed, angle):
        try:
            self.__send_message("a", angle)
            self.__send_message("f", speed)
        except IOError:
            raise Exception("IO error - unable to send 'forward' command to controller")

    def reverse(self, speed, angle):
        try:
            self.__send_message("a", angle)
            self.__send_message("r", speed)

        except IOError:
            raise Exception("IO error - unable to send 'backward' command to controller")

    def stop(self):
        try:
            self.__send_message("s", 0)
        except IOError:
            raise Exception("IO error - unable to send 'stop' command to controller")

    def __send_message(self, command, value):
        msg = command+value
        self.controller1.send_data(msg)
