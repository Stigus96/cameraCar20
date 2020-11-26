# Class plans a movement based on input, this means converting a distance to fitting
# time based on speed.

from MovementOrder import MovementOrder

car_max_speed = 64  # Maximum speed, will limit values above to this.
car_min_speed = 10  # Minimum speed, will limit values below to this.
initial_speed = 32  # Default speed to move at.
base_unit = 100   # Distance to move from a given command.


# Movement variables:
# TODO: Test if distance per time unit is somewhat accurate, might need a scaling function.
speed_multiplier = 1  # For calibrating speed from integer to actual value
accel_time = 0.05  # Time to make up for start delay


def plan_time(distance, speed):
    speed = abs(speed)
    move_time = distance / (speed * speed_multiplier) + accel_time
    move_time = max(move_time, 0)
    return move_time


def move_vector_spd(length, angle, custom_speed):
    move_time = plan_time(length, custom_speed)
    if angle > 180:
        custom_speed = -custom_speed
        angle = 360 - angle
    order = MovementOrder(custom_speed, angle, move_time)
    return order


class MovementPlanner:

    def __init__(self):
        self.default_speed = initial_speed

    def move_vector(self, length, angle):
        speed = self.default_speed
        move_time = plan_time(length, speed)
        if angle > 180:
            speed = -speed
            angle = 360 - angle
        order = MovementOrder(speed, angle, move_time)
        return order

    def v_turn(self, angle):
        distance = base_unit / 2
        move_time = plan_time(self.default_speed, distance)
        order1 = MovementOrder(-self.default_speed, -angle, move_time)  # Negative speed and angle in reverse
        order2 = MovementOrder(self.default_speed, angle, move_time)
        return order1, order2

    def turn_in_place_l(self):
        distance = base_unit/2
        angle = 60
        speed = self.default_speed
        move_time = plan_time(speed, distance)
        order1 = MovementOrder(-speed, -angle)
        order2 = MovementOrder(speed, angle)
        order3 = MovementOrder(-speed, -angle)
        return order1, order2, order3
    
    def move_forward(self):
        distance = base_unit
        angle = 0
        speed = self.default_speed
        move_time = plan_time(speed, distance)
        order1 = MovementOrder(speed, angle, move_time)
        return order1

    def move_reverse(self):
        distance = base_unit
        speed = - self.default_speed
        angle = 0
        move_time = plan_time(speed, distance)
        order1 = MovementOrder(-speed, -angle, move_time)
        return order1

    def set_speed(self, new_speed):
        new_speed = max(new_speed, car_max_speed)
        new_speed = min(new_speed, 0)
        self.default_speed = new_speed
