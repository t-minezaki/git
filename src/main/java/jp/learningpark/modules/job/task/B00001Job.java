/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.job.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.modules.sys.entity.SysAsyncTaskEntity;
import jp.learningpark.modules.sys.service.SysAsyncTaskService;
import jp.learningpark.modules.xapi.XApiClient;
import jp.learningpark.modules.xapi.XApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * XAPI連携処理に対する処置を行うJobです。
 *
 * @author NWT : GAOLI
 * 変更履歴 <br/>
 * 日付 : 2019/04/24  GAOLI: 新規
 * @version 1.0
 */
@Component("b00001Job")
public class B00001Job {
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 非同期タスクマスタ
     */
    @Autowired
    private SysAsyncTaskService sysAsyncTaskService;
    

    
    /**
     * ジョブを実行する。
     */
    public void execute(String params) {
        XApiClient client = null;
        try {
            /* 2021/09/16 manamiru1-772 cuikl edit start */
            logger.debug("B00001JOB START" + params);
            /* 2021/09/16 manamiru1-772 cuikl edit end */
            // 状態 0 新規と2 エラーのデータを取得する
            List<SysAsyncTaskEntity> dataList = sysAsyncTaskService.list(new QueryWrapper<SysAsyncTaskEntity>().eq("status", 0).or().eq("status", 2));
            
            // データなしの場合、バッチを終了する
            if (dataList.isEmpty()) {
                return;
            }
            // XAPIサーバURL
            String serverUrl= XApiUtils.getServerUrl();
             // 認証ヘッダー
            String basicAuth = XApiUtils.getBasicAuth();
            // xapiクライアント
            client = new XApiClient(serverUrl, basicAuth);
            for (SysAsyncTaskEntity data:dataList) {
                try {
                    // データを送信する
                    String result = client.postStatement(data.getContext());
                    // 状態は1:送信完了を設定する
                    data.setStatus(1);
                    /* 2021/09/16 manamiru1-772 cuikl edit start */
                    logger.debug("送信完了! XAPI ID:" + result);
                    /* 2021/09/16 manamiru1-772 cuikl edit end */
                } catch (Exception e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("ERROR ID:" + data.getId());
                        logger.error("ERROR CONTEXT:" + data.getContext());
                        logger.error(e.getMessage());
                    }
                    // 状態は2:送信失敗を設定する
                    data.setStatus(2);
                } finally {
                    sysAsyncTaskService.updateById(data);
                }
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage());
            }
        } finally {
            logger.info("END");
        }
    }


}
