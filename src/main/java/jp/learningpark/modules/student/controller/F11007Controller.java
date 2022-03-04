package jp.learningpark.modules.student.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.entity.MstBlockEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.StuGetPointHstService;
import jp.learningpark.modules.common.service.StuWeeklyPlanPerfService;
import jp.learningpark.modules.student.dto.F11001Dto;
import jp.learningpark.modules.student.service.F11007Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * スマホ_学習情報登録｜タイマー登録２
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/04/24 ： NWT)hxl ： 新規作成
 * @date 2020/04/24 17:03
 */
@RestController
@RequestMapping(value = "/student/F11007", method = RequestMethod.GET)
public class F11007Controller extends AbstractController {

    // 2020/11/12 zhangminghao modify start
    private final StuWeeklyPlanPerfService stuWeeklyPlanPerfService;

    private final MstCodDService mstCodDService;
    private final F11007Service f11007Service;
    private final StuGetPointHstService stuGetPointHstService;

    public F11007Controller(StuWeeklyPlanPerfService stuWeeklyPlanPerfService,
                            MstCodDService mstCodService, F11007Service f11007Service,
                            StuGetPointHstService stuGetPointHstService) {
        this.stuWeeklyPlanPerfService = stuWeeklyPlanPerfService;
        this.mstCodDService = mstCodService;
        this.f11007Service = f11007Service;
        this.stuGetPointHstService = stuGetPointHstService;
    }
    // 2020/11/12 zhangminghao modify end
    @RequestMapping(value = "/init")
    public R f11007init(Integer id){
        StuWeeklyPlanPerfEntity one = stuWeeklyPlanPerfService.getOne(new QueryWrapper<StuWeeklyPlanPerfEntity>().eq("id", id).eq("del_flg", 0));
        List<MstCodDEntity> colors = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_value", "cod_value_2", "cod_cd").eq("cod_key", "LEARN_LEV_UNDS").eq("del_flg", 0).orderByAsc("sort"));

        // 2020/11/12 zhangminghao modify start
        // 該当生徒より、コードマスタ_明細から科目を取得する。
        List<F11001Dto> subjtDiv = f11007Service.getSubjt();
        // マスタブロックからカテゴリを取得する。
        List<MstBlockEntity> blockType = f11007Service.getBlockType();

        return R.ok().put("one", one).put("colors", colors)
                .put("subjtDiv", subjtDiv).put("blockType", blockType);
        // 2020/11/12 zhangminghao modify end
    }

    @RequestMapping(value = "/submit")
    public R submit(String one){
        // 2020/12/4 huangxinliang modify start
        String stuId = ShiroUtils.getUserId();
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 2020/12/4 huangxinliang modify end
        try {
            one = URLDecoder.decode(one, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StuWeeklyPlanPerfEntity stuWeeklyPlanPerfEntity = JSON.parseObject(one, StuWeeklyPlanPerfEntity.class);
        StuWeeklyPlanPerfEntity one1 = stuWeeklyPlanPerfService.getOne(new QueryWrapper<StuWeeklyPlanPerfEntity>().eq("id", stuWeeklyPlanPerfEntity.getId()).eq("del_flg", 0));
        Timestamp startTime = stuWeeklyPlanPerfEntity.getPerfLearnStartTime();

        // 2020/11/20 zhangminghao modify start
        // データがある場合は、データを更新します
        if (one1 != null){
            if (!StringUtils.equals(DateUtils.format(stuWeeklyPlanPerfEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSS), DateUtils.format(one1.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSS))){
                //情報は期限切れです
                return R.error(MessageUtils.getMessage("MSGCOMN0019"));
            }
            try {
                stuWeeklyPlanPerfUpdate(stuWeeklyPlanPerfEntity);
            }catch (Exception e){
                e.printStackTrace();
                return R.error("update error!");
            }
        }else {
            stuWeeklyPlanPerfSave(stuWeeklyPlanPerfEntity);
        }
        // 2020/11/20 zhangminghao modify end
        if(startTime!=null){
            // 2020/12/7 huangxinliang modify start
            //学習実績登録時、実績登録ポイントを取得する。
            CM0005.PointVo pointVo = CM0005.getPointVo(ShiroUtils.getUserEntity().getOrgId());
            CM0005.addPoint(stuId, ShiroUtils.getUserEntity().getOrgId(), pointVo, 2, stuId,startTime);
            //学習実績累計時間によって、累計時間ポイントを取得する。　
            CM0005.addPoint(stuId, orgId, pointVo, 3, stuId,sysTimestamp);
            // 2020/12/7 huangxinliang modify end
        }
        return R.ok();
    }

    /**
     * 生徒ウィークリー計画実績設定登録
     * @param entity ページデータ
     */
    private void stuWeeklyPlanPerfSave(StuWeeklyPlanPerfEntity entity) {
        // [カテゴリ]と[教科/科目]の詳細情報を取得します
        // 対象が選択されている限り、異常になることは不可能です
        List<MstBlockEntity> blockType = f11007Service.getBlockType();
        MstBlockEntity blockEntity = blockType.stream()
                .filter(b -> Objects.equals(b.getBlockTypeDiv(), entity.getBlockTypeDiv()))
                .findFirst().orElseThrow(() -> new RRException(MessageUtils.getMessage("MSGCOMD0001", "カテゴリ")));

        List<F11001Dto> subjtDiv = f11007Service.getSubjt();
        F11001Dto subjtInfo = subjtDiv.stream()
                .filter(s -> Objects.equals(s.getSubjtDiv(), entity.getSubjtDiv()))
                .findFirst().orElseThrow(() -> new RRException(MessageUtils.getMessage("MSGCOMD0001", "[教科/科目]")));

        // 理解度
        boolean isUnderstand = true;
        String learnLevUnds = entity.getLearnLevUnds();
        // 実施していない（0分）
        if (Objects.equals(learnLevUnds, "0")){
            isUnderstand = false;
        }

        // データがない場合は、データを追加します
        //更新フラグ「0」場合
        StuWeeklyPlanPerfEntity newEntity = new StuWeeklyPlanPerfEntity();
        String stuId = ShiroUtils.getUserId();
        //生徒ID
        newEntity.setStuId(stuId);
        //単元ID
        newEntity.setUnitId(blockEntity.getId());
        //生徒タームプラン設定ID
        newEntity.setStuTermPlanId(null);
        //ブロック表示名
        newEntity.setBlockDispyNm(entity.getBlockDispyNm());
        //ブロック種類区分
        newEntity.setBlockTypeDiv(entity.getBlockTypeDiv());
        //計画年月日
        newEntity.setPlanYmd(DateUtils.getSysDate());
        //教科区分
        newEntity.setSubjtDiv(entity.getSubjtDiv());
        //教科
        newEntity.setSubjtNm(subjtInfo.getSubjtNm());
        //生徒計画学習時間（分）
        newEntity.setStuPlanLearnTm(entity.getPerfLearnTm());
        //計画学習時期ID
        newEntity.setPlanLearnSeasnId(null);
        //計画学習開始時間
        newEntity.setPlanLearnStartTime(entity.getPerfLearnStartTime());
        // 実績年月日
        newEntity.setPerfYmd(entity.getPerfYmd());
        // 実績学習開始時間
        newEntity.setPerfLearnStartTime(isUnderstand ? entity.getPerfLearnStartTime() : null);
        //実績学習時間（分）
        newEntity.setPerfLearnTm(entity.getPerfLearnTm());
        // タイマー時間（秒）
        newEntity.setTimerTm(isUnderstand ? entity.getTimerTm() : null);
        //学習理解度
        newEntity.setLearnLevUnds(entity.getLearnLevUnds());
        //積み残し対象フラグ
        newEntity.setRemainDispFlg(null);
        //生徒削除フラグ
        newEntity.setStuDelFlg("0");
        //表示順番
        newEntity.setDispyOrder(null);
        //作成日時
        newEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザＩＤ
        newEntity.setCretUsrId(stuId);
        //更新日時
        newEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        newEntity.setUpdUsrId(stuId);
        //del_flg
        newEntity.setDelFlg(0);
        stuWeeklyPlanPerfService.save(newEntity);
    }

    /**
     * 生徒ウィークリー計画実績設定更新
     *
     * @param entity ページデータ
     */
    private void stuWeeklyPlanPerfUpdate(StuWeeklyPlanPerfEntity entity) {
        if (StringUtils.equals(entity.getLearnLevUnds(), "0")){
            entity.setPerfLearnTm(null);
            entity.setPerfLearnStartTime(null);
            entity.setTimerTm(null);
        }
        entity.setUpdUsrId(getUserCd());
        entity.setUpdDatime(DateUtils.getSysTimestamp());
        stuWeeklyPlanPerfService.update(entity, new UpdateWrapper<StuWeeklyPlanPerfEntity>()
                .eq("id", entity.getId()));
    }

    /**
     * 削除ボタン押下
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public R delete(Integer weeklyPlanId){
        try {
            stuWeeklyPlanPerfService.removeById(weeklyPlanId);
        }catch (Exception e){
            return R.error("データの削除に失敗しました。");
        }
        return R.ok();
    }
}
