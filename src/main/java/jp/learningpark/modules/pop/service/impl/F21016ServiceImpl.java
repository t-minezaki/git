package jp.learningpark.modules.pop.service.impl;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.pop.dao.F21016Dao;
import jp.learningpark.modules.pop.dto.F21016GuardDto;
import jp.learningpark.modules.pop.dto.F21016SchyDto;
import jp.learningpark.modules.pop.dto.F21016StudentDto;
import jp.learningpark.modules.pop.service.F21016Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * F21016ServiceImpl
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/21 ： NWT)hxl ： 新規作成
 * @date 2019/11/21 9:35
 */
@Service
public class F21016ServiceImpl implements F21016Service {
    /**
     * f21016Dao
     */
    @Autowired
    F21016Dao f21016Dao;

    /**
     * 保護者情報を取得する
     *
     * @param orgId     組織ID
     * @param guardName 画面．検索条件．姓名
     * @return
     */
    @Override
    public R getGuardList(String orgId, String guardName) {
        R info = R.ok();
        List<F21016GuardDto> guardList = f21016Dao.getGuardList(orgId, "%" + guardName + "%");
        info.put("guardList", guardList);
        return info;
    }

    /**
     * 生徒情報を取得する
     *
     * @param orgId   組織ID
     * @param schy    学年
     * @param stuName 生徒名
     * @return
     */
    @Override
    public R getStudentList(String orgId, String schy, String stuName) {
        R info = R.ok();
        List<F21016StudentDto> studnetList = f21016Dao.getStudentList(orgId, schy, "%" + stuName + "%");
        info.put("studentList", studnetList);
        return info;
    }

    /**
     * 学年の区別を取得する
     *
     * @return
     */
    @Override
    public List<F21016SchyDto> getSchy() {
        return f21016Dao.getSchy();
    }
}
