/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;

import jp.learningpark.modules.student.dao.F10004Dao;
import jp.learningpark.modules.student.dto.F10004Dto;
import jp.learningpark.modules.student.service.F10004Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>F10004 生徒Myページ画面ServiceImpl</p >
 *
 * @author NWT : zhaoxiaoqin <br />
 * @version 1.0
 */
@Service
public class F10004ServiceImpl implements F10004Service {

    /**
     * スケジュールブロックの色設定画面 Dao
     */
    @Autowired
    private F10004Dao f10004Dao;

    /**
     * <p>当生徒の基本情報を取得</p>
     *
     * @param stuId 生徒ID
     * @param orgId 組織ID
     * @return List<F10004Dto>
     */
    @Override
    public List<F10004Dto> selectSubjtInfo(String stuId, String orgId) {

        return f10004Dao.selectSubjtInfo(stuId,orgId);
    }
    /**
     * 生徒フリーブロック情報取得
     * @return
     */
    @Override
    public List<F10004Dto> selectFreeInfo(String stuId) {

        return f10004Dao.selectFreeInfo(stuId);
    }
    /**
     * 生徒固定ブロック情報取得
     * @return
     */
    @Override
    public List<F10004Dto> selectFixedInfo(String stuId) {

        return f10004Dao.selectFixedInfo(stuId);
    }
    /**
     * 生徒その他ブロック情報取得
     * @param stuId
     * @return
     */
    @Override
    public List<F10004Dto> selectOtherInfo(String stuId) {

        return f10004Dao.selectOtherInfo(stuId);
    }

}
