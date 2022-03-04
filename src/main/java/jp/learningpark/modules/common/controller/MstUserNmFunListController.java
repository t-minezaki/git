/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstUserNmFunListEntity;
import jp.learningpark.modules.common.service.MstUserNmFunListService;
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
 * ユーザ別機能一覧マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstusernmfunlist")
public class MstUserNmFunListController {
    @Autowired
    private MstUserNmFunListService mstUserNmFunListService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstusernmfunlist:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstUserNmFunListService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstusernmfunlist:info")
    public R info(@PathVariable("id") Integer id){
        MstUserNmFunListEntity mstUserNmFunList = mstUserNmFunListService.getById(id);

        return R.ok().put("mstUserNmFunList", mstUserNmFunList);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstusernmfunlist:save")
    public R save(@RequestBody MstUserNmFunListEntity mstUserNmFunList){
        mstUserNmFunListService.save(mstUserNmFunList);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstusernmfunlist:update")
    public R update(@RequestBody MstUserNmFunListEntity mstUserNmFunList){
        ValidatorUtils.validateEntity(mstUserNmFunList);
        mstUserNmFunListService.updateById(mstUserNmFunList);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstusernmfunlist:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstUserNmFunListService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstusernmfunlist:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstUserNmFunListService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
