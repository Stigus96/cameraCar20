from MovementHandler import MovementHandler
import time
handler = MovementHandler(16)


def main():
    while True:
        handler.add_command_vector(0, 64)
        time.sleep(5)
        handler.add_command_vector(0, 0)
        time.sleep(5)
        handler.add_command_vector(0, -64)
        time.sleep(5)
        handler.add_command_vector(0, 0)
        time.sleep(5)


if __name__ == "__main__":
    main()
