package com.bookmarket.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private Long mno;

    private String mid;
    private String mpw;
    private String mname;
    private String mbirth;
    private String memail;
    private String mtel;
    private String maddr;
    private String mlev;
    private boolean msocial;
}
