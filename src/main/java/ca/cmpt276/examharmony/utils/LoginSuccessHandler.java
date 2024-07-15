package ca.cmpt276.examharmony.utils;

import ca.cmpt276.examharmony.utils.CustomUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String redirectURL = request.getContextPath();

        if(userDetails.hasRole("ADMIN")){
            redirectURL = "/admin/home";
        } else if (userDetails.hasRole("INSTRUCTOR")){
            redirectURL = "/instructor/home";
        } else if (userDetails.hasRole("INVIGILATOR")){
            redirectURL = "/invigilator/home";
        } else {
            redirectURL = "/test";  //For testing purposes
        }

        response.sendRedirect(redirectURL);
    }
}
