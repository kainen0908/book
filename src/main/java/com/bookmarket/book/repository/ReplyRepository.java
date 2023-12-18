package com.bookmarket.book.repository;

import com.bookmarket.book.entity.Book;
import com.bookmarket.book.entity.Member;
import com.bookmarket.book.entity.Reply;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Reply> findByBook(Book book);

    @Modifying
    @Query("delete from Reply r where r.member = :member")
    void deleteByMember(Member member);

    @Modifying
    @Query("delete from Reply r where r.book = :book")
    void deleteByBook(Book book);
}
