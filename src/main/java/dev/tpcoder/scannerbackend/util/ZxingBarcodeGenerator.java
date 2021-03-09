package dev.tpcoder.scannerbackend.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.UPCAWriter;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
@Slf4j
public class ZxingBarcodeGenerator {

    private ZxingBarcodeGenerator() {
    }

    public static Mono<byte[]> generateUPCABarcodeImage(String barcodeText) {
        return generateByteArrayByFormat(barcodeText, BarcodeFormat.UPC_A);
    }

    public static Mono<byte[]> generateEAN13BarcodeImage(String barcodeText) {
        return generateByteArrayByFormat(barcodeText, BarcodeFormat.EAN_13);
    }

    public static Mono<byte[]> generateCode128BarcodeImage(String barcodeText) {
        return generateByteArrayByFormat(barcodeText, BarcodeFormat.CODE_128);
    }

    public static Mono<byte[]> generatePDF417BarcodeImage(String barcodeText) {
        return generateByteArrayByFormat(barcodeText, BarcodeFormat.PDF_417);
    }

    public static Mono<byte[]> generateQRCodeImage(String barcodeText) {
        return generateByteArrayByFormat(barcodeText, BarcodeFormat.QR_CODE);
    }

    private static Mono<byte[]> generateByteArrayByFormat(String barcodeText, BarcodeFormat barcodeFormat) {
        Writer writer;
        switch (barcodeFormat) {
            case UPC_A:
                writer = new UPCAWriter();
                break;
            case EAN_13:
                writer = new EAN13Writer();
                break;
            case CODE_128:
                writer = new Code128Writer();
                break;
            case PDF_417:
                writer = new PDF417Writer();
                break;
            default:
                writer = new QRCodeWriter();
                break;
        }
        return generateByteArray(barcodeText, writer, barcodeFormat);
    }

    private static Mono<byte[]> generateByteArray(String barcodeText, Writer writer, BarcodeFormat barcodeFormat) {
        return Mono.create(sink -> {
            log.info("Will generate image  text=[{}], width=[{}], height=[{}]", barcodeText, 200, 200);
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                BitMatrix bitMatrix = writer.encode(barcodeText, barcodeFormat, 200, 200);
                MatrixToImageWriter.writeToStream(bitMatrix, MediaType.IMAGE_PNG.getSubtype(), byteArrayOutputStream, new MatrixToImageConfig());
                sink.success(byteArrayOutputStream.toByteArray());
            } catch (IOException | WriterException ex) {
                log.error("exception: ", ex);
                sink.error(ex);
            }
        });
    }

}
