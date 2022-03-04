/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.dao;

import jp.learningpark.modules.com.dto.F40018Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * <p></p >
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/3 : xie: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F40018Dao {

    F40018Dto authenticationPassword(@Param("userId") String userId);

    /**
     * 更新
     * @param userId
     * @param mailad
     * @param roleDiv
     * @param afterUsrId
     */
    void update(@Param("userId") String userId, @Param("mailad") String mailad, @Param("roleDiv") String roleDiv, @Param("afterUsrId")String afterUsrId);
}
