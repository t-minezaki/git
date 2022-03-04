/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service.impl;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.StuWeeklyPlanPerfDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;
import jp.learningpark.modules.pop.dao.F10303Dao;
import jp.learningpark.modules.pop.dto.F10303Dto;
import jp.learningpark.modules.pop.service.F10303Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>F10303 復習教科選択画面(POP) ServiceImpl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/11 : gong: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F10303ServiceImpl implements F10303Service {

    /**
     * F10303 復習教科選択画面(POP) Dao
     */
    @Autowired
    private F10303Dao f10303Dao;

    /**
     * 生徒ウィークリー計画実績設定
     */
    @Autowired
    private StuWeeklyPlanPerfDao stuWeeklyPlanPerfDao;

    // 2020/11/12 zhangminghao modify start
    /**
     * 教科デフォルト値「学習」
     */
    private final static String  DEFAULT_SUBJECT_VALUE = "学習";
    // 2020/11/12 zhangminghao modify end

    /**
     * <p>F10303 当生徒の学校で学習するすべて教科を取得し</p>
     *
     * @param stuId 生徒Id
     * @param orgId 組織ID
     * @return list(教科div 教科名 教科img)
     */
    @Override
    public List<MstCodDEntity> getSubjtDivsByStuId(String stuId, String orgId) {
        return f10303Dao.selectSubjtDivsByStuId(stuId, orgId);
    }

    // 2020/11/12 zhangminghao modify start
    @Override
    public MstCodDEntity getDefaultSubject() {
        return f10303Dao.getDefaultSubject(DEFAULT_SUBJECT_VALUE);
    }
    // 2020/11/12 zhangminghao modify end
    /**
     * <p>登録ボタン押下</p>
     *
     * @param stuId 生徒Id
     * @param dto
     * @return
     */
    @Override
    public R submitFn(String stuId, F10303Dto dto) {
        StuWeeklyPlanPerfEntity stuWeeklyPlanPerfEntity = new StuWeeklyPlanPerfEntity();
        //生徒ID
        stuWeeklyPlanPerfEntity.setStuId(stuId);
        //単元ID
        stuWeeklyPlanPerfEntity.setUnitId(dto.getUnitId());
        //生徒タームプラン設定ID
        stuWeeklyPlanPerfEntity.setStuTermPlanId(null);
        //ブロック表示名
        stuWeeklyPlanPerfEntity.setBlockDispyNm(dto.getBlockDispyNm());
        //ブロック種類区分
        stuWeeklyPlanPerfEntity.setBlockTypeDiv(dto.getBlockTypeDiv());
        //計画年月日
        stuWeeklyPlanPerfEntity.setPlanYmd(DateUtils.parse(dto.getPlanYmdStr(), GakkenConstant.DATE_FORMAT_YYYYMMDD));
        //教科区分
        stuWeeklyPlanPerfEntity.setSubjtDiv(dto.getSubjtDiv());
        //教科
        stuWeeklyPlanPerfEntity.setSubjtNm(dto.getSubjtNm());
        //生徒計画学習時間（分）
        stuWeeklyPlanPerfEntity.setStuPlanLearnTm(dto.getStuPlanLearnTm());
        //計画学習時期ID
        stuWeeklyPlanPerfEntity.setPlanLearnSeasnId(null);
        //計画学習開始時間
        stuWeeklyPlanPerfEntity.setPlanLearnStartTime(new Timestamp(DateUtils.parse(dto.getPlanLearnStartTime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime()));
        //実績学習時間（分）
        stuWeeklyPlanPerfEntity.setPerfLearnTm(null);
        //学習理解度
        stuWeeklyPlanPerfEntity.setLearnLevUnds(null);
        //積み残し対象フラグ
        stuWeeklyPlanPerfEntity.setRemainDispFlg("0");
        //生徒削除フラグ
        stuWeeklyPlanPerfEntity.setStuDelFlg("0");
        //表示順番
        stuWeeklyPlanPerfEntity.setDispyOrder(null);
        //作成日時
        stuWeeklyPlanPerfEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザＩＤ
        stuWeeklyPlanPerfEntity.setCretUsrId(stuId);
        //更新日時
        stuWeeklyPlanPerfEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        stuWeeklyPlanPerfEntity.setUpdUsrId(stuId);
        //del_flg
        stuWeeklyPlanPerfEntity.setDelFlg(0);
        stuWeeklyPlanPerfDao.insert(stuWeeklyPlanPerfEntity);
        return R.ok();
    }
}
