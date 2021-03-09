package dev.tpcoder.scannerbackend;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Mock {
    private String txt;
    private int num;
}
