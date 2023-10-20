import cv2
import serial
import numpy as np
import pytesseract
import re
import time

# Inicializa la comunicación con Arduino (ajusta el puerto y la velocidad de baudios según corresponda)
esp32 = serial.Serial('COM6', 115200)

# Inicializa la cámara
cap = cv2.VideoCapture(0)

# Tiempo mínimo entre detecciones para evitar envío excesivo de señales
tiempo_entre_detecciones = 8  #8 segundos
ultimo_tiempo_deteccion = 0

while True:
    ret, frame = cap.read()
    if not ret:
        continue

    # Utiliza un clasificador de matrículas (debes entrenar uno o usar uno preentrenado)
    # Aquí un ejemplo simple utilizando un detector de bordes Canny
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    edges = cv2.Canny(gray, 50, 150)

    # Detecta contornos en la imagen
    contours, _ = cv2.findContours(edges, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    detected_plate = None

    for contour in contours:
        # Filtra los contornos que tengan un área mínima para evitar falsos positivos
        if cv2.contourArea(contour) > 1000:
            x, y, w, h = cv2.boundingRect(contour)
            cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 2)

            # Recorta la región de la matrícula
            plate_region = gray[y:y + h, x:x + w]

            # Realiza la detección de texto en la región de la matrícula
            plate_text = pytesseract.image_to_string(plate_region, config='--psm 6')

            # Verifica si el texto tiene el formato deseado (4 números, 1 espacio, 3 letras)
            if re.match(r'^\d{4}\s[A-Z]{3}$', plate_text.strip()):
                detected_plate = plate_text.strip()

    if detected_plate:
        tiempo_actual = time.time()
        if tiempo_actual - ultimo_tiempo_deteccion >= tiempo_entre_detecciones:
            print("Matrícula detectada:", detected_plate)

            # Envía la matricula a Arduino
            esp32.write(detected_plate.encode() + b'\n') # Agrega '\n' para indicar el final del comando

            # Actualiza el tiempo de última detección
            ultimo_tiempo_deteccion = tiempo_actual

    cv2.imshow("License Plate Detection", frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()