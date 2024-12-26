package com.uexcel.eazybank.service;

import com.uexcel.eazybank.dto.ResponseDto;
import com.uexcel.eazybank.model.Notice;

import java.util.List;

public interface INoticeService {
    List<Notice> getNotices();
    ResponseDto addNotice(Notice notice);
    ResponseDto updateNotice(Notice notice);

}
