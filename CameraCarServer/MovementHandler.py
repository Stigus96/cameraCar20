from multiprocessing import shared_memory
from CarControl import CarControl
from MovementOrder import MovementOrder
from MovementPlanner import MovementPlanner
from OrderBuffer import OrderBuffer
import threading

import time


def millis_time():
    time_millis = int(round(time.time() * 1000))
    return time_millis


class MovementHandler:
    def __init__(self, max_buffer_size):  # Do nothing
        self.controller = CarControl()
        self.currentOrder = MovementOrder(0, 0, 0)
        self.planner = MovementPlanner()
        self.buffer = OrderBuffer(max_buffer_size)
        self.time_run = 0
        self.time_done = 0
        self.has_order = False

    def add_command_vector(self, value, angle):
        order = self.planner.move_vector(value, angle)
        return self.__add_command_order(order)

    def add_command_fwd(self):
        order = self.planner.move_forward()
        return self.__add_command_order(order)

    def add_command_rev(self):
        order = self.planner.move_reverse()
        return self.__add_command_order(order)

    def check_num_commands(self):
        return self.buffer.num_orders()

    # Returns a string review of the current order
    def get_current_status(self):
        status = "Moving at speed "
        status += self.currentOrder.get_speed()
        status += " with dir: "
        status += self.currentOrder.get_angle()
        status += " for "
        status += self.currentOrder.get_time()
        status += " more seconds"
        return status

    def threaded(self, shared_resource, atomic_lock):
        while True:
            #  Gets command if there is any written in the shared resource
            with atomic_lock:
                self.__get_command(shared_resource)

            if self.buffer.has_order():
                if self.has_order is False:

                    self.__next_order()
                    move_time = self.currentOrder.get_time()
                    speed = self.currentOrder.get_speed()
                    angle = self.currentOrder.get_angle()

                    self.__move(speed, angle)
                    self.has_order = True

                else:
                    if millis_time() > self.time_done:
                        self.has_order = False

            else:
                self.__move(0, 0)

    def __get_command(self, shared_resource):
        if shared_resource[0] is not 0:
            value = shared_resource[1]
            angle = int(shared_resource[2] - shared_resource[3])
            self.add_command_vector(value, angle)
            shared_resource[0] = 0

    def __move(self, speed, angle):
        if speed is 0:
            self.controller.stop()
        if speed > 0:
            self.controller.forward(speed, angle)
        if speed < 0:
            self.controller.reverse(speed, angle)

    def __add_command_order(self, order):
        success = False
        if self.buffer.has_space():
            self.buffer.put_order(order)
            success = True
        return success

    def __set_default_speed(self, new_speed):
        self.planner.set_speed(new_speed)

    def __next_order(self):
        if self.buffer.has_order():
            self.currentOrder = self.buffer.get_next_order()
        else:
            self.currentOrder = MovementOrder(0, 0, 0)

    def __order_done(self):
        self.has_order = False
