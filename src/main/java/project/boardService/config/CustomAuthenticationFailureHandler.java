package project.boardService.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logException(exception);

        String errorMessage = "Login Failed!";

        if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호가 잘못되었습니다.";
        } else if (exception instanceof DisabledException) {
            errorMessage = "DisabledException";
        } else if (exception instanceof LockedException) {
            errorMessage = "LockedException";
        } else if (exception instanceof AccountExpiredException) {
            errorMessage = "AccountExpiredException";
        } else if (exception instanceof CredentialsExpiredException) {
            errorMessage = "CredentialsExpiredException";
        }

        setDefaultFailureUrl("/member/login?error=true&message=" + URLEncoder.encode(errorMessage, StandardCharsets.UTF_8));

        super.onAuthenticationFailure(request, response, exception);
    }

    private void logException(AuthenticationException exception) {
        logger.error("Authentication failure: " + exception.getMessage(), exception);
    }
}
