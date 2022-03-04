/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.student.dto.F10004Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F10004 スケジュールブロックの色設定画面 Dao</p >
 *
 * @author NWT : zhaoxiaoqin <br />
 * @version 1.0
 */
@Mapper
public interface F10004Dao {

    /**
     * <p>当生徒の基本情報を取得</p>
     *
     * @param stuId 生徒ID
     * @param orgId 組織ID
     * @return List<F10004Dto>
     */
    List<F10004Dto> selectSubjtInfo(@Param("stuId") String stuId, @Param("orgId") String orgId);
    /**
     * 生徒フリーブロック情報取得
     * @return
     */
    List<F10004Dto> selectFreeInfo(@Param("stuId") String stuId);
    /**
     * 生徒固定ブロック情報取得
     * @return
     */
    List<F10004Dto> selectFixedInfo(@Param("stuId") String stuId);
    /**
     * 生徒固定ブロック情報取得
     * @param stuId
     * @return
     */
    List<F10004Dto> selectOtherInfo(@Param("stuId") String stuId);
}
