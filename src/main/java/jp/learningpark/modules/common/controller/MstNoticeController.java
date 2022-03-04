/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.service.MstNoticeService;
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
 * お知らせ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstnotice")
public class MstNoticeController {
    @Autowired
    private MstNoticeService mstNoticeService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstnotice:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstNoticeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstnotice:info")
    public R info(@PathVariable("id") Integer id){
        MstNoticeEntity mstNotice = mstNoticeService.getById(id);

        return R.ok().put("mstNotice", mstNotice);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstnotice:save")
    public R save(@RequestBody MstNoticeEntity mstNotice){
        mstNoticeService.save(mstNotice);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstnotice:update")
    public R update(@RequestBody MstNoticeEntity mstNotice){
        ValidatorUtils.validateEntity(mstNotice);
        mstNoticeService.updateById(mstNotice);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstnotice:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstNoticeService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstnotice:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstNoticeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
