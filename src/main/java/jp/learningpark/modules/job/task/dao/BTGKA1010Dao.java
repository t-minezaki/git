package jp.learningpark.modules.job.task.dao;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.Date;
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
 * 2021/3/23 ： NWT)hxl ： 新規作成
 * @date 2021/3/23 11:25
 */
@Mapper
public interface BTGKA1010Dao {

    /**
     * 直近退会データを取得する
     * @return
     */
    List<String> selectExitedGidpkList();

    /**
     * 生徒の本棚連携対象を取得する
     * @param brandCd           ブランドコード
     * @param gidFlg            GIDフラグ
     * @param exitedGidpkList   退会中情報リスト
     * @param firstRunFlg       バッチ初回実施フラグ
     * @param preMonth          バッチ実施日時の月－1
     * @param roleDiv           roleDiv
     * @return  gidpk&delflg
     */
    List<String> selectGidpkAndDelFlgList(@Param("brandCd")String brandCd, @Param("gidFlg")String gidFlg, @Param("exitedGidpkList")List<String> exitedGidpkList,
                                          @Param("firstRunFlg")String firstRunFlg, @Param("preMonth")String preMonth, @Param("roleDiv")String roleDiv);

    /**
     * ユーザ基本マスタ更新
     * @param gidpk GIDPK
     * @return
     */
    int updateStudent(@Param("gidpk")String gidpk, @Param("updTime")Date updTime);

    /**
     * コードマスタ明細更新
     * @param updatime  更新日時
     * @return
     */
    int updateMstCodD(@Param("updatime")Date updatime);
}
