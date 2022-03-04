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
import jp.learningpark.modules.common.dao.StuTestGoalResultDDao;
import jp.learningpark.modules.common.dao.StuTestGoalResultHDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.StuTestGoalResultDEntity;
import jp.learningpark.modules.common.entity.StuTestGoalResultHEntity;
import jp.learningpark.modules.student.dao.F10502Dao;
import jp.learningpark.modules.student.dto.F10502Dto;
import jp.learningpark.modules.student.service.F10502Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>F10502 テスト目標結果登録画面 ServiceImpl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/11/21 : gong: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F10502ServiceImpl implements F10502Service {

    /**
     * F10502 テスト目標結果登録画面 Dao
     */
    @Autowired
    private F10502Dao dao;

    /**
     * 生徒テスト目標結果_ヘッダ dao
     */
    @Autowired
    private StuTestGoalResultHDao stuTestGoalResultHDao;

    /**
     * 生徒テスト目標結果_明細 dao
     */
    @Autowired
    private StuTestGoalResultDDao stuTestGoalResultDDao;

    /**
     * <p>1.4 生徒基本マスタ、教科書マスタ、コードマスタより、教科リストを取得し</p>
     *
     * @param stuId 生徒Id
     * @return
     */
    @Override
    public List<MstCodDEntity> getSubjtDivByStuId(String stuId) {
        return dao.selectSubjtDivByStuId(stuId);
    }

    /**
     * 1.5 生徒テスト目標結果_明細リストの情報
     *
     * @param id
     * @param schyDiv
     * @return
     */
    @Override
    public List<F10502Dto> getWithCodById(Integer id, String schyDiv) {
        return dao.selectWithCodById(id, schyDiv);
    }

    /**
     * <p>「登録」ボタン押下</p>
     *
     * @param dtos 画面．生徒テスト目標結果_明細リスト
     * @return
     */
    @Override
    public R update(List<F10502Dto> dtos) {
        //id
        Integer id = dtos.get(0).getId();
        //生徒ID
        String stuId = StringUtils.isEmpty(dtos.get(0).getStuId()) || StringUtils.equals(
                "undefined", dtos.get(0).getStuId()) ? ShiroUtils.getUserId() : dtos.get(0).getStuId();
        //画面．学年区分
        String schyDiv = dtos.get(0).getSchyDiv();
        //画面．テスト分類区分
        String testTypeDiv = dtos.get(0).getTestTypeDiv();
        //画面．テスト種別区分
        String testKindDiv = dtos.get(0).getTestKindDiv();
        //画面．テスト対象年
        Integer year = dtos.get(0).getTestTgtY();
        //画面．テスト対象月
        Integer month = dtos.get(0).getTestTgtM();
        String dateStr = year + "/" + month;
        Date date = DateUtils.parse(dateStr, GakkenConstant.DATE_FORMAT_YYYY_MM_SLASH);
        //日付チェック
        if (date == null) {
            throw new RRException(MessageUtils.getMessage("MSGCOMD0013", "年月"));
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("stuId", stuId);
        dataMap.put("schyDiv", schyDiv);
        dataMap.put("testTypeDiv", testTypeDiv);
        dataMap.put("testKindDiv", testKindDiv);
        dataMap.put("year", year);
        dataMap.put("month", month);
        dataMap.put("dtos", dtos);
        //新規
        if (id == null) {
            dataMap.put("headId", null);
            if (!subjtCheck(dataMap)) {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0097", "教科名"));
            }
            //生徒テスト目標結果_ヘッダ登録
            StuTestGoalResultHEntity stuTestGoalResultHEntity = new StuTestGoalResultHEntity();
            //生徒ID
            stuTestGoalResultHEntity.setStuId(stuId);
            //画面．学年区分
            stuTestGoalResultHEntity.setSchyDiv(schyDiv);
            //画面．テスト分類区分
            stuTestGoalResultHEntity.setTestTypeDiv(testTypeDiv);
            //画面．テスト種別区分
            stuTestGoalResultHEntity.setTestKindDiv(testKindDiv);
            //画面．テスト対象年
            stuTestGoalResultHEntity.setTestTgtY(year);
            //画面．テスト対象月
            stuTestGoalResultHEntity.setTestTgtM(month);
            //作成ユーザＩＤ
            stuTestGoalResultHEntity.setCretUsrId(stuId);
            //作成日時
            stuTestGoalResultHEntity.setCretDatime(DateUtils.getSysTimestamp());
            //更新日時
            stuTestGoalResultHEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            stuTestGoalResultHEntity.setUpdUsrId(stuId);
            //del_flg
            stuTestGoalResultHEntity.setDelFlg(0);
            //新規
            stuTestGoalResultHDao.insert(stuTestGoalResultHEntity);

            //生徒テスト目標結果_明細登録
            StuTestGoalResultDEntity stuTestGoalResultDEntity;
            for (F10502Dto dto : dtos) {
                stuTestGoalResultDEntity = new StuTestGoalResultDEntity();
                //id
                stuTestGoalResultDEntity.setId(stuTestGoalResultHEntity.getId());
                //教科区分
                stuTestGoalResultDEntity.setSubjtDiv(dto.getSubjtDiv());
                //目標点数
                stuTestGoalResultDEntity.setGoalPoints(dto.getGoalPoints());
                //結果点数
                stuTestGoalResultDEntity.setResultPoints(dto.getResultPoints());
                //結果満点
                stuTestGoalResultDEntity.setResultPerfectPoints(dto.getResultPerfectPoints());
                //結果順位
                stuTestGoalResultDEntity.setResultRank(dto.getResultRank());
                //結果平均点
                stuTestGoalResultDEntity.setResultAvg(dto.getResultAvg());
                //偏差値
                stuTestGoalResultDEntity.setDevionValue(dto.getDevionValue());
                //作成日時
                stuTestGoalResultDEntity.setCretDatime(DateUtils.getSysTimestamp());
                //作成ユーザＩＤ
                stuTestGoalResultDEntity.setCretUsrId(stuId);
                //更新ユーザＩＤ
                stuTestGoalResultDEntity.setUpdUsrId(stuId);
                //更新日時
                stuTestGoalResultDEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //削除フラグ
                stuTestGoalResultDEntity.setDelFlg(0);
                //新規
                stuTestGoalResultDDao.insert(stuTestGoalResultDEntity);
            }
        }
        //更新
        else {
            //排他チェックエラーの場合、処理を中断し、エラー内容を画面の上部に表示する
            StuTestGoalResultHEntity check = stuTestGoalResultHDao.selectById(id);
            if (check == null || !StringUtils.equals(
                    DateUtils.format(check.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS), dtos.get(0).getUpdateTimeString())) {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
            }
            dataMap.put("headId", id);
            if (!subjtCheck(dataMap)) {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0097", "教科名"));
            }
            //生徒テスト目標結果_ヘッダ更新
            StuTestGoalResultHEntity stuTestGoalResultHEntity = new StuTestGoalResultHEntity();
            //id
            stuTestGoalResultHEntity.setId(id);
            //画面．学年区分
            stuTestGoalResultHEntity.setSchyDiv(schyDiv);
            //画面．テスト分類区分
            stuTestGoalResultHEntity.setTestTypeDiv(testTypeDiv);
            //画面．テスト種別区分
            stuTestGoalResultHEntity.setTestKindDiv(testKindDiv);
            //画面．テスト対象年
            stuTestGoalResultHEntity.setTestTgtY(year);
            //画面．テスト対象月
            stuTestGoalResultHEntity.setTestTgtM(month);
            //更新日時
            stuTestGoalResultHEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            stuTestGoalResultHEntity.setUpdUsrId(stuId);
            //更新
            stuTestGoalResultHDao.updateById(stuTestGoalResultHEntity);

            //生徒テスト目標結果_明細更新
            stuTestGoalResultDDao.delete(new QueryWrapper<StuTestGoalResultDEntity>().and(w->w.eq("id", id)));
            StuTestGoalResultDEntity stuTestGoalResultDEntity;
            for (F10502Dto dto : dtos) {
                stuTestGoalResultDEntity = new StuTestGoalResultDEntity();
                //id
                stuTestGoalResultDEntity.setId(id);
                //教科区分
                stuTestGoalResultDEntity.setSubjtDiv(dto.getSubjtDiv());
                //目標点数
                stuTestGoalResultDEntity.setGoalPoints(dto.getGoalPoints());
                //結果点数
                stuTestGoalResultDEntity.setResultPoints(dto.getResultPoints());
                //結果満点
                stuTestGoalResultDEntity.setResultPerfectPoints(dto.getResultPerfectPoints());
                //結果順位
                stuTestGoalResultDEntity.setResultRank(dto.getResultRank());
                //結果平均点
                stuTestGoalResultDEntity.setResultAvg(dto.getResultAvg());
                //偏差値
                stuTestGoalResultDEntity.setDevionValue(dto.getDevionValue());
                //作成日時
                stuTestGoalResultDEntity.setCretDatime(DateUtils.getSysTimestamp());
                //作成ユーザＩＤ
                stuTestGoalResultDEntity.setCretUsrId(stuId);
                //更新ユーザＩＤ
                stuTestGoalResultDEntity.setUpdUsrId(stuId);
                //更新日時
                stuTestGoalResultDEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //削除フラグ
                stuTestGoalResultDEntity.setDelFlg(0);
                //新規
                stuTestGoalResultDDao.insert(stuTestGoalResultDEntity);
            }
        }
        return R.ok();
    }

    @Override
    public List<MstCodDEntity> getTestSubjt(String ShyDiv) {
        return dao.getTestSubjt(ShyDiv);
    }

    /**
     * @return
     */
    private boolean subjtCheck(Map<String, Object> dataMap) {
        //同じ学年、テスト分類、種別区分、時期の場合、教科名重複チェックを行います、
        List<Integer> detailIds = dao.getHeadId(dataMap);
        List<String> detail;
        List<String> subjt = new ArrayList<>();
        boolean disjoint = true;
        List<F10502Dto> dtos = (List<F10502Dto>)dataMap.get("dtos");
        if (detailIds.size() > 0) {
            for (F10502Dto dto : dtos) {
                subjt.add(dto.getSubjtDiv());
            }
            detail = dao.getSubjt(detailIds);
            if (!Collections.disjoint(detail, subjt)) {
                disjoint = Collections.disjoint(detail, subjt);
            }
        }
        return disjoint;
    }
}
