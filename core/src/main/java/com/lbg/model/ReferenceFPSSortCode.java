package com.lbg.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReferenceFPSSortCode {

    private String sortCode;
    private Boolean fpsEnabled;
}
