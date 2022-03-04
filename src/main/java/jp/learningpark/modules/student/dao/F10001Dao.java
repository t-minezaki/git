/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.student.dto.F10001Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>F10001 情報登録変更画面 Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/01/03 : gong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F10001Dao {
    /**
     * 生徒タイムプラン
     *
     * @param stuId 生徒ID
     * @param orgId 組織ID
     * @return StuMstDto(写真 　 生徒姓名 　 生徒ID 　 生年月日 　 性別 　 学校学年 　 塾)
     */
    F10001Dto selectByStuId(@Param("stuId") String stuId, @Param("orgId") String orgId);

    /**
     * <p>生徒が選択した教科書情報(stu_textb_choc_mst)</p>
     *
     * @param stuId 生徒id
     * @return 教科書Idリスト
     */
    List<String> selectTextbChocList(String stuId);

    /**
     * 更新内容
     * @param entity
     * @return
     */
    Integer updateStudentInfo(@Param("entity") Map<String, Object> entity);
}
