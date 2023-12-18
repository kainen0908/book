package com.bookmarket.book.service;

import com.bookmarket.book.entity.Member;
import com.bookmarket.book.entity.Order;

public interface OrderService {
    Order autoOrder(Member member); // 자동 주문
}