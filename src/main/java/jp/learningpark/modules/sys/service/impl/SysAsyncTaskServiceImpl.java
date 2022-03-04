/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.Query;
import jp.learningpark.modules.sys.dao.SysAsyncTaskDao;
import jp.learningpark.modules.sys.entity.SysAsyncTaskEntity;
import jp.learningpark.modules.sys.service.SysAsyncTaskService;
import jp.learningpark.modules.xapi.XApiClient;
import jp.learningpark.modules.xapi.XApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("sysAsyncTaskService")
@Transactional
public class SysAsyncTaskServiceImpl extends ServiceImpl<SysAsyncTaskDao, SysAsyncTaskEntity> implements SysAsyncTaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysAsyncTaskEntity> page = baseMapper.selectPage(new Query<SysAsyncTaskEntity>(params).getPage(),
                new QueryWrapper<SysAsyncTaskEntity>());

        return new PageUtils(page);
    }

    /**
     * ステートメントデータの更新処理
     * @param entity　非同期タスクマスタ
     * @return 更新結果
     */
    @Override
    public boolean saveStatements(SysAsyncTaskEntity entity) {

        // xapiクライアント
        try {
            // XAPIサーバURL
            String serverUrl= XApiUtils.getServerUrl();
             // 認証ヘッダー
            String basicAuth = XApiUtils.getBasicAuth();

            XApiClient client = new XApiClient(serverUrl, basicAuth);
            // データを送信する
            String result = client.postStatement(entity.getContext());
            // 状態は1:送信完了を設定する
            entity.setStatus(1);
            // LRS ID
            entity.setLrsId(result);
        } catch (Exception e) {
            // 状態は1:送信完了を設定する
            entity.setStatus(2);
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage());
            }
        }
        // データの更新処理
        return this.save(entity);
    }
}
