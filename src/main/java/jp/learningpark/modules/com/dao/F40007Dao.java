package jp.learningpark.modules.com.dao;

import jp.learningpark.modules.common.entity.MstUsrEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F40007_パスワード再設定画面 Dao</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2018/12/29 : xiong: 新規<br />
 * @version 1.0
 */
public interface F40007Dao {

    /**
     * パスワード再設定
     * @param afterUserId
     * @return
     */// 2021/10/25 manamiru1-776 cuikl edit
    List<MstUsrEntity> selectById(@Param("afterUserId") String afterUserId, @Param("gidFlg")String gidFlg);
}
