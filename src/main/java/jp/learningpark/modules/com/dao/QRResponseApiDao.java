package jp.learningpark.modules.com.dao;

import jp.learningpark.modules.com.dto.QRResponseApiDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/07/23 ： NWT)hxl ： 新規作成
 * @date 2020/07/23 14:10
 */
@Mapper
public interface QRResponseApiDao {
    /**
     * ユーザマスタから該当登録者の存在をチェック
     * @param userCd
     * @param manaFlag
     * @param brandCd
     * @param gidFlag
     * @param tchCdFlg
     * @return
     */
    QRResponseApiDto selectLoginUser(@Param("userCd") String userCd, @Param("manaFlag")String manaFlag, @Param("brandCd")String brandCd, @Param("gidFlag")String gidFlag,@Param("tchCdFlg")String tchCdFlg);

}
