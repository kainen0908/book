package com.bookmarket.book.dto;

import lombok.Data;

@Data
public class MemberJoinDTO {

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
