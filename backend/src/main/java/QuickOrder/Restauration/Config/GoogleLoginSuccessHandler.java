package QuickOrder.Restauration.Config;

import QuickOrder.Restauration.Services.GoogleAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GoogleLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final GoogleAccountService googleAccountService;

    @Value("${frontend.base-url}")
    private String frontendBaseUrl;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        if (!(authentication.getPrincipal() instanceof OAuth2User googleUser)) {
            response.sendRedirect(frontendUrl() + "/login?googleError=failed");
            return;
        }

        try {
            String token = googleAccountService.authenticate(googleUser);
            response.sendRedirect(frontendUrl() + "/auth/callback#token=" + token);
        } catch (org.springframework.security.oauth2.core.OAuth2AuthenticationException exception) {
            response.sendRedirect(frontendUrl() + "/login?googleError=account");
        }
    }

    private String frontendUrl() {
        return frontendBaseUrl.replaceAll("/+$", "");
    }
}
