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
                try:
                    data = connection.recv(16)
                except:
                    print("cannot recive data, client has probably forcibly closed connection")
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
                    #turn sensor off
                    print('turning sensor off')

                if cmd == "SENSOR_ON":
                     #turn sensor on
                    print('turning sensor on')

                else:
                    print('command not supported?')
                    connection.sendall(bytes('command not supported', 'utf-8'))

        finally:
            connection.close()
