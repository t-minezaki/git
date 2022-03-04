/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.modules.guard.dao.F30002Dao;
import jp.learningpark.modules.guard.dto.F30002Dto;
import jp.learningpark.modules.guard.service.F30002Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>F30002_子供選択画面 ServiceImpl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/12 : gong: 新規<br />
 * @version 1.0
 */
@Service
public class F30002ServiceImpl implements F30002Service {

    /**
     * <p>F30002_子供選択画面 Dao</p>
     */
    @Autowired
    private F30002Dao f30002Dao;

    /**
     * <p>塾学習期間IDの取得</p>
     *
     * @param stuId 生徒Id
     * @return
     */
    @Override
    public Integer getCrmLearnPrdId(String stuId) {
        return f30002Dao.getCrmLearnPrdId(stuId);
    }

    /**
     * <p>保護者の全子供を取得し</p>
     *
     * @param guard 保護者id
     * @return
     */
    @Override
    public List<F30002Dto> getAboutStus(String guard) {
        return f30002Dao.selectAboutStus(guard);
    }

    /**
     * <p>保護者の全子供を取得し</p>
     *
     * @param guards 保護者IDリスト
     * @return
     */
    @Override
    public List<F30002Dto> getStudents(String guards) {
        return f30002Dao.selectStudents(guards);
    }

    /**
     * 保護者を取得
     * @param stuId
     * @return
     */
    @Override
    public List<F30002Dto> selectGuardInfo(String stuId) {
        return f30002Dao.selectGuardInfo(stuId);
    }

    /**
     * QRコード暫定対応
     * @param guards 保護者IDリスト
     * @return
     */
    @Override
    public List<F30002Dto> getStudentsCount(String guards) {
        return f30002Dao.selectStudentsCount(guards);
    }

    // 2021/12/14 MANAMIRU1-785 add Start
    /**
     * getBrandcdByStu
     * @param stuId 生徒Id
     * @return
     */
    @Override
    public String getBrandcdByStu(String stuId) {
        return f30002Dao.getBrandcdByStu(stuId);
    }
    // 2021/12/14 MANAMIRU1-785 add end
}
