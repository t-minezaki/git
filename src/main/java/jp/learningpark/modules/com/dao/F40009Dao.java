/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.dao;

import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>F40009_ログインID解除画面 Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/02/27 : gong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F40009Dao {
    /**
     * <p>画面．ＩＤがユーザ基本マスタに存在すること。</p>
     *
     * @param afterUsrId 変更後ユーザＩＤ
     * @param brandCd    URLのブランドコード
     * @return
     */
    MstUsrEntity selectOne(@Param("afterUsrId") String afterUsrId, @Param("brandCd") String brandCd);

    /**
     * <p>①　ユーザ基本マスタ．ロール ＝ 「生徒」の場合、</p>
     *
     * @param usrId ユーザＩＤ
     * @return 生徒の情報
     */
    MstStuEntity selectWithGuard(@Param("usrId") String usrId, @Param("email") String email);
}
