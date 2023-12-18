package com.bookmarket.book.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUploadResult is a Querydsl query type for UploadResult
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QUploadResult extends EntityPathBase<UploadResult> {

    private static final long serialVersionUID = 1968541376L;

    public static final QUploadResult uploadResult = new QUploadResult("uploadResult");

    public final StringPath fileName = createString("fileName");

    public final StringPath folderPath = createString("folderPath");

    public final StringPath uuid = createString("uuid");

    public QUploadResult(String variable) {
        super(UploadResult.class, forVariable(variable));
    }

    public QUploadResult(Path<? extends UploadResult> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUploadResult(PathMetadata metadata) {
        super(UploadResult.class, metadata);
    }

}

