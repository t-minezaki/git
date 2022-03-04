package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;

import java.io.File;
import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/11/3 ： NWT)hxl ： 新規作成
 * @date 2020/11/3 11:55
 */
public interface F08020Service {
    /**
     * 保護者配信対象データ
     * @param orgId         組織ID
     * @param openDiv       開封状況区分
     * @param readDiv       閲覧状況区分
     * @param noticeId      お知らせＩＤ
     * @return
     */
    R init(String openDiv, String orgId, String readDiv, Integer noticeId);

    /**
     * ファイルを取得
     * @param orgId         組織ID
     * @param openDiv       開封状況区分
     * @param readDiv       閲覧状況区分
     * @param noticeId      お知らせＩＤ
     * @param stuIdList     生徒IDリスト
     * @return
     */
    File getDownloadFile(String orgId, String openDiv, String readDiv, Integer noticeId, List<String> stuIdList);
}
