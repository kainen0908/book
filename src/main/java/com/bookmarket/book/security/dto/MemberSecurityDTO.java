package com.bookmarket.book.security.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User{

//    private String mid;
//    private String mpw;
//    private String email;
//    private boolean del;
//    private boolean social;

    private Long mno;
    private String mid;
    private String mpw;
    private String mname;
    private String mbirth;
    private String memail;
    private String mtel;
    private String maddr;
    private String mlev;
    private boolean msocial;

//    private Map<String, Object> props; //소셜 로그인 정보

    public MemberSecurityDTO(String username, String password, String mname, String mbirth, String memail, String mtel, String maddr, String mlev, boolean social,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.mid = username;
        this.mpw = password;
        this.mname = mname;
        this.mbirth = mbirth;
        this.memail = memail;
        this.mtel = mtel;
        this.maddr = maddr;
        this.mlev = mlev;
        this.msocial = social;

    }

//    public Map<String, Object> getAttributes() {
//        return this.getProps();
//    }
//
//    @Override
//    public String getName() {
//        return this.mid;
//    }

}
