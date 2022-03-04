package jp.learningpark.modules.guard.dao;

import jp.learningpark.modules.guard.dto.F30112Dto;
import jp.learningpark.modules.guard.dto.F30413Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * <p>報告書詳細画面</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2019/12/09 : zpa: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F30413Dao {

    /**
     * @param orgId 組織ID
     * @param GrId 指導報告書ID
     * @param deliverCd 指導報告書配信コード
     * @param Ymd 対象日付
     * @param stuId 生徒ID
     * @return
     */
    F30413Dto init(
            @Param("orgId") String orgId,
            @Param("grId") Integer GrId, @Param("deliverCd") String deliverCd, @Param("tgtYmd") Date Ymd, @Param("stuId") String stuId);

    /**
     * 塾・教室ニュース未読件数
     *
     * @param guardId
     * @param stuId
     * @param orgId
     * @return
     */
    F30112Dto selectNoticeInfo(
            @Param("id") Integer id,
            @Param("orgId") String orgId,
            @Param("stuId") String stuId,
            @Param("guardId") String guardId, @Param("guidReprId") Integer guidReprId, @Param("guidReprDeliverCd") String deliverCd);
}
