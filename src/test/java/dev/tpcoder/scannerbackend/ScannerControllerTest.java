package dev.tpcoder.scannerbackend;

import dev.tpcoder.scannerbackend.model.ScannerGenerateBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@DirtiesContext
class ScannerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    private final String baseEndpoint = "/scanner";
    private ScannerGenerateBody scannerGenerateBody;
    private Mock mockData;

    @BeforeEach
    void setUpTest() {
        scannerGenerateBody = new ScannerGenerateBody();
        mockData = new Mock();
        scannerGenerateBody.setBarcodeText("12312321211");
    }

    @AfterEach
    void tearDown() {
        scannerGenerateBody = null;
        mockData = null;
    }


    @Test
    void generateUPCABarcodeImage_success_returnSuccess() {
        String url = baseEndpoint + "/upca";
        webTestClient.post()
                .uri(url)
                .body(Mono.just(scannerGenerateBody), ScannerGenerateBody.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void generateEAN13BarcodeImage_success_returnSuccess() {
        String url = baseEndpoint + "/ean13";
        scannerGenerateBody.setBarcodeText(scannerGenerateBody.getBarcodeText() + "1");
        webTestClient.post()
                .uri(url)
                .body(Mono.just(scannerGenerateBody), ScannerGenerateBody.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void generateEAN13BarcodeImage_error_returnError() {
        String url = baseEndpoint + "/ean13";
        webTestClient.post()
                .uri(url)
                .body(Mono.just(scannerGenerateBody), ScannerGenerateBody.class)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/code128", "/pdf417", "/qrcode"})
    void generateBarcode_success_returnSuccess(String suffix) {
        String url = baseEndpoint + suffix;
        webTestClient.post()
                .uri(url)
                .body(Mono.just(scannerGenerateBody), ScannerGenerateBody.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Generate QR Code with JSON - Success")
    void generateQrCodeWithJson_success_returnSuccess() {
        String url = baseEndpoint + "/qrcode/json";
        mockData.setTxt("Test").setNum(1);
        webTestClient.post()
                .uri(url)
                .body(Mono.just(mockData), Mock.class)
                .exchange()
                .expectStatus().isOk();
    }

}
