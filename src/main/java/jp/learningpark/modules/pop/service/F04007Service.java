package jp.learningpark.modules.pop.service;

import jp.learningpark.modules.pop.dto.F04007Dto;

import java.util.List;

/**
 * <p>保護者既読未読詳細一覧画面（ニュース）</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/06 : xiong: 新規<br />
 * @version 1.0
 */
public interface F04007Service {
    /**
     * お知らせ配信先、組織情報、保護者お知らせ閲覧状況より、取得する。
     * @param pageSize 1ページのMAX件数30件
     * @param noticeId セッションデータ．ＩＤ
     * @param orgId    セッションデータ．組織ＩＤ
     * @param readFlg  既読未読フラグ
     * @return
     */
    List<F04007Dto> selectGuardStuById(Integer noticeId, String orgId, Integer pageSize, String readFlg, Integer limit);

    /**
     * @param noticeId セッションデータ．ＩＤ
     * @param orgId    セッションデータ．組織ＩＤ
     * @param readFlg  既読未読フラグ
     * @return
     */
    Integer selectGuardCount(Integer noticeId, String orgId, String readFlg);
}
