package com.project.tour.Security;

import com.project.tour.Entity.Account;
import com.project.tour.Repository.AccountRepository;
import com.project.tour.Repository.AccountRoleRepository;
import com.project.tour.Service.OAuthVerificationService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final AccountRepository accountRepository;
    private final AccountRoleRepository roleRepository;
    private final OAuthVerificationService oAuthVerificationService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Google trả về name, email trong attribute
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        if (email == null) {
            throw new OAuth2AuthenticationException("Google OAuth2: Không tìm thấy email!");
        }

        // role mặc định → USER
    // Lấy role để đảm bảo tồn tại, logic sử dụng role nằm trong service pending
    roleRepository.findByRoleName("ROLE_USER")
        .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

        // Nếu đã có account → cho login bình thường
        if (accountRepository.findByUsername(email).isPresent()) {
            Account existing = accountRepository.findByUsername(email).get();
            return new CustomOAuth2User(oAuth2User, existing);
        }

    // Chưa có account: tạo pending + gửi email xác thực, sau đó chặn đăng nhập
    oAuthVerificationService.createPendingForGoogle(email);
        throw new OAuth2AuthenticationException("Email chưa được xác thực. Vui lòng kiểm tra hộp thư để xác thực.");
    }
}
