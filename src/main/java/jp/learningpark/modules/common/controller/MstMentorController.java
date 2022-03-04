/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstMentorEntity;
import jp.learningpark.modules.common.service.MstMentorService;
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
 * メンター基本マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstmentor")
public class MstMentorController {
    @Autowired
    private MstMentorService mstMentorService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstmentor:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstMentorService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstmentor:info")
    public R info(@PathVariable("id") Integer id){
        MstMentorEntity mstMentor = mstMentorService.getById(id);

        return R.ok().put("mstMentor", mstMentor);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstmentor:save")
    public R save(@RequestBody MstMentorEntity mstMentor){
        mstMentorService.save(mstMentor);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstmentor:update")
    public R update(@RequestBody MstMentorEntity mstMentor){
        ValidatorUtils.validateEntity(mstMentor);
        mstMentorService.updateById(mstMentor);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstmentor:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstMentorService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstmentor:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstMentorService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
