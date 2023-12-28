package com.app.milkman.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerAuthResponse extends ParentResponse {
    private String authToken;
    private String customerName;
}
