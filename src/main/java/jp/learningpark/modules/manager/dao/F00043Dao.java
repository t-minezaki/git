/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.manager.dto.F00043Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>ユーザー基本情報修正画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/15 : xiong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F00043Dao {

    /**
     * ユーザー基本情報
     *
     * @param usrId        引渡データ．ユーザＩＤ
     * @param ignoreDelete
     * @return
     */
    F00043Dto getAfterUserId(@Param("usrId") String usrId, @Param("ignoreDelete") boolean ignoreDelete);

    /**
     * 現在ログインしている管理者組織に応じて、対象のユーザー組織を取得します。
     * 取得したユーザー組織は、現在の管理者の組織レベル以下です。
     *
     * @param usrId             照会するターゲットユーザーのID
     * @param orgIds 現在の組織と下位組織のコレクション
     * @return 現在ログインしている管理者以下の組織のコレクションを返します
     */
    List<MstOrgEntity> getUserOrgList(@Param("usrId") String usrId,
                                      @Param("orgIds") List<String> orgIds);
}
