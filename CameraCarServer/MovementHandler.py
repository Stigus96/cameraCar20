# TODO Create table of requested commands
# TODO make time handling
# TODO multithread this function with semaphore

from CarControl import CarControl
from MovementOrder import MovementOrder
from MovementPlanner import MovementPlanner
from OrderBuffer import OrderBuffer
from threading import Timer as ThreadTimer


class MovementHandler:
    def __init__(self, max_buffer_size):  # Do nothing
        self.controller = CarControl()
        self.currentOrder = MovementOrder(0, 0, 0)
        self.planner = MovementPlanner()
        self.buffer = OrderBuffer(max_buffer_size)
        self.time_run = 0
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

    def thread_function(self):
        while True:
            if self.has_order is False:
                self.__next_order()

                if self.currentOrder.get_time() is not 0:

                    time = self.currentOrder.get_time()
                    speed = self.currentOrder.get_speed()
                    angle = self.currentOrder.get_angle()

                    self.__move(speed, angle)
                    self.has_order = True

                    timer = ThreadTimer(time, self.__order_done)
                    timer.start()

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

    def __next_order(self):
        if self.buffer.has_order():
            self.currentOrder = self.buffer.get_next_order()
        else:
            self.currentOrder = MovementOrder(0, 0, 0)

    def __order_done(self):
        self.has_order = False
