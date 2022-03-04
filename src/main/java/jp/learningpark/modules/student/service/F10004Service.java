/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service;

import jp.learningpark.modules.student.dto.F10004Dto;

import java.util.List;

/**
 * <p>F10004 スケジュールブロックの色設定画面Service</p >
 *
 * @author NWT : zhaoxiaoqin <br />
 * @version 1.0
 */
public interface F10004Service {
    /**
     * <p>当生徒の基本情報を取得</p>
     *
     * @param stuId 生徒ID
     * @param orgId 組織ID
     * @return List<F10004Dto>
     */
    List<F10004Dto> selectSubjtInfo(String stuId, String orgId);

    /**
     * 生徒フリーブロック情報取得
     * @return
     */
    List<F10004Dto> selectFreeInfo(String stuId);

    /**
     * 生徒固定ブロック情報取得
     * @return
     */
    List<F10004Dto> selectFixedInfo(String stuId);

    /**
     * 生徒その他ブロック情報取得
     * @param stuId
     * @return
     */
    List<F10004Dto> selectOtherInfo(String stuId);
}
