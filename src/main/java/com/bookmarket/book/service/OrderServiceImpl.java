package com.bookmarket.book.service;

import com.bookmarket.book.entity.Member;
//import jakarta.transaction.Transactional;
import com.bookmarket.book.entity.Order;
import com.bookmarket.book.entity.Payment;
import com.bookmarket.book.entity.PaymentStatus;
import com.bookmarket.book.repository.OrderRepository;
import com.bookmarket.book.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public Order autoOrder(Member member) {

        // 임시 결제내역 생성
        Payment payment = Payment.builder()
                .price(1000L)
                .status(PaymentStatus.READY)
                .build();

        paymentRepository.save(payment);

        // 주문 생성
        Order order = Order.builder()
                .member(member)
                .price(1000L)
                .itemName("BookStore 테스트 상품")
                .orderUid(UUID.randomUUID().toString())
                .payment(payment)
                .build();

        return orderRepository.save(order);
    }
}