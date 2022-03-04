/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.modules.common.entity.EntryExitHstEntity;

import java.util.Map;

/**
 * 入退室履歴
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
public interface EntryExitHstService extends IService<EntryExitHstEntity> {

    /**
     * ページ処理
     * @param params　ページパラメータ
     * @return １ページ
     */
    PageUtils queryPage(Map<String, Object> params);

}