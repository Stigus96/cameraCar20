# Class plans a movement based on input, this means converting a distance to fitting
# time based on speed.

from MovementOrder import MovementOrder

car_max_speed = 64  # Maximum speed, will limit values above to this.
initial_speed = 32  # Speed to move at

base_unit = 10;

# Movement variables:
# TODO: Test if distance per time unit is somewhat accurate, might need a scaling function.
distance_per_timeunit = 0.1  # expected distance to have moved per second * speed
accel_time = 0.05  # Time to make up for start delay


def plan_time(self, distance):
    move_time = distance / (self.currentSpeed * distance_per_timeunit) + accel_time
    return move_time


class MovementPlanner:

    def __init__(self):
        self.currentSpeed = initial_speed

    def move_vector(self, distance, direction):
        move_time = plan_time(self, distance)

    def turn_in_place(self, angle):
        distance = base_unit / 2
        move_time = plan_time(self, distance)
        order1 = MovementOrder(-self.currentSpeed, -angle, move_time)  # Negative speed and angle in reverse
        order2 = MovementOrder(self.currentSpeed, angle, move_time)

        return 2, order1, order2

    def move_forward(self):
        distance = base_unit
        angle = 0
        move_time = plan_time(self, distance)
        order1 = MovementOrder(self.currentSpeed, angle, move_time)

        return order1

    def move_reverse(self):
        distance = base_unit
        angle = 0
        move_time = plan_time(self, distance)
        order1 = MovementOrder(-self.currentSpeed, -angle, move_time)

        return order1

    def set_speed(self, new_speed):
        new_speed = max(new_speed, car_max_speed)
        new_speed = min(new_speed, 0)
        self.currentSpeed = new_speed
