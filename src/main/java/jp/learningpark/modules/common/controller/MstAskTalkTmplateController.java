/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstAskTalkTmplateEntity;
import jp.learningpark.modules.common.service.MstAskTalkTmplateService;
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
 * 質問面談テンプレート
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstasktalktmplate")
public class MstAskTalkTmplateController {
    @Autowired
    private MstAskTalkTmplateService mstAskTalkTmplateService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstasktalktmplate:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstAskTalkTmplateService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstasktalktmplate:info")
    public R info(@PathVariable("id") Integer id){
        MstAskTalkTmplateEntity mstAskTalkTmplate = mstAskTalkTmplateService.getById(id);

        return R.ok().put("mstAskTalkTmplate", mstAskTalkTmplate);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstasktalktmplate:save")
    public R save(@RequestBody MstAskTalkTmplateEntity mstAskTalkTmplate){
        mstAskTalkTmplateService.save(mstAskTalkTmplate);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstasktalktmplate:update")
    public R update(@RequestBody MstAskTalkTmplateEntity mstAskTalkTmplate){
        ValidatorUtils.validateEntity(mstAskTalkTmplate);
        mstAskTalkTmplateService.updateById(mstAskTalkTmplate);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstasktalktmplate:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstAskTalkTmplateService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstasktalktmplate:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstAskTalkTmplateService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
