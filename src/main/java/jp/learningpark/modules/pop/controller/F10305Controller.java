/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.models.auth.In;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstBlockEntity;
import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;
import jp.learningpark.modules.common.service.MstBlockService;
import jp.learningpark.modules.common.service.StuWeeklyPlanPerfService;
import jp.learningpark.modules.pop.dto.F10305Dto;
import jp.learningpark.modules.pop.service.F10305Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>F10305 計画時間調整画面(POP)</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/10/10 : hujunjie: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/pop/F10305")
public class F10305Controller extends AbstractController {

    /**
     * 教科書デフォルトターム情報 Service
     */
    @Autowired
    private F10305Service f10305Service;

    /**
     * ブロッグマスタ Service
     */
    @Autowired
    private MstBlockService blockMstService;

    /**
     * 生徒ウィークリー計画実績設定 service
     */
    @Autowired
    private StuWeeklyPlanPerfService stuWeeklyPlanPerfService;

    /**
     * <p>F10305 初期表示</p>
     *
     * @param type         更新フラグ
     * @param wppId        生徒ウィークリー計画実績設定Id
     * @param blockId      ブロックID
     * @param blockTypeDiv ブロック種類区分
     * @param startTime    計画学習開始時間
     * @param endTime      計画学習終了時間
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public R info(String type, Integer wppId, Integer blockId, String blockTypeDiv, @Param("startTime") String startTime, @Param("endTime") String endTime) {
        //塾ID
        String crmschId = ShiroUtils.getUserEntity().getOrgId();
        //教科書デフォルトターム情報
        F10305Dto f10305Dto = new F10305Dto();
        //小分類ブロック
        MstBlockEntity smallBlock = new MstBlockEntity();
        //大分類ブロック
        MstBlockEntity bigBlock = new MstBlockEntity();
        //生徒ウィークリー計画実績設定
        StuWeeklyPlanPerfEntity stuWeeklyPlanPerfEntity = null;
        //新規場合
        if (wppId == null) {
            //小分類ブロックId
            smallBlock.setId(blockId);
            //del_flg
            smallBlock.setDelFlg(0);
            QueryWrapper<MstBlockEntity> queryWrapper = new QueryWrapper<>(smallBlock);
            smallBlock = blockMstService.getOne(queryWrapper);
            if (smallBlock.getUpplevBlockId() != 0) {
                //大分類ブロックId
                bigBlock.setId(smallBlock.getUpplevBlockId());
                //del_flg
                bigBlock.setDelFlg(0);
                queryWrapper.setEntity(bigBlock);
                bigBlock = blockMstService.getOne(queryWrapper);
            }
        }
        //更新場合
        else {
            //「その他」「復習」「予習」「学校の宿題」「塾の宿題」の場合
            // 2020/11/11 modify LiYuHuan start
            stuWeeklyPlanPerfEntity = stuWeeklyPlanPerfService.getById(wppId);
            if(!StringUtils.equals(blockTypeDiv,"S1")&& stuWeeklyPlanPerfEntity.getStuTermPlanId()==null){
                if(stuWeeklyPlanPerfService != null){
                    smallBlock=blockMstService.getById(stuWeeklyPlanPerfEntity.getUnitId());
                    if(smallBlock!=null){
                        bigBlock=blockMstService.getById(smallBlock.getUpplevBlockId());
                    }
                }
            }
            else {
                f10305Dto = f10305Service.getTextbDefByUnitiId(wppId, crmschId,blockTypeDiv);
            }
            // 2020/11/11 modify LiYuHuan end
        }

        //生徒計画学習時間
        Long minutes = 0L;
        if (startTime != null) {
            minutes = (DateUtils.parse(endTime, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS).getTime() - DateUtils.parse(startTime, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS).getTime()) / (1000 * 60);
        }
        R pageInfo = R.ok();
        pageInfo.put("text", f10305Dto);
        pageInfo.put("smallBlock", smallBlock);
        pageInfo.put("weekday", DateUtils.format(startTime, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS, GakkenConstant.DATE_FORMAT_MM_DD_E));
        pageInfo.put("bigBlock", bigBlock);
        pageInfo.put("planLearnStartTime", DateUtils.format(startTime, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS, GakkenConstant.DATE_FORMAT_HH_MM));
        pageInfo.put("planLearnEndTime", DateUtils.format(endTime, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS, GakkenConstant.DATE_FORMAT_HH_MM));
        pageInfo.put("minutes", minutes);
		pageInfo.put("smallBlockNm", stuWeeklyPlanPerfEntity);
        return pageInfo;
    }

    /**
     * <p>「登録」ボタン押下</p>
     *
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R f10305Submit(String type, F10305Dto dto) {
        String stuId = getUserCd();
        return f10305Service.submitFn(stuId, type, dto);
    }

    /**
     * <p>「登録」ボタン押下</p>
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R f10305Del(F10305Dto dto) {
        String stuId = getUserCd();
        return f10305Service.delete(stuId, dto);
    }
}
