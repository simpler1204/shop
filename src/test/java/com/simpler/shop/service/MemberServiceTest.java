package com.simpler.shop.service;

import com.simpler.shop.dto.MemberFormDto;
import com.simpler.shop.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword(passwordEncoder.encode("1234"));
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @Transactional
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() {
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getRole(), savedMember.getRole());

    }

    @Test
    @Transactional
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest () {
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> memberService.saveMember(member2));
        assertEquals("이미 가입된 회원입니다", exception.getMessage());

    }
}