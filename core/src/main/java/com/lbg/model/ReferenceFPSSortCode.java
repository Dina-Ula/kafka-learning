package com.lbg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReferenceFPSSortCode {

    private String sortCode;
    private Boolean fpsEnabled;
}
