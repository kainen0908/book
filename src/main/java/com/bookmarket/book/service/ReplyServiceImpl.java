package com.bookmarket.book.service;

import com.bookmarket.book.dto.ReplyDTO;
import com.bookmarket.book.entity.Book;
import com.bookmarket.book.entity.Reply;
import com.bookmarket.book.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;

    @Override
    public List<ReplyDTO> getListOfBook(Long b_no) {
        Book book = Book.builder().bno(b_no).build();
        List<Reply> result = replyRepository.findByBook(book);

        return result.stream().map(bookReply -> entityToDTO(bookReply)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReplyDTO bookReplyDTO) {
        Reply bookReply = dtoToEntity(bookReplyDTO);
        replyRepository.save(bookReply);

        return bookReply.getRno();
    }

    @Override
    public void modify(ReplyDTO bookReplyDTO) {
        Optional<Reply> result = replyRepository.findById(bookReplyDTO.getRno());

        if(result.isPresent()) {
            Reply bookReply = result.get();
            bookReply.changePoint(bookReplyDTO.getRpoint());
            bookReply.changeReply(bookReplyDTO.getRreply());

            replyRepository.save(bookReply);
        }
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }
}
