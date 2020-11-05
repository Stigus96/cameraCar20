# FIFO Queue class
from MovementOrder import MovementOrder


class OrderBuffer:

    def __init__(self, max_size):
        # Queue is of type first in - first out
        self.max_size = max_size
        self.order_list = []

    def has_order(self):
        has_order = False
        if self.order_list.__len__() > 0:
            has_order = True
        return has_order

    def num_orders(self):
        number_of_orders = self.order_list.__len__()
        return number_of_orders

    def has_space(self):
        has_space = False
        if self.order_list.__len__() < self.max_size:
            has_space = True
        return has_space

    def num_space(self):
        number_of_space = self.max_size - self.order_list.__len__
        return number_of_space

    def get_next_order(self):
        order = None
        if self.has_order():
            order = self.order_list.pop(0)
        return order

    def put_order(self, order):
        is_order = isinstance(order, MovementOrder)
        if is_order and self.has_space():
            self.order_list.append(order)







