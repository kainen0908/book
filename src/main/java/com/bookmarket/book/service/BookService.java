package com.bookmarket.book.service;

import com.bookmarket.book.dto.BookDTO;
import com.bookmarket.book.dto.PageRequestDTO;
import com.bookmarket.book.dto.PageResultDTO;
import com.bookmarket.book.dto.UploadResultDTO;
import com.bookmarket.book.entity.Book;

import java.util.List;

public interface BookService {
    Long register(BookDTO bookDTO);

    PageResultDTO<BookDTO, Object[]> getList(PageRequestDTO requestDTO);

    PageResultDTO<BookDTO, Object[]> getListWithCategory(PageRequestDTO requestDTO);

    PageResultDTO<BookDTO, Object[]> getMainPageNewBook();

    BookDTO getBook(Long b_no);

    void remove(Long b_no);

    void modify(BookDTO bookDTO);

    default BookDTO entitiesToDTO(Book book, Double avg, Long replyCnt) {
        BookDTO bookDTO = null;
        if (book.getBthumb() != null && book.getBthumb().length() >0) {
            bookDTO = BookDTO.builder()
                    .bno(book.getBno())
                    .bauthor(book.getBauthor())
                    .bcat(book.getBcat())
                    .bisbn(book.getBisbn())
                    .bprice(book.getBprice())
                    .bpublisher(book.getBpublisher())
                    .bqty(book.getBqty())
                    .bsummary(book.getBsummary())
                    .bthumb(book.getBthumb())
                    .btitle(book.getBtitle())
                    .imageDTOList(UploadResultDTO.builder()
                            .folderPath(book.getBthumb().substring(0, 10))
                            .uuid(book.getBthumb().substring(11, 47))
                            .fileName(book.getBthumb().substring(48))
                            .build())
                    .build();

        } else {
            bookDTO = BookDTO.builder()
                    .bno(book.getBno())
                    .bauthor(book.getBauthor())
                    .bcat(book.getBcat())
                    .bisbn(book.getBisbn())
                    .bprice(book.getBprice())
                    .bpublisher(book.getBpublisher())
                    .bqty(book.getBqty())
                    .bsummary(book.getBsummary())
                    .bthumb(book.getBthumb())
                    .btitle(book.getBtitle())
                    .build();
        }
        bookDTO.setAvg(avg);
        bookDTO.setReplyCnt(replyCnt.intValue());
        return bookDTO;
    }


    default Object dtoToEntity(BookDTO bookDTO) {
        //UploadResultDTO 처리
        UploadResultDTO imageDTOList = bookDTO.getImageDTOList();
        if (imageDTOList != null) {
            Book book = Book.builder()
                    .bno(bookDTO.getBno())
                    .btitle(bookDTO.getBtitle())
                    .bsummary(bookDTO.getBsummary())
                    .bcat(bookDTO.getBcat())
                    .bprice(bookDTO.getBprice())
                    .bisbn(bookDTO.getBisbn())
                    .bauthor(bookDTO.getBauthor())
                    .bqty(bookDTO.getBqty())
                    .bpublisher(bookDTO.getBpublisher())
                    .bthumb(imageDTOList.getFolderPath()+ "\\" + imageDTOList.getUuid() + "_" + imageDTOList.getFileName())
                    .build();
            return book;
        } //else {
//            Book book = Book.builder()
//                    .b_no(bookDTO.getB_no())
//                    .b_title(bookDTO.getB_title())
//                    .b_summary(bookDTO.getB_summary())
//                    .b_cat(bookDTO.getB_cat())
//                    .b_price(bookDTO.getB_price())
//                    .b_isbn(bookDTO.getB_isbn())
//                    .b_author(bookDTO.getB_author())
//                    .b_qty(bookDTO.getB_qty())
//                    .b_publisher(bookDTO.getB_publisher())
//                    .build();
//            return book;
//        }
        return "";
    }

}
