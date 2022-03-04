/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.dao;

import jp.learningpark.modules.com.dto.F40020Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * <p></p >
 *
 * @author NWT : wang <br />
 * 変更履歴 <br />
 * 2020/9/22 : wang: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F40020Dao {

    F40020Dto getDiv(@Param("userId") String userId);
//    ,@Param("roleDiv") String roleDiv,@Param("fstLoginFlg") String fstLoginFlg,@Param("systemKbn") String systemKbn,@Param("webUseFlg") String webUseFlg

}
