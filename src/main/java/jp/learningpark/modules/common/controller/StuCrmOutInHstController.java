/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StuCrmOutInHstEntity;
import jp.learningpark.modules.common.service.StuCrmOutInHstService;
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
 * 生徒入退室履歴
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/stucrmoutinhst")
public class StuCrmOutInHstController {
    @Autowired
    private StuCrmOutInHstService stuCrmOutInHstService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:stucrmoutinhst:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stuCrmOutInHstService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:stucrmoutinhst:info")
    public R info(@PathVariable("id") Integer id){
        StuCrmOutInHstEntity stuCrmOutInHst = stuCrmOutInHstService.getById(id);

        return R.ok().put("stuCrmOutInHst", stuCrmOutInHst);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:stucrmoutinhst:save")
    public R save(@RequestBody StuCrmOutInHstEntity stuCrmOutInHst){
        stuCrmOutInHstService.save(stuCrmOutInHst);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:stucrmoutinhst:update")
    public R update(@RequestBody StuCrmOutInHstEntity stuCrmOutInHst){
        ValidatorUtils.validateEntity(stuCrmOutInHst);
        stuCrmOutInHstService.updateById(stuCrmOutInHst);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:stucrmoutinhst:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            stuCrmOutInHstService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:stucrmoutinhst:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        stuCrmOutInHstService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
