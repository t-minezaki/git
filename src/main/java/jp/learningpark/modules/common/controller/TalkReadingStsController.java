/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.TalkReadingStsEntity;
import jp.learningpark.modules.common.service.TalkReadingStsService;
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
 * 面談メッセージ閲覧状況
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/talkreadingsts")
public class TalkReadingStsController {
    @Autowired
    private TalkReadingStsService talkReadingStsService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:talkreadingsts:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = talkReadingStsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:talkreadingsts:info")
    public R info(@PathVariable("id") Integer id){
        TalkReadingStsEntity talkReadingSts = talkReadingStsService.getById(id);

        return R.ok().put("talkReadingSts", talkReadingSts);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:talkreadingsts:save")
    public R save(@RequestBody TalkReadingStsEntity talkReadingSts){
        talkReadingStsService.save(talkReadingSts);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:talkreadingsts:update")
    public R update(@RequestBody TalkReadingStsEntity talkReadingSts){
        ValidatorUtils.validateEntity(talkReadingSts);
        talkReadingStsService.updateById(talkReadingSts);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:talkreadingsts:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            talkReadingStsService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:talkreadingsts:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        talkReadingStsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
