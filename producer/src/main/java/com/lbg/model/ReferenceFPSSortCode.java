package com.lbg.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ReferenceFPSSortCode {

    private String sortCode;
    private Boolean fpsEnabled;
}
