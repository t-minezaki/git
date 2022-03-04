package jp.learningpark.modules.job.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
@Mapper
public interface BTGKA1006Dao {
    /**
     * 2.1.1　以前の創学高等部のデータを全部論理的削除
     * @return
     */
    Integer delMstUsrByLogic();
    /**
     * 2.1.1　以前の創学高等部のデータを全部論理的削除
     * @return
     */
    Integer delMstStuByLogic();
    /**
     * 2.1.1所属組織ID存在チェック
     * @return
     */
    String orgIdExistenceCheck(@Param("orgNum") String orgNum);

    String getUser(@Param("orgValue") String orgValue,@Param("careerId") Integer careerId);
    /**
     * 2.2.3.2ユーザ基本マスタ更新
     * @return
     */
    Integer mstUsrUpdate(@Param("usrId") String usrId, @Param("userName") String userName, @Param("orgValue") String orgValue, @Param("afterUsrId") String afterUsrId,
                         @Param("gidFlg") String gidFlg, @Param("updDatime") Timestamp updDatime, @Param("flg") Integer flg);
    /**
     * 2.2.3.2生徒基本マスタ更新
     * @return
     */
    Integer mstStuUpdate(@Param("usrId") String usrId, @Param("sch") String sch, @Param("flnm_nm") String flnm_nm,
                         @Param("flnm_lnm") String flnm_lnm, @Param("flnm_kn_nm") String flnm_kn_nm,
                         @Param("flnm_kn_lnm") String flnm_kn_lnm, @Param("gendr_div") String gendr_div,
                         @Param("birthd") Date birthd, @Param("schy_div") String schy_div,
                         @Param("oria_cd") String oria_cd, @Param("updDatime") Timestamp updDatime, @Param("flg") Integer flg);
}
