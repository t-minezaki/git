package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;

import java.io.File;
import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)wyh
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/11/9 ： NWT)wyh ： 新規作成
 * @date 2020/11/9 18:55
 */
public interface F08021Service {
    /**
     * 保護者配信対象データ
     * @param orgId         組織ID
     * @param openDiv       開封状況区分
     * @param readDiv       閲覧状況区分
     * @param messageId      お知らせＩＤ
     *      * @return
     */
    R init(String openDiv, String orgId, String readDiv, Integer messageId);
    /**
     * 適格なダウンロードデータを取得する
     * @param orgId         組織ID
     * @param openDiv       開封状況区分
     * @param readDiv       閲覧状況区分
     * @param messageId      お知らせＩＤ
     * * @return
     */
    File getDownloadFile(String orgId, String openDiv, String readDiv,Integer messageId, List<String> stuIdList);
}
