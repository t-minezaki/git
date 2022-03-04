//package jp.learningpark.framework.security.saml;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.security.web.util.matcher.RequestMatcher;
//
//public class SamlRequestedMatcher implements RequestMatcher {
//    public boolean matches(HttpServletRequest request) {
//        if ("true".equals(request.getSession().getAttribute("authenticated")) || request.getRequestURI().contains("saml2")) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//}