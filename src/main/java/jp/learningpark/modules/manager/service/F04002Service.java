/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F04002Dto;
import jp.learningpark.modules.manager.dto.F04002DtoIn;

import java.util.List;

/**
 * <p>F04002 塾ニュース新規画面 Service</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/06 : wen: 新規<br />
 * @version 1.0
 */
public interface F04002Service {

    /**
     *
     * @param stuIdList
     * @return
     */
    List<F04002Dto> getStuList(List<String> stuIdList);

    /**
     *
     * @param stuIdList
     * @return
     */
    List<F04002DtoIn> getList(List<String> stuIdList);

    /**
     *
     * @param dto
     * @param noticeCont
     * @param orgId
     * @return
     */
    R doInsert(F04002Dto dto, String noticeCont, String orgId);

}
