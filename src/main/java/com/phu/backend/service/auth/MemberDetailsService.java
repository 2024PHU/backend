package com.phu.backend.service.auth;

import com.phu.backend.domain.member.Member;
import com.phu.backend.dto.auth.MemberDetails;
import com.phu.backend.exception.member.EmailOrPasswordNotExistException;
import com.phu.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(EmailOrPasswordNotExistException::new);

        return new MemberDetails(member.getEmail(),member.getPassword(),member.getRole());
    }
}