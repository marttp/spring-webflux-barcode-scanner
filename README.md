# Spring WebFlux Barcode scanner generator
Side project to POC how to use ZXing with Spring WebFlux + Unit Test

## Standard that this project handle
- UPC_A
- EAN_13
- CODE_128
- PDF_417
- QR Code

---

## How to use
The project represents how to generate barcode standard via url path

Base Path: /scanner
- UPC_A: /upca
- EAN_13: /ean13
- CODE_128: /code128
- PDF_417: /pdf417
- QR Code: /qrcode
- QR Code [Recieved JSON String]: /qrcode/json

Result of each endpoint will produce MediaType.IMAGE_PNG_VALUE

---

## สำหรับบุคคลชาวไทย
สำหรับบุคคลที่เห็นว่า Repository นี้เป็นประโยชน์ หรือสามารถนำไปต่อยอดได้  
ถ้าอยากจะสนับสนุน สามารถ Donate ได้ผ่านทางช่องทางนี้เลยครับ

### Prompt Pay QR
<img alt="Thanaphoom Babparn Promptpay QR Code cost 50 BAHT" src="./Thanaphoom-Babparn-PromptPayQR.jpg" width="400"/>
