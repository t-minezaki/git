/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstManagerEntity;

/**
 * <p>F00002 管理者基本情報登録画面Service</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/11 : xiong: 新規<br />
 * @version 1.0
 */
public interface F00002Service {

    /**
     * 初期化
     * @return
     */
    R initial();

    /**
     * 管理者基本マスタへ更新する
     * @param mstManagerEntity 管理者基本マスタへ
     * @param updateTime 更新日時
     * @return
     */
    R updateManager(MstManagerEntity mstManagerEntity,String updateTime);
}
