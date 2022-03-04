//package jp.learningpark.framework.config;
//
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.security.PrivateKey;
//import java.security.cert.CertificateFactory;
//import java.security.cert.X509Certificate;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.converter.RsaKeyConverters;
//import org.springframework.security.saml2.core.Saml2X509Credential;
//import org.springframework.security.saml2.core.Saml2X509Credential.Saml2X509CredentialType;
//import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
//import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
//
//import jp.learningpark.framework.security.saml.SamlAuthFailureHandler;
//import jp.learningpark.framework.security.saml.SamlAuthSuccessHandler;
//import jp.learningpark.framework.security.saml.SamlRequestedMatcher;
//
//
///**
// * Spring Security用設定.
// * 
// * @author NWT
// *
// */
//@EnableWebSecurity
//public class Saml2SecurityConfig extends WebSecurityConfigurerAdapter {
//    
//    /**
//     * registrationId
//     */
//    @Value("${saml2.regId}")
//    private String regId;
//    
//    /**
//     * IdP エンティティID
//     */
//    @Value("${saml2.idpEntityId}")
//    private String idpEntityId;
//    
//    /**
//     * IdP SSOエンドポイント
//     */
//    @Value("${saml2.idpSSOLocation}")
//    private String idpSSOLocation;
//    
//    /**
//     * SPの秘密鍵
//     */
//    @Value("${saml2.spPrivateKey}")
//    private String spPrivateKey;
//    
//    /**
//     * SPの共通鍵
//     */
//    @Value("${saml2.spCertificate}")
//    private String spCertificate;
//    
//    /**
//     * IDPの共通鍵
//     */
//    @Value("${saml2.idpCertificate}")
//    private String idpCertificate;
//    
//    /**
//     * 認証成功URL
//     */
//    @Value("${saml2.successUrl}")
//    private String successUrl;
//    
//    /**
//     * 認証失敗URL
//     */
//    @Value("${saml2.failureUrl}")
//    private String failureUrl;
//
//    Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        logger.info("SAML Config init start");
//        checkEmpty("saml2.regId", regId);
//        checkEmpty("saml2.idpEntityId", idpEntityId);
//        checkEmpty("saml2.idpSSOLocation", idpSSOLocation);
//        checkEmpty("saml2.spPrivateKey", spPrivateKey);
//        checkEmpty("saml2.spCertificate", spCertificate);
//        checkEmpty("saml2.idpCertificate", idpCertificate);
//        http.csrf().disable();
//        http.headers().frameOptions().sameOrigin();
//        http.requestMatcher(new SamlRequestedMatcher());
//        http.authorizeRequests().antMatchers(getMatchers()).permitAll().and().saml2Login()
//                .relyingPartyRegistrationRepository(new InMemoryRelyingPartyRegistrationRepository(
//                        // IdPとの紐づけ
//                        getIdpRelyingPartyRegistration()//
//                )).successHandler(new SamlAuthSuccessHandler(successUrl)).failureHandler(new SamlAuthFailureHandler(failureUrl));
//        logger.info("SAML Config init end");
//    }
//
//    private RelyingPartyRegistration getIdpRelyingPartyRegistration() throws FileNotFoundException {
//        // SP エンティティID
//        String relyingPartyEntityId = "{baseUrl}/saml2/service-provider-metadata/{registrationId}";
//        // SP SSOエンドポイント
//        String assertionConsumerServiceLocation = "{baseUrl}/login/saml2/sso/{registrationId}";
//        // SP 証明書
//        Saml2X509Credential relyingPartySigningCredential = getSigningCredential();
//        logger.info("Sp CRT:" + relyingPartySigningCredential.toString());
//        // IdP 証明書（CRT）
//        Saml2X509Credential assertingPartyVerificationCredential = getVerificationCertificate();
//        logger.info("IdP CRT:" + assertingPartyVerificationCredential.toString());
//
//        RelyingPartyRegistration rp = RelyingPartyRegistration.withRegistrationId(regId)
//                .entityId(relyingPartyEntityId).assertionConsumerServiceLocation(assertionConsumerServiceLocation)
//                .signingX509Credentials(c -> c.add(relyingPartySigningCredential))
//                .assertingPartyDetails(details -> details.entityId(idpEntityId)
//                        .singleSignOnServiceLocation(idpSSOLocation)
//                        .verificationX509Credentials(c -> c.add(assertingPartyVerificationCredential)))
//                .build();
//        return rp;
//    }
//
//    private Saml2X509Credential getSigningCredential() throws FileNotFoundException {
//        // SPの秘密鍵 (must generate)
//        InputStream inputStream = this.getClass().getResourceAsStream(spPrivateKey);
//        PrivateKey pk = RsaKeyConverters.pkcs8().convert(inputStream);
//        // SPの共通鍵 (must generate)
//        inputStream = this.getClass().getResourceAsStream(spCertificate);
//        X509Certificate cert = x509Certificate(inputStream);
//        return new Saml2X509Credential(pk, cert, Saml2X509CredentialType.SIGNING, Saml2X509CredentialType.DECRYPTION);
//    }
//
//    private Saml2X509Credential getVerificationCertificate() throws FileNotFoundException {
//        // IdPの共通鍵
//        InputStream inputStream = this.getClass().getResourceAsStream(idpCertificate);
//        return new Saml2X509Credential(x509Certificate(inputStream), Saml2X509CredentialType.VERIFICATION);
//    }
//
//    private X509Certificate x509Certificate(InputStream inStream) {
//        try {
//            final CertificateFactory factory = CertificateFactory.getInstance("X.509");
//            return (X509Certificate) factory.generateCertificate(inStream);
//        } catch (Exception e) {
//            throw new IllegalArgumentException(e);
//        }
//    }
//    
//    private String[] getMatchers() {
//        List<String> mathchers = new ArrayList<String>();
//        new ShiroConfig().getFilterMap().forEach((k, v) -> {
//            if ("anon".equals(v)) {
//                mathchers.add(k);
//            }
//        });
//        return mathchers.toArray(new String[mathchers.size()]);
//    }
//    
//    private void checkEmpty(String key, String value) {
//        if (StringUtils.isEmpty(value)) {
//            logger.error(key + " is empty! value:" + value);
//        } else {
//            logger.info(key + ":" + regId);
//        }
//    }
//}
