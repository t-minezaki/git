/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstHolidayEntity;
import jp.learningpark.modules.common.service.MstHolidayService;
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
 * 祝日マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstholiday")
public class MstHolidayController {
    @Autowired
    private MstHolidayService mstHolidayService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstholiday:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstHolidayService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstholiday:info")
    public R info(@PathVariable("id") Integer id){
        MstHolidayEntity mstHoliday = mstHolidayService.getById(id);

        return R.ok().put("mstHoliday", mstHoliday);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstholiday:save")
    public R save(@RequestBody MstHolidayEntity mstHoliday){
        mstHolidayService.save(mstHoliday);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstholiday:update")
    public R update(@RequestBody MstHolidayEntity mstHoliday){
        ValidatorUtils.validateEntity(mstHoliday);
        mstHolidayService.updateById(mstHoliday);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstholiday:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstHolidayService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstholiday:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstHolidayService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
