/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.GuardReadingStsEntity;
import jp.learningpark.modules.common.service.GuardReadingStsService;
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
 * 保護者お知らせ閲覧状況
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/guardreadingsts")
public class GuardReadingStsController {
    @Autowired
    private GuardReadingStsService guardReadingStsService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:guardreadingsts:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = guardReadingStsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:guardreadingsts:info")
    public R info(@PathVariable("id") Integer id){
        GuardReadingStsEntity guardReadingSts = guardReadingStsService.getById(id);

        return R.ok().put("guardReadingSts", guardReadingSts);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:guardreadingsts:save")
    public R save(@RequestBody GuardReadingStsEntity guardReadingSts){
        guardReadingStsService.save(guardReadingSts);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:guardreadingsts:update")
    public R update(@RequestBody GuardReadingStsEntity guardReadingSts){
        ValidatorUtils.validateEntity(guardReadingSts);
        guardReadingStsService.updateById(guardReadingSts);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:guardreadingsts:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            guardReadingStsService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:guardreadingsts:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        guardReadingStsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
