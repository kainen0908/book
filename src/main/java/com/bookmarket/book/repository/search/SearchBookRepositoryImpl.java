package com.bookmarket.book.repository.search;

import com.bookmarket.book.entity.Book;
import com.bookmarket.book.entity.QBook;
import com.bookmarket.book.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchBookRepositoryImpl extends QuerydslRepositorySupport implements SearchBookRepository {

    public SearchBookRepositoryImpl() {
        super(Book.class);
    }

//    @Override
//    public Book search1() {
//        log.info("search1.....");
//
//        QBook book = QBook.book;
//        QReply reply = QReply.reply;
//        QMember member = QMember.member;
//
//        JPQLQuery<Book> jpqlQuery = from(book);
//        jpqlQuery.leftJoin(reply).on(reply.book.eq(book));
//
//        JPQLQuery<Tuple> tuple = jpqlQuery.select(book, reply);
//        tuple.groupBy(book);
//
//        List<Tuple> result = tuple.fetch();
//
//        return null;
//    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        QBook book = QBook.book;
        QReply reply = QReply.reply;

        JPQLQuery<Book> jpqlQuery = from(book);
        jpqlQuery.leftJoin(reply).on(book.eq(reply.book));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(book, reply.rpoint.coalesce(0).avg(), reply.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = book.bno.gt(0L);

        booleanBuilder.and(expression);

        if(type != null) {
            String[] typeArr = type.split("");

            // 검색 조건 작성
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for(String t : typeArr) {
                switch (t){
                    case "t" :
                        conditionBuilder.or(book.btitle.contains(keyword));
                        break;
                    case "w" :
                        conditionBuilder.or(book.bauthor.contains(keyword));
                        break;
                    case "c" :
                        conditionBuilder.or(book.bsummary.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }
        tuple.where(booleanBuilder);

        tuple.groupBy(book);

        this.getQuerydsl().applyPagination(pageable, tuple);

        List<Tuple> result = tuple.fetch();

        log.info(result);

        long count = tuple.fetchCount();
        log.info("count: " + count);

        return new PageImpl<Object[]>(
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, count);
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, String category, Pageable pageable) {
        QBook book = QBook.book;
        QReply reply = QReply.reply;

        JPQLQuery<Book> jpqlQuery = from(book);
        jpqlQuery.leftJoin(reply).on(book.eq(reply.book));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(book, reply.rpoint.coalesce(0).avg(), reply.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = book.bno.gt(0L);

        booleanBuilder.and(expression);

        if(type != null) {
            String[] typeArr = type.split("");

            // 검색 조건 작성
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for(String t : typeArr) {
                switch (t){
                    case "t" :
                        conditionBuilder.or(book.btitle.contains(keyword));
                        break;
                    case "w" :
                        conditionBuilder.or(book.bauthor.contains(keyword));
                        break;
                    case "c" :
                        conditionBuilder.or(book.bsummary.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);

            if(category != null && category.length() >0) {
                //BooleanBuilder conditionBuilder = new BooleanBuilder();
                conditionBuilder.or(book.bcat.eq(Integer.valueOf(category)));
                booleanBuilder.and(conditionBuilder);
            }
        }
        tuple.where(booleanBuilder);

        tuple.groupBy(book);

        this.getQuerydsl().applyPagination(pageable, tuple);

        List<Tuple> result = tuple.fetch();

        log.info(result);

        long count = tuple.fetchCount();
        log.info("count: " + count);

        return new PageImpl<Object[]>(
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, count);
    }

}
