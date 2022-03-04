/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.common.entity.MstCrmschLearnPrdEntity;
import jp.learningpark.modules.common.entity.MstTextbEntity;
import jp.learningpark.modules.student.dto.F10003Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>F10003Service 生徒教科書選択画面 Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/04 : gong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F10003Dao {
    /**
     * <p>当生徒当学年の教科ごとの塾の教科書情報を取得</p>
     *
     * @param stuId 生徒Id
     * @param orgId 塾Id
     * @return
     */
    List<F10003Dto> selectTextbDtoListOfSchByStuId(@Param("stuId") String stuId, @Param("orgId") String orgId);

    /**
     * <p>生徒選択した教科書Idリストを取得</p>
     *
     * @param map
     * @return 教科Idリスト
     */
    List<Integer> selectCurrentChocList(Map<String, Object> map);

    /**
     * <p>1.2 塾学習期間IDの取得</p>
     *
     * @param orgId 塾Id
     * @return
     */
    MstCrmschLearnPrdEntity selectCrmschLearnPrdId(String orgId);

    /**
     * <p>生徒選択した教科書リストを取得</p>
     *
     * @param map
     * @return TexmstEntityリスト
     */
    List<MstTextbEntity> selectTextbListOfStuByStuIdAndOrgIdAndSchyDiv(Map<String, Object> map);

    /**
     * <p>生徒ウィークリー計画実績設定リストを取得する</p>
     *
     * @param stuId    生徒ID
     * @param textbIds 教科書IDリスト
     * @return 業務エラー判定リスト
     */
    List<F10003Dto> selectAboutWeekly(@Param("stuId") String stuId, @Param("textbIds") List<Integer> textbIds);

    /**
     * 生徒タームプラン設定データをすべて物理削除する。
     *
     * @param stuId   生徒ID
     * @param textbId 教科書ID
     * @return
     */
    int deleteTerm(@Param("stuId") String stuId, @Param("textbId") Integer textbId);

    /**
     * <p>教科書デフォルトターム情報から単元情報を取得</p>
     *
     * @param orgId            塾Id
     * @param textbId          教科書ID
     * @param crmschLearnPrdId 塾学習期間ID
     * @return
     */
    List<F10003Dto> selectTexdeffExtend(@Param("orgId") String orgId, @Param("textbId") Integer textbId, @Param("crmschLearnPrdId") Integer crmschLearnPrdId);
}
