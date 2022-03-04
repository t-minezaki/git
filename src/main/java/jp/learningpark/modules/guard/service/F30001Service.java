/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;


import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstGuardEntity;

/**
 * <p>F30001_保護者基本情報登録画面 Service</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/11 : xiong: 新規<br />
 * @version 1.0
 */
public interface F30001Service {

    /**
     * 初期化
     * @return
     */
    R getGuard();

    /**
     * 住所マスタより、郵便番号を元に、住所情報を取得し、住所エリアに表示される
     * @param addr
     * @return
     */
    R searchAddr(String addr);

    /**
     * 保護者基本マスタへ更新する
     * @param mstGuardEntity 保護者基本マスタ
     * @param updateTime 更新日時
     * @return
     */
    R submit(MstGuardEntity mstGuardEntity,String updateTime);
}
