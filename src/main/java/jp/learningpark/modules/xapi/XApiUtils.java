package jp.learningpark.modules.xapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gov.adlnet.xapi.model.Account;
import gov.adlnet.xapi.model.Activity;
import gov.adlnet.xapi.model.ActivityDefinition;
import gov.adlnet.xapi.model.Actor;
import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.IStatementObject;
import gov.adlnet.xapi.model.Statement;
import gov.adlnet.xapi.model.Verb;
import gov.adlnet.xapi.model.adapters.ActorAdapter;
import gov.adlnet.xapi.model.adapters.StatementObjectAdapter;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.SpringContextUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.sys.entity.SysAsyncTaskEntity;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import jp.learningpark.modules.sys.manager.AsyncManager;
import jp.learningpark.modules.sys.manager.factory.AsyncFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * LRS ユーティリティクラス
 * 
 * @author NWT : GAOLI
 * 変更履歴 <br/>
 * 日付 : 2019/04/28  GAOLI: 新規
 * @version 1.0
 */
@Component
public class XApiUtils {
    private static Logger logger = LoggerFactory.getLogger(XApiUtils.class);
    /**
     * 基本リソース
     */
    @Value("${xapi.basicURI}")
    private String basicURI;
    
    /**
     * XAPIサーバーURL
     */
    @Value("${xapi.serverUrl}")
    private String serverUrl;
    
    /**
     * 基本Auth
     */
    @Value("${xapi.basicAuth}")
    private String basicAuth;

    private static String xapiBasicURI;
    private static String xapiServerUrl;
    private static String xapiBasicAuth;
    
    private static String XAPI_BASIC_URI = "XAPI_BASIC_URI";
    private static String XAPI_BASIC_AUTH = "XAPI_BASIC_AUTH";
    private static String XAPI_SERVER_URL = "XAPI_SERVER_URL";

    @PostConstruct
    public void init() {
        xapiBasicURI = this.basicURI;
        xapiServerUrl = this.serverUrl;
        xapiBasicAuth = this.basicAuth;
        if ("prod".equals(SpringContextUtils.getActiveProfile())) {
            if (StringUtils.isEmpty(System.getProperty(XAPI_BASIC_URI))) {
                logger.error(MessageUtils.getMessage("MSGCOMD0001", XAPI_BASIC_URI));
            }
            if (StringUtils.isEmpty(System.getProperty(XAPI_BASIC_AUTH))) {
                logger.error(MessageUtils.getMessage("MSGCOMD0001", XAPI_BASIC_AUTH));
            }
            if (StringUtils.isEmpty(System.getProperty(XAPI_SERVER_URL))) {
                logger.error(MessageUtils.getMessage("MSGCOMD0001", XAPI_SERVER_URL));
            }
        }
    }

    /**
     * XAPIデータを保存する
     * 
     * @param verb 内容
     * @param object 内容
     * @throws Exception 
     */
    public static void saveStatement(Verb verb, Activity object) throws Exception {
        saveStatement(verb, object, null);
    }

    /**
     * XAPIデータを保存する
     * 
     * @param context 内容
     * @throws Exception 
     */
    public static void saveStatement(Verb verb, Activity object, Extensions extensions){
        if (verb == null || object == null) {
            Logger logger = LoggerFactory.getLogger(XApiUtils.class);
            logger.debug("saveStatement param verb OR activity is null");
            return;
        }
        String baseUrl = getBasicURI();

        SysUserEntity user = ShiroUtils.getUserEntity();

        SysAsyncTaskEntity entity = new SysAsyncTaskEntity();

        // 状態
        entity.setStatus(GakkenConstant.STATUS_FLG.NEW.getValue());

        // タスクタイプ
        entity.setTaskType(GakkenConstant.TASK_TYPE.STATEMENT.getValue());

        // ステートメント
        Statement statement = new Statement();
        // 主語
        Agent agent = new Agent();

        Account account = new Account();
        account.setHomePage(baseUrl + "/account/" + user.getId());
        account.setName(user.getAfterUsrId());
        agent.setName(user.getUsrNm());
        agent.setAccount(account);
        // アクタ
        statement.setActor(agent);
        // 動詞
        statement.setVerb(verb);
        // Activity
        if (object.getDefinition() == null) {
            object.setDefinition(new ActivityDefinition());
        }
        if (extensions != null && !extensions.isEmpty()) {
            object.getDefinition().setExtensions(extensions);
        }
        statement.setObject(object);

        Gson gson = getDecoder();
        // JSONコンテキストを設定する
        entity.setContext(gson.toJson(statement));
        // 非同期タスクを実行する
        AsyncManager.me().execute(AsyncFactory.saveStatements(entity));
    }

    /**
     * デコーダを取得する
     * 
     * @param context 内容
     * @throws Exception 
     */
    private static Gson getDecoder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Actor.class, new ActorAdapter());
        builder.registerTypeAdapter(IStatementObject.class, new StatementObjectAdapter());
        return builder.create();
    }

    /**
     * XAPI基本リソースを取得する
     * 
     * @return xapiBasicURI
     */
    public static String getBasicURI() {
        return System.getProperty(XAPI_BASIC_URI, xapiBasicURI);
    }
    
    /**
     * XAPIサーバーURLを取得する
     * 
     * @return xapiServerUrl
     */
    public static String getServerUrl() {
        return System.getProperty(XAPI_SERVER_URL, xapiServerUrl);
    }
    
    /**
     * XAPI基本Authを取得する
     * 
     * @return xapiBasicAuth
     */
    public static String getBasicAuth() {
        return System.getProperty(XAPI_BASIC_AUTH, xapiBasicAuth);
    }
}
