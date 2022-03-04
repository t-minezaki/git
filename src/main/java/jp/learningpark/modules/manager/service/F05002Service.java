/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.manager.dto.F05002Dto;
import jp.learningpark.modules.manager.dto.F05002DtoIn;
import jp.learningpark.modules.manager.dto.pushMsgReceives;

import java.util.List;

/**
 * <p>F05002 知らせ新規画面 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/02/26 : gong: 新規<br />
 * 2019/06/14 : hujiaxing: mod<br />
 * @version 1.0
 */
public interface F05002Service {
    /**
     * <p>該当組織対応する生徒、保護者リストを取得する</p>
     *
     * @param stuIdList 組織IDList
     * @return
     */
    List<F05002Dto> getStuAndGuardList(List<String> stuIdList);

    /**
     * get student list by student id list
     * @param stuIdList student id list
     * @return
     */
    List<F05002DtoIn> selectStuByIdList(List<String> stuIdList);
    /**
     * 登録処理
     *
     * @param dto
     * @param noticeCont 　ニュースの内容
     * @param orgId      組織ID
     * @return
     */
    MstNoticeEntity doInsert(F05002Dto dto, String noticeCont, String orgId);

    /**
     * 通知pushToGuard
     */
    R sendMessage(pushMsgReceives receiver, MstNoticeEntity mstNoticeEntity);
}
