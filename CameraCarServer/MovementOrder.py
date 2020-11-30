# Single order to be handled
# A new instance is to be created for whenever a order is sent
# The instance is to be deleted when a order has executed


class MovementOrder:

    def __init__(self, speed, angle, time):
        self.speed = speed
        self.angle = angle
        self.time = time

    def get_speed(self):
        return self.speed

    def get_angle(self):
        return self.angle

    def get_time(self):
        return self.time

    def remove_time(self, time):
        self.time = min(self.time-time, 0)
