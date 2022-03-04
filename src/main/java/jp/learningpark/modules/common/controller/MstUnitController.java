/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstUnitEntity;
import jp.learningpark.modules.common.service.MstUnitService;
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
 * 単元マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstunit")
public class MstUnitController {
    @Autowired
    private MstUnitService mstUnitService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstunit:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstUnitService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstunit:info")
    public R info(@PathVariable("id") Integer id){
        MstUnitEntity mstUnit = mstUnitService.getById(id);

        return R.ok().put("mstUnit", mstUnit);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstunit:save")
    public R save(@RequestBody MstUnitEntity mstUnit){
        mstUnitService.save(mstUnit);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstunit:update")
    public R update(@RequestBody MstUnitEntity mstUnit){
        ValidatorUtils.validateEntity(mstUnit);
        mstUnitService.updateById(mstUnit);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstunit:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstUnitService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstunit:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstUnitService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
