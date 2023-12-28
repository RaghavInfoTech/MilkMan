package com.app.milkman.model;

import lombok.Data;

@Data
public class CustomerRegRequest {

    private String firstName;
    private String lastName;
    private String primaryPhone;
    private String secondaryPhone;
    private String emailId;
    private String dateOfBirth;
    private String authPin;
    private String address;
    private String pincode;
    private String landmark;
}
