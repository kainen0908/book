package com.bookmarket.book.repository;

import com.bookmarket.book.entity.Book;
import com.bookmarket.book.entity.Member;
import com.bookmarket.book.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply() {
        IntStream.rangeClosed(1,50).forEach(i -> {
            // 도서 번호
            Long bno = (long)(Math.random()*50)+1;

            //멤버 번호
            Long mno = (long)(Math.random()*50) +1;
            Member member = Member.builder().mno(mno).build();

            Reply reply = Reply.builder()
                    .member(member)
                    .book(Book.builder().bno(bno).build())
                    .rpoint((int)(Math.random()*10) + 1)
                    .rreply("이 책은 좋은 책이다. 몇 번이고 다시 읽게 ....")
                    .build();

            replyRepository.save(reply);
        });
    }

    @Test
    public void testGetBookReplies() {
        Book book = Book.builder().bno(36L).build();
        List<Reply> result = replyRepository.findByBook(book);

        result.forEach(bookReply -> {
            System.out.print(bookReply.getRno());
            System.out.print("\t" +bookReply.getRpoint());
            System.out.print("\t" +bookReply.getRreply());
            System.out.print("\t" + bookReply.getMember().getMno());
            System.out.println("----------------------------------------");
        });
    }
}
