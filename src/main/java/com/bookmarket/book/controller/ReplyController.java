package com.bookmarket.book.controller;

import com.bookmarket.book.dto.ReplyDTO;
import com.bookmarket.book.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("replies")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping("/{bno}/all")
    public ResponseEntity<List<ReplyDTO>> getList(@PathVariable("bno") Long bno) {
        log.info("--------list--------");
        log.info("Bno: " + bno);

        List<ReplyDTO> replyDTOList = replyService.getListOfBook(bno);

        return new ResponseEntity<>(replyDTOList, HttpStatus.OK);
    }

    @PostMapping("/{bno}")
    public ResponseEntity<Long> addReply(@RequestBody ReplyDTO bookReplyDTO) {
        log.info("-----------add BookReply------------");
        log.info("replyDTO: " + bookReplyDTO);

        Long rno = replyService.register(bookReplyDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    @PutMapping("/{bno}/{rno}")
    public  ResponseEntity<Long> modifyReply(@PathVariable Long rno, @RequestBody ReplyDTO bookReplyDTO) {
        log.info("----------modify BookReply----------");
        log.info("replyDTO: " + bookReplyDTO);

        replyService.modify(bookReplyDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    @DeleteMapping("/{bno}/{rno}")
    public ResponseEntity<Long> removeReply(@PathVariable Long rno) {
        log.info("-------------removeReply--------------");
        log.info("Rno: " + rno);

        replyService.remove(rno);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }
}
