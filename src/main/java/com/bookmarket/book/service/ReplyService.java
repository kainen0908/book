package com.bookmarket.book.service;

import com.bookmarket.book.dto.ReplyDTO;
import com.bookmarket.book.entity.Book;
import com.bookmarket.book.entity.Member;
import com.bookmarket.book.entity.Reply;

import java.util.List;

public interface ReplyService {

    List<ReplyDTO> getListOfBook(Long b_no);

    Long register(ReplyDTO bookReplyDTO);

    void modify(ReplyDTO bookReplyDTO);

    void remove(Long r_no);

    default Reply dtoToEntity(ReplyDTO bookReplyDTO) {
        Reply bookReply = Reply.builder()
                .rno(bookReplyDTO.getRno())
                .book(Book.builder().bno(bookReplyDTO.getBno()).build())
                .member(Member.builder().mno(bookReplyDTO.getMno()).build())
                .rpoint(bookReplyDTO.getRpoint())
                .rreply(bookReplyDTO.getRreply())
                .build();
        return bookReply;
    }

    default ReplyDTO entityToDTO(Reply bookReply) {
        ReplyDTO bookReplyDTO = ReplyDTO.builder()
                .rno(bookReply.getRno())
                .bno(bookReply.getBook().getBno())
                .mno(bookReply.getMember().getMno())
                .mid(bookReply.getMember().getMid())
                .memail(bookReply.getMember().getMemail())
                .rpoint(bookReply.getRpoint())
                .rreply(bookReply.getRreply())
                .regDate(bookReply.getRegDate())
                .updateDate(bookReply.getUpdateDate())
                .build();
        return bookReplyDTO;
    }
}
