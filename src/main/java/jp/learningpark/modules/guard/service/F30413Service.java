package jp.learningpark.modules.guard.service;

import jp.learningpark.modules.guard.dto.F30112Dto;
import jp.learningpark.modules.guard.dto.F30413Dto;

import java.util.Date;

/**
 * <p>報告書詳細画面</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2019/12/09 : zpa: 新規<br />
 * @version 1.0
 */
public interface F30413Service {

    /**
     * @param orgId 組織ID
     * @param GrId 指導報告書ID
     * @param deliverCd 指導報告書配信コード
     * @param Ymd 対象日付
     * @param stuId 生徒ID
     * @return
     */
    F30413Dto init(String orgId, Integer GrId, String deliverCd, Date Ymd, String stuId);

    /**
     * 塾のニュースを取得し、画面で表示される。
     *
     * @param stuId 生徒Id
     * @param guardId 保護者Id
     * @param id id
     * @param orgId orgId
     * @return
     */
    F30112Dto updateStatus(Integer id, String orgId, String stuId, String guardId, Integer guidReprId, String deliverCd);
}
