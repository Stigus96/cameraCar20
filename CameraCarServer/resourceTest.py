import threading
import time
from multiprocessing import shared_memory

memory1 = shared_memory.SharedMemory(create=True, size=5)
test = memory1.buf
test[0] = 0
threads = []

def shared_counter(shared_resource):
    while True:
        try:
            print(len(shared_resource))
            shared_resource[0] = shared_resource[0] + 10
            print("new value is", shared_resource[0])

        except:
            print("failed writing for some reason")
        time.sleep(1)


def shared_printer(shared_resource):
    while True:
        try:
            print(shared_resource[0])
        except:
            print("failed reading for some reason")
        time.sleep(0.4)


def main():
    count = 0
    test1 = threading.Thread(target=shared_counter, args=(test,))
    test2 = threading.Thread(target=shared_printer, args=(test,))
    test1.start()
    test2.start()

    #    threads.append(test1)
    #    threads.append(test2)

    while True:
        # do nothing
        count = count + 1
        # time.sleep(0.1)


if __name__ == '__main__':
    main()
