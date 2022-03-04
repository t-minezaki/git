/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F05003Dto;
import jp.learningpark.modules.manager.dto.F05003DtoIn;

import java.util.List;

/**
 * <p>F05003 知らせ編集画面 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/02/26 : gong: 新規<br />
 * 2019/06/18 : hujiaxing: mod<br />
 * @version 1.0
 */
public interface F05003Service {
    /**
     * <p>該当組織対応する生徒、保護者リストを取得する</p>
     *
     * @param orgIdList 組織IDList
     * @return
     */
    List<F05003Dto> getStuAndGuardList(List<String> orgIdList);

    /**
     * 登録処理
     *
     * @param dto
     * @param noticeCont 　ニュースの内容
     * @param orgId      組織ID
     * @return
     */
    R doInsert(F05003Dto dto, String noticeCont, String orgId);

    /**
     * <p>get student dto list by student id list</p>
     * @param stuIdList student id list
     * @return
     */
    List<F05003DtoIn> getStuByIdList( Integer noticeId);

//    List<F05003DtoIn> selectStuByIdList(List<String> stuIdList);
}
