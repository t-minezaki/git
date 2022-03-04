/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F04003Dto;
import jp.learningpark.modules.manager.dto.F04003DtoIn;

import java.util.List;

/**
 * <p>F04003 塾ニュース編集画面 Service</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/12 : wen: 新規<br />
 * @version 1.0
 */
public interface F04003Service {
    /**
     * noticeID
     * @param id
     * @return
     */
    List<F04003Dto> getStuList(Integer id);

    /**
     *
     * @param stuIdList
     * @return
     */
    List<F04003DtoIn> getList(List<String> stuIdList);

    /**
     *
     * @param dto
     * @param noticeCont
     * @param orgId
     * @return
     */
    R doInsert(F04003Dto dto, String noticeCont, String orgId);
}
