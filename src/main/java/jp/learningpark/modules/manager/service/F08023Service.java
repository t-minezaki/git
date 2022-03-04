package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;

import java.io.File;
import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)ckl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/11/5 ： NWT)ckl ： 新規作成
 * @date 2020/11/5 10:55
 */
public interface F08023Service {
    /**
     * 保護者配信対象データ
     * @param orgId         組織ID
     * @param div           閲覧状況区分
     * @param guidReprDeliverCd    指導報告書配信コード
     * @return
     */
    R init(String orgId, String div,String guidReprDeliverCd);

    /**
     * ファイルを取得
     * @param orgId         組織ID
     * @param guidReprDeliverCd      指導報告書配信コード
     * @param div           閲覧状況区分
     * @param stuIdList     生徒IDリスト
     * @return
     */
    File getDownloadFile(String orgId, String guidReprDeliverCd, String div, List<String> stuIdList);
}
