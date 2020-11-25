from Server import Server
from VideoStream import VideoStream
import socket
import threading

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
        #newthread = addr
        #newthread.start()
        threads.append(addr)

        print('Connected to :', addr[0], ':', addr[1])

        # lock acquired by client
        #print_lock.acquire()
        jpg_as_text = streamer.getVideo()
        sendVideo(conn, jpg_as_text)

        start_new_thread(server.threaded, (conn,))
        #start_new_thread(streamVideo, (conn,))

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