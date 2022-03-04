package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.dao.MstVariousSetDao;
import jp.learningpark.modules.common.dao.StuGetPointHstDao;
import jp.learningpark.modules.common.dao.StuPointDao;
import jp.learningpark.modules.common.entity.MstVariousSetEntity;
import jp.learningpark.modules.common.entity.StuGetPointHstEntity;
import jp.learningpark.modules.common.entity.StuPointEntity;
import jp.learningpark.modules.manager.dao.F09006Dao;
import jp.learningpark.modules.manager.dto.F09006Dto;
import jp.learningpark.modules.manager.dto.F09006ResetPointDto;
import jp.learningpark.modules.manager.dto.F09006StuPointDto;
import jp.learningpark.modules.manager.service.F09006Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class F09006ServiceImpl implements F09006Service {
    private final F09006Dao f09006Dao;
    private final StuPointDao stuPointDao;
    private final MstVariousSetDao mstVariousSetDao;
    private final StuGetPointHstDao stuGetPointHstDao;
    @Autowired
    private F09006ServiceImpl f09006Service;
    Logger logger = LoggerFactory.getLogger(F09006ServiceImpl.class);

    public F09006ServiceImpl(F09006Dao f09006Dao, StuPointDao stuPointDao, MstVariousSetDao mstVariousSetDao,
                             StuGetPointHstDao stuGetPointHstDao) {
        this.f09006Dao = f09006Dao;
        this.stuPointDao = stuPointDao;
        this.mstVariousSetDao = mstVariousSetDao;
        this.stuGetPointHstDao = stuGetPointHstDao;
    }

    @Override
    public R init(Map<String, Object> params, Integer limit, Integer page) {
        List<F09006Dto> f09006DtoList = f09006Dao.init(params, limit, (page - 1) * limit);
        int totalCount = f09006Dao.initTotalCount(params);
        giveStuAccumulatedPoint(f09006DtoList);

        // 上記取得できない場合、画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する。
        if (f09006DtoList.isEmpty()) {
            return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0017", "ユーザー"));
        }
        return R.ok().put("page", new PageUtils(f09006DtoList, totalCount, limit, page));

    }

    @Override
    public R checkAfter(List<String> stuList, Integer limit, Integer page) {
        if (stuList == null || stuList.size() == 0) {
            return null;
        }
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        List<F09006Dto> f09006DtoList = f09006Dao.check_after(stuList, orgId, limit, (page - 1) * limit);
        int totalCount = f09006Dao.checkAfterTotalCount(stuList, orgId);
        giveStuAccumulatedPoint(f09006DtoList);

        if (f09006DtoList.isEmpty()) {
            return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0017", "ユーザー"));
        }

        return R.ok().put("page" , new PageUtils(f09006DtoList, totalCount, limit, page));
    }

    /**
     * 返されるパラメータ[f09006DtoList]について、学生の累積スコアを計算します
     * [f09006DtoList]に累積ポイントを割り当てます
     * @param f09006DtoList 返されるページデータ
     */
    private void giveStuAccumulatedPoint(List<F09006Dto> f09006DtoList){
        if (f09006DtoList.isEmpty()){
            return;
        }

        // 2020/12/4 huangxinliang modify start
        Map<String, Integer> stuAccumulatedPoint = calculateStudentPoints(f09006DtoList);

        for (F09006Dto dto : f09006DtoList) {
            dto.setAllPoint(stuAccumulatedPoint.getOrDefault(dto.getUsrId(), 0) + dto.getMovePoint());
        }
        // 2020/12/4 huangxinliang modify end
    }

    /**
     *学生リストに従って学生の累積ポイント情報を照会する
     * @param stuIdList ログインポイントを獲得した学生のコレクション
     * @return 学生ポイント情報を返す
     *  <pre>
     *      key:   学生ID
     *      value: 累計ポイント
     *  </pre>
     */
    @SuppressWarnings("ALL")
    public Map<String, Integer> calculateStudentPoints(List<F09006Dto> stuIdList) {
        // 入退室各種設定マスタを取得する
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        MstVariousSetEntity variousSet = mstVariousSetDao.selectOne(new QueryWrapper<MstVariousSetEntity>()
                .eq("org_id", orgId)
                .eq("del_flg", 0));

        if (variousSet == null){
            throw new RRException(MessageUtils.getMessage("MSGCOMN0017", "入退室各種設定マスタ"));
        }
        // 2020/12/4 huangxinliang modify start
        Map<String, String> mapOfLoginIdAndUserId = stuIdList.stream().collect(Collectors.toMap(F09006Dto::getStuId, F09006Dto::getUsrId));
        // 累計ポイントを取得する。　
        List<F09006StuPointDto> moveHistoryPoint = moveHistoryPoint(stuIdList, orgId);
        return calculateTotalScore(moveHistoryPoint);

    }

    /**
     * 調整ポイントを取得する
     * @param stuIdList     ログインポイントを獲得した学生のコレクション
     * @param orgId         組織ID
     */
    private List<F09006StuPointDto> moveHistoryPoint(List<F09006Dto> stuIdList, String orgId) {
        return f09006Dao.moveHistoryPoint(stuIdList, orgId);
    }

    /**
     * さまざまな場所から照会された学生ポイント情報を合計します
     * @param lists 学生リスト
     */
    @SuppressWarnings("unchecked")
    private Map<String, Integer> calculateTotalScore(List<F09006StuPointDto>... lists){
        Map<String, Integer> totalScore = new HashMap<>();
        for (List<F09006StuPointDto> list : lists) {
            for (F09006StuPointDto dto : list) {
                String key = dto.getStuId();
                if (totalScore.containsKey(key)) {
                    totalScore.put(key, totalScore.get(key) + dto.getPoint());
                }else {
                    totalScore.put(key, dto.getPoint());
                }
            }
        }
        return totalScore;
    }

    @Override
    public R resetStuPoint(F09006ResetPointDto dto) {
        String userId = ShiroUtils.getUserId();
        List<String> stuIdList = dto.getStuIdList();

        // 既存の学生データを取得する
        List<StuPointEntity> stuId = stuPointDao.selectList(new QueryWrapper<StuPointEntity>()
                .in("stu_id", stuIdList));
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        StuGetPointHstEntity stuGetPointHstEntity = new StuGetPointHstEntity();
        //付与ポイント区分
        stuGetPointHstEntity.setPointTypeDiv("5");
        //付与ポイント日時
        stuGetPointHstEntity.setGetPointDatime(sysTimestamp);
        //作成日時
        stuGetPointHstEntity.setCretDatime(sysTimestamp);
        //作成ユーザＩＤ
        stuGetPointHstEntity.setCretUsrId(userId);
        //更新日時
        stuGetPointHstEntity.setUpdDatime(sysTimestamp);
        //更新ユーザＩＤ
        stuGetPointHstEntity.setUpdUsrId(userId);
        //削除フラグ
        stuGetPointHstEntity.setDelFlg(0);
        R r = R.ok();
        for (StuPointEntity stuPoint : stuId) {
            // 2020/12/4 huangxinliang modify start
            //更新日時
            stuPoint.setUpdDatime(sysTimestamp);
            //更新ユーザＩＤ
            stuPoint.setUpdUsrId(userId);
            //リセット日時
            stuPoint.setResetDatime(sysTimestamp);
            try {
                f09006Service.doResetPoint(stuGetPointHstEntity, stuPoint);
            }catch (Exception e){
                logger.error(e.getMessage());
                // modify at 2021/09/01 for V9.02 by NWT wen START
                throw new RRException(MessageUtils.getMessage("MSGCOMN0142", "生徒ポイント", "更新"));
                // modify at 2021/09/01 for V9.02 by NWT wen END
            }
            // 2020/12/4 huangxinliang modify end
        }
        return r;
    }

    @Transactional(rollbackFor = Exception.class)
    public void doResetPoint(StuGetPointHstEntity stuGetPointHstEntity, StuPointEntity stuPoint){
        //ID
        stuGetPointHstEntity.setId(null);
        //組織ID
        stuGetPointHstEntity.setOrgId(stuPoint.getOrgId());
        //生徒ID
        stuGetPointHstEntity.setStuId(stuPoint.getStuId());
        //付与ポイント数
        stuGetPointHstEntity.setPointNum(stuPoint.getMovePoint());
        //調整ポイント履歴データを作成する。
        stuGetPointHstDao.insert(stuGetPointHstEntity);
        //ポイント
        stuPoint.setPoint(0);
        //学習実績登録ポイント
        stuPoint.setDoLoginPoint(0);
        //学習実績累計ポイント
        stuPoint.setDoTotalPoint(0);
        //入室ポイント
        stuPoint.setInRoomPoint(0);
        //調整ポイント
        stuPoint.setMovePoint(0);
        //add at 2021/08/23 for V9.02 by NWT LiGX START
        //出席簿登録時ポイント
        stuPoint.setAttendPoint(0);
        //誕生日時ポイント
        stuPoint.setBirthdayPoint(0);
        //add at 2021/08/23 for V9.02 by NWT LiGX END
        //生徒ポイントを更新する
        stuPointDao.updateById(stuPoint);
    }
}
