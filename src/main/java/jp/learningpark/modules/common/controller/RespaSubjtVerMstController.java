/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.RespaSubjtVerMstEntity;
import jp.learningpark.modules.common.service.RespaSubjtVerMstService;
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
 * 解答集教科バージョン管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/respasubjtvermst")
public class RespaSubjtVerMstController {
    @Autowired
    private RespaSubjtVerMstService respaSubjtVerMstService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:respasubjtvermst:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = respaSubjtVerMstService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:respasubjtvermst:info")
    public R info(@PathVariable("id") Integer id){
        RespaSubjtVerMstEntity respaSubjtVerMst = respaSubjtVerMstService.getById(id);

        return R.ok().put("respaSubjtVerMst", respaSubjtVerMst);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:respasubjtvermst:save")
    public R save(@RequestBody RespaSubjtVerMstEntity respaSubjtVerMst){
        respaSubjtVerMstService.save(respaSubjtVerMst);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:respasubjtvermst:update")
    public R update(@RequestBody RespaSubjtVerMstEntity respaSubjtVerMst){
        ValidatorUtils.validateEntity(respaSubjtVerMst);
        respaSubjtVerMstService.updateById(respaSubjtVerMst);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:respasubjtvermst:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            respaSubjtVerMstService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:respasubjtvermst:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        respaSubjtVerMstService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
