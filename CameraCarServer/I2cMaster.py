# -*- coding: utf-8 -*-
"""
Created on Tue Nov  3 16:37:01 2020
"""
# import sys
import smbus2 as smbus
import time
from datetime import datetime

I2CBus = smbus.SMBbus(1)


# Convert string to array of bytes
def __convert_string_to_bytes(src):
    converted = []
    for letter in src:
        converted.append(ord(letter))
    return converted


# Get current time
def __get_time():
    now = datetime.now()
    current_time = now.strftime("%H:%M:%S")
    return current_time


class I2cMaster(object):
    # I2C_Slave_address =  0x44;
    def __init__(self, slave_address):
        self.I2C_Slave_address = slave_address
    # Do nothing

    def send_data(self, command):
        bytes_to_send = self.convertStringToBytes(command)
        try:
            print("Trying to send: " + str(command))
            print(bytes_to_send)
            self.I2CBus.write_i2c_block_data(self.I2C_Slave_address, 0x00, bytes_to_send)
            # I2CBus.write_word_data(I2C_Slave_address, 0x00, 15, force=True)
            print(bytes_to_send)
            print("Success!")

        except IOError:
            print(self.GetTime(), "Remote I/O error - failed to write i2c data")
        except TimeoutError:
            print("i2c Connection timed out")

    def receive_data(self):
        data_received = []
        try:
            data_received = I2CBus.read_byte_data(self.I2C_Slave_address, 0x00)
            print("Received from slave: ")
            print(data_received)

        except IOError:
            print(self.getTime(), "Remote I/O error - failed to get i2c data")
            raise IOError
        except TimeoutError:
            print("i2c Connection timed out")

        return data_received
