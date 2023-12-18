package com.bookmarket.book.repository;

import com.bookmarket.book.entity.Book;
import com.bookmarket.book.repository.search.SearchBookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, SearchBookRepository {
    @Query("select b, avg(coalesce(r.rpoint,0)), count(r) from Book b "
            + "left outer join Reply r on r.book = b group by b")
    Page<Object[]> getListPage(Pageable pageable); // 페이지 처리


    @Query("select b, avg(coalesce(r.rpoint, 0)), count(r)" +
            " from Book b left outer join Reply r on r.book = b " +
            " where b.bno = :bno")
    List<Object[]> getBookWithAll(Long bno); // 책 상세 보기

    @Query("select b, avg(coalesce(r.rpoint,0)), count(r) from Book b "
            + "left outer join Reply r on r.book = b group by b")
    Page<Object[]> getMainPage(Pageable pageable); // 페이지 처리


    @Query("SELECT b, AVG(COALESCE(r.rpoint,0)), COUNT(r) FROM Book b "
            + "LEFT OUTER JOIN Reply r ON r.book = b GROUP BY b ORDER BY AVG(COALESCE(r.rpoint)) DESC")
    Page<Object[]> getMainBestPage(Pageable pageable); // 페이지 처리
}
