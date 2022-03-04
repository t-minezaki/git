/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StuFixdSchdlEntity;
import jp.learningpark.modules.common.service.StuFixdSchdlService;
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
 * 生徒固定スケジュール設定
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/stufixdschdl")
public class StuFixdSchdlController {
    @Autowired
    private StuFixdSchdlService stuFixdSchdlService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:stufixdschdl:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stuFixdSchdlService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:stufixdschdl:info")
    public R info(@PathVariable("id") Integer id){
        StuFixdSchdlEntity stuFixdSchdl = stuFixdSchdlService.getById(id);

        return R.ok().put("stuFixdSchdl", stuFixdSchdl);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:stufixdschdl:save")
    public R save(@RequestBody StuFixdSchdlEntity stuFixdSchdl){
        stuFixdSchdlService.save(stuFixdSchdl);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:stufixdschdl:update")
    public R update(@RequestBody StuFixdSchdlEntity stuFixdSchdl){
        ValidatorUtils.validateEntity(stuFixdSchdl);
        stuFixdSchdlService.updateById(stuFixdSchdl);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:stufixdschdl:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            stuFixdSchdlService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:stufixdschdl:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        stuFixdSchdlService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
