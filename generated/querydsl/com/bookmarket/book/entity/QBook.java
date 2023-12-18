package com.bookmarket.book.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBook is a Querydsl query type for Book
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBook extends EntityPathBase<Book> {

    private static final long serialVersionUID = -299389397L;

    public static final QBook book = new QBook("book");

    public final StringPath bauthor = createString("bauthor");

    public final NumberPath<Integer> bcat = createNumber("bcat", Integer.class);

    public final StringPath bisbn = createString("bisbn");

    public final NumberPath<Long> bno = createNumber("bno", Long.class);

    public final NumberPath<Integer> bprice = createNumber("bprice", Integer.class);

    public final StringPath bpublisher = createString("bpublisher");

    public final NumberPath<Integer> bqty = createNumber("bqty", Integer.class);

    public final StringPath bsummary = createString("bsummary");

    public final StringPath bthumb = createString("bthumb");

    public final StringPath btitle = createString("btitle");

    public QBook(String variable) {
        super(Book.class, forVariable(variable));
    }

    public QBook(Path<? extends Book> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBook(PathMetadata metadata) {
        super(Book.class, metadata);
    }

}

