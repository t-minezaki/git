package jp.learningpark.modules.com.dao;

import jp.learningpark.modules.com.dto.QRCodeApiDto;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/12/18 ： NWT)hxl ： 新規作成
 * @date 2019/12/18 15:54
 */
@Mapper
public interface QRCodeApiDao {

    //V8.0 new version
    /**
     * ユーザマスタから該当登録者の存在をチェック
     *
     * @param loginId           変更後ユーザーID
     * @param orgId             ブランドコード
     * @return
     */
    List<SysUserEntity> selectLoginUsers(@Param("afterUsrId") String loginId, @Param("orgId") String orgId, @Param("testFlag")String testFlag);

    /**
     * 生徒の存在チェック
     *
     * @param stuId     生徒ID
     * NWT　楊　2021/09/15　MANAMIRU1-782　Edit
     * @return
     */
    QRCodeApiDto selectOne(@Param("stuId") String stuId);

    /**
     * ユーザマスタから該当登録者の存在をチェック
     * NWT　楊　2021/07/02　MANAMIRU1-703　Edit
     */
    List<SysUserEntity> selectUserByLoginIdAndOrgId(@Param("userCd") String userCd, @Param("manaFlag")String manaFlag, @Param("orgId")String orgId, @Param("gidFlag")String gidFlag,@Param("tchCdFlg")String tchCdFlg, @Param("testFlag")String testFlag);
}
