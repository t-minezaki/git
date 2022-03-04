package jp.learningpark.modules.com.dao;

import jp.learningpark.modules.com.dto.F40016Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>教室選択画面(生徒スマホ画面)</p >
 * @author NWT : lpp <br />
 * 変更履歴 <br />
 * 2020/08/05 : lpp: 新規<br />
 * @version 8.0
 */
@Mapper
public interface F40016Dao {

    /**
     *組織データの情報を取得する
     * @param afterUsrId セッションデータ．ログインID
     * @return
     */
    List<F40016Dto> search(@Param("afterUsrId") String afterUsrId);


    /**
     * @param afterUsrId セッションデータ．ログインID
     * @param pageSize ページあたりのアイテム数
     * @return
     */
    List<F40016Dto> searchMore(@Param("afterUsrId") String afterUsrId, @Param("pageSize") Integer pageSize,@Param("orgId")String orgId,@Param("orgName")String orgName);
}
