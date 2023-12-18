package com.bookmarket.book.repository;

import com.bookmarket.book.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from Member m where m.mid = :mid")
    Optional<Member> getWithRoles(String mid);


    @EntityGraph(attributePaths = "roleSet")
    Optional<Member> findByMemail(String memail);


    @Modifying
    @Transactional
    @Query("update Member set mpw =:mpw where mid = :mid ")
    void updatePassword(@Param("mpw") String password, @Param("mid") String mid);


}
