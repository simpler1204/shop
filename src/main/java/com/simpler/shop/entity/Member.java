package com.simpler.shop.entity;

import com.simpler.shop.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member {
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;


    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private String role;

    public static Member createMember(MemberFormDto memberFromDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFromDto.getName());
        member.setEmail(memberFromDto.getEmail());
        member.setPassword(passwordEncoder.encode(memberFromDto.getPassword()));
        member.setAddress(memberFromDto.getAddress());
        member.setRole("ROLE_USER");
        return member;
    }
}
