package jp.learningpark.modules.sys.dao;

import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:11
 */
@Mapper
public interface SysUserDao {

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     */
    List<String> selectAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> selectAllMenuId(Long userId);

    /**
     * 根据用户名，查询系统用户
     */
    SysUserEntity selectByUserName(String username);

    /**
     * 根据用户名，查询系统用户
     */
    String selectUserRole(@Param("userId") Long userId);

    /**
     * 根据用户名，查询系统用户
     */
    SysUserEntity selectUserById(@Param("id") Integer id);

    /**
     * 根据用户名，查询系统用户
     */
    SysUserEntity selectUserByUserId(@Param("userId") String userId);

    /**
     * 根据用户名，查询系统用户
     */
    List<SysUserEntity> selectUserByBrandCd(@Param("userCd") String userCd,@Param("brandCd") String brandCd);

    /**
     * 根据用户名，查询系统用户
     */
    List<SysUserEntity> selectUserByLoginId(@Param("userCd") String userCd, @Param("manaFlag")String manaFlag, @Param("brandCd")String brandCd, @Param("gidFlag")String gidFlag,@Param("tchCdFlg")String tchCdFlg);

    /**
     * 子供個数の取得
     * @param guardId 保護者ID
     * @return 件数と生徒ID
     */
    Map<String,Object> selectStuCountByGuardId(@Param("guardId") String guardId);

    /**
     * 塾学習期間IDの取得
     * @param userId ユーザーID
     * @return
     */
    Integer selectLearnPrdIdByUserId(@Param("userId") String userId,@Param("orgId")String orgId);

    /**
     * ユーザーマスタより、ロックエラー回数を更新する。
     *
     * @param loginId ユーザーID
     * @return
     */
    Integer addErrorCount(@Param("loginId") String loginId, @Param("errorCount") int errorCount);

    /**
     * ユーザーマスタより、ロックフラグを更新する。
     *
     * @param loginId ユーザーID
     * @return
     */
    Integer setLockFlg(@Param("loginId") String loginId);

    /**
     * 各ロールのメールアドレス取得処理
     * @param roleDiv
     * @param brandCd
     * @param afterUsrId
     * @return
     */
    List<String> selectEmailAddress(@Param("roleDiv")String roleDiv, @Param("brandCd")String brandCd, @Param("afterUsrId")String afterUsrId);

    /**
     * 基本マスタ更新
     *
     * @param afterUsrId
     * @param roleDiv
     * @param mailad
     * @return
     */
    Integer updateMailAddress(@Param("afterUsrId")String afterUsrId, @Param("roleDiv")String roleDiv, @Param("mailad")String mailad, @Param("usrId")String usrId);

    /**
     * 指導者のGIDとGIDPKを更新する。
     *
     * @param tchCd
     * @param gidpk
     * @param gid
     * @return
     */
    Integer updateGidpkByTchCd(@Param("tchCd")String tchCd, @Param("usrNm")String usrNm, @Param("gidpk") String gidpk
            , @Param("gid") String gid, @Param("gidRuleFlag")String gidRuleFlag, @Param("updatime") Timestamp updatime);

    /**
     * 組織IDとログインユーザーIDでユーザーを検索する
     *
     * @param afterUsrId
     * @param orgId
     * @return
     */
    SysUserEntity selectUserByOrgIdAndAfterUsrId(@Param("afterUsrId") String afterUsrId,@Param("orgId") String orgId);
    /**
     * 組織IDとログインtchCdでユーザーを検索する
     *
     * @param tchCd
     * @param orgId
     * @return
     */
    SysUserEntity selectUserByOrgIdAndTchCd(@Param("tchCd") String tchCd,@Param("orgId") String orgId);


    /**
     *  組織IDとログインgidPkでユーザーを検索する
     * @param gidpk
     *     //NWT　楊　2021/07/01　MANAMIRU1-699 --> 727　Edit
     * @return
     */
    SysUserEntity selectUserByGidpk(@Param("gidpk")String gidpk, @Param("orgId")String orgId);

    //NWT　楊　2021/07/21　MANAMIRU1-727.④　Edit　Start
    List<MstUsrEntity> tchCdCount(@Param("tchCd")String tchCd,@Param("orgId")String orgId);
    //NWT　楊　2021/07/21　MANAMIRU1-727.④　Edit　End

    List<MstUsrEntity> selectStudentGidpkList(@Param("userIds")List<String> guardUserIdList);

    List<SysUserEntity> selectSamlUserByLoginId(@Param("userCd") String userCd);

    /**
     * tcd非初回登録件数取得
     * NWT　楊　2021/07/15　MANAMIRU1-727　Edit
     * @param tchCd
     * @return
     */
    Integer selectFirstLoginUsrByTchcd(@Param("tchCd") String tchCd);
}
