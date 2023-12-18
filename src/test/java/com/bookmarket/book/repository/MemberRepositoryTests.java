package com.bookmarket.book.repository;

import com.bookmarket.book.entity.Member;
import com.bookmarket.book.entity.MemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertMember() {
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Member member = Member
                    .builder()
                    .mid("testid" + i)
                    .mname("tester" + i)
                    .mpw(passwordEncoder.encode("1111"))
                    .memail("tester" + i + "@test.org")
                    .mtel("010-1234-5678")
                    .msocial(false)
                    .mlev("0")
                    .mbirth("19900101")
                    .maddr("서울시 강남구 역삼" + i + "동")
                    .build();

            member.addRole(MemberRole.USER);

            if(i >= 40) {
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        });

    }

//    @Commit
//    @Transactional
//    @Test
//    public void testDeleteMember() {
//        Long mno = 28L; // Member의 m_no
//        Member member = Member.builder().mno(mno).build();
//
//        //commit, transactional 추가 전 에러 코드
//        //memberRepository.deleteById(m_no);
//        //replyRepository.deleteByMember(member);
//
//        //순서 주의! 멤버 삭제 -> 댓글 삭제 (댓글의 외래키가 멤버이므로 외래키부터 삭제!)
//        replyRepository.deleteByMember(member);
//        memberRepository.deleteById(mno);
//    }
}
