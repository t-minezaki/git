/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F05004Dto;
import jp.learningpark.modules.manager.dto.F05004DtoStu;

import java.util.List;

/**
 * <p>F05004_知らせ照会画面 service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/3/14 : gong: 新規<br />
 * 2019/06/18 : hujiaxing: mod<br />
 * @version 1.0
 */
public interface F05004Service {
    /**
     * 画面．配信先組織を取得し表示する。
     *
     * @param noticeId お知らせＩＤ
     * @return
     */
    List<F05004Dto> getOrgList(Integer noticeId,String orgId);

    /**
     * get student list by notice id (and org id)
     * @param noticeId お知らせＩＤ
     * @param orgId 組織ID
     * @return
     */
    List<F05004DtoStu> getStuListByNoticeId(Integer noticeId ,String orgId);
}
