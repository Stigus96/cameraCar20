import socket

from _thread import *
import threading
import sys

print_lock = threading.Lock()


def threaded(connection):
    while True:
        try:
            data = connection.recv(16)
        except TimeoutError:
            print("cannot receive data, client has probably forcibly closed connection")
            break
        if not data: break
        cmd = data.decode("utf-8")
        print('received ' + cmd)
        cmd = cmd.strip()

        if cmd == "START":
            print('starting stuff')

            connection.sendall(bytes("starting stuff", 'utf-8'))
        elif cmd == "VECTOR":
            # incoming string on form "VECTOR speed angle"
            cmdSplit = cmd.split(" ")
            speed = cmdSplit[1]
            angle = cmdSplit[2]
            connection.sendall(bytes('vectors successfully received', 'utf-8'))
        elif cmd == "SENSOR_OFF":
            # turn sensor off
            print('turning sensor off')
            connection.sendall(bytes('sensor turned off', 'utf-8'))

        elif cmd == "SENSOR_ON":
            # turn sensor on
            print('turning sensor on')
            connection.sendall(bytes('sensor turned on', 'utf-8'))

        else:
            print('command not supported?')
            connection.sendall(bytes('command not supported', 'utf-8'))

    connection.close()


# serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# server_address = ('', 1300)
# print('starting up on {} port {}'.format(*server_address))
# serversocket.bind(server_address)
# serversocket.listen(1)
