/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.RespaFileMstEntity;
import jp.learningpark.modules.common.service.RespaFileMstService;
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
 * 解答集ファイル管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/respafilemst")
public class RespaFileMstController {
    @Autowired
    private RespaFileMstService respaFileMstService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:respafilemst:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = respaFileMstService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:respafilemst:info")
    public R info(@PathVariable("id") Integer id){
        RespaFileMstEntity respaFileMst = respaFileMstService.getById(id);

        return R.ok().put("respaFileMst", respaFileMst);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:respafilemst:save")
    public R save(@RequestBody RespaFileMstEntity respaFileMst){
        respaFileMstService.save(respaFileMst);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:respafilemst:update")
    public R update(@RequestBody RespaFileMstEntity respaFileMst){
        ValidatorUtils.validateEntity(respaFileMst);
        respaFileMstService.updateById(respaFileMst);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:respafilemst:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            respaFileMstService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:respafilemst:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        respaFileMstService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
