/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StuCrmschStsEntity;
import jp.learningpark.modules.common.service.StuCrmschStsService;
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
 * 生徒通塾ステータス
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/stucrmschsts")
public class StuCrmschStsController {
    @Autowired
    private StuCrmschStsService stuCrmschStsService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:stucrmschsts:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stuCrmschStsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:stucrmschsts:info")
    public R info(@PathVariable("id") Integer id){
        StuCrmschStsEntity stuCrmschSts = stuCrmschStsService.getById(id);

        return R.ok().put("stuCrmschSts", stuCrmschSts);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:stucrmschsts:save")
    public R save(@RequestBody StuCrmschStsEntity stuCrmschSts){
        stuCrmschStsService.save(stuCrmschSts);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:stucrmschsts:update")
    public R update(@RequestBody StuCrmschStsEntity stuCrmschSts){
        ValidatorUtils.validateEntity(stuCrmschSts);
        stuCrmschStsService.updateById(stuCrmschSts);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:stucrmschsts:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            stuCrmschStsService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:stucrmschsts:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        stuCrmschStsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
