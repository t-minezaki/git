/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstManagerEntity;
import jp.learningpark.modules.common.service.MstManagerService;
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
 * 管理者基本マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstmanager")
public class MstManagerController {
    @Autowired
    private MstManagerService mstManagerService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstmanager:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstManagerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstmanager:info")
    public R info(@PathVariable("id") Integer id){
        MstManagerEntity mstManager = mstManagerService.getById(id);

        return R.ok().put("mstManager", mstManager);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstmanager:save")
    public R save(@RequestBody MstManagerEntity mstManager){
        mstManagerService.save(mstManager);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstmanager:update")
    public R update(@RequestBody MstManagerEntity mstManager){
        ValidatorUtils.validateEntity(mstManager);
        mstManagerService.updateById(mstManager);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstmanager:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstManagerService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstmanager:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstManagerService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
