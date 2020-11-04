from CarControl import CarControl
from ClientHandler import ClientHandler

def main(args):
    # TODO: Create proper main, figure out realtime management


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
