/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstColorBlockEntity;
import jp.learningpark.modules.common.service.MstColorBlockService;
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
 * ${comments}
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstcolorblock")
public class MstColorBlockController {
    @Autowired
    private MstColorBlockService mstColorBlockService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstcolorblock:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstColorBlockService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstcolorblock:info")
    public R info(@PathVariable("id") Integer id){
        MstColorBlockEntity mstColorBlock = mstColorBlockService.getById(id);

        return R.ok().put("mstColorBlock", mstColorBlock);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstcolorblock:save")
    public R save(@RequestBody MstColorBlockEntity mstColorBlock){
        mstColorBlockService.save(mstColorBlock);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstcolorblock:update")
    public R update(@RequestBody MstColorBlockEntity mstColorBlock){
        ValidatorUtils.validateEntity(mstColorBlock);
        mstColorBlockService.updateById(mstColorBlock);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstcolorblock:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstColorBlockService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstcolorblock:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstColorBlockService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
