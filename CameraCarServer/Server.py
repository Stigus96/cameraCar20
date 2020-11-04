import socket

from _thread import *
import threading
import sys

print_lock = threading.Lock()


def threaded(connection):
    while True:
        try:
            data = connection.recv(16)
        except:
            print("cannot receive data, client has probably forcibly closed connection")
            break
        if not data: break
        cmd = data.decode("utf-8")
        print('received ' + cmd)
        cmd = cmd.strip()

        if cmd == "START":
            print('starting stuff')

            connection.sendall(bytes("starting stuff", 'utf-8'))
        if cmd == "VECTOR":
            # incoming string on form "VECTOR speed angle"
            cmdSplit = cmd.split(" ")
            speed = cmdSplit[1]
            angle = cmdSplit[2]
            connection.sendall(bytes('vectors successfully received', 'utf-8'))
        if cmd == "SENSOR_OFF":
            # turn sensor off
            print('turning sensor off')

        if cmd == "SENSOR_ON":
            # turn sensor on
            print('turning sensor on')

        else:
            print('command not supported?')
            connection.sendall(bytes('command not supported', 'utf-8'))

    connection.close()


def main():
    port = 1300
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind(('', 1300))
    print('socket binded to port', port)

    s.listen(5)
    print("socket is listening")

    while True:
        # establish connection with client
        c, addr = s.accept()

        # lock acquired by client
        print_lock.acquire()
        print('Connected to :', addr[0], ':', addr[1])

        # Start a new thread and return its identifier
        start_new_thread(threaded, (c,))
    s.close()


if __name__ == '__main__':
    main()

# serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# server_address = ('', 1300)
# print('starting up on {} port {}'.format(*server_address))
# serversocket.bind(server_address)
# serversocket.listen(1)
