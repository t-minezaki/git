package jp.learningpark.modules.com.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>F40006 学研コミュニケーションアプリ Service</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2018/12/28 : xiong: 新規<br />
 * @version 1.0
 */
public interface F40009Service {

    /**
     * <p>画面．ＩＤがユーザ基本マスタに存在すること。</p>
     *
     * @param afterUsrId 変更後ユーザＩＤ
     * @param brandCd    URLのブランドコード
     * @return
     */
    MstUsrEntity getOne(String afterUsrId, String brandCd);

    /**
     * <p>①　ユーザ基本マスタ．ロール ＝ 「生徒」の場合、</p>
     *
     * @param usrId ユーザＩＤ
     * @return 生徒の情報
     */
    MstStuEntity getWithGuard(String usrId, String email);

    /**
     * 送信する
     *
     * @param id
     * @param userId
     * @param userName
     * @param email
     * @return
     */
    R postEmail(String id, String userId, String userName, String email, String brandCd,String uuid, HttpServletRequest request);


}
