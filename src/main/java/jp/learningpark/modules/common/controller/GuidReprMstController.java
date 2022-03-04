/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.GuidReprMstEntity;
import jp.learningpark.modules.common.service.GuidReprMstService;
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
 * 指導報告書マスタ管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/guidreprmst")
public class GuidReprMstController {
    @Autowired
    private GuidReprMstService guidReprMstService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:guidreprmst:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = guidReprMstService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:guidreprmst:info")
    public R info(@PathVariable("id") Integer id){
        GuidReprMstEntity guidReprMst = guidReprMstService.getById(id);

        return R.ok().put("guidReprMst", guidReprMst);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:guidreprmst:save")
    public R save(@RequestBody GuidReprMstEntity guidReprMst){
        guidReprMstService.save(guidReprMst);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:guidreprmst:update")
    public R update(@RequestBody GuidReprMstEntity guidReprMst){
        ValidatorUtils.validateEntity(guidReprMst);
        guidReprMstService.updateById(guidReprMst);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:guidreprmst:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            guidReprMstService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:guidreprmst:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        guidReprMstService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
