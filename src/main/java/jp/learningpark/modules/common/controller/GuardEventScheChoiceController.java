/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.GuardEventScheChoiceEntity;
import jp.learningpark.modules.common.service.GuardEventScheChoiceService;
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
 * 保護者イベント日程選択
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/guardeventschechoice")
public class GuardEventScheChoiceController {
    @Autowired
    private GuardEventScheChoiceService guardEventScheChoiceService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:guardeventschechoice:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = guardEventScheChoiceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:guardeventschechoice:info")
    public R info(@PathVariable("id") Integer id){
        GuardEventScheChoiceEntity guardEventScheChoice = guardEventScheChoiceService.getById(id);

        return R.ok().put("guardEventScheChoice", guardEventScheChoice);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:guardeventschechoice:save")
    public R save(@RequestBody GuardEventScheChoiceEntity guardEventScheChoice){
        guardEventScheChoiceService.save(guardEventScheChoice);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:guardeventschechoice:update")
    public R update(@RequestBody GuardEventScheChoiceEntity guardEventScheChoice){
        ValidatorUtils.validateEntity(guardEventScheChoice);
        guardEventScheChoiceService.updateById(guardEventScheChoice);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:guardeventschechoice:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            guardEventScheChoiceService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:guardeventschechoice:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        guardEventScheChoiceService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
