/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.VariousScannerTokenMstEntity;
import jp.learningpark.modules.common.service.VariousScannerTokenMstService;
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
 * 入退室スキャンTOKEN管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/variousscannertokenmst")
public class VariousScannerTokenMstController {
    @Autowired
    private VariousScannerTokenMstService variousScannerTokenMstService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:variousscannertokenmst:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = variousScannerTokenMstService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:variousscannertokenmst:info")
    public R info(@PathVariable("id") Integer id){
        VariousScannerTokenMstEntity variousScannerTokenMst = variousScannerTokenMstService.getById(id);

        return R.ok().put("variousScannerTokenMst", variousScannerTokenMst);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:variousscannertokenmst:save")
    public R save(@RequestBody VariousScannerTokenMstEntity variousScannerTokenMst){
        variousScannerTokenMstService.save(variousScannerTokenMst);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:variousscannertokenmst:update")
    public R update(@RequestBody VariousScannerTokenMstEntity variousScannerTokenMst){
        ValidatorUtils.validateEntity(variousScannerTokenMst);
        variousScannerTokenMstService.updateById(variousScannerTokenMst);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:variousscannertokenmst:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            variousScannerTokenMstService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:variousscannertokenmst:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        variousScannerTokenMstService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
