/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.service.MstStuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

/**
 * 生徒基本マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mststu")
public class MstStuController {
    @Autowired
    private MstStuService mstStuService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mststu:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstStuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mststu:info")
    public R info(@PathVariable("id") Integer id){
        MstStuEntity mstStu = mstStuService.getById(id);

        return R.ok().put("mstStu", mstStu);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mststu:save")
    public R save(@RequestBody MstStuEntity mstStu){
        mstStuService.save(mstStu);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mststu:update")
    public R update(@RequestBody MstStuEntity mstStu){
        ValidatorUtils.validateEntity(mstStu);
        mstStuService.updateById(mstStu);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mststu:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstStuService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mststu:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstStuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
