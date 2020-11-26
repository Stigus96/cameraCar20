from Server import Server
from VideoStream import VideoStream
import socket
import threading
from _thread import *

socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
connectedClients = {}
port = 1300
threads = []
print_lock = threading.Lock()


def main():
    socket.bind(('', port))
    print('socket binded to port', port)
    socket.listen(10)
    streamer = VideoStream()
    server = Server()

    while True:
        # establish connection with client

        print("socket is listening")
        conn, addr = socket.accept()
        connectedClients[addr] = conn
        recivecommand = threading.Thread(target=server.threaded, args=(conn,))
        threads.append(recivecommand)
        sendvideo = threading.Thread(target=sendVideo, args=(conn, streamer.getVideo()))
        threads.append(sendvideo)

        #newthread = addr
        #newthread.start()
        #threads.append(addr)

        print('Connected to :', addr[0], ':', addr[1])

        # lock acquired by client
        #print_lock.acquire()
        #sendVideo(conn, jpg_as_text)

        recivecommand.start()
        sendvideo.start()

        #start_new_thread(server.threaded, (conn,))
        #start_new_thread(sendVideo, (conn, streamer.getVideo()))

    socket.close()


def sendVideo(connection, jpg_as_text):
    try:
        connection.sendall(jpg_as_text)
        print(len(jpg_as_text))
        print('// this is a divide//')
    except TimeoutError:
        print('failed to stream video')


if __name__ == '__main__':
    main()