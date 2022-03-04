package jp.learningpark.modules.com.service;

import jp.learningpark.framework.utils.R;

/**
 * <p>F40008_パスワード変更画面 Service</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/03 : xiong: 新規<br />
 * @version 1.0
 */
public interface F40008Service {

    /**
     * パスワード再設定
     * @param newPwd 新しいパスワード
     * @param oldPwd 古いパスワード
     * @param imageId 変更後ユーザＩＤ
     * @param updDatime 更新日時
     * @return
     */
    R resetPwd(String newPwd, String oldPwd, String imageId, String updDatime,String gidFlg);

    /**
     * 初期化
     * @return
     */
    R getInit();

    /**
     *
     * @param afterId
     * @return
     */
    R updateStatus(String afterId, String updDatime);

    /**
     *  子供個数の取得
     * @param afterUsrId
     * @return
     */
    R getStuNumber(String afterUsrId);

//    /**
//     *
//     * @param id
//     * @return
//     */
//    R judgeId(String id);
}
