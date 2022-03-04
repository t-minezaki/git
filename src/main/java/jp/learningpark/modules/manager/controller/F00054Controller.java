/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.dao.StuGrpDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstGrpEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.StuGrpEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstGrpService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.StuGrpService;
import jp.learningpark.modules.manager.dao.F00054Dao;
import jp.learningpark.modules.manager.dto.F00054Dto;
import jp.learningpark.modules.manager.service.F00054Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * F00054_生徒グループ関係設定修正画面
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/19 : xiong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/f00054")
@RestController
public class F00054Controller extends AbstractController {

    @Autowired
    F00054Service f00054Service;

    @Autowired
    F00054Dao f00054Dao;
    @Autowired
    MstCodDService mstCodDService;
    @Autowired
    StuGrpDao stuGrpDao;
    @Autowired
    StuGrpService stuGrpService;
    @Autowired
    MstGrpService mstGrpService;
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * 初期化
     * @param flg 画面の区分
     * @param groupId セッションデータ．グループID
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R initial(Integer flg, Integer groupId) {
        R info = new R();
        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 組織
        MstOrgEntity mstOrgEntity =mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));
        info.put("mstOrgEntity", mstOrgEntity);
        //学年区分取得
        List<MstCodDEntity> mstCodDEntityList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().eq("cod_key", "SCHY_DIV").eq("del_flg", 0).orderByAsc("sort"));
        //グループID、グループ名情報取得
        List<F00054Dto> groupList = f00054Dao.getGroupList(orgId);
        List<MstGrpEntity> grpEntityList = mstGrpService.list(new QueryWrapper<MstGrpEntity>().eq("org_id",orgId).eq("del_flg",0).orderByAsc("grp_id"));
        //新規の場合
        if (flg == 0) {
            // 追加グループ情報を表示するため
//            List<F00054Dto> stuList = f00054Dao.getStuList(orgId);
//            info.put("stuList", stuList);
        }
        //編集の場合
        if (flg == 1) {
            List<F00054Dto> groupStuInfoList = f00054Dao.getGrpStuInfo(orgId, groupId);
            MstGrpEntity mstGrpEntity = mstGrpService.getOne(new QueryWrapper<MstGrpEntity>().eq("grp_id", groupId).eq("del_flg", 0));
            info.put("groupName", mstGrpEntity.getGrpNm()).put("groupStuInfoList",groupStuInfoList);
        }
        //コピーして作成追加の場合
        if (flg == 2) {
            List<F00054Dto> groupStuInfoList = f00054Dao.getGrpStuInfo(orgId, groupId);
            MstGrpEntity mstGrpEntity = mstGrpService.getOne(new QueryWrapper<MstGrpEntity>().eq("grp_id", groupId).eq("del_flg", 0));
            info.put("groupName", mstGrpEntity.getGrpNm()).put("groupStuInfoList", groupStuInfoList);
        }
        info.put("mstCodDEntityList", mstCodDEntityList).put("groupList", groupList).put("grpEntityList",grpEntityList);
        return info;
    }

    /**
     * 検索ボタン押下
     * @param params 引渡のパラメータ
     * @return
     */

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public R search(String params) {
        //解析引渡のパラメータ
        Map<String, String> paramsMap = (Map) JSON.parse(params);
        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //検索結果
        List<F00054Dto> searchStuList = f00054Dao.getStuInformation(orgId, paramsMap);
        //取得できない場合
        if (searchStuList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒"));
        }
        return R.ok().put("searchStuList", searchStuList);
    }

    /**
     *  登録ボタン押下
     * @param groupId グループID
     * @param stuIdListRight 対象者とす
     * @param stuIdListLeft 検索結果エリア
     * @param flg   画面の区分
     * @param newgroupName 新しいグループ名
     * @return
     */

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R submit(Integer groupId, @RequestParam(value = "stuIdListRight") List<String> stuIdListRight, @RequestParam(value = "stuIdListLeft") List<String> stuIdListLeft, Integer flg, String newgroupName) throws UnsupportedEncodingException {

        //ログインユーザＩＤ
        String userId = ShiroUtils.getUserEntity().getUsrId();

        //セッションデータ．組織ＩＤ
        String orgId = ShiroUtils.getUserEntity().getOrgId();

        String groupName = URLDecoder.decode(newgroupName,"UTF-8");


//        //新規の場合
//        if (flg == 0) {
//            for (int i = 0; i < stuIdListRight.size(); i++) {
//                StuGrpEntity stuGrpEntity = new StuGrpEntity();
//                stuGrpEntity.setStuId(stuIdListRight.get(i));
//                stuGrpEntity.setGrpId(groupId);
//                stuGrpEntity.setCretDatime(DateUtils.getSysTimestamp());
//                stuGrpEntity.setCretUsrId(userId);
//                stuGrpEntity.setUpdDatime(DateUtils.getSysTimestamp());
//                stuGrpEntity.setUpdUsrId(userId);
//                stuGrpEntity.setDelFlg(0);
//                stuGrpDao.insert(stuGrpEntity);
//            }
//        }

        //編集の場合
        if (flg == 1) {
            List<StuGrpEntity> stuGrpEntityList;
            stuGrpEntityList = stuGrpService.list(new QueryWrapper<StuGrpEntity>().eq("grp_id", groupId).eq("del_flg", 0));
            for (StuGrpEntity stuGrpEntity : stuGrpEntityList) {
                stuGrpEntity.setDelFlg(1);
                stuGrpService.update(stuGrpEntity, new QueryWrapper<StuGrpEntity>().eq("id", stuGrpEntity.getId()));
            }
            if (stuIdListRight.size() > 0) {
                for (int i = 0; i < stuIdListRight.size(); i++) {
                    //対象者とするエリアで新規生徒IDがある場合、
                    stuGrpEntityList = stuGrpService.list(new QueryWrapper<StuGrpEntity>().eq("stu_id", stuIdListRight.get(i)).eq("grp_id", groupId).eq("del_flg", 1));
                    //チェック条件に合致する場合、更新行う。
                    if (!stuGrpEntityList.isEmpty()) {
                        for (StuGrpEntity stuGrpEntity : stuGrpEntityList) {
                            stuGrpEntity.setUpdDatime(DateUtils.getSysTimestamp());
                            stuGrpEntity.setUpdUsrId(userId);
                            stuGrpEntity.setDelFlg(0);
                            stuGrpService.update(stuGrpEntity, new QueryWrapper<StuGrpEntity>().eq("id", stuGrpEntity.getId()));
                        }
                    }
                    //チェック条件に合致しない場合、登録行う。
                    else {
                        StuGrpEntity stuGrpEntity = new StuGrpEntity();
                        stuGrpEntity.setStuId(stuIdListRight.get(i));
                        stuGrpEntity.setGrpId(groupId);
                        stuGrpEntity.setCretDatime(DateUtils.getSysTimestamp());
                        stuGrpEntity.setCretUsrId(userId);
                        stuGrpEntity.setUpdDatime(DateUtils.getSysTimestamp());
                        stuGrpEntity.setUpdUsrId(userId);
                        stuGrpEntity.setDelFlg(0);
                        stuGrpDao.insert(stuGrpEntity);
                    }
                }
            }
            if (stuIdListLeft.size() > 0) {
                for (int i = 0; i < stuIdListLeft.size(); i++) {
                    stuGrpEntityList = stuGrpService.list(new QueryWrapper<StuGrpEntity>().eq("stu_id", stuIdListLeft.get(i)).eq("grp_id", groupId).eq("del_flg", 0));
                    //対象者とするエリアで既存生徒IDが減る場合、減る生徒IDによって、更新行う。
                    if (stuGrpEntityList.size() > 0) {
                        for (StuGrpEntity stuGrpEntity : stuGrpEntityList) {
                            stuGrpEntity.setUpdDatime(DateUtils.getSysTimestamp());
                            stuGrpEntity.setUpdUsrId(userId);
                            stuGrpEntity.setDelFlg(1);
                            stuGrpService.update(stuGrpEntity, new QueryWrapper<StuGrpEntity>().eq("id", stuGrpEntity.getId()));
                        }
                    }
                }
            }
        }

        //コピーして作成追加の場合
        else if (flg == 2){
            MstGrpEntity mstGrpEntity = null;
            mstGrpEntity = mstGrpService.getOne(new QueryWrapper<MstGrpEntity>().eq("grp_nm", groupName.trim()).eq("del_flg", 0));
            if (mstGrpEntity != null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0010", "グループ名", "DB"));
            } else {
                //グループマスタ登録
                mstGrpEntity = new MstGrpEntity();
                MstGrpEntity copyWeek = mstGrpService.getById(groupId);
                mstGrpEntity.setOrgId(orgId);
                mstGrpEntity.setDayweekDiv(copyWeek.getDayweekDiv());
                mstGrpEntity.setGrpNm(groupName);
                mstGrpEntity.setCretDatime(DateUtils.getSysTimestamp());
                mstGrpEntity.setCretUsrId(userId);
                mstGrpEntity.setUpdDatime(DateUtils.getSysTimestamp());
                mstGrpEntity.setUpdUsrId(userId);
                mstGrpEntity.setDelFlg(0);
                mstGrpService.save(mstGrpEntity);
                StuGrpEntity stuGrpEntity;
                for (int i = 0; i < stuIdListRight.size(); i++) {
                    //生徒グループ管理更新
                    stuGrpEntity = new StuGrpEntity();
                    stuGrpEntity.setStuId(stuIdListRight.get(i));
                    stuGrpEntity.setGrpId(mstGrpEntity.getGrpId());
                    stuGrpEntity.setCretUsrId(userId);
                    stuGrpEntity.setCretDatime(DateUtils.getSysTimestamp());
                    stuGrpEntity.setUpdUsrId(userId);
                    stuGrpEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    stuGrpEntity.setDelFlg(0);
                    stuGrpDao.insert(stuGrpEntity);
                }
            }
        }

        return R.ok();
    }

}
