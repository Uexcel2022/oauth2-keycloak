package com.uexcel.eazybank.controller;

import com.uexcel.eazybank.dto.ResponseDto;
import com.uexcel.eazybank.model.Notice;
import com.uexcel.eazybank.persistence.NoticeRepository;
import com.uexcel.eazybank.service.INoticeService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class NoticeController {
    private INoticeService iNoticeService;
    @GetMapping("/notices")
    public ResponseEntity<List<Notice>> getNotices() {
        List<Notice> notice = iNoticeService.getNotices();
        return ResponseEntity.ok(notice);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-notice")
    public ResponseEntity<ResponseDto> addNotice(@RequestBody Notice notice) {
        ResponseDto res = iNoticeService.addNotice(notice);
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-notice")
    public ResponseEntity<ResponseDto> updateNotice(@RequestBody Notice notice) {
        ResponseDto res = iNoticeService.updateNotice(notice);
        return ResponseEntity.status(res.getStatus()).body(res);
    }
}
