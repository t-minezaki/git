/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.modules.sys.entity.SysAsyncTaskEntity;

import java.util.Map;

/**
 * 非同期タスクマスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
public interface SysAsyncTaskService extends IService<SysAsyncTaskEntity> {

    /**
     * ページ処理
     * @param params　ページパラメータ
     * @return １ページ
     */
    PageUtils queryPage(Map<String, Object> params);
    
    /**
     * ステートメントデータの更新処理
     * @param entity　非同期タスクマスタ
     * @return 更新結果
     */
    boolean saveStatements(SysAsyncTaskEntity entity);

}
