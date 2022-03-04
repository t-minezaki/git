/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.GuardEventReadingStsEntity;
import jp.learningpark.modules.common.service.GuardEventReadingStsService;
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
 * 保護者イベント閲覧状況
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/guardeventreadingsts")
public class GuardEventReadingStsController {
    @Autowired
    private GuardEventReadingStsService guardEventReadingStsService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:guardeventreadingsts:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = guardEventReadingStsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:guardeventreadingsts:info")
    public R info(@PathVariable("id") Integer id){
        GuardEventReadingStsEntity guardEventReadingSts = guardEventReadingStsService.getById(id);

        return R.ok().put("guardEventReadingSts", guardEventReadingSts);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:guardeventreadingsts:save")
    public R save(@RequestBody GuardEventReadingStsEntity guardEventReadingSts){
        guardEventReadingStsService.save(guardEventReadingSts);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:guardeventreadingsts:update")
    public R update(@RequestBody GuardEventReadingStsEntity guardEventReadingSts){
        ValidatorUtils.validateEntity(guardEventReadingSts);
        guardEventReadingStsService.updateById(guardEventReadingSts);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:guardeventreadingsts:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            guardEventReadingStsService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:guardeventreadingsts:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        guardEventReadingStsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
