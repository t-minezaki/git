package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.dao.PerfStandMstDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.PerfStandMstEntity;
import jp.learningpark.modules.manager.dao.F21028Dao;
import jp.learningpark.modules.manager.dto.F21028Dto;
import jp.learningpark.modules.manager.service.F21029Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * F21029ServiceImpl
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/17 ： NWT)hxl ： 新規作成
 * @date 2020/02/17 11:56
 */
@Service
public class F21029ServiceImpl implements F21029Service {

    /**
     * f21028Dao
     */
    @Autowired
    F21028Dao f21028Dao;

    /**
     * perfStandMstDao
     */
    @Autowired
    PerfStandMstDao perfStandMstDao;

    /**
     * mstCodDDao
     */
    @Autowired
    MstCodDDao mstCodDDao;

    /**
     * 生徒情報を取得する
     *
     * @param params パラメータ
     * @return
     */
    @Override
    public R getLineDate(Map<String, Object> params) {
        //result R
        R r = R.ok();
        //result grid data
        List<F21028Dto> lineData;
        //該当組織の全部グループを抽出して
        List<F21028Dto> grpList = f21028Dao.getGroup((String) params.get("orgId"));
        r.put("grpList", grpList);
        //コードマスタ_明細から学年リストの値を抽出して
        List<F21028Dto> schyList = f21028Dao.getSchy();
        r.put("schyList", schyList);
        //初期化かどうか
        if ((Boolean) params.get("isFirst")){
            //学生ID範囲検索
            lineData = f21028Dao.getStuListByIdList(params);
            if (!(Boolean) params.get("pageTurning")) {
                //該当登録ユーザの設定済標準値を抽出して、判断用にする
                PerfStandMstEntity perfStandMstEntity = perfStandMstDao.selectOne(new QueryWrapper<PerfStandMstEntity>().select("perf_stand_week1", "perf_stand_week2", "perf_stand_week3").eq("org_id", params.get("orgId")).eq("usr_id", params.get("usrId")).eq("del_flg", 0));
                r.put("standEntity", perfStandMstEntity);
                r.put("schyList", schyList);
                String orgId = ShiroUtils.getUserEntity().getOrgId();
                String userId = ShiroUtils.getUserId();
                r.put("orgId", orgId);
                r.put("userId", userId);
                //for sort of schy
                List<MstCodDEntity> schyDivs = mstCodDDao.selectList(new QueryWrapper<MstCodDEntity>().select("cod_value, sort").eq("cod_key", "SCHY_DIV").eq("del_flg", 0));
                r.put("schyDivs", schyDivs);
            }
            if (!(Boolean) params.get("pageTurning") && lineData.size() == 0){
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒")).put("grpList", grpList).put("schyList", schyList);
            }
        }else {
            //学生ID範囲検索
            lineData = f21028Dao.getStuList(params);
            //学生ID数量確認
            if (lineData.size() == 0){
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒")).put("grpList", grpList).put("schyList", schyList);
            }
        }
        if (lineData.size() > 0){
            //学徒IDと行データのマッピング
            Map<String, F21028Dto> stuIdLineDataMap = new HashMap<>(lineData.size());
            //学徒IDリスト
            List<String> stuIdList = new ArrayList<>();
            //配列を反復処理する
            for (F21028Dto f21028Dto : lineData) {
                //学生ID list -> map
                stuIdLineDataMap.put(f21028Dto.getStuId(), f21028Dto);
                //学生ID list
                stuIdList.add(f21028Dto.getStuId());
            }
            //minDate
            Date startDate = (Date) params.get("date");
            //maxDate
            Date endDate = DateUtils.addDays(startDate, 48);
            //学習時間データを取得する
            List<F21028Dto> dataByDay = f21028Dao.getDataByWeek(stuIdList, startDate, endDate);
//            if (!(Boolean) params.get("isFirst")) {
//                //初期化されていない && 学習データは0です
//                if (dataByDay.size() == 0) {
//                    return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒"));
//                }
//            }
            //日付とインデックスのマッピング
            Map<Date, Integer>dayIndexMap = new HashMap<>(8);
            for (int i = 0; i < 7; i++){
                dayIndexMap.put(DateUtils.addDays(startDate, i * 7), 6 - i);
            }
            //データ入力
            dataByDay.forEach(v -> {
                stuIdLineDataMap.get(v.getStuId()).getDay()[dayIndexMap.get(v.getPerfYmd())] = v.getPerfTime()==null?-1:v.getPerfTime();
            });
            List<F21028Dto> f21028Dtos = new ArrayList<>(stuIdLineDataMap.values());
            Collections.sort(f21028Dtos);
            //行データを返します
            r.put("lineData", f21028Dtos);
        }
        return r;
    }
}
