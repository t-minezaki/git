/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F05003Dto;
import jp.learningpark.modules.manager.dto.F05003DtoIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F05003_知らせ編集画面 Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/02/26 : gong: 新規<br />
 * 2019/06/18 : hujiaxing: mod<br />
 * @version 1.0
 */
@Mapper
public interface F05003Dao {

    /**
     * <p>該当組織対応する生徒、保護者リストを取得する</p>
     *
     * @param orgIdList 組織IDList
     * @return
     */
    List<F05003Dto> selectStuAndGuardList(@Param("orgIdList") List<String> orgIdList);

    /**
     * <p>get student dto list by student id list</p>
     * @param stuIdList  student id list
     * @return
     */
    List<F05003DtoIn> selectStuByIdList(@Param("noticeId") Integer noticeId);
}
