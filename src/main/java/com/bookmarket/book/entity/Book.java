package com.bookmarket.book.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "bs_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String btitle;
    private String bsummary;
    private int bcat;
    private String bisbn;
    private String bauthor;
    private String bpublisher;
    private String bthumb;
    private int bprice;
    private int bqty;

    public void changeB_title(String btitle) {
        this.btitle = btitle;
    }

    public void changeB_summary(String bsummary) {
        this.bsummary = bsummary;
    }

    public void changeB_cat(int bcat) {
        this.bcat = bcat;
    }

    public void changeB_isbn(String bisbn) {
        this.bisbn = bisbn;
    }

    public void changeB_author(String bauthor) {
        this.bauthor = bauthor;
    }
    public void changeB_publisher(String bpublisher) {
        this.bpublisher = bpublisher;
    }

    public void changeB_thumb(String bthumb) {
        this.bthumb = bthumb;
    }

    public void changeB_price(int bprice) {
        this.bprice = bprice;
    }
    public void changeB_qty(int bqty) {
        this.bqty = bqty;
    }


}
