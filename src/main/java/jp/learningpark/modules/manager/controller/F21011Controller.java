package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.manager.dto.F21011Dto;
import jp.learningpark.modules.manager.service.F21011Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author NWT : LiYuHuan <br />
 * @version 1.0
 */
@RequestMapping("/manager/F21011")
@RestController
public class F21011Controller {
    /**
     * F21011Service
     */
    @Autowired
    F21011Service f21011Service;
    /**
     * コードマスタ_明細 Service
     */
    @Autowired
    MstCodDService mstCodDService;
    @RequestMapping(value = "/init",method = RequestMethod.GET)
    public R init(Integer limit, Integer page,String guidReprCd,String stuIdList){
        List<String> stuId = (List<String>) JSON.parse(stuIdList);
        //セッションデータ．組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        List<F21011Dto> f21011Dto = new ArrayList<>();
        List<F21011Dto> f21011DtoTotal = new ArrayList<>();
        if (stuId.size() == 0) {
            /* 2020/12/07 V9.0 liguangxin modify start */
         f21011Dto = f21011Service.select(orgId, guidReprCd,page,limit);
         f21011DtoTotal =  f21011Service.selectTotal(orgId, guidReprCd);
            /* 2020/12/07 V9.0 liguangxin modify end */
        }else {
//         List<F21011Dto>   f21011Dto1 = f21011Service.select(orgId, guidReprCd);
//        /* 2020/12/07 V9.0 liguangxin modify start */
          f21011Dto = f21011Service.reselect(stuId,page,limit);
          f21011DtoTotal = f21011Service.reselectTotal(stuId);
          /* 2020/12/07 V9.0 liguangxin modify end */
//            f21011Dto.addAll(f21011Dto1);
        }
        if (guidReprCd != null) {
            return R.ok().put("page", new PageUtils(f21011Dto, f21011DtoTotal.size(), limit, page));
        }
        return R.ok().put("page", new PageUtils(f21011Dto, f21011DtoTotal.size(), limit, page));
    }
    @RequestMapping(value = "/getCod",method = RequestMethod.GET)
    public R getCod(){
        List<MstCodDEntity> mstCodDEntityList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd","cod_value").eq("cod_key","EVENT_STS_DIV").eq("del_flg",0));
        return R.ok().put("mstCodDEntityList",mstCodDEntityList);
    }
}
