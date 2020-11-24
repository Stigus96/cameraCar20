import base64
import pickle
import socket

from _thread import *
import threading
import sys
import cv2

print_lock = threading.Lock()
socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
threads = []
connectedClients = {}
port = 1300
cap = cv2.VideoCapture(0)


def main():
    socket.bind(('', port))
    print('socket binded to port', port)
    socket.listen(10)

    while True:
        # establish connection with client

        print("socket is listening")
        conn, addr = socket.accept()
        connectedClients[addr] = conn
        #newthread = addr
        #newthread.start()
        threads.append(addr)

        print('Connected to :', addr[0], ':', addr[1])

        # lock acquired by client
        #print_lock.acquire()
        streamVideo(conn)
        #start_new_thread(threaded, (conn,))
        #start_new_thread(streamVideo, (conn,))

    socket.close()


def threaded(connection):
    while True:
        try:
            data = connection.recv(100)
        except:
            print("cannot receive data, client has probably forcibly closed connection")
            break
        cmd = data.decode("utf-8")
        print('received ' + cmd)
        cmd = cmd.strip()
        handleCommands(cmd, connection)

    connection.close()


def streamVideo(connection):
    while True:
        grabbed, frame = cap.read()
        dim = (640, 480)
        frame = cv2.resize(frame, dim)
        encode_param = [int(cv2.IMWRITE_JPEG_QUALITY), 70]
        encoded, buffer = cv2.imencode('.jpg', frame, encode_param)
        jpg_as_text = base64.b64encode(buffer)
        try:
            connection.sendall(jpg_as_text)
            print(len(jpg_as_text))
            print('// this is a divide//')
        except TimeoutError:
            print('failed to stream video')


def handleCommands(cmd, connection):
    if cmd == "START":
        print('starting stuff')

    elif cmd.startswith("VECTOR"):
        # incoming string on form "VECTOR speed angle"
        cmdSplit = cmd.split(" ")
        speed = cmdSplit[1]
        angle = cmdSplit[2]
        print('Speed: ' + speed, 'angle: ' + angle)
        __send('vectors successfully received', connection)


    elif cmd == "SENSOR_OFF":
        # turn sensor off
        print('turning sensor off')
        __send('sensor turned off', connection)

    elif cmd == "SENSOR_ON":
        # turn sensor on
        print('turning sensor on')
        __send('sensor turned on', connection)

    else:
        print('command not supported?')
        __send('command not supported', connection)


def __send(msg, connection):
    try:
        connection.sendall(bytes(msg, 'utf-8'))
    except TimeoutError:
        print('failed to send msg')


if __name__ == '__main__':
    main()

# serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# server_address = ('', 1300)
# print('starting up on {} port {}'.format(*server_address))
# serversocket.bind(server_address)
# serversocket.listen(1)
