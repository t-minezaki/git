package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F21069Dto;
import jp.learningpark.modules.manager.dto.F21069DtoIn;

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
 * 2020/05/25 ： NWT)hxl ： 新規作成
 * @date 2020/05/25 11:13
 */
public interface F21069Service {

    /**
     * get admin list by admin id list
     *
     * @param adminIdList admin id list
     * @return
     */
    List<F21069DtoIn> selectStuByIdList(List<String> adminIdList,Integer page,Integer limit);
    /**
     * get total admin list by admin id list
     *
     * @param adminIdList admin id list
     * @return
     */
    List<F21069DtoIn> selectStuByIdListTotal(List<String> adminIdList);
    /**
     * 登録処理
     *
     * @param dto
     * @param messageCont 　メッセージの内容
     * @param orgId       組織ID
     * @return
     */
    R doInsert(F21069Dto dto, String messageCont, String orgId);
}
