import socket
import sys


class Server:
    serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    server_address = ('', 1300)
    print('starting up on {} port {}'.format(*server_address))
    serversocket.bind(server_address)
    serversocket.listen(1)

    while True:
        print('waiting for a connection')
        connection, client_address = serversocket.accept()
        try:
            print('connection from', client_address)

            while True:
                data = connection.recv(16)
                cmd = data.decode("utf-8")
                print('received ' + cmd)
                cmd = cmd.strip()

                if cmd == "START":
                    print('starting stuff')

                    connection.sendall(bytes("starting stuff", 'utf-8'))
                else:
                    print('command not supported?')
                    connection.sendall(bytes('command not supported?', 'utf-8'))


        finally:
            connection.close()
