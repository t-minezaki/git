/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F07003Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F70003_教科メンテナンス一覧画面 Dao</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/04/02 : wen: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F07003Dao {

    /**
     *
     * @param subjtCd  教科CD
     * @param startRow 開始位置
     * @param subjtNm  教科名
     * @param commonType ブロックタイプ（共通）
     * @param reviewType ブロックタイプ（復習系のみ）
     * @return
     */
    List<F07003Dto> selectMstCodDList(@Param("subjtCd") String subjtCd, @Param("subjtNm") String subjtNm,@Param("commonType") String commonType,@Param("reviewType") String reviewType, @Param("startRow") Integer startRow);

    /**
     * <p>コードマスタ情報記録数取得</p>
     *
     * @param subjtCd  教科CD
     * @param subjtNm  教科名
     * @param commonType ブロックタイプ（共通）
     * @param reviewType ブロックタイプ（復習系のみ）
     * @return　コードマスタ情報記録数
     */
    Integer selectMstCodDListCount(@Param("subjtCd") String subjtCd, @Param("subjtNm") String subjtNm,@Param("commonType") String commonType,@Param("reviewType") String reviewType);
}
