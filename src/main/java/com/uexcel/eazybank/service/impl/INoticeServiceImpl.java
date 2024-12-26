package com.uexcel.eazybank.service.impl;

import com.uexcel.eazybank.dto.ResponseDto;
import com.uexcel.eazybank.exceptionhandling.AppExceptionHandler;
import com.uexcel.eazybank.model.Notice;
import com.uexcel.eazybank.persistence.NoticeRepository;
import com.uexcel.eazybank.service.INoticeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class INoticeServiceImpl implements INoticeService {
    private final NoticeRepository noticeRepository;
    @Override
    public List<Notice> getNotices() {
        return noticeRepository.findAll();
    }

    @Override
    public ResponseDto addNotice(Notice notice) {
       Notice savedNotice = noticeRepository.save(notice);
       if (savedNotice.getId() == null) {
           throw new AppExceptionHandler(
                   HttpStatus.EXPECTATION_FAILED.value(), "Notice could not be created");
       }
       return new ResponseDto(
               HttpStatus.CREATED.value(), HttpStatus.CREATED,"Notice created successfully."
       );
    }

    @Override
    public ResponseDto updateNotice(Notice notice) {
        if(notice.getId() == null){
            throw new AppExceptionHandler(
                    HttpStatus.BAD_REQUEST.value(), "Notice could not be updated without id");
        }
        noticeRepository.save(notice);
        return new ResponseDto(
                HttpStatus.OK.value(), HttpStatus.OK,"Notice updated successfully."
        );
    }
}
