package com.ms.semicolans.sereportapi.sereportapi.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardResponse {
    private int status;
    private String message;
    private Object data;
}