/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstInCourseEntity;
import jp.learningpark.modules.common.service.MstInCourseService;
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
 * <p>入会コース管理 controller</p>
 * <p></p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/26 : xie: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("common/mstincourse")
public class MstInCourseController {

    @Autowired
    private MstInCourseService mstInCourseService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstincourse:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstInCourseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstincourse:info")
    public R info(@PathVariable("id") Integer id){
        MstInCourseEntity mstInCourse = mstInCourseService.getById(id);

        return R.ok().put("mstInCourse", mstInCourse);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstincourse:save")
    public R save(@RequestBody MstInCourseEntity mstInCourse){
        mstInCourseService.save(mstInCourse);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstincourse:update")
    public R update(@RequestBody MstInCourseEntity mstInCourse){
        ValidatorUtils.validateEntity(mstInCourse);
        mstInCourseService.updateById(mstInCourse);//全部更新

        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstincourse:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
        mstInCourseService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstincourse:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstInCourseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
