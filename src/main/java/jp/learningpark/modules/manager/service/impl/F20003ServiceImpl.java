/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstLearnSeasnDao;
import jp.learningpark.modules.common.dao.StuTermPlanDao;
import jp.learningpark.modules.common.entity.StuTermPlanEntity;
import jp.learningpark.modules.manager.dao.F20003Dao;
import jp.learningpark.modules.manager.dto.F20003Dto;
import jp.learningpark.modules.manager.service.F20003Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>生徒タームプラン設定 Extend ServiceImplです。</p>
 *
 * @author NWT : gaoli <br />
 * 変更履歴 <br />
 * 2018/10/09 : gaoli: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F20003ServiceImpl implements F20003Service {

    /**
     * 生徒タームプラン設定 Dao
     */
    @Autowired
    StuTermPlanDao termPlanDao;

    /**
     * F20003_タームプラン作成画面 Dao
     */
    @Autowired
    F20003Dao f20003Dao;

    /**
     *
     */
    @Autowired
    MstLearnSeasnDao mstLearnSeasnDao;

    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * <p>生徒基本情報を取得し</p>
     *
     * @param stuId 生徒id
     * @param orgId 塾id
     * @return
     */
    @Override
    public F20003Dto getStuInfoByStuId(String stuId, String orgId) {
        return f20003Dao.selectStuInfoByStuId(stuId, orgId);
    }

    /**
     * <p>当生徒の教科と教科書情報を取得し</p>
     *
     * @param stuId         生徒id
     * @param crmLearnPrdId 塾学習期間ID
     * @return
     */
    @Override
    public List<F20003Dto> getStuTextchocList(String stuId, Integer crmLearnPrdId) {
        return f20003Dao.selectStuTextchocList(stuId, crmLearnPrdId);
    }

    /**
     * <p>登録処理</p>
     *
     * @param dtos
     * @return
     */
    @Override
    public R update(List<F20003Dto> dtos) {
        //生徒ID
        String stuId = ShiroUtils.getSessionAttribute("stuId").toString();
        //塾学習期間ID
        Integer crmLearnPrdId = (Integer) ShiroUtils.getSessionAttribute(GakkenConstant.CRM_LEARN_PRD_ID);
        //教科区分
        String subjtDiv = dtos.get(0).getSubjtDiv();

        //(1) 生徒未計画まだは削除の生徒タームプラン設定データをすべでロックして、物理削除する。
        f20003Dao.deleteByStuIdAndTextbIdsAndSubjtDiv(stuId, crmLearnPrdId, subjtDiv);

        //(2) 当教科の単元ごとに、下記取得条件を元に、登録レコード数（※）を取得する。
        List<F20003Dto> termPlanExtendDtos = f20003Dao.selectListOfPlanReged(stuId, crmLearnPrdId, subjtDiv);
        Map<String, F20003Dto> termMap = new HashMap<>();
        //※当単元の画面．選択マス数－当単元のタームプランの計画済みレコード数
        for (int i = 0; i < termPlanExtendDtos.size(); i++) {
            termMap.put("" + termPlanExtendDtos.get(i).getUnitId() + termPlanExtendDtos.get(i).getTextbDefUnitId() + termPlanExtendDtos.get(i).getPlanLearnSeasnId(), termPlanExtendDtos.get(i));
        }

        //追加
        for (int i = 0; i < dtos.size(); i++) {
            StuTermPlanEntity addTerm = new StuTermPlanEntity();
            //単元ＩＤ
            Integer unitId = dtos.get(i).getUnitId();
            String key = "" + dtos.get(i).getUnitId() + dtos.get(i).getTextbDefUnitId() + dtos.get(i).getPlanLearnSeasnId();
            //存在する
            if (termMap.get(key) != null) {
                int times = 0;
                //マス数
                int records = dtos.get(i).getCont() - termMap.get(key).getPlanedCount();
                if (records % 2 != 0) {
                    times = records / 2 + 1;
                } else {
                    times = records / 2;
                }
                for (int j = 0; j < times; j++) {
                    //生徒ID
                    addTerm.setStuId(stuId);
                    //単元ID
                    addTerm.setUnitId(unitId);
                    //枝番
                    if (termMap.get(key).getPlanedCount() > 0) {
                        //タームプランの計画済みマス数＞０の場合
                        addTerm.setBnum(termMap.get(key).getMaxBnum() + j + 1);
                    }
                    //教科書デフォルト単元ID
                    addTerm.setTextbDefUnitId(dtos.get(i).getTextbDefUnitId());
                    //計画学習時期ID
                    addTerm.setPlanLearnSeasnId(dtos.get(i).getPlanLearnSeasnId());
                    //ブロック表示名
                    addTerm.setBlockDispyNm(dtos.get(i).getBlockDispyNm());
                    //教科区分
                    addTerm.setSubjtDiv(dtos.get(i).getSubjtDiv());
                    //計画学習時間（分）
                    if (records % 2 != 0 && j == times - 1) {
                        addTerm.setPlanLearnTm(15);
                    } else {
                        addTerm.setPlanLearnTm(30);
                    }
                    //計画登録フラグ
                    addTerm.setPlanRegFlg("0");
                    //表示順番
                    addTerm.setDispyOrder(dtos.get(i).getDispyOrder());
                    //作成日時
                    addTerm.setCretDatime(DateUtils.getSysTimestamp());
                    //作成ユーザＩＤ
                    addTerm.setCretUsrId(stuId);
                    //更新日時
                    addTerm.setUpdDatime(DateUtils.getSysTimestamp());
                    //更新ユーザＩＤ
                    addTerm.setUpdUsrId(stuId);
                    //削除フラグ
                    addTerm.setDelFlg(0);
                    try {
                        termPlanDao.insert(addTerm);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                        throw new RRException("データの更新に失敗しました。");
                    }
                }
            }
            //存在しない
            else {
                int times = 0;
                if (dtos.get(i).getCont() % 2 != 0) {
                    times = dtos.get(i).getCont() / 2 + 1;
                } else {
                    times = dtos.get(i).getCont() / 2;
                }
                for (int j = 0; j < times; j++) {
                    //生徒ID
                    addTerm.setStuId(stuId);
                    //単元ID
                    addTerm.setUnitId(unitId);
                    //枝番
                    addTerm.setBnum(j + 1);
                    //計画学習時期ID
                    addTerm.setPlanLearnSeasnId(dtos.get(i).getPlanLearnSeasnId());
                    //教科書デフォルト単元ID
                    addTerm.setTextbDefUnitId(dtos.get(i).getTextbDefUnitId());
                    //ブロック表示名
                    addTerm.setBlockDispyNm(dtos.get(i).getBlockDispyNm());
                    //教科区分
                    addTerm.setSubjtDiv(dtos.get(i).getSubjtDiv());
                    //計画学習時間（分）
                    if (j == times - 1 && dtos.get(i).getCont() % 2 != 0) {
                        addTerm.setPlanLearnTm(15);
                    } else {
                        addTerm.setPlanLearnTm(30);
                    }

                    //計画登録フラグ
                    addTerm.setPlanRegFlg("0");
                    //表示順番
                    addTerm.setDispyOrder(dtos.get(i).getDispyOrder());
                    //作成日時
                    addTerm.setCretDatime(DateUtils.getSysTimestamp());
                    //作成ユーザＩＤ
                    addTerm.setCretUsrId(stuId);
                    //更新日時
                    addTerm.setUpdDatime(DateUtils.getSysTimestamp());
                    //更新ユーザＩＤ
                    addTerm.setUpdUsrId(stuId);
                    //削除フラグ
                    addTerm.setDelFlg(0);
                    try {
                        termPlanDao.insert(addTerm);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                        throw new RRException("データの更新に失敗しました。");
                    }
                }
            }
        }
        return R.ok();
    }

    /**
     * <p>生徒タームプラン設定、教科書デフォルトターム情報から取得し、画面で表示する</p>
     *
     * @param crmLearnPrdId 塾学習期間ID
     * @param stuId         生徒id
     * @param textbId       教科書Id
     * @return
     */
    @Override
    public List<F20003Dto> getDefaultAndTermPlan(Integer crmLearnPrdId, String stuId, Integer textbId) {
        return f20003Dao.selectDefaultAndTermPlan(crmLearnPrdId, stuId, textbId);
    }
}
