package com.bookmarket.book.service;

import com.bookmarket.book.dto.MemberDTO;
import com.bookmarket.book.dto.MemberJoinDTO;
import com.bookmarket.book.entity.Member;

public interface MemberService {
    Long register(MemberDTO memberDTO);
    Member autoRegister(); // 자동 회원 가입

    default Member dtoToEntity(MemberDTO memberDTO) {
        Member member = Member.builder()
                .mno(memberDTO.getMno())
                .mid(memberDTO.getMid())
                .mname(memberDTO.getMname())
                .maddr(memberDTO.getMaddr())
                .mpw(memberDTO.getMpw())
                .mbirth(memberDTO.getMbirth())
                .memail(memberDTO.getMemail())
                .mtel(memberDTO.getMtel())
                .mlev("0")
                .msocial(false)
                .build();
        return member;
    }

    default MemberDTO entityToDTO(Member member) {
        MemberDTO memberDTO = MemberDTO.builder()
                .mno(member.getMno())
                .maddr(member.getMaddr())
                .mbirth(member.getMbirth())
                .memail(member.getMemail())
                .mid(member.getMid())
                .mlev(member.getMlev())
                .mname(member.getMname())
                .mpw(member.getMpw())
                .msocial(member.isMsocial())
                .mtel(member.getMtel())
                .build();
        return memberDTO;
    }

    static class MidExistException extends Exception{}

    void join(MemberJoinDTO memberJoinDTO) throws MidExistException;
}
