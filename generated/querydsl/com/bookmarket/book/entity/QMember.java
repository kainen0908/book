package com.bookmarket.book.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 355218780L;

    public static final QMember member = new QMember("member1");

    public final StringPath maddr = createString("maddr");

    public final StringPath mbirth = createString("mbirth");

    public final StringPath memail = createString("memail");

    public final StringPath mid = createString("mid");

    public final StringPath mlev = createString("mlev");

    public final StringPath mname = createString("mname");

    public final NumberPath<Long> mno = createNumber("mno", Long.class);

    public final StringPath mpw = createString("mpw");

    public final BooleanPath msocial = createBoolean("msocial");

    public final StringPath mtel = createString("mtel");

    public final SetPath<MemberRole, EnumPath<MemberRole>> roleSet = this.<MemberRole, EnumPath<MemberRole>>createSet("roleSet", MemberRole.class, EnumPath.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

