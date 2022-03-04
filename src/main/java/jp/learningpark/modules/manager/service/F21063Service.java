package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstMessageEntity;
import jp.learningpark.modules.manager.dto.F21063Dto;
import jp.learningpark.modules.manager.dto.F21063DtoIn;

import java.util.List;

/**
 * <p>F21063_メッセージ作成画面 Service</p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/20 ： NWT)hxl ： 新規作成
 * 2020/11/11 ： NWT)文 ： 要件変更
 */
public interface F21063Service {

    /**
     * get student list by student id list
     * @param stuIdList student id list
     * @return
     */
    List<F21063DtoIn> selectStuByIdList(List<String> stuIdList,Integer page,Integer limit);
    /**
     * get student list by student id list
     * @param stuIdList student id list
     * @return
     */
    List<F21063DtoIn> selectStuByIdListCount(List<String> stuIdList);
    /**
     * 登録処理
     *
     * @param dto
     * @param messageCont 　メッセージの内容
     * @param orgId      組織ID
     * @return
     */
    R doInsert(F21063Dto dto, String messageCont, String orgId);

    R sendMessage(F21063Dto dto, MstMessageEntity mstMessageEntity);
}
