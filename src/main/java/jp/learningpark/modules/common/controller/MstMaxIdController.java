/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstMaxIdEntity;
import jp.learningpark.modules.common.service.MstMaxIdService;
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
 * MAX採番
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstmaxid")
public class MstMaxIdController {
    @Autowired
    private MstMaxIdService mstMaxIdService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstmaxid:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstMaxIdService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstmaxid:info")
    public R info(@PathVariable("id") Integer id){
        MstMaxIdEntity mstMaxId = mstMaxIdService.getById(id);

        return R.ok().put("mstMaxId", mstMaxId);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstmaxid:save")
    public R save(@RequestBody MstMaxIdEntity mstMaxId){
        mstMaxIdService.save(mstMaxId);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstmaxid:update")
    public R update(@RequestBody MstMaxIdEntity mstMaxId){
        ValidatorUtils.validateEntity(mstMaxId);
        mstMaxIdService.updateById(mstMaxId);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstmaxid:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstMaxIdService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstmaxid:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstMaxIdService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
