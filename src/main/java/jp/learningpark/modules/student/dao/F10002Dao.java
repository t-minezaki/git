/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.student.dto.F10002Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>F10002 生徒Myページ画面 Dao</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/14 : hujunjie: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F10002Dao {

    /**
     * <p>当生徒の基本情報を取得</p>
     *
     * @param stuId 生徒ID
     * @param orgId 組織ID
     * @return 生徒情報
     */
    F10002Dto selectStuInfo(@Param("stuId") String stuId, @Param("orgId") String orgId);
}
