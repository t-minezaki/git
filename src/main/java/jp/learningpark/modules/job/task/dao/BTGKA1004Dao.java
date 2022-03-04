package jp.learningpark.modules.job.task.dao;

import jp.learningpark.modules.job.entity.BTGKA1004OrgCommonKeyDto;
import jp.learningpark.modules.job.entity.BTGKA1004StudentDto;
import jp.learningpark.modules.job.entity.BTGKA1004UsrDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
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
 * 2020/8/26 ： NWT)hxl ： 新規作成
 * @date 2020/8/26 12:03
 */
@Mapper
public interface BTGKA1004Dao {
    /**
     * 4.1.3　管理者基本マスタデータを削除する。※論理削除
     * @return
     */
    Integer deleteTeacherLogic();
    /**
     * 4.1.4　ユーザ基本マスタデータを削除する。※論理削除
     * @return
     */
    Integer deleteTeacherUsrLogic();
    /**
     * 4.2.2.1　管理者基本マスタのデータ存在チェック
     * @param tchCd 指導者管理コード
     * @param gidpk 指導者GIDPK
     * @return
     */
    List<String> teacherCheck(@Param("tchCd") String tchCd, @Param("gidpk")String gidpk);

    /**
     * 5.1.3　ユーザ基本マスタに指導者（管理者）と教室関係を解除する。
     * @param orgId
     * @param updDatime
     * @param updUsrId
     * @return
     */
    Integer resTeacherAndClassroom(@Param("orgId") String orgId, @Param("updDatime") Timestamp updDatime, @Param("updUsrId") String updUsrId);

    /**
     * 指導者
     * @param mgrCd 指導者管理コード
     * @param gidpk 指導者GIDPK
     * @return
     */
    List<BTGKA1004UsrDto> managerCheck(@Param("mgrCd") String mgrCd, @Param("gidpk")String gidpk);

    Integer getMaxLevel();

    List<BTGKA1004OrgCommonKeyDto> getFirstCommonKey();

    Integer selectThisAndUpOrgIdListFromOrgIdList(@Param("orgId")String orgId, @Param("orgIdList")List<String> orgIdList, @Param("brandCd")String brandCd);

    /**
     * ユーザ基本マスタと生徒基本マスタから該当保護者の子供数を取得する。
     * @param guardGidpk            保護者GIDPK
     * @param orgId                 取得した組織コード付き文字＋会員情報．教室コード
     * @return
     */
    List<String> getMemberCodeList(@Param("guardGidpk")String guardGidpk,
                                   @Param("orgId")String orgId);

    /**
     * 指導者
     * @return
     */
    List<BTGKA1004UsrDto> selectAllTeacher();

    /**
     * 生徒データ取得
     * @param gidpk 取得した会員情報補足リスト．生徒GIDPK
     * @param orgId 取得した組織コード付き文字＋会員情報．教室コード
     * @return
     */
    List<BTGKA1004StudentDto> selectStudentByGidpkAndOrgId(@Param("gidpk")String gidpk, @Param("orgId")String orgId);

    /**
     * 管理者基本マスタを削除する。　　※物理削除
     * @return
     */
    Integer deleteTeacherPhysical();

    /**
     * ユーザ基本マスタを削除する。　　※物理削除
     * @return
     */
    Integer deleteTeacherUserPhysical();

    /**
     * ユーザーキャラタを削除する。　　※物理削除
     * @return
     */
    Integer deleteTeacherSysUserRolePhysical();

    /**
     * mst_manager.del_flgを0に設定します
     * @param userId
     * @return
     */
    Integer activeTeacherByUserId(@Param("userId")String userId);

    /**
     * mst_usr.del_flgを0に設定します
     * @param userId
     * @return
     */
    Integer activeTeacherUserByUserId(@Param("userId")String userId);

    Integer selectOrgLevelByOrgId(@Param("orgId")String orgId);
}
