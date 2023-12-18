package com.bookmarket.book.service;

import com.bookmarket.book.dto.MemberDTO;
import com.bookmarket.book.dto.MemberJoinDTO;
import com.bookmarket.book.entity.Member;
import com.bookmarket.book.entity.MemberRole;
import com.bookmarket.book.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long register(MemberDTO memberDTO) {

        Member member = dtoToEntity(memberDTO);
        memberRepository.save(member);
        return member.getMno();

    }

    @Override
    public void join(MemberJoinDTO memberJoinDTO) throws MidExistException{

        String mid = memberJoinDTO.getMid();
        boolean exist = memberRepository.existsById(mid);

        if(exist){
            throw new MidExistException();
        }

        Member member = modelMapper.map(memberJoinDTO, Member.class);
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getMpw()));
        member.addRole(MemberRole.USER);

        log.info("=================================");
        log.info(member);
        log.info(member.getRoleSet());

        memberRepository.save(member);
    }

    // 회원 자동 생성
    @Override
    public Member autoRegister() {
        Member member = Member.builder()
                .username(UUID.randomUUID().toString())
                .memail("example@example.com")
                .maddr("서울특별시 서초구 역삼동")
                .build();

        return memberRepository.save(member);
    }
}
