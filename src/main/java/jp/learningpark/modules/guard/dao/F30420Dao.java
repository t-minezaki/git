/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dao;

import jp.learningpark.modules.guard.dto.F30420Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>チャンネル詳細</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/20 : zpa: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F30420Dao {
    F30420Dto init(@Param("noticeId") Integer noticeId,@Param("guardId") String guardId,@Param("stuId")String stuId);
}
