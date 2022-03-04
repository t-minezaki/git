/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F05002Dto;
import jp.learningpark.modules.manager.dto.F05002DtoIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F05002 知らせ新規画面 Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/02/26 : gong: 新規<br />
 * 2019/06/24 : hujiaxing: mod<br />
 * @version 1.0
 */
@Mapper
public interface F05002Dao {

    /**
     * <p>該当組織対応する生徒、保護者リストを取得する</p>
     *
     * @param stuIdList 組織IDList
     * @return
     */
    List<F05002Dto> selectStuAndGuardList(@Param("stuIdList") List<String> stuIdList);

    /**
     * <p>生徒IDListによるdtotListの取得</p>
     *
     * @param stuIdList 生徒IDList
     * @return
     */
    List<F05002DtoIn> selectStuByIdList(@Param("stuIdList") List<String> stuIdList);
}
