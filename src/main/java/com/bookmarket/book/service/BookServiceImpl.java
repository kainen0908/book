package com.bookmarket.book.service;

import com.bookmarket.book.dto.BookDTO;
import com.bookmarket.book.dto.PageRequestDTO;
import com.bookmarket.book.dto.PageResultDTO;
import com.bookmarket.book.dto.UploadResultDTO;
import com.bookmarket.book.entity.Book;
import com.bookmarket.book.entity.QBook;
import com.bookmarket.book.repository.BookRepository;
import com.bookmarket.book.repository.ReplyRepository;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ReplyRepository replyRepository;

    @Override
    public Long register(BookDTO bookDTO) {
        Object entity = dtoToEntity(bookDTO);
        Book book = (Book)entity;

        bookRepository.save(book);

        return bookDTO.getBno();
    }

    @Override
    public PageResultDTO<BookDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("bno").descending());

        // 문제 b_no를 비롯해 모든 entity에서 '_'제거해야하는 듯
        Page<Object[]> result = bookRepository.searchPage(
                requestDTO.getType(),
                requestDTO.getKeyword(),
                requestDTO.getPageable(Sort.by("bno").descending())
        );

        //Page<Object[]> result = bookRepository.getListPage(pageable); // 원본

        Function<Object[], BookDTO> fn = (arr -> entitiesToDTO(
                (Book)arr[0],
                //arr[1],
                (Double) arr[1],
                (Long)arr[2])
        );
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public PageResultDTO<BookDTO, Object[]> getMainPageNewBook() {
        Pageable pageable = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC, "bno"));

        Page<Object[]> result = bookRepository.getListPage(pageable); // 원본

        Function<Object[], BookDTO> fn = (arr -> entitiesToDTO(
                (Book)arr[0],
                //arr[1],
                (Double) arr[1],
                (Long)arr[2])
        );
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public PageResultDTO<BookDTO, Object[]> getListWithCategory(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("bno").descending());

        // 문제 b_no를 비롯해 모든 entity에서 '_'제거해야하는 듯
        Page<Object[]> result = bookRepository.searchPage(
                requestDTO.getType(),
                requestDTO.getKeyword(),
                requestDTO.getCategory(),
                requestDTO.getPageable(Sort.by("bno").descending())
        );

        //Page<Object[]> result = bookRepository.getListPage(pageable); // 원본

        Function<Object[], BookDTO> fn = (arr -> entitiesToDTO(
                (Book)arr[0],
                //arr[1],
                (Double) arr[1],
                (Long)arr[2])
        );
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BookDTO getBook(Long bno) {
        List<Object[]> result = bookRepository.getBookWithAll(bno);
        Book book = (Book) result.get(0)[0];
        UploadResultDTO bookImageList = new UploadResultDTO();

        Double avg = (Double) result.get(0)[1];
        Long replyCnt = (Long) result.get(0)[2];

        return entitiesToDTO(book, avg, replyCnt);
    }

    @Transactional
    @Override
    public void remove(Long bno) {
        replyRepository.deleteByBook(Book.builder().bno(bno).build());
        bookRepository.deleteById(bno);
    }

    @Override
    public void modify(BookDTO bookDTO) {
        Optional<Book> result = bookRepository.findById(bookDTO.getBno());

        if(result.isPresent()) {
            Book entity = result.get();

            entity.changeB_title(bookDTO.getBtitle());
            entity.changeB_summary(bookDTO.getBsummary());
            entity.changeB_cat(bookDTO.getBcat());
            entity.changeB_isbn(bookDTO.getBisbn());
            entity.changeB_author(bookDTO.getBauthor());
            entity.changeB_publisher(bookDTO.getBpublisher());
            entity.changeB_price(bookDTO.getBprice());
            entity.changeB_qty(bookDTO.getBqty());
            entity.changeB_thumb(bookDTO.getImageDTOList().getFolderPath() + "\\"
                                + bookDTO.getImageDTOList().getUuid() + "_" + bookDTO.getImageDTOList().getFileName());

            bookRepository.save(entity);
        }
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){
        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBook qBook = QBook.book;
        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qBook.bno.gt(0L); // bno > 0 인 조건 생성
        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0) { //검색 조건이 없는 경우
            return booleanBuilder;
        }

        // 검색조건
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) {
            conditionBuilder.or(qBook.btitle.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qBook.bsummary.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(qBook.bauthor.contains(keyword));
        }

        // 위의 조건들 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
