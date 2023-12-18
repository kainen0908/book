package com.bookmarket.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {
    private Long rno;
    private Long bno;
    private Long mno;
    private String mid;
    private String memail;

    private int rpoint;
    private String rreply;
    private LocalDateTime regDate, updateDate;
}
