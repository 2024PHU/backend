package com.phu.backend.config.oauth;

import com.phu.backend.domain.member.Member;
import com.phu.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("oAuth2User:{}", oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        // TODO 추후 다른 소셜로그인 도입시 추가 로직 구성 현재는 구글만
        if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String socialId = oAuth2Response.getProvider() + oAuth2Response.getProviderId();

        // 소셜로그인으로 등록한 기록이 있는지
        Member existData = memberRepository.findBySocialId(socialId);

        if (existData == null || existData.getRole().equals("ROLE_BEFORE_USER")) {

            if (existData == null) {
                Member member = Member.builder()
                        .email(oAuth2Response.getEmail())
                        .name(oAuth2Response.getName())
                        .role("ROLE_BEFORE_USER")
                        .build();

                member.confirmSocialId(socialId);

                memberRepository.save(member);
            }

            // 추가 회원가입을 안한 회원인가
            OAuthRequest oAuthRequest = OAuthRequest.builder()
                    .username(socialId)
                    .name(oAuth2Response.getName())
                    .role("ROLE_BEFORE_USER")
                    .build();


            return new CustomOAuth2User(oAuthRequest);
        } else {
            existData.changeMemberInfo(oAuth2Response.getEmail(), oAuth2Response.getName());
            memberRepository.save(existData);

            OAuthRequest oAuthRequest = OAuthRequest.builder()
                    .username(socialId)
                    .name(oAuth2Response.getName())
                    .role("ROLE_USER")
                    .build();
            return new CustomOAuth2User(oAuthRequest);
        }
    }
}
