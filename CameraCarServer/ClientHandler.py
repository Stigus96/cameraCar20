import socketserver


class ClientHandeler(socketserver.BaseRequestHandler):

    def handle(self):
        self.data = self.request.recv(1024).strip()
        print("{} wrote:".format(self.client_address[0]))
        print(self.data)
        self.request.send('cool')

if __name__ == "__main__":
    HOST, PORT = "localhost", 1300

    with socketserver.TCPServer((HOST, PORT), ClientHandeler) as server:
        server.serve_forever()