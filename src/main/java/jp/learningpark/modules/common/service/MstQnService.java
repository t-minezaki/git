/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.modules.common.entity.MstQnEntity;

import java.util.Map;

/**
 * 質問
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
public interface MstQnService extends IService<MstQnEntity> {

    /**
     * ページ処理
     * @param params　ページパラメータ
     * @return １ページ
     */
    PageUtils queryPage(Map<String, Object> params);

}
