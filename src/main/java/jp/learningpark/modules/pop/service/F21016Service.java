package jp.learningpark.modules.pop.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.pop.dto.F21016SchyDto;

import java.util.List;

/**
 * <p>
 * F21016Service
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/21 ： NWT)hxl ： 新規作成
 * @date 2019/11/21 9:35
 */
public interface F21016Service {
    /**
     * 保護者情報を取得する
     *
     * @param orgId     組織ID
     * @param guardName 画面．検索条件．姓名
     * @return
     */
    R getGuardList(String orgId, String guardName);

    /**
     * 生徒情報を取得する
     *
     * @param orgId   組織ID
     * @param schy    学年
     * @param stuName 生徒名
     * @return
     */
    R getStudentList(String orgId, String schy, String stuName);

    /**
     * 学年の区別を取得する
     *
     * @return
     */
    List<F21016SchyDto> getSchy();
}
