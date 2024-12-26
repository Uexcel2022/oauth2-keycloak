package com.uexcel.eazybank.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCustomerDto{
    @Pattern(regexp = "[a-zA-Z]{3,} [a-zA-Z]{3,} ?[a-zA-Z]*",message =
            "Expecting maximum of 3 names consisting of (at least 3) letters only.")
    @NotNull(message = "Name is a required field")
    private String name;
    @NotNull(message = "Mobile number is a required field")
    @Pattern(regexp = "0[7-9][01][1-9]{8}",message = "Invalid mobile number.")
    private String mobileNumber;
    @Email(message = "Enter a valid email address")
    @NotNull(message = "Email address is a required field")
    private String email;
}
