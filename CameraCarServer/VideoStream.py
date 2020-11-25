import base64
import cv2

cap = cv2.VideoCapture(0)

class VideoStream:

    def getVideo(self):
        while True:
            grabbed, frame = cap.read()
            dim = (640, 480)
            frame = cv2.resize(frame, dim)
            encode_param = [int(cv2.IMWRITE_JPEG_QUALITY), 70]
            encoded, buffer = cv2.imencode('.jpg', frame, encode_param)
            jpg_as_text = base64.b64encode(buffer)
            return jpg_as_text



