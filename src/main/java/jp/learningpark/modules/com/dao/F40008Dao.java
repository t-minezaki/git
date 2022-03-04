package jp.learningpark.modules.com.dao;

import jp.learningpark.modules.common.entity.MstUsrEntity;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>F40008_パスワード変更画面 Dao</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/03 : xiong: 新規<br />
 * @version 1.0
 */
public interface F40008Dao {

    /**
     * 対応IDを検索
     * @param id
     * @return
     */
    MstUsrEntity selectById(String id);
    /**
     * 対応IDを検索
     * @param brand
     * @param afterId
     * @return
     */
    List<MstUsrEntity> selectUsr(@Param("brand") String brand,@Param("afterId") String afterId);

    /**
     * 変更後ユーザＩＤの重複チェック
     * @param id
     * @return
     */
    Integer selectCountById(@Param("id") String id);

    /**
     *
     * @param oldAfterUserId
     * @param newAfterUserId
     * @return
     */
    Integer updateAfterUserId(@Param("oldAfterUserId")String oldAfterUserId, @Param("newAfterUserId")String newAfterUserId);
    /* 2021/03/09 manamiru4-33 add start */
    /**
     * 指導者管理コードを取得
     */
    Integer selectTchCod(@Param("usrId")String usrId);
    /* 2021/03/09 manamiru4-33 add end */
}
