package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.entity.GuidReprDEntity;
import jp.learningpark.modules.common.entity.GuidReprHEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstMaxIdEntity;
import jp.learningpark.modules.common.service.GuidReprDService;
import jp.learningpark.modules.common.service.GuidReprHService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstMaxIdService;
import jp.learningpark.modules.manager.dto.F21008Dto;
import jp.learningpark.modules.manager.dto.F21009Dto;
import jp.learningpark.modules.manager.service.F21008Service;
import jp.learningpark.modules.manager.service.F21009Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/manager/F21009")
@RestController
public class F21009Controller {
    /**
     * 出席簿出欠情報入力画面 Service
     */
    @Autowired
    F21008Service f21008Service;
    /**
     * 出席簿指導内容情報画面 Service
     */
    @Autowired
    F21009Service f21009Service;
    /**
     * コードマスタ Service
     */
    @Autowired
    MstCodDService mstCodDService;
    /**
     * 指導報告書明細 Service
     */
    @Autowired
    GuidReprDService guidReprDService;
    /**
     * MAX採番 Service
     */
    @Autowired
    MstMaxIdService mstMaxIdService;
    /**
     * 指導報告書ヘーダ Service
     */
    @Autowired
    GuidReprHService guidReprHService;

    /**
     * 初期表示
     *
     * @param
     * @return
     */

    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R init(String attendBookCd, String absDiv) {
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        List<MstCodDEntity> mstCodDEntityList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().eq("cod_key", "ABS_DIV").eq("del_flg", 0).orderByAsc("sort"));
        List<F21009Dto> f21009DtoList = new ArrayList<>();
        //遅欠ステータス
        Map<String, String> absStsDiv = new HashMap<>();
        List<MstCodDEntity> layoutAbsSts = mstCodDService.list(new QueryWrapper<MstCodDEntity>().eq("cod_key", "ABS_STS_DIV").eq("del_flg", 0).orderByAsc("sort"));
        for (MstCodDEntity mstCodDEntity : layoutAbsSts) {
            absStsDiv.put(mstCodDEntity.getCodCd(), mstCodDEntity.getCodValue());
        }
        //前回の宿題
        Map<String, String> lastTimeHwkDiv = new HashMap<>();
        getMap("LAST_TIME_HWK_DIV", lastTimeHwkDiv);
        //進捗の状況
        Map<String, String> schStsDiv = new HashMap<>();
        getMap("SCH_STS_DIV", schStsDiv);
        //授業の様子
        Map<String, String> lectShapeDiv = new HashMap<>();
        getMap("LECT_SHAPE_DIV", lectShapeDiv);
        //新規編集区分フラグ
        Integer pageDiff = 1;
        //指導報告書ヘーダから指導報告書情報を取得し
        f21009DtoList = f21009Service.getGrdData(attendBookCd, orgId, absDiv);
        //MANAMIRU1-300 modify yang 2020/12/22
        GuidReprHEntity guidReprHEntity = guidReprHService.getOne(new QueryWrapper<GuidReprHEntity>().eq("attend_book_cd",attendBookCd).eq("org_id",orgId).eq("del_flg",0));
        //データ取得できない場合
        if (guidReprHEntity == null) {
            pageDiff = 2;
            //、出席簿明細から出席簿情報を取得し
            f21009DtoList = f21009Service.getAbhData(attendBookCd, orgId, absDiv);
        }
        if (pageDiff == 1) {
            for (F21009Dto f21009Dto : f21009DtoList) {
                //前回の宿題の取得
                f21009Dto.setLastTimeHwkCod(f21009Dto.getLastTimeHwkDiv());
                f21009Dto.setLastTimeHwkDiv(lastTimeHwkDiv.get(f21009Dto.getLastTimeHwkDiv()));
                //進捗の状況の取得
                f21009Dto.setSchStsCod(f21009Dto.getSchStsDiv());
                f21009Dto.setSchStsDiv(schStsDiv.get(f21009Dto.getSchStsDiv()));
                //授業の様子の取得
                f21009Dto.setLectShapeCod(f21009Dto.getLectShapeDiv());
                if (f21009Dto.getLectShapeDiv() != null && !StringUtils.isEmpty(f21009Dto.getLectShapeDiv())){
                    String[] lectShapeDivArray = f21009Dto.getLectShapeDiv().split(",");
                    String singleLectShape = "";
                    //授業の様子文字のつづり合わせ
                    for (int i = 0; i < lectShapeDivArray.length; i++) {
                        if (i == 0) {
                            singleLectShape = singleLectShape + lectShapeDiv.get(lectShapeDivArray[i]);
                        } else {
                            singleLectShape = singleLectShape + "," + lectShapeDiv.get(lectShapeDivArray[i]);
                        }
                    }
                    f21009Dto.setLectShapeDiv(singleLectShape);
                }
            }
        }
        for (F21009Dto f21009Dto : f21009DtoList) {
            //遅欠ステータスの取得
            f21009Dto.setAbsStsCod(f21009Dto.getAbsStsDiv());
            f21009Dto.setAbsStsDiv(absStsDiv.get(f21009Dto.getAbsStsDiv()));
        }
        //上記取得できない場合
        if (f21009DtoList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒グループ")).put("mstCodDEntityList", mstCodDEntityList);
        }
        return R.ok().put("page", new PageUtils(f21009DtoList, f21009DtoList.size(), 0, 0)).put("mstCodDEntityList", mstCodDEntityList).put("pageDiff", pageDiff);
    }

    @RequestMapping(value = "getshowItems", method = RequestMethod.GET)
    public R getshowItems() {
        //画面表示項目
        F21008Dto displayItems = f21008Service.selectDspItems(ShiroUtils.getUserId(), "F21009");
        return R.ok().put("displayItems", displayItems);
    }

    /**
     * Map作成
     *
     * @param mstCodDEntityList
     * @param map
     * @return
     */
    public Map<String, String> getMap(String codKey, Map<String, String> map) {
        List<MstCodDEntity> mstCodDEntityList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().eq("cod_key", codKey).eq("del_flg", 0).orderByAsc("sort"));
        for (MstCodDEntity mstCodDEntity : mstCodDEntityList) {
            map.put(mstCodDEntity.getCodCd(), mstCodDEntity.getCodValue2());
        }
        return map;
    }

    /**
     * 「登録」ボタン押下
     *
     * @return
     */
    @RequestMapping(value = "submit", method = RequestMethod.POST)
    public R submit(String jqGridList, Integer pageDiff, String attendBookCd, Integer id) {
        //ログインユーザＩＤ
        String userId = ShiroUtils.getUserId();
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //生徒情報を取得し
        List<F21009Dto> stuList = JSON.parseArray(jqGridList, F21009Dto.class);
        //指導報告書情報が編集の場合
        if (pageDiff == 1) {
            for (F21009Dto stu : stuList) {
                GuidReprDEntity guidReprDEntity = guidReprDService.getOne(new QueryWrapper<GuidReprDEntity>().eq("guid_repr_id",id).eq("stu_id",stu.getStuId()).eq("del_flg",0));
                //出欠ステータス区分
                guidReprDEntity.setAbsStsDiv(stu.getAbsStsDiv());
                //使用テキスト
                guidReprDEntity.setUseCont(stu.getUseCont());
                //指導内容
                guidReprDEntity.setGuidCont(stu.getGuidCont());
                //宿題内容
                guidReprDEntity.setHwkCont(stu.getHwkCont());
                //小テスト単元名
                guidReprDEntity.setTestUnitNm(stu.getTestUnitNm());
                //前回宿題完成区分
                guidReprDEntity.setLastTimeHwkDiv(stu.getLastTimeHwkDiv());
                //進捗ステータス区分
                guidReprDEntity.setSchStsDiv(stu.getSchStsDiv());
                //授業様子区分
                guidReprDEntity.setLectShapeDiv(stu.getLectShapeDiv());
                //連絡事項内容
                guidReprDEntity.setConcItemCont(stu.getConcItemCont());
                //作成ユーザＩＤ
                guidReprDEntity.setCretUsrId(userId);
                //作成日時
                guidReprDEntity.setCretDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                guidReprDEntity.setUpdUsrId(userId);
                //更新日時
                guidReprDEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //削除フラグ
                guidReprDEntity.setDelFlg(0);
                guidReprDService.update(guidReprDEntity,new QueryWrapper<GuidReprDEntity>().eq("id",guidReprDEntity.getId()).eq("del_flg",0));
            }
        }
        //指導報告書情報が新規作成の場合
        else {
            //MaxIdの取得
            MstMaxIdEntity mstMaxIdEntity = mstMaxIdService.getOne(new QueryWrapper<MstMaxIdEntity>().eq("org_id", orgId).eq("type_div", "g").eq("del_flg", 0));
            Integer maxId = 1;
            if (mstMaxIdEntity == null) {
                //MAX採番登録
                mstMaxIdEntity = new MstMaxIdEntity();
                //組織ID
                mstMaxIdEntity.setOrgId(orgId);
                //MAXID
                mstMaxIdEntity.setMaxId(1);
                //種類区分
                mstMaxIdEntity.setTypeDiv("g");
                //作成日時
                mstMaxIdEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //作成ユーザＩＤ
                mstMaxIdEntity.setUpdUsrId(userId);
                //更新日時
                mstMaxIdEntity.setCretDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                mstMaxIdEntity.setCretUsrId(userId);
                //削除フラグ
                mstMaxIdEntity.setDelFlg(0);
                mstMaxIdService.save(mstMaxIdEntity);
            } else {
                maxId = mstMaxIdEntity.getMaxId();
                //MAXID
                mstMaxIdEntity.setMaxId(maxId + 1);
                //更新日時
                mstMaxIdEntity.setCretDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                mstMaxIdEntity.setCretUsrId(userId);
                //削除フラグ
                mstMaxIdEntity.setDelFlg(0);
                mstMaxIdService.updateById(mstMaxIdEntity);
            }
            GuidReprHEntity guidReprHEntity = new GuidReprHEntity();
            //組織ID
            guidReprHEntity.setOrgId(orgId);
            //指導報告書コード
            guidReprHEntity.setGuidReprCd("g" + maxId);
            //出席簿コード
            guidReprHEntity.setAttendBookCd(attendBookCd);
            //報告書作成日
            guidReprHEntity.setReprCretDt(DateUtils.getSysTimestamp());
            //作成日時
            guidReprHEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            guidReprHEntity.setCretUsrId(userId);
            //更新ユーザＩＤ
            guidReprHEntity.setUpdUsrId(userId);
            //更新日時
            guidReprHEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //削除フラグ
            guidReprHEntity.setDelFlg(0);
            guidReprHService.save(guidReprHEntity);
            for (F21009Dto stu : stuList) {
                GuidReprDEntity guidReprDEntity = new GuidReprDEntity();
                //指導報告書ID
                guidReprDEntity.setGuidReprId(guidReprHEntity.getId());
                //生徒ID
                guidReprDEntity.setStuId(stu.getStuId());
                //出欠ステータス区分
                guidReprDEntity.setAbsStsDiv(stu.getAbsStsDiv());
                //使用テキスト
                guidReprDEntity.setUseCont(stu.getUseCont());
                //指導内容
                guidReprDEntity.setGuidCont(stu.getGuidCont());
                //宿題内容
                guidReprDEntity.setHwkCont(stu.getHwkCont());
                //小テスト単元名
                guidReprDEntity.setTestUnitNm(stu.getTestUnitNm());
                //前回宿題完成区分
                guidReprDEntity.setLastTimeHwkDiv(stu.getLastTimeHwkDiv());
                //進捗ステータス区分
                guidReprDEntity.setSchStsDiv(stu.getSchStsDiv());
                //授業様子区分
                guidReprDEntity.setLectShapeDiv(stu.getLectShapeDiv());
                //連絡事項内容
                guidReprDEntity.setConcItemCont(stu.getConcItemCont());
                //作成ユーザＩＤ
                guidReprDEntity.setCretUsrId(userId);
                //作成日時
                guidReprDEntity.setCretDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                guidReprDEntity.setUpdUsrId(userId);
                //更新日時
                guidReprDEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //削除フラグ
                guidReprDEntity.setDelFlg(0);
                guidReprDService.save(guidReprDEntity);
            }
        }
        return R.ok();
    }

}
