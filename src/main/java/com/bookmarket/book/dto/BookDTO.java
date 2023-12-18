package com.bookmarket.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long bno;
    private String btitle;
    private String bsummary;
    private int bcat;
    private String bisbn;
    private String bauthor;
    private String bpublisher;
    private String bthumb;
    private int bprice;
    private int bqty;

    @Builder.Default
    //private List<UploadResultDTO> imageDTOList = new ArrayList<>();
    private UploadResultDTO imageDTOList = new UploadResultDTO("null.jpg","xxx","2023\\00\\00");

    private double avg;
    private int replyCnt;
}
