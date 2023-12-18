package com.bookmarket.book.entity;

import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(value={AuditingEntityListener.class})
@Getter
public class UploadResult {
    private String fileName;
    private String uuid;
    private String folderPath;
}
