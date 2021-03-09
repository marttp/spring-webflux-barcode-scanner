package dev.tpcoder.scannerbackend.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScannerGenerateBody implements Serializable {
    private String barcodeText;
}
