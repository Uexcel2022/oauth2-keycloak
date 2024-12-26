package com.uexcel.eazybank.service;

import com.uexcel.eazybank.dto.ResponseDto;
import com.uexcel.eazybank.model.ContactMessages;

import java.util.List;

public interface IContactMessageService {
    ResponseDto addContactMessage(ContactMessages contactMessages);
    List<ContactMessages> getContactMessages();
}
