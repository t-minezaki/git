/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.LateAbsHstEntity;
import jp.learningpark.modules.common.service.LateAbsHstService;
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
 * 遅刻欠席連絡履歴
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/lateabshst")
public class LateAbsHstController {
    @Autowired
    private LateAbsHstService lateAbsHstService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:lateabshst:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = lateAbsHstService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:lateabshst:info")
    public R info(@PathVariable("id") Integer id){
        LateAbsHstEntity lateAbsHst = lateAbsHstService.getById(id);

        return R.ok().put("lateAbsHst", lateAbsHst);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:lateabshst:save")
    public R save(@RequestBody LateAbsHstEntity lateAbsHst){
        lateAbsHstService.save(lateAbsHst);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:lateabshst:update")
    public R update(@RequestBody LateAbsHstEntity lateAbsHst){
        ValidatorUtils.validateEntity(lateAbsHst);
        lateAbsHstService.updateById(lateAbsHst);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:lateabshst:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            lateAbsHstService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:lateabshst:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        lateAbsHstService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
