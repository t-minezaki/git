package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.common.entity.MstOrgEntity;

import java.util.List;

/**
 * <p>F04014_マナミルチャンネル参照画面</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/02/25 : yang: 新規<br />
 * @version 6.0
 */
public interface F04014Service {
    /**
     * 全体の送信組織を抽出
     * @param noticeId
     * @return
     */
    List<MstOrgEntity> getOrgList(Integer noticeId);
}
