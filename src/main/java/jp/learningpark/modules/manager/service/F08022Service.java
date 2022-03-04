package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>F08022_未読・未回答者送信一覧画面（インフォメーション） Service</p>
 *
 * @author NWT文
 * @version 9.0
 * 変更履歴:
 * 2020/11/3 ： NWT文 ： 新規作成
 */
public interface F08022Service {
    /**
     * 配信対象データ
     *
     * @param readDiv 閲覧状況区分
     * @param orgId セッションデータ．組織ＩＤ
     * @param messageId メッセージＩＤ
     * * @return
     */
    R init(String readDiv, String orgId, Integer messageId);

    /**
     * ファイルを取得
     *
     * @param orgId 組織ID
     * @param readDiv 閲覧状況区分
     * @param messageId ＩＤ
     * @param usrIdList 管理者OR 先生IDリスト
     * *  @return
     */
    File getDownloadFile(String orgId, String readDiv, Integer messageId, List<String> usrIdList) throws IOException;
}
