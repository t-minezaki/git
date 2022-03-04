package jp.learningpark.modules.com.dao;

import jp.learningpark.modules.common.entity.MstUsrEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>F40006 学研コミュニケーションアプリ Dao</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2018/12/28 : xiong: 新規<br />
 * @version 1.0
 */
public interface F40006Dao {

    /**
     * 学生であるかどうかを判断する
     * @param id
     * @return
     */
    Integer selectCountStuById(@Param("id") String id,@Param("email")String email);

    /**
     * 保護者であるかどうかを判断する
     * @param id
     * @param email
     * @return
     */
    Integer selectCountGuaById(@Param("id") String id,@Param("email")String email);

    /**
     * メンターであるかどうかを判断する
     * @param id
     * @param email
     * @return
     */
    Integer selectCountMenById(@Param("id") String id,@Param("email")String email);

    /**
     * 管理者であるかどうかを判断する
     * @param id
     * @param email
     * @return
     */
    Integer selectCountManById(@Param("id") String id,@Param("email")String email);

    /**
     * 対応IDを検索
     * @param id
     * @param brandCd
     * @return
     */
    // 2021/10/13　MANAMIRU1-776 cuikl　Edit Start
    List<MstUsrEntity> selectRoleDivById(@Param("id") String id, @Param("manaFlag")String manaFlag, @Param("brandCd") String brandCd, @Param("gidpk")String gidpk);

    /**
     * 生徒に対応する保護者の変更後ユーザＩＤを取得
     */
    String selectGuardByStu(@Param("id")String id);

    /**
     * 指導者管理コードを取得
     */
    Integer selectTchCod(@Param("afterUserId")String afterUserId);
    // 2021/10/13　MANAMIRU1-776 cuikl　Edit End
}
