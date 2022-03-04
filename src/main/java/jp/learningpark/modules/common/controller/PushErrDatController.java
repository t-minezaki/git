/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.PushErrDatEntity;
import jp.learningpark.modules.common.service.PushErrDatService;
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
 * プッシュ失敗データ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/pusherrdat")
public class PushErrDatController {
    @Autowired
    private PushErrDatService pushErrDatService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:pusherrdat:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pushErrDatService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:pusherrdat:info")
    public R info(@PathVariable("id") Integer id){
        PushErrDatEntity pushErrDat = pushErrDatService.getById(id);

        return R.ok().put("pushErrDat", pushErrDat);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:pusherrdat:save")
    public R save(@RequestBody PushErrDatEntity pushErrDat){
        pushErrDatService.save(pushErrDat);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:pusherrdat:update")
    public R update(@RequestBody PushErrDatEntity pushErrDat){
        ValidatorUtils.validateEntity(pushErrDat);
        pushErrDatService.updateById(pushErrDat);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:pusherrdat:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            pushErrDatService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:pusherrdat:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        pushErrDatService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
