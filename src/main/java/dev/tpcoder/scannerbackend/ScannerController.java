package dev.tpcoder.scannerbackend;

import com.fasterxml.jackson.databind.JsonNode;
import dev.tpcoder.scannerbackend.model.ScannerGenerateBody;
import dev.tpcoder.scannerbackend.util.ZxingBarcodeGenerator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/scanner")
public class ScannerController {

    @PostMapping(value = "/upca", produces = MediaType.IMAGE_PNG_VALUE)
    public Mono<ResponseEntity<byte[]>> generateUPCABarcodeImage(@RequestBody final ScannerGenerateBody body) {
        return ZxingBarcodeGenerator.generateUPCABarcodeImage(body.getBarcodeText())
                .map(bufferImage -> ResponseEntity.ok().body(bufferImage));
    }

    @PostMapping(value = "/ean13", produces = MediaType.IMAGE_PNG_VALUE)
    public Mono<ResponseEntity<byte[]>> generateEAN13BarcodeImage(@RequestBody final ScannerGenerateBody body) {
        return ZxingBarcodeGenerator.generateEAN13BarcodeImage(body.getBarcodeText())
                .map(bufferImage -> ResponseEntity.ok().body(bufferImage));
    }

    @PostMapping(value = "/code128", produces = MediaType.IMAGE_PNG_VALUE)
    public Mono<ResponseEntity<byte[]>> generateCode128BarcodeImage(@RequestBody final ScannerGenerateBody body) {
        return ZxingBarcodeGenerator.generateCode128BarcodeImage(body.getBarcodeText())
                .map(bufferImage -> ResponseEntity.ok().body(bufferImage));
    }

    @PostMapping(value = "/pdf417", produces = MediaType.IMAGE_PNG_VALUE)
    public Mono<ResponseEntity<byte[]>> generatePDF417BarcodeImage(@RequestBody final ScannerGenerateBody body) {
        return ZxingBarcodeGenerator.generatePDF417BarcodeImage(body.getBarcodeText())
                .map(bufferImage -> ResponseEntity.ok().body(bufferImage));
    }

    @PostMapping(value = "/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public Mono<ResponseEntity<byte[]>> generateQrCode(@RequestBody final ScannerGenerateBody body) {
        return ZxingBarcodeGenerator.generateQRCodeImage(body.getBarcodeText())
                .map(bufferImage -> ResponseEntity.ok().body(bufferImage));
    }

    @PostMapping(value = "/qrcode/json", produces = MediaType.IMAGE_PNG_VALUE)
    public Mono<ResponseEntity<byte[]>> generateQrCode(@RequestBody final JsonNode jsonNode) {
        return ZxingBarcodeGenerator.generateQRCodeImage(jsonNode.toString())
                .map(bufferImage -> ResponseEntity.ok().body(bufferImage));
    }
}
