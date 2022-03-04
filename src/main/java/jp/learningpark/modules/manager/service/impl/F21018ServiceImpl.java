package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.gakken.id2.V2GakkenIDPrivilegeSvcStub;
import jp.gakken.id2.V2GakkenTransactionIDPrivilegeSvcStub;
import jp.learningpark.framework.gakkenID.GakkenIdAPI;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.service.impl.F40006ServiceImpl;
import jp.learningpark.modules.common.dao.MstStuDao;
import jp.learningpark.modules.common.dao.MstUsrDao;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.manager.dao.F21018Dao;
import jp.learningpark.modules.manager.dto.F21018Dto;
import jp.learningpark.modules.manager.service.F21018Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import sun.misc.BASE64Decoder;

/**
 * <p>
 * F21018ServiceImpl
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/20 ： NWT)hxl ： 新規作成
 * @date 2020/02/20 17:32
 */
@Service
public class F21018ServiceImpl implements F21018Service {

    /**
     * f21018Dao
     */
    @Autowired
    F21018Dao f21018Dao;

    /**
     * mstStuDao
     */
    @Autowired
    MstStuDao mstStuDao;

    /**
     * mstUsrDao
     */
    @Autowired
    MstUsrDao mstUsrDao;

    /**
     * f40006Service
     */
    @Autowired
    F40006ServiceImpl f40006Service;

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 情報を取得する
     *
     * @param stuId 生徒ID
     * @return
     */
    @Override
    public R init(String stuId) {
        //学生IDが空です
        if (StringUtils.isEmpty(stuId)){
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒情報"));
        }
        //生徒IDに基づいて生徒情報を取得する
        F21018Dto f21018Dto = f21018Dao.selectStuInfo(stuId);
        //生徒情報が存在しません
        if (f21018Dto == null){
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒情報"));
        }
        //パッケージデータ
        R r = R.ok().put("stuInfo", f21018Dto);
        //保護者IDリスト
        List<String> guardIdList = new ArrayList<>();
        //保護者IDは空ではありません
        if (!StringUtils.isEmpty(f21018Dto.getGuardId())){
            //リストに保護者IDを追加します
            guardIdList.add(f21018Dto.getGuardId());
        }
        //保護者1IDは空ではありません
        if (!StringUtils.isEmpty(f21018Dto.getGuard1Id())){
            //リストに保護者1IDを追加します
            guardIdList.add(f21018Dto.getGuard1Id());
        }
        //保護者2IDは空ではありません
        if (!StringUtils.isEmpty(f21018Dto.getGuard2Id())){
            //リストに保護者2IDを追加します
            guardIdList.add(f21018Dto.getGuard2Id());
        }
        //保護者3IDは空ではありません
        if (!StringUtils.isEmpty(f21018Dto.getGuard3Id())){
            //リストに保護者3IDを追加します
            guardIdList.add(f21018Dto.getGuard3Id());
        }
        //保護者4IDは空ではありません
        if (!StringUtils.isEmpty(f21018Dto.getGuard4Id())){
            //リストに保護者4IDを追加します
            guardIdList.add(f21018Dto.getGuard4Id());
        }
        //保護者IDが存在します
        if (guardIdList.size() != 0){
            //保護者IDに基づいて保護者情報を取得する
            List<F21018Dto> guardInfoList = f21018Dao.selectGuardInfo(guardIdList);
            //保護者情報が存在します
            if (guardInfoList.size() > 0){
                /* 2021/3/12 4-37 modify start */
                MstUsrEntity mstUsrEntity = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().eq("usr_id",stuId).eq("del_flg",0));
                if (mstUsrEntity!=null && StringUtils.equals(mstUsrEntity.getGidFlg(),"1")){
                    for (int i=0;i<guardInfoList.size();i++){
                        String gid = f21018Dao.selectAfterIdByUsrId(guardInfoList.get(i).getGuardId());
                        if (!StringUtils.isEmpty(gid)){
                            // 2021/10/12　MANAMIRU1-776 cuikl　Edit　Start
                            HashMap<String,String> resultMap = f40006Service.getGidEmail(gid);
                            F21018Dto dto = guardInfoList.get(i);
                            dto.setMailad(resultMap !=null ? resultMap.get("mail") : null);
                            // 2021/10/12　MANAMIRU1-776 cuikl　Edit　End
                            guardInfoList.set(i,dto);
                        }
                    }
                }
                /* 2021/3/12 4-37 modify end */
                //パッケージデータ
                r.put("guardInfo", guardInfoList);
            }
        }
        //todo
        //データを返す
        return r;
    }

    /**
     * 情報を保存する
     *
     * @param params パラメータ
     * @return
     */
    @Override
    public R update(Map<String, Object> params) {
        //システム時間
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        //生徒存在判断
        MstStuEntity stu = mstStuDao.selectOne(new QueryWrapper<MstStuEntity>().eq("stu_id", params.get("stuId")).eq("del_flg", 0));
        if (stu == null){
            //生徒情報が存在しません
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒情報"));
        }
        if (!StringUtils.equals(DateUtils.format(stu.getUpdDatime(), Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS), DateUtils.format((Timestamp) params.get("updDatime"), Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS))) {
            //情報は期限切れです
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        //メモ
        stu.setMemoCont((String) params.get("memo"));
        //曜日
        stu.setDayweekDiv((String) params.get("dayweek"));
        //英語名
//        stu.setEnglishNm((String) params.get("englishName"));
        //システム時間
        stu.setUpdDatime(sysTimestamp);
        //ログインユーザーID
        stu.setUpdUsrId((String) params.get("userId"));
        //情報の変更
        mstStuDao.updateById(stu);
        return R.ok(MessageUtils.getMessage("MSGCOMN0014", "生徒情報"));
    }
}
