/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.manager.dto.F00042Dto;
import jp.learningpark.modules.manager.dto.F00043Dto;

import java.util.List;

/**
 * <p>ユーザー基本情報修正画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/15 : xiong: 新規<br />
 * @version 1.0
 */
public interface F00043Service {

    /**
     * ユーザー基本情報
     * @param usrId 引渡データ．ユーザＩＤ
     * @return
     */
    F00043Dto getAfterUserId(String usrId);


    /**
     * ユーザー画面基本情報
     * @param f00042Dto  ユーザー画面基本情報
     * @return
     */
    R setInformation(F00042Dto f00042Dto);

    /**
     * 現在ログインしている管理者組織に応じて、対象のユーザー組織を取得します。
     * 取得したユーザー組織は、現在の管理者の組織レベル以下です。
     *
     * @param usrId 照会するターゲットユーザーのID
     * @return 現在ログインしている管理者以下の組織のコレクションを返します
     */
    List<MstOrgEntity> getUserOrgListByLoginUserOrgLevel(String usrId);
}
