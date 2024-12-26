package com.uexcel.eazybank.controller;

import com.uexcel.eazybank.dto.ResponseDto;
import com.uexcel.eazybank.model.ContactMessages;
import com.uexcel.eazybank.service.IContactMessageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class ContactController {
    private final IContactMessageService contactMessageService;
    @GetMapping("/contact")
    public ResponseEntity<List<ContactMessages>> getContactDetails() {
        List<ContactMessages> contactMessages =
                contactMessageService.getContactMessages();
        return ResponseEntity.ok(contactMessages);
    }

    @PostMapping("/contact")
    public ResponseEntity<ResponseDto> addContact(@Valid  @RequestBody ContactMessages contactMessage) {
        ResponseDto  res = contactMessageService.addContactMessage(contactMessage);
        return ResponseEntity.status(res.getStatus()).body(res);
    }
}
