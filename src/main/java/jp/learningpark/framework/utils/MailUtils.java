package jp.learningpark.framework.utils;

/**
 * <p>配信プラグイン</p>.
 *
 * @author NWT : ZHAO
 * @version 1.0
 */

import jp.learningpark.modules.common.GakkenConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

@Service
@ConfigurationProperties("mail")
public class MailUtils {

    private String host;
    private String username;
    private String password;
    private String protocol;
    private String port;
    private String smtpAuth;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSmtpAuth() {
        return smtpAuth;
    }

    public void setSmtpAuth(String smtpAuth) {
        this.smtpAuth = smtpAuth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     * @param to メールアドレス
     * @param title メールタイトル
     * @param content メール内容
     */
    public void sendMail(String to, String title, String content) {
        try {
            /* 2021/09/15 manamiru1-772 cuikl edit start */
            logger.debug("MailAddress:" + to);
            logger.debug("title:" + title);
            logger.debug("content:" + content);
            /* 2021/09/15 manamiru1-772 cuikl edit end */
            if (GakkenConstant.localFlag) {
                Properties prop = new Properties();
                prop.setProperty("mail.host", host);
                prop.setProperty("mail.transport.protocol", protocol);
                prop.setProperty("mail.smtp.socketFactory.port", port);
                prop.setProperty("mail.smtp.auth", smtpAuth);
                Session session = Session.getInstance(prop);
                /* 2021/09/22 manamiru1-772 cuikl edit start */
                session.setDebug(true);
                /* 2021/09/22 manamiru1-772 cuikl edit end */
                Transport ts = session.getTransport();
                ts.connect(host, username, password);
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject(title);
                message.setContent(content, "text/html;charset=UTF-8");
                message.saveChanges();
                ts.sendMessage(message, message.getAllRecipients());
                ts.close();
            }else {
                Context context = new InitialContext();
                //メール送信
                Session mailSession = (Session) context.lookup(GakkenConstant.getProperty("spring.mail.jndi-name"));

                MimeMessage message = null;
                message = new MimeMessage(mailSession);
                MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
                // From
                helper.setFrom(mailSession.getProperty("mail.from"));
                // To
                helper.setTo(to);
                // タイトル
                helper.setSubject(StringUtils.isBlank(title)?"タイトル":title);
                helper.setText(content, true);
                // 送信
                Transport.send(message);
            }
        } catch (Exception e) {
            logger.error("sendMail:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 送信する(添付ファイルを含める)
     * @param to メールアドレス
     * @param title メールタイトル
     * @param content メール内容
     * @param attachUrl 添付アドレス
     */
    public void sendMailAttach(String to, String title, String content, List<String> attachUrl) {
        try {
            /* 2021/09/15 manamiru1-772 cuikl edit start */
            logger.debug("MailAddress:" + to);
            logger.debug("title:" + title);
            logger.debug("content:" + content);
            /* 2021/09/15 manamiru1-772 cuikl edit end */
            if (GakkenConstant.localFlag) {
                Properties prop = new Properties();
                prop.setProperty("mail.host", host);
                prop.setProperty("mail.transport.protocol", protocol);
                prop.setProperty("mail.smtp.socketFactory.port", port);
                prop.setProperty("mail.smtp.auth", smtpAuth);
                Session session = Session.getInstance(prop);
                /* 2021/09/22 manamiru1-772 cuikl edit start */
                session.setDebug(true);
                /* 2021/09/22 manamiru1-772 cuikl edit end */
                Transport ts = session.getTransport();
                ts.connect(host, username, password);
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject(title);
                //メールの内容Multipart
                MimeMultipart multipart = new MimeMultipart();
                //テキストノードを作成
                MimeBodyPart text = new MimeBodyPart();
                text.setContent(content,"text/html;charset=UTF-8");
                //添付ファイルノードを作成してローカルファイルを読み込み、添付ファイル名を読み取ります。
                if(attachUrl.size() > 0){
                    for(String fileUrl : attachUrl){
                        // 添付ファイルのノードを作成
                        MimeBodyPart attachment = new MimeBodyPart();
                        //ファイルを読み込みます
                        DataHandler dh2 = new DataHandler(new FileDataSource(fileUrl));
                        // 添付データを「ノード」に追加します。
                        attachment.setDataHandler(dh2);
                        // 添付ファイルのファイル名を設定します
                        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
                        multipart.addBodyPart(attachment);
                    }
                }
                //テキストと添付ファイルをmultipartに追加します
                multipart.addBodyPart(text);
                //混合関係
                multipart.setSubType("mixed");
                message.setContent(multipart);
                message.saveChanges();
                ts.sendMessage(message, message.getAllRecipients());
                ts.close();
            }else {
                Context context = new InitialContext();
                //メール送信
                Session mailSession = (Session) context.lookup(GakkenConstant.getProperty("spring.mail.jndi-name"));
                MimeMessage message = null;
                message = new MimeMessage(mailSession);
                MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
                // From
                helper.setFrom(mailSession.getProperty("mail.from"));
                // To
                helper.setTo(to);
                // タイトル
                helper.setSubject(StringUtils.isBlank(title)?"タイトル":title);
                helper.setText(content, true);
                for(String fileUrl : attachUrl){
                    File file = new File(fileUrl);
                    helper.addAttachment(file.getName(), file);
                }
                // 送信
                Transport.send(message);
            }
        } catch (Exception e) {
            logger.error("sendMail:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

