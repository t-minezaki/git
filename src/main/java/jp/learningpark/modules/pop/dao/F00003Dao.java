package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.common.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface F00003Dao {
    /**
     * @param orgId
     * @return
     */
    String getAfterId(@Param("orgId") String orgId, @Param("QR") String QR);

    /**
     * 画面．上位組織をもとに、下記条件でユーザ基本マスタを取得する
     *
     * @param upOrgId
     * @return
     */
    List<MstUsrEntity> getUpUsr(@Param("upOrgId") String upOrgId);

    /**
     * 取得したロール区分＝「1：管理者」の場合
     *
     * @param mgrId
     * @return
     */
    MstManagerEntity getMgr(@Param("mgrId") String mgrId);

    /**
     * 取得したロール区分＝「2：メンター」の場合
     *
     * @param mentorId
     * @return
     */
    MstMentorEntity getMentor(@Param("mentorId") String mentorId);

    /**
     * 取得したロール区分＝「3：保護者」の場合
     *
     * @param guardId
     * @return
     */
    MstGuardEntity getGuard(@Param("guardId") String guardId);

    /**
     * 取得したロール区分＝「4：生徒」の場合
     *
     * @param stuId
     * @return
     */
    MstStuEntity getStu(@Param("stuId") String stuId);

    /**
     * 引渡データ．組織IDより、編集中組織に対するアカウントを取得する
     *
     * @param orgId
     * @return
     */
    List<String> getUsrs(@Param("orgId") String orgId);


    List<MstUsrEntity> getAllUsrs( @Param("afterUsrIds") List<String> afterUsrIds,@Param("orgIdList") List<String> orgIdList);

    void updateAcount(@Param("usrId") String usrId);

}
