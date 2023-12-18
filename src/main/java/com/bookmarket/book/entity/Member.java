package com.bookmarket.book.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "roleSet")
@Table(name = "bs_member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(unique = true)
    private String mid;
    private String username;

    private String mpw;
    private String mname;
    private String mbirth;
    private String memail;
    private String mtel;
    private String maddr;
    private String mlev;
    private boolean msocial;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void changePassword(String mpw ){
        this.mpw = mpw;
    }

    public void changeEmail(String memail){
        this.memail = memail;
    }

    public void addRole(MemberRole memberRole){
        this.roleSet.add(memberRole);
    }

    public void clearRoles() {
        this.roleSet.clear();
    }

    public void changeSocial(boolean msocial){this.msocial = msocial;}
}
