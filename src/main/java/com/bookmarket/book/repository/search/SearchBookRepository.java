package com.bookmarket.book.repository.search;

import com.bookmarket.book.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBookRepository {
    //Book search1();

   Page<Object[]> searchPage(String type, String keyword, Pageable pageable);

   Page<Object[]> searchPage(String type, String keyword, String category, Pageable pageable);
}
