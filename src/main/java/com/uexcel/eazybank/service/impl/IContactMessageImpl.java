package com.uexcel.eazybank.service.impl;

import com.uexcel.eazybank.dto.ResponseDto;
import com.uexcel.eazybank.exceptionhandling.AppExceptionHandler;
import com.uexcel.eazybank.model.ContactMessages;
import com.uexcel.eazybank.persistence.ContactMessageRepository;
import com.uexcel.eazybank.service.IContactMessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@AllArgsConstructor
public class IContactMessageImpl implements IContactMessageService {
    private final ContactMessageRepository contactMessageRepository;
    @Override
    public ResponseDto addContactMessage(ContactMessages contactMessages) {
        contactMessages.setCreateDt(LocalDate.now());
      ContactMessages cMsg = contactMessageRepository.save(contactMessages);

        if(cMsg.getId()>0){
            return new ResponseDto(HttpStatus.CREATED.value(),
                    HttpStatus.CREATED,"Message sent successfully.");
        }
        throw new AppExceptionHandler(
                HttpStatus.EXPECTATION_FAILED.value(),"Message send failed.");
    }

    @Override
    public List<ContactMessages> getContactMessages() {
        return (List<ContactMessages>) contactMessageRepository.findAll();
    }
}
