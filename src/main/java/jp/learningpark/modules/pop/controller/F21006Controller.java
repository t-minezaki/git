package jp.learningpark.modules.pop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.pop.service.F21006Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p></p >
 *
 * @author NWT : LiYuHuan <br />
 * @version 1.0
 */

@RequestMapping("/pop/f21006")
@RestController
public class F21006Controller {

    /**
     * コードマスタ_明細 Service
     */
    @Autowired
    MstCodDService mstCodDService;
    /**
     * F21006 Service
     */
    @Autowired
    F21006Service f21006Service;

    /**
     * modify at 2021/08/12 for V9.02 by NWT wen
     * 引数名を一致させたまま返す
     * 例：return R.ok().put("list", ***);
     *
     * @param grpId
     * @param pageDiv
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer grpId, Integer pageDiv) {
        //教科内容
        if (pageDiv == 1) {
            String[] subjtDiv = {"k1", "m1", "m2", "e1", "s4", "r1"};
            List<MstCodDEntity> subjtList = f21006Service.getSubjt(subjtDiv);
            MstCodDEntity mstCodDEntity = new MstCodDEntity();
            for (int i = 0; i < subjtList.size(); i++) {
                if (subjtList.get(i).getCodCd().equals("m1")) {
                    subjtList.get(i).setCodValue("数学/算数");
                }
                if (subjtList.get(i).getCodCd().equals("m2")) {
                    mstCodDEntity = subjtList.get(i);
                }
            }
            subjtList.remove(mstCodDEntity);
            return R.ok().put("list", subjtList);
        }

        //欠席、遅刻内容
        else if (pageDiv == 2) {
            List<MstCodDEntity> absStsDivList = mstCodDService.list(
                    new QueryWrapper<MstCodDEntity>().select("cod_value", "cod_cd").eq("cod_key", "ABS_STS_DIV").eq("del_flg", 0));
            return R.ok().put("list", absStsDivList);
        }

        //宿題内容
        else if (pageDiv == 3) {
            List<MstCodDEntity> homeWkDiv = mstCodDService.list(
                    new QueryWrapper<MstCodDEntity>().select("cod_value", "cod_cd").eq("cod_key", "HOME_WK_DIV").eq("del_flg", 0));
            return R.ok().put("list", homeWkDiv);
        }

        //ケア内容
        else if (pageDiv == 5) {
            List<MstCodDEntity> careDiv = mstCodDService.list(
                    new QueryWrapper<MstCodDEntity>().select("cod_value", "cod_cd").eq("cod_key", "CARE_DIV").eq("del_flg", 0));
            return R.ok().put("list", careDiv);
        }

        //前回の宿題内容
        else if (pageDiv == 6) {
            List<MstCodDEntity> lastTimeHwtDiv = mstCodDService.list(
                    new QueryWrapper<MstCodDEntity>().select("cod_value_2", "cod_cd").eq("cod_key", "LAST_TIME_HWK_DIV").eq("del_flg", 0));
            return R.ok().put("list", lastTimeHwtDiv);
        }

        //進度の状況内容
        else if (pageDiv == 7) {
            List<MstCodDEntity> schStsDiv = mstCodDService.list(
                    new QueryWrapper<MstCodDEntity>().select("cod_value_2", "cod_cd").eq("cod_key", "SCH_STS_DIV").eq("del_flg", 0));
            return R.ok().put("list", schStsDiv);
        }

        //　add at 2021/08/12 for V9.02 by NWT wen START
        //小テスト合否
        else if (pageDiv == 9) {
            List<MstCodDEntity> testPassKbn = mstCodDService.list(
                    new QueryWrapper<MstCodDEntity>().select("cod_value", "cod_cd").eq("cod_key", "TEST_PASS_KBN").eq("del_flg", 0));
            return R.ok().put("list", testPassKbn);
        }
        //　add at 2021/08/12 for V9.02 by NWT wen END

        //授業の様子内容
        else {
            List<MstCodDEntity> lectShapeDiv = mstCodDService.list(
                    new QueryWrapper<MstCodDEntity>().select("cod_value_2", "cod_cd").eq("cod_key", "LECT_SHAPE_DIV").eq("del_flg", 0));
            return R.ok().put("list", lectShapeDiv);
        }

        //        //出欠内容
        //        else {
        //            List<MstCodDEntity> absSts = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_value", "cod_cd").eq("cod_key", "ABS_STS").eq("del_flg", 0));
        //            return R.ok().put("absStsList", absSts);
        //        }else {
        //            List<MstCodDEntity> absreason = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_value","cod_cd").eq("cod_key","ABS_REASON").eq("del_flg",0));
        //            return R.ok().put("absreason",absreason);

    }
}

