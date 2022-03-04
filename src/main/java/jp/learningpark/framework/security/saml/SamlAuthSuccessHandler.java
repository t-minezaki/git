//package jp.learningpark.framework.security.saml;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//public class SamlAuthSuccessHandler implements AuthenticationSuccessHandler {
//    
//    private String successUrl;
//    
//    public SamlAuthSuccessHandler(String url) {
//        successUrl = url;
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//        // 認証結果はTrueを設定します
//        authentication.setAuthenticated(true);
//        request.getSession().setAttribute("authenticated", "true");
//        response.sendRedirect(request.getContextPath() + successUrl);
//    }
//
//}
