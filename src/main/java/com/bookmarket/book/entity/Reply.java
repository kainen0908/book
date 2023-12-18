package com.bookmarket.book.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"book", "member"})
@Table(name = "bs_reply")
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    private String rreply;
    private int rpoint;

    public void changePoint(int rpoint) {
        this.rpoint = rpoint;
    }

    public void changeReply(String rreply) {
        this.rreply = rreply;
    }
}
