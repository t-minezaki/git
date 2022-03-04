/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstQnEntity;
import jp.learningpark.modules.common.service.MstQnService;
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
 * 質問
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstqn")
public class MstQnController {
    @Autowired
    private MstQnService mstQnService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstqn:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstQnService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstqn:info")
    public R info(@PathVariable("id") Integer id){
        MstQnEntity mstQn = mstQnService.getById(id);

        return R.ok().put("mstQn", mstQn);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstqn:save")
    public R save(@RequestBody MstQnEntity mstQn){
        mstQnService.save(mstQn);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstqn:update")
    public R update(@RequestBody MstQnEntity mstQn){
        ValidatorUtils.validateEntity(mstQn);
        mstQnService.updateById(mstQn);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstqn:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstQnService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstqn:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstQnService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
