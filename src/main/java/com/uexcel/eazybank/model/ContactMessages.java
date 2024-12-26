package com.uexcel.eazybank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Getter @Setter
public class ContactMessages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "[a-zA-Z]{3,} [a-zA-Z]{3,} ?[a-zA-Z]*",message =
            "Expecting maximum of 3 names consisting of (at least 3) letters only.")
    @NotNull(message = "Name is a required field")
    private String name;
    @Email(message = "Enter a valid email address")
    @NotNull(message = "Email address is a required field")
    private String email;
    @NotEmpty(message = "Must not be empty")
    @NotEmpty (message = "Must not be null")
    private String subject;
    @NotEmpty(message = "Must not be empty")
    @NotNull(message = "Must not be null")
    private String message;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDate createDt;
}
