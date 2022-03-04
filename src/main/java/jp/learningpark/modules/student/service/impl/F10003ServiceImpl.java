/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.dao.StuTermPlanDao;
import jp.learningpark.modules.common.dao.StuTextbChocDao;
import jp.learningpark.modules.common.dao.TextbDefTimeInfoDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstCrmschLearnPrdEntity;
import jp.learningpark.modules.common.entity.MstTextbEntity;
import jp.learningpark.modules.common.entity.StuTermPlanEntity;
import jp.learningpark.modules.common.entity.StuTextbChocEntity;
import jp.learningpark.modules.common.entity.TextbDefTimeInfoEntity;
import jp.learningpark.modules.student.dao.F10003Dao;
import jp.learningpark.modules.student.dto.F10003Dto;
import jp.learningpark.modules.student.service.F10003Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>生徒教科書選択画 Impl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/11/05 : gong: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F10003ServiceImpl implements F10003Service {

    /**
     * 生徒教科書選択画 Dao
     */
    @Autowired
    private F10003Dao f10003Dao;

    /**
     * 生徒タームプラン設定 Dao
     */
    @Autowired
    private StuTermPlanDao termPlanDao;

    /**
     * 生徒教科書選択管理マスタ Dao
     */
    @Autowired
    private StuTextbChocDao stuTexchocMstDao;

    /**
     * コードマスタ_明細 Dao
     */
    @Autowired
    private MstCodDDao codMstDDao;

    /**
     * 教科書デフォルトターム情報 Dao
     */
    @Autowired
    private TextbDefTimeInfoDao textbDefTimeInfoDao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * <p>当生徒当学年の教科ごとの塾の教科書情報を取得</p>
     *
     * @param stuId 生徒Id
     * @param orgId 塾Id
     * @return
     */
    @Override
    public List<F10003Dto> getTextbDtoListOfSchByStuId(String stuId, String orgId) {
        return f10003Dao.selectTextbDtoListOfSchByStuId(stuId, orgId);
    }

    /**
     * <p>1.2 塾学習期間IDの取得</p>
     *
     * @param orgId 塾Id
     * @return
     */
    @Override
    public MstCrmschLearnPrdEntity getCrmschLearnPrdId(String orgId) {
        return f10003Dao.selectCrmschLearnPrdId(orgId);
    }

    /**
     * <p>生徒選択した教科書リストを取得</p>
     *
     * @param map
     * @return
     */
    @Override
    public List<MstTextbEntity> getTextbListOfStuByStuIdAndOrgIdAndSchyDiv(Map<String, Object> map) {
        return f10003Dao.selectTextbListOfStuByStuIdAndOrgIdAndSchyDiv(map);
    }

    /**
     * <p>登録ボタンの処理</p>
     *
     * @param bookDto
     * @return
     */
    @Override
    public R updated(F10003Dto bookDto) {
        //塾学習期間ID
        Integer crmschLearnPrdId = bookDto.getCrmschLearnPrdId();
        ///塾Id
        String orgId = ShiroUtils.getUserEntity().getOrgId();

        //生徒Id
        String stuId = ShiroUtils.getUserId();
        //現在の生徒の選択教科書リストを入手する
        Map<String, Object> map = new HashMap<>();
        map.put("stuId", stuId);
        map.put("schyDiv", bookDto.getSchyDiv());
        //塾学習期間ID
        map.put("crmLearnPrdId", crmschLearnPrdId);
        List<Integer> crruentList = f10003Dao.selectCurrentChocList(map);
        if (crruentList.size() == 0) {
            crruentList.add(0);
        }

        //生徒ウィークリー計画実績設定をもとに、当教科書データが存在するかどうかを判定する。
        List<F10003Dto> f10003DtoList = f10003Dao.selectAboutWeekly(stuId, crruentList);
        //生徒ウィークリー計画実績設定Map(教科区分,教科書ID)
        Map<String, String> weeklyMap = new HashMap<String, String>();
        if (f10003DtoList.size() > 0) {
            //Map(教科区分,教科書ID)の作成
            for (F10003Dto dto : f10003DtoList) {
                weeklyMap.put(dto.getSubjtDiv(), dto.getTextbNm());
            }
        }

        for (Map<String, String> choc : bookDto.getChocList()) {
            //Empty
            if (!StringUtils.isEmpty(choc.get("value"))) {
                if (!crruentList.contains(Integer.parseInt(choc.get("value")))) {
                    //業務エラー判定
                    if (weeklyMap.get(choc.get("name")) != null) {
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0045", weeklyMap.get(choc.get("name"))));
                    }

                    //2.2.1  判定条件取得
                    StuTextbChocEntity stuTexchocMstEntity = stuTexchocMstDao.selectOne(new QueryWrapper<StuTextbChocEntity>().and(wrapper -> wrapper.eq("subjt_div", choc.get("name")).eq("stu_id", stuId).eq("crm_learn_prd_id", crmschLearnPrdId).eq("del_flg", 0)));
                    //更新
                    if (stuTexchocMstEntity != null) {
                        //排他チェックエラー
                        if (!StringUtils.equals(DateUtils.format(stuTexchocMstEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS), (String) choc.get("updateTimeStr"))) {
                            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
                        }

                        //(2) 生徒タームプラン設定データをすべて物理削除する。
                        f10003Dao.deleteTerm(stuId, stuTexchocMstEntity.getTextbId());

                        //教科書ID
                        stuTexchocMstEntity.setTextbId(Integer.parseInt(choc.get("value")));
                        //更新ユーザＩＤ
                        stuTexchocMstEntity.setUpdUsrId(stuId);
                        //更新日時
                        stuTexchocMstEntity.setUpdDatime(DateUtils.getSysTimestamp());
                        stuTexchocMstDao.updateById(stuTexchocMstEntity);

                    }
                    //登録
                    else {
                        try {
                            stuTexchocMstEntity = new StuTextbChocEntity();
                            //作成ユーザＩＤ
                            stuTexchocMstEntity.setCretUsrId(stuId);
                            //作成日時
                            stuTexchocMstEntity.setCretDatime(DateUtils.getSysTimestamp());
                            //更新ユーザＩＤ
                            stuTexchocMstEntity.setUpdUsrId(stuId);
                            //更新日時
                            stuTexchocMstEntity.setUpdDatime(DateUtils.getSysTimestamp());
                            //生徒ID
                            stuTexchocMstEntity.setStuId(stuId);
                            //塾学習期間ID
                            stuTexchocMstEntity.setCrmLearnPrdId(crmschLearnPrdId);
                            //教科区分
                            stuTexchocMstEntity.setSubjtDiv((String) choc.get("name"));
                            //教科書ID
                            stuTexchocMstEntity.setTextbId(Integer.parseInt(choc.get("value")));
                            stuTexchocMstDao.insert(stuTexchocMstEntity);
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
                        }
                    }

                    //(3) 下記条件で、生徒選択した当塾の教科書より、単元IDと計画学習時間（分）/30分の件数分で繰り返し、
                    List<F10003Dto> f10003DtoList1 = f10003Dao.selectTexdeffExtend(orgId, Integer.parseInt(choc.get("value")), crmschLearnPrdId);
                    //
                    MstCodDEntity codMstDEntity = codMstDDao.selectOne(new QueryWrapper<MstCodDEntity>().select("cod_value").and(w -> w.eq("cod_cd", choc.get("name")).eq("cod_key", "SUBJT_DIV").eq("del_flg", 0)));
                    String subjtSub = codMstDEntity.getCodValue();
                    if (f10003DtoList1.size() != 0) {
                        for (int j = 0; j < f10003DtoList1.size(); j++) {
                            F10003Dto f10003Dto = f10003DtoList1.get(j);
                            TextbDefTimeInfoEntity textbDefTimeInfoEntity = textbDefTimeInfoDao.selectOne(new QueryWrapper<TextbDefTimeInfoEntity>().and(w -> w.eq("unit_id", f10003Dto.getUnitId()).eq("plan_learn_seasn", f10003Dto.getPlanLearnSeasn()).eq("textb_id",Integer.parseInt(choc.get("value"))).eq("del_flg", 0)));
                            //計画学習時間（分）/30分の切り上げ値の件数
                            int count;
                            if ((textbDefTimeInfoEntity.getPlanLearnTm() % 30) == 0) {
                                count = textbDefTimeInfoEntity.getPlanLearnTm() / 30;
                            } else {
                                count = (textbDefTimeInfoEntity.getPlanLearnTm() / 30) + 1;
                            }
                            StuTermPlanEntity termPlanEntity;
                            for (int i = 0; i < count; i++) {
                                termPlanEntity = new StuTermPlanEntity();
                                //生徒ID
                                termPlanEntity.setStuId(stuId);
                                //単元ID
                                termPlanEntity.setUnitId(textbDefTimeInfoEntity.getUnitId());
                                //枝番
                                termPlanEntity.setBnum(i + 1);
                                //教科書デフォルト単元ID
                                termPlanEntity.setTextbDefUnitId(textbDefTimeInfoEntity.getId());
                                //計画学習時期ID
                                termPlanEntity.setPlanLearnSeasnId(f10003Dto.getPlanLearnSeasnId());
                                //ブロック表示名
                                termPlanEntity.setBlockDispyNm(subjtSub + " " + StringUtils.defaultString(textbDefTimeInfoEntity.getSectnNo()) + "-" + StringUtils.defaultString(textbDefTimeInfoEntity.getUnitNo()) + " " + f10003Dto.getChaptNm());
                                //教科区分
                                termPlanEntity.setSubjtDiv(textbDefTimeInfoEntity.getSubjtDiv());
                                //計画学習時間（分）
                                if (textbDefTimeInfoEntity.getPlanLearnTm() - 30 * (i) >= 30) {
                                    termPlanEntity.setPlanLearnTm(30);
                                } else {
                                    termPlanEntity.setPlanLearnTm(textbDefTimeInfoEntity.getPlanLearnTm() - 30 * (i));
                                }
                                //計画登録フラグ
                                termPlanEntity.setPlanRegFlg("0");
                                //表示順番
                                termPlanEntity.setDispyOrder(textbDefTimeInfoEntity.getDispyOrder());
                                //作成日時
                                termPlanEntity.setCretDatime(DateUtils.getSysTimestamp());
                                //作成ユーザＩＤ
                                termPlanEntity.setCretUsrId(stuId);
                                //更新日時
                                termPlanEntity.setUpdDatime(DateUtils.getSysTimestamp());
                                //更新ユーザＩＤ
                                termPlanEntity.setUpdUsrId(stuId);
                                //削除フラグ
                                termPlanEntity.setDelFlg(0);
                                termPlanDao.insert(termPlanEntity);
                            }
                        }
                    }
                }
            } else {
                //業務エラー判定
                if (weeklyMap.get(choc.get("name")) != null) {
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0045", weeklyMap.get(choc.get("name"))));
                }
                try {
                    if (!StringUtils.equals("", (String) choc.get("updateTimeStr")) && choc.get("updateTimeStr") != null) {
                        //2.2.1  判定条件取得
                        StuTextbChocEntity stuTexchocMstEntity = stuTexchocMstDao.selectOne(new QueryWrapper<StuTextbChocEntity>().and(wrapper -> wrapper.eq("stu_id", stuId).eq("subjt_div", choc.get("name")).eq("crm_learn_prd_id", crmschLearnPrdId).eq("del_flg", 0)));
                        //排他チェックエラー
                        if (stuTexchocMstEntity == null || !StringUtils.equals(DateUtils.format(stuTexchocMstEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS), (String) choc.get("updateTimeStr"))) {
                            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
                        }

                        //(1) 生徒教科書選択管理マスタより、該当レコードを物理削除する。
                        stuTexchocMstDao.deleteById(stuTexchocMstEntity.getId());
                        //(2) 生徒タームプラン設定データをすべて物理削除する。
                        f10003Dao.deleteTerm(stuId, stuTexchocMstEntity.getTextbId());
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
                }
            }
        }
        return R.ok(MessageUtils.getMessage("MSGCOMN0014", "生徒の教科書"));
    }
}
