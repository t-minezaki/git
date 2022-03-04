/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.modules.guard.dao.F30413Dao;
import jp.learningpark.modules.guard.dto.F30112Dto;
import jp.learningpark.modules.guard.dto.F30413Dto;
import jp.learningpark.modules.guard.service.F30413Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>報告書詳細画面</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2019/12/09 : zpa: 新規<br />
 * @version 1.0
 */
@Service
public class F30413ServiceImpl implements F30413Service {

    /**
     * 報告書詳細画面 Dao
     */
    @Autowired
    F30413Dao f30413Dao;

    /**
     * @param orgId 組織ID
     * @param GrId 指導報告書ID
     * @param deliverCd 指導報告書配信コード
     * @param Ymd 対象日付
     * @param stuId 生徒ID
     * @return
     */
    @Override
    public F30413Dto init(String orgId, Integer GrId, String deliverCd, Date Ymd, String stuId) {
        return f30413Dao.init(orgId, GrId, deliverCd, Ymd, stuId);
    }


    /**
     * 塾・教室ニュース未読件数
     *
     * @param guardId
     * @param stuId
     * @param orgId
     * @return
     */
    @Override
    public F30112Dto updateStatus(Integer id, String orgId, String stuId, String guardId, Integer guidReprId, String deliverCd) {
        return f30413Dao.selectNoticeInfo(id, orgId, stuId, guardId, guidReprId, deliverCd);
    }
}