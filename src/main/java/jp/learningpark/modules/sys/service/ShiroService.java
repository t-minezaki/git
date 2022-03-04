package jp.learningpark.modules.sys.service;

import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.sys.entity.SysUserEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * shiroサービス
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-06 8:49
 */
public interface ShiroService {
    /**
     * ユーザー権限を取得する
     *
     * @param id ユーザーID
     * @return
     */
    Set<String> getUserPermissions(long id);

    /**
     * ユーザー情報を取得する
     *
     * @param id ユーザーID
     * @return
     */
    SysUserEntity getUserById(Integer id);

    /**
     * ユーザー情報を取得する
     *
     * @param userId ユーザーID
     * @return
     */
    SysUserEntity getUserByUserId(String userId);

    /**
     * ユーザー情報を取得する
     *
     * @param userCd  ユーザーID
     * @param brandCd ブランドコード
     * @return
     */
    List<SysUserEntity> getUserByBrandCd(String userCd, String brandCd);

    /**
     * ユーザー情報を取得する
     *
     * @param userCd  ユーザーID
     * @return
     */
    List<SysUserEntity> getUserByLoginId(String userCd, String manaFlag, String brandCd, String gidFlag, String tchCdFlg);

    /**
     * ユーザー役割を取得する
     *
     * @param userId ユーザーID
     * @return
     */
    String getUserRole(long userId);

    /**
     * 子供個数の取得
     *
     * @param guardId 保護者ID
     * @return
     */
    Map<String, Object> getStuCountByGuardId(String guardId);

    /**
     * 塾学習期間IDの取得
     *
     * @param userId ユーザーID
     * @return
     */
    Integer getLearnPrdIdByUserId(String userId,String orgId);

    /**
     * ユーザーマスタより、ロックエラー回数を更新する。
     *
     * @param loginId ユーザーID
     * @return
     */
    Integer updateErrorCount(String loginId, int errorCount);

    /**
     * ユーザーマスタより、ロックフラグを更新する。
     *
     * @param loginId ユーザーID
     * @return
     */
    Integer updateLockFlg(String loginId);

    /**
     * 各ロールのメールアドレス取得処理
     *
     * @param roleDiv
     * @param brandCd
     * @param afterUsrId
     * @return
     */
    List<String> getMailAddress(String roleDiv, String brandCd, String afterUsrId);

    /**
     * 基本マスタ更新
     *
     * @param afterUsrId ユーザーID
     * @return
     */
    Integer updateMailAddress(String afterUsrId, String roleDiv, String mailad, String usrId);

    /**
     * 指導者のGIDとGIDPKを更新する。
     *
     * @param tchCd
     * @param gidRuleFlag
     * @param updatime
     * @param usrNm
     * @param gidpk
     * @param gid
     * @return
     */
    Integer updateGidpkByTchCd(String tchCd, String usrNm, String gidpk, String gid, String gidRuleFlag, Timestamp updatime);

    /**
     * 組織IDとログインユーザーIDでユーザーを検索する
     *
     * @param afterUsrId
     * @param orgId
     * @return
     */
    SysUserEntity selectUserByOrgIdAndAfterUsrId(String afterUsrId, String orgId);

    /**
     * 組織IDとログインtchCdでユーザーを検索する
     *
     * @param tchCd
     * @param orgId
     * @return
     */
    SysUserEntity selectUserByOrgIdAndTchCd(String tchCd, String orgId);

    /**
     * 組織IDとログインgidPkでユーザーを検索する
     *
     * @param gidpk
     * @param orgId
     * NWT　楊　2021/07/01　MANAMIRU1-699 --> 727　Edit　Start
     * @return
     */
    SysUserEntity getUserByGidpk(String gidpk, String orgId);

    List<MstUsrEntity> gidpkCount(String gidpk);
    //NWT　楊　2021/07/21　MANAMIRU1-727.④　Edit　Start
    List<MstUsrEntity> tchCdCount(String tchCd,String orgId);
    //NWT　楊　2021/07/21　MANAMIRU1-727.④　Edit　End

    List<MstUsrEntity> selectStudentGidkpList(List<String> guardUsrIdList);

    List<SysUserEntity> getSamlUserByLoginId(String username);

    /**
     * tcd非初回登録件数取得
     * NWT　楊　2021/07/15　MANAMIRU1-727　Edit
     * @param tchCd
     * @return
     */
    Integer selectFirstLoginUsrByTchcd(String tchCd);
}
