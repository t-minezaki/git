package jp.learningpark.framework.utils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import jp.learningpark.modules.job.service.impl.BTGKA1006ServiceImpl;
import org.apache.commons.math3.analysis.function.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
/**
 * <p>google sheet api utils</p>
 *
 * @author NWT : LiGuangXin <br />
 * 2019/12/23 : tan: 新規<br />
 * @version 1.0
 */
public class GoogleSheetApiUtils {
    private static final Logger log = LoggerFactory.getLogger(BTGKA1006ServiceImpl.class);
    private static final String APPLICATION_NAME = "Google";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "classpath:/googleSheetApiClient";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/googleSheetApiClient/client_secret_jp.json";
    /**
     * 承認された資格情報オブジェクトを作成します。
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return 承認資格証明書オブジェクト。
     * @throws IOException 書類が見つからないなら。
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT,String useId) throws IOException {
        //クライアントの秘密を読み込みます。
        log.info("START クライアントの秘密を読み込みます。");
        InputStream in = GoogleSheetApiUtils.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        log.info("START  ビルドフローとトリガのユーザー認証要求。");
        // ビルドフローとトリガのユーザー認証要求。
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(ResourceUtils.getFile(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        log.info("START GET-PORT ");
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setHost("127.0.0.1").setPort(8081).build();
        log.info("host:" + receiver.getHost());
        log.info("port:"+ receiver.getPort());
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize(useId);
    }
    /**
     * 生徒データをスプレッドシートに印刷する
     */
    public static List<List<Object>> googleSheet(String usrId, String sheetId, String range) throws IOException, GeneralSecurityException {
        // 新しい承認APIクライアントサービスを構築します。
        log.info("START 新しい承認APIクライアントサービスを構築します。");
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT,usrId))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange response = service.spreadsheets().values()
                .get(sheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        return values;
    }
}
