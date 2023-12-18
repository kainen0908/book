package com.bookmarket.book.repository;

import com.bookmarket.book.entity.Book;
import com.bookmarket.book.repository.search.SearchBookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    @Commit
    @Test
    public void insertBook() {
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Book book = Book.builder()
                    .btitle("Book..." + i)
                    .bauthor("author..." + i)
                    .bcat((int)(Math.random()*17) + 1)
                    .bisbn("testIsbn" + i)
                    .bqty(100)
                    .bpublisher("TestPublisher")
                    .bthumb("")
                    .bprice(23000)
                    .bsummary("This is Test Book's summary.")
                    .build();
            System.out.println("------------------------");
            bookRepository.save(book);
        });
    }

    @Test
    public void testListPage() {
        PageRequest pageRequest = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC, "bno"));
        Page<Object[]> result = bookRepository.getListPage(pageRequest);

        for(Object[] objects : result.getContent()) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void testGetBookWithAll() {
        List<Object[]> result = bookRepository.getBookWithAll(36L);

        System.out.println(result);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }

//    @Test
//    public void testSearch1() {
//        bookRepository.search1();
//    }

    @Test
    public void testSearchPage() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending().and(Sort.by("btitle").ascending()));

        Page<Object[]> result = bookRepository.searchPage("t", "23", pageable);
    }
}
