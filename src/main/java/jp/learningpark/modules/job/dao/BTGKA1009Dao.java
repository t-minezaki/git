/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.job.dao;

import jp.learningpark.modules.common.entity.BookendSendHstEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 本棚入会日次バッチDao
 *
 * @author NWT)ckl
 * @date 2021/3/22 16:51
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2021/3/22 ： NWT)ckl ： 新規作成
 */
@Mapper
public interface BTGKA1009Dao {

    /**
     * 生徒の本棚連携対象を取得
     * @return
     */
    List<MstUsrEntity> selectUsrList(@Param("gidpkArr") List<String> gidpkArr);

    /**
     * ユーザ基本マスタ更新
     * @param updUsr
     * @param gidpk
     * @return
     */
    Integer bookSend(@Param("updUsr")String updUsr, @Param("gidpk") String gidpk);

    /**
     * 本棚連携履歴を取得
     */
    List<BookendSendHstEntity> selectBookSendHst();

    /**
     * 入会中のデータを更新
     */
    Integer updateBookSended(@Param("gidpkArr") List<String> gidpkArr);
}
