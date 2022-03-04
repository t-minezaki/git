/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstTextbDao;
import jp.learningpark.modules.common.dao.StuTermPlanDao;
import jp.learningpark.modules.common.dao.StuTextbChocDao;
import jp.learningpark.modules.common.dao.TextbDefTimeInfoDao;
import jp.learningpark.modules.common.entity.MstTextbEntity;
import jp.learningpark.modules.common.entity.StuTermPlanEntity;
import jp.learningpark.modules.common.entity.StuTextbChocEntity;
import jp.learningpark.modules.common.entity.TextbDefTimeInfoEntity;
import jp.learningpark.modules.common.utils.dao.CommonDao;
import jp.learningpark.modules.manager.dao.F03002Dao;
import jp.learningpark.modules.manager.dto.F0300202Dto;
import jp.learningpark.modules.manager.dto.F03002Dto;
import jp.learningpark.modules.manager.service.F03002Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>F03002_教科書単元編集画面 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/01/08 : gong: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F03002ServiceImpl implements F03002Service {
    /**
     * F03002_教科書単元編集画面 Dao
     */
    @Autowired
    private F03002Dao f03002Dao;

    /**
     * 教科書デフォルトターム情報 Dao
     */
    @Autowired
    private TextbDefTimeInfoDao textbDefTimeInfoDao;

    /**
     * 教科書マスタ dao
     */
    @Autowired
    private MstTextbDao mstTextbDao;

    /**
     * 生徒タームプラン設定 Dao
     */
    @Autowired
    private StuTermPlanDao stuTermPlanDao;

    /**
     * 共通機能概要 Dao
     */
    @Autowired
    private CommonDao commonDao;

    /**
     * 生徒教科書選択管理 Dao
     */
    @Autowired
    private StuTextbChocDao stuTextbChocDao;

    /**
     * <p>教科書情報と教科書単元情報を表示するため、教科書マスタ、教科書デフォルトターム情報を元に、前画面引渡の教科書IDを取得し、画面で表示する。</p>
     *
     * @param textbId       教科書Id
     * @param crmLearnPrdId 塾学習期間ID
     * @return              教科書情報
     */
    @Override
    public List<F03002Dto> getTexdiff(Integer textbId, Integer crmLearnPrdId) {
        return f03002Dao.selectTexdiff(textbId, crmLearnPrdId);
    }

    /**
     * <p>編集保存ボタン押下</p>
     *
     * @param dtoList 画面の情報
     * @return        編集情報
     */
    @Override
    public R editFn(List<F03002Dto> dtoList) {
        //ユーザＩＤ
        String usrId = ShiroUtils.getUserId();
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // ブロック表示名
        String blockNm;
        StuTermPlanEntity stuTermPlanEntity = null;
        //教科書デフォルトターム情報
        TextbDefTimeInfoEntity textbDefTimeInfoEntity;
        //生徒ごとの計画済み件数リストの情報
        List<F0300202Dto> f0300202DtoList = null;
        //生徒教科書選択管理の学生の情報リスト
        List<StuTextbChocEntity> textbChocEntityList = null;
        //削除用の計画済み件数
        Integer planFlg = 0;
        //教科書Id
        Integer textbId = dtoList.get(0).getTextbId();
        //教科書名
        String textbNm = dtoList.get(0).getTextbNm();
        //出版社区分
        String publisherDiv = dtoList.get(0).getPublisherDiv();

        MstTextbEntity mstTextbEntity1;
        //7.4 教科書名の重複チェック
        mstTextbEntity1 = mstTextbDao.selectOne(new QueryWrapper<MstTextbEntity>().and(w -> w.ne("textb_id",textbId).eq("subjt_div", dtoList.get(0).getSubjtDiv()).eq("org_id", orgId).eq("publisher_div", dtoList.get(0).getPublisherDiv()).eq("schy_div", dtoList.get(0).getSchyDiv()).eq("textb_nm", dtoList.get(0).getTextbNm()).eq("del_flg", 0)));
        if (mstTextbEntity1 != null) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0046", dtoList.get(0).getTextbNm(), "教科書マスタ"));
        }

        MstTextbEntity mstTextbEntity = mstTextbDao.selectById(textbId);
        //教科書情報より、教科書マスタへ更新時、排他チェックエラー
        if (mstTextbEntity == null || !StringUtils.equals(DateUtils.format(mstTextbEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS), dtoList.get(0).getTextbUpdatimeStr())) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
        }

        //教科書マスタへ更新する。
        mstTextbEntity.setTextbNm(textbNm);
        mstTextbEntity.setPublisherDiv(publisherDiv);
        mstTextbEntity.setUpdDatime(DateUtils.getSysTimestamp());
        mstTextbEntity.setUpdUsrId(usrId);
        mstTextbDao.updateById(mstTextbEntity);

        for (F03002Dto dto : dtoList) {
            //(1)　下記条件で、教科書デフォルトターム情報に存在するかどうかをチェックする。
            textbDefTimeInfoEntity = textbDefTimeInfoDao.selectById(dto.getTdtiId());
            // 生徒タームプラン設定に生徒ごとの計画済み件数を取得する。
            f0300202DtoList = f03002Dao.selectStuOfPlanedCountList(dto.getTdtiId());
            // 画面．単元一覧のデータが選択されない場合
            if (dto.getIsCheck() == 0) {
                //チェックされない単元を繰り返して、生徒タームプラン設定に計画済みかどうかをチェックする。
                planFlg = f03002Dao.selectPlanReg(dto.getTdtiId());
                if (planFlg != null) {
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0092", "背景色赤の単元")+"@"+dto.getTdtiId());
                }

                //・　計画済み件数＝0の場合、当生徒の生徒タームプラン設定の削除登録を行う。
                for (F0300202Dto dto1 : f0300202DtoList) {
                    if (dto1.getPlanedCount() == 0) {
                        //1.当生徒の生徒タームプラン設定を物理削除する。
                        stuTermPlanDao.delete(new QueryWrapper<StuTermPlanEntity>().eq("stu_id", dto1.getStuId()).eq("textb_def_unit_id", dto.getTdtiId()));
                    }
                }

                //教科書デフォルトターム情報を論理削除する。
                if (textbDefTimeInfoEntity != null) {
                    //del_flg
                    textbDefTimeInfoEntity.setDelFlg(1);
                    //更新日時
                    textbDefTimeInfoEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //更新ユーザＩＤ
                    textbDefTimeInfoEntity.setUpdUsrId(usrId);
                    textbDefTimeInfoDao.updateById(textbDefTimeInfoEntity);
                }
            }
            //(2)　画面．単元一覧のデータが選択された場合、
            else {
                //更新
                if (textbDefTimeInfoEntity != null) {
                    TextbDefTimeInfoEntity textbDefTimeInfoEntity1 = null;
                    textbDefTimeInfoEntity1 = textbDefTimeInfoDao.selectOne(new QueryWrapper<TextbDefTimeInfoEntity>().ne("id",textbDefTimeInfoEntity.getId()).
                            eq("textb_id",mstTextbEntity.getTextbId()).eq("unit_id",dto.getUnitId()).eq("plan_learn_seasn",dto.getPlanLearnSeasn()).eq("del_flg",0));
                    if (textbDefTimeInfoEntity1 != null){
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0103")+"@"+dto.getUnitId()+"@"+dto.getPlanLearnSeasn());
                    }
                    //単元表示名
                    textbDefTimeInfoEntity.setUnitDispyNm(dto.getUnitDispyNm());
                    //単元NO
                    textbDefTimeInfoEntity.setUnitNo(dto.getUnitNo());
                    //節NO
                    textbDefTimeInfoEntity.setSectnNo(dto.getSectnNo());
                    //章NO
                    textbDefTimeInfoEntity.setChaptNo(dto.getChaptNo());
                    //教科書ページ
                    textbDefTimeInfoEntity.setTextbPage(dto.getTextbPage());
                    //計画学習時期
                    textbDefTimeInfoEntity.setPlanLearnSeasn(dto.getPlanLearnSeasn());
                    //計画学習時間（分）
                    textbDefTimeInfoEntity.setPlanLearnTm(dto.getPlanLearnTm());
                    //表示順番
                    textbDefTimeInfoEntity.setDispyOrder(dto.getDispyOrder());
                    //更新ユーザＩＤ
                    textbDefTimeInfoEntity.setUpdUsrId(usrId);
                    //del_flg
                    textbDefTimeInfoEntity.setDelFlg(0);
                    //更新日時
                    textbDefTimeInfoEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    textbDefTimeInfoDao.updateById(textbDefTimeInfoEntity);

                    //・　計画済み件数＝0の場合、当生徒の生徒タームプラン設定の削除登録を行う。
                    for (F0300202Dto dto1 : f0300202DtoList) {
                        if (dto1.getPlanedCount() == 0) {
                            //1.当生徒の生徒タームプラン設定を物理削除する。
                            stuTermPlanDao.delete(new QueryWrapper<StuTermPlanEntity>().eq("stu_id", dto1.getStuId()).eq("textb_def_unit_id", textbDefTimeInfoEntity.getId()));

                            //2.画面情報より、（画面．目標学習時間（分）/30分）の切り上げの件数分で繰り返し、生徒タームプラン設定へ登録する。
                            int j = 0;
                            if (dto.getPlanLearnTm() % 30 != 0) {
                                j = dto.getPlanLearnTm() / 30 + 1;
                            } else {
                                j = dto.getPlanLearnTm() / 30;
                            }
                            for (int i = 0; i < j; i++) {
                                //生徒タームプラン設定へ登録する。
                                stuTermPlanEntity = new StuTermPlanEntity();
                                //教科書デフォルト単元ID
                                stuTermPlanEntity.setTextbDefUnitId(textbDefTimeInfoEntity.getId());
                                //生徒ID
                                stuTermPlanEntity.setStuId(dto1.getStuId());
                                //単元ID
                                stuTermPlanEntity.setUnitId(dto.getUnitId());
                                //枝番
                                stuTermPlanEntity.setBnum(i + 1);
                                //計画学習時期ID
                                stuTermPlanEntity.setPlanLearnSeasnId(dto.getPlanLearnSeasnId());
                                //ブロック表示名(画面．教科名＋△＋画面．単元一覧．節NO＋'-'+画面．単元一覧．単元NO+△+画面．単元一覧．章名)
                                if (dto.getSectnNo() == "" && dto.getUnitNo() == "") {
                                    stuTermPlanEntity.setBlockDispyNm(dto.getSubjt() + "  " + dto.getChaptNm());
                                }else{
                                    stuTermPlanEntity.setBlockDispyNm(dto.getSubjt() + " " + dto.getSectnNo() + "-"+dto.getUnitNo()+" " + dto.getChaptNm());
                                }
                                //教科区分
                                stuTermPlanEntity.setSubjtDiv(dto.getSubjtDiv());
                                //計画学習時間（分）
                                if (dto.getPlanLearnTm() % 30 != 0 && i == j - 1) {
                                    stuTermPlanEntity.setPlanLearnTm(15);
                                } else {
                                    stuTermPlanEntity.setPlanLearnTm(30);
                                }
                                //計画登録フラグ
                                stuTermPlanEntity.setPlanRegFlg("0");
                                //表示順番
                                stuTermPlanEntity.setDispyOrder(dto.getDispyOrder());
                                //作成日時
                                stuTermPlanEntity.setCretDatime(DateUtils.getSysTimestamp());
                                //作成ユーザＩＤ
                                stuTermPlanEntity.setCretUsrId(usrId);
                                //更新日時
                                stuTermPlanEntity.setUpdDatime(DateUtils.getSysTimestamp());
                                //更新ユーザＩＤ
                                stuTermPlanEntity.setUpdUsrId(usrId);
                                //削除フラグ
                                stuTermPlanEntity.setDelFlg(0);
                                stuTermPlanDao.insert(stuTermPlanEntity);
                            }

                        }
                    }

                }
                //登録
                else {
                    TextbDefTimeInfoEntity textbDefTimeInfoEntity1 = null;
                    textbDefTimeInfoEntity1 = textbDefTimeInfoDao.selectOne(new QueryWrapper<TextbDefTimeInfoEntity>().eq("textb_id",mstTextbEntity.getTextbId()).eq("unit_id",dto.getUnitId()).eq("plan_learn_seasn",dto.getPlanLearnSeasn()).eq("del_flg",0));
                    if (textbDefTimeInfoEntity1 != null){
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0103")+"@"+dto.getUnitId()+"@"+dto.getPlanLearnSeasn());
                    }
                    textbDefTimeInfoEntity = new TextbDefTimeInfoEntity();
                    //教科書ID
                    textbDefTimeInfoEntity.setTextbId(dto.getTextbId());
                    //教科区分
                    textbDefTimeInfoEntity.setSubjtDiv(dto.getSubjtDiv());
                    //組織ID
                    textbDefTimeInfoEntity.setOrgId(orgId);
                    //単元ID
                    textbDefTimeInfoEntity.setUnitId(dto.getUnitId());
                    //単元表示名
                    textbDefTimeInfoEntity.setUnitDispyNm(dto.getUnitDispyNm());
                    //単元NO
                    textbDefTimeInfoEntity.setUnitNo(dto.getUnitNo());
                    //節NO
                    textbDefTimeInfoEntity.setSectnNo(dto.getSectnNo());
                    //章NO
                    textbDefTimeInfoEntity.setChaptNo(dto.getChaptNo());
                    //教科書ページ
                    textbDefTimeInfoEntity.setTextbPage(dto.getTextbPage());
                    //計画学習時期
                    textbDefTimeInfoEntity.setPlanLearnSeasn(dto.getPlanLearnSeasn());
                    //計画学習時間（分）
                    textbDefTimeInfoEntity.setPlanLearnTm(dto.getPlanLearnTm());
                    //表示順番
                    textbDefTimeInfoEntity.setDispyOrder(dto.getDispyOrder());
                    //更新ユーザＩＤ
                    textbDefTimeInfoEntity.setUpdUsrId(usrId);
                    //作成ユーザＩＤ
                    textbDefTimeInfoEntity.setCretUsrId(usrId);
                    //del_flg
                    textbDefTimeInfoEntity.setDelFlg(0);
                    //更新日時
                    textbDefTimeInfoEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //作成日時
                    textbDefTimeInfoEntity.setCretDatime(DateUtils.getSysTimestamp());
                    textbDefTimeInfoDao.insert(textbDefTimeInfoEntity);

                    //生徒登録リストを取得する。
                    textbChocEntityList = stuTextbChocDao.selectList(new QueryWrapper<StuTextbChocEntity>().select("stu_id").eq("textb_id", textbId).eq("del_flg", 0));
                    for (StuTextbChocEntity entity : textbChocEntityList) {
                        int j = 0;
                        if (dto.getPlanLearnTm() % 30 != 0) {
                            j = dto.getPlanLearnTm() / 30 + 1;
                        } else {
                            j = dto.getPlanLearnTm() / 30;
                        }
                        for (int i = 0; i < j; i++) {
                            stuTermPlanEntity = new StuTermPlanEntity();
                            //教科書デフォルト単元ID
                            stuTermPlanEntity.setTextbDefUnitId(textbDefTimeInfoEntity.getId());
                            //生徒ID
                            stuTermPlanEntity.setStuId(entity.getStuId());
                            //単元ID
                            stuTermPlanEntity.setUnitId(dto.getUnitId());
                            //枝番
                            stuTermPlanEntity.setBnum(i + 1);
                            //計画学習時期ID
                            stuTermPlanEntity.setPlanLearnSeasnId(dto.getPlanLearnSeasnId());
                            //ブロック表示名(画面．教科名＋△＋画面．単元一覧．節NO＋'-'+画面．単元一覧．単元NO+△+画面．単元一覧．章名)
                            if (dto.getSectnNo() == "" && dto.getUnitNo() == "") {
                                 blockNm = dto.getSubjt() + "  " + dto.getChaptNm();
                                if (blockNm.length()>100){
                                    blockNm = blockNm.substring(0,100);
                                }
                                stuTermPlanEntity.setBlockDispyNm(blockNm);
                            }else{
                                 blockNm = dto.getSubjt() + " " + dto.getSectnNo() + "-"+dto.getUnitNo()+" " + dto.getChaptNm();
                                if (blockNm.length()>100){
                                    blockNm = blockNm.substring(0,100);
                                }
                                stuTermPlanEntity.setBlockDispyNm(blockNm);
                            }
                            //教科区分
                            stuTermPlanEntity.setSubjtDiv(dto.getSubjtDiv());
                            //計画学習時間（分）
                            if (dto.getPlanLearnTm() % 30 != 0 && i == j - 1) {
                                stuTermPlanEntity.setPlanLearnTm(15);
                            } else {
                                stuTermPlanEntity.setPlanLearnTm(30);
                            }
                            //計画登録フラグ
                            stuTermPlanEntity.setPlanRegFlg("0");
                            //表示順番
                            stuTermPlanEntity.setDispyOrder(dto.getDispyOrder());
                            //作成日時
                            stuTermPlanEntity.setCretDatime(DateUtils.getSysTimestamp());
                            //作成ユーザＩＤ
                            stuTermPlanEntity.setCretUsrId(usrId);
                            //更新日時
                            stuTermPlanEntity.setUpdDatime(DateUtils.getSysTimestamp());
                            //更新ユーザＩＤ
                            stuTermPlanEntity.setUpdUsrId(usrId);
                            //削除フラグ
                            stuTermPlanEntity.setDelFlg(0);
                            stuTermPlanDao.insert(stuTermPlanEntity);
                        }
                    }
                }
            }
        }
        return R.ok();
    }

    /**
     * 教科書作成ボタン押下
     *
     * @param dtoList 画面の情報
     * @return        教科書作成情報
     */
    @Override
    public R createFn(List<F03002Dto> dtoList) {
        //ユーザＩＤ
        String usrId = ShiroUtils.getUserId();
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //
        String brandCd = ShiroUtils.getBrandcd();

        //変更後学年の塾学習期間IDの取得
        Integer crmLearnPrdId = commonDao.seletctCrmLearnPrdIdAfterUpdateSchy(orgId, brandCd, dtoList.get(0).getSchyDiv());
        if (crmLearnPrdId == null) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0123", "塾時期"));
        }
        MstTextbEntity mstTextbEntity;
        //7.4 教科書名の重複チェック
        mstTextbEntity = mstTextbDao.selectOne(new QueryWrapper<MstTextbEntity>().and(w -> w.eq("subjt_div", dtoList.get(0).getSubjtDiv()).eq("org_id", orgId).eq("publisher_div", dtoList.get(0).getPublisherDiv()).eq("schy_div", dtoList.get(0).getSchyDiv()).eq("textb_nm", dtoList.get(0).getTextbNm()).eq("del_flg", 0)));
        if (mstTextbEntity != null) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0046", dtoList.get(0).getTextbNm(), "教科書マスタ"));
        }

        //教科書マスタ登録
        mstTextbEntity = new MstTextbEntity();
        //教科区分
        mstTextbEntity.setSubjtDiv(dtoList.get(0).getSubjtDiv());
        //組織ID
        mstTextbEntity.setOrgId(orgId);
        //教科書名
        mstTextbEntity.setTextbNm(dtoList.get(0).getTextbNm());
        //出版社区分
        mstTextbEntity.setPublisherDiv(dtoList.get(0).getPublisherDiv());
        //学年区分
        mstTextbEntity.setSchyDiv(dtoList.get(0).getSchyDiv());
        //教科書廃止フラグ
        mstTextbEntity.setTextbAboltFlg("0");
        //更新ユーザＩＤ
        mstTextbEntity.setUpdUsrId(usrId);
        //作成ユーザＩＤ
        mstTextbEntity.setCretUsrId(usrId);
        //del_flg
        mstTextbEntity.setDelFlg(0);
        //更新日時
        mstTextbEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //作成日時
        mstTextbEntity.setCretDatime(DateUtils.getSysTimestamp());
        mstTextbDao.insert(mstTextbEntity);
        Integer textbId = mstTextbEntity.getTextbId();
        String subjtDiv = mstTextbEntity.getSubjtDiv();
        //教科書デフォルトターム情報
        TextbDefTimeInfoEntity textbDefTimeInfoEntity;
        for (F03002Dto dto : dtoList) {
            TextbDefTimeInfoEntity textbDefTimeInfoEntity1 = null;
            textbDefTimeInfoEntity1 = textbDefTimeInfoDao.selectOne(new QueryWrapper<TextbDefTimeInfoEntity>().eq("textb_id",mstTextbEntity.getTextbId()).eq("unit_id",dto.getUnitId()).eq("plan_learn_seasn",dto.getPlanLearnSeasn()).eq("del_flg",0));
            if (textbDefTimeInfoEntity1 != null){
                throw new RRException(MessageUtils.getMessage("MSGCOMN0103")+"@"+dto.getUnitId()+"@"+dto.getPlanLearnSeasn());
            }
            textbDefTimeInfoEntity = new TextbDefTimeInfoEntity();
            //教科書ID
            textbDefTimeInfoEntity.setTextbId(textbId);
            //教科区分
            textbDefTimeInfoEntity.setSubjtDiv(subjtDiv);
            //組織ID
            textbDefTimeInfoEntity.setOrgId(orgId);
            //単元ID
            textbDefTimeInfoEntity.setUnitId(dto.getUnitId());
            //単元表示名
            textbDefTimeInfoEntity.setUnitDispyNm(dto.getUnitDispyNm());
            //単元NO
            textbDefTimeInfoEntity.setUnitNo(dto.getUnitNo());
            //節NO
            textbDefTimeInfoEntity.setSectnNo(dto.getSectnNo());
            //章NO
            textbDefTimeInfoEntity.setChaptNo(dto.getChaptNo());
            //教科書ページ
            textbDefTimeInfoEntity.setTextbPage(dto.getTextbPage());
            //計画学習時期
            textbDefTimeInfoEntity.setPlanLearnSeasn(dto.getPlanLearnSeasn());
            //計画学習時間（分）
            textbDefTimeInfoEntity.setPlanLearnTm(dto.getPlanLearnTm());
            //表示順番
            textbDefTimeInfoEntity.setDispyOrder(dto.getDispyOrder());
            //更新ユーザＩＤ
            textbDefTimeInfoEntity.setUpdUsrId(usrId);
            //作成ユーザＩＤ
            textbDefTimeInfoEntity.setCretUsrId(usrId);
            //del_flg
            textbDefTimeInfoEntity.setDelFlg(0);
            //更新日時
            textbDefTimeInfoEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //作成日時
            textbDefTimeInfoEntity.setCretDatime(DateUtils.getSysTimestamp());
            textbDefTimeInfoDao.insert(textbDefTimeInfoEntity);
        }
        return R.ok();
    }
}
