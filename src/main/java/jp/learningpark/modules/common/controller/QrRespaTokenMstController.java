/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.QrRespaTokenMstEntity;
import jp.learningpark.modules.common.service.QrRespaTokenMstService;
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
 * QR解答集用TOKEN管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/qrrespatokenmst")
public class QrRespaTokenMstController {
    @Autowired
    private QrRespaTokenMstService qrRespaTokenMstService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:qrrespatokenmst:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = qrRespaTokenMstService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:qrrespatokenmst:info")
    public R info(@PathVariable("id") Integer id){
        QrRespaTokenMstEntity qrRespaTokenMst = qrRespaTokenMstService.getById(id);

        return R.ok().put("qrRespaTokenMst", qrRespaTokenMst);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:qrrespatokenmst:save")
    public R save(@RequestBody QrRespaTokenMstEntity qrRespaTokenMst){
        qrRespaTokenMstService.save(qrRespaTokenMst);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:qrrespatokenmst:update")
    public R update(@RequestBody QrRespaTokenMstEntity qrRespaTokenMst){
        ValidatorUtils.validateEntity(qrRespaTokenMst);
        qrRespaTokenMstService.updateById(qrRespaTokenMst);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:qrrespatokenmst:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            qrRespaTokenMstService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:qrrespatokenmst:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        qrRespaTokenMstService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
