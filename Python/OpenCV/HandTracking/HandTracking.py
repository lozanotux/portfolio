import cv2
import mediapipe
import time

# Here we are capturing images from webcam (Device ID: 0)
cap = cv2.VideoCapture(0)

mp_hands = mediapipe.solutions.hands
hands = mp_hands.Hands()
mp_draw = mediapipe.solutions.drawing_utils

previous_time = 0
current_time = 0

while True:
    success, img = cap.read()
    img_rgb = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    results = hands.process(img_rgb)

    if results.multi_hand_landmarks:
        # handLms has information about the hands data
        for handLms in results.multi_hand_landmarks:
            for dot_id, lm in enumerate(handLms.landmark):
                height, width, channels = img.shape
                current_x, current_y = int(lm.x * width), int(lm.y * height)
                if dot_id == 0:
                    # values: image, coordinates, size, color, style
                    cv2.circle(img, (current_x, current_y), 25, (255, 0, 255), cv2.FILLED)
            mp_draw.draw_landmarks(img, handLms, mp_hands.HAND_CONNECTIONS)

    # Printing FPS
    current_time = time.time()
    fps = 1 / (current_time - previous_time)
    previous_time = current_time
    # params: image, value, coordinates, font_family, size, color, thickness
    cv2.putText(
        img, str(int(fps)), (10, 70),
        cv2.FONT_HERSHEY_PLAIN, 3, (255, 0, 255), 3
    )

    cv2.imshow("Hand Tracking", img)
    cv2.waitKey(1)
