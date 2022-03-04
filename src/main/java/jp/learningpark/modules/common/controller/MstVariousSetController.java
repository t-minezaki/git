/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstVariousSetEntity;
import jp.learningpark.modules.common.service.MstVariousSetService;
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
 * 入退室各種設定マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstvariousset")
public class MstVariousSetController {
    @Autowired
    private MstVariousSetService mstVariousSetService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstvariousset:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstVariousSetService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstvariousset:info")
    public R info(@PathVariable("id") Integer id){
        MstVariousSetEntity mstVariousSet = mstVariousSetService.getById(id);

        return R.ok().put("mstVariousSet", mstVariousSet);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstvariousset:save")
    public R save(@RequestBody MstVariousSetEntity mstVariousSet){
        mstVariousSetService.save(mstVariousSet);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstvariousset:update")
    public R update(@RequestBody MstVariousSetEntity mstVariousSet){
        ValidatorUtils.validateEntity(mstVariousSet);
        mstVariousSetService.updateById(mstVariousSet);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstvariousset:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstVariousSetService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstvariousset:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstVariousSetService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
