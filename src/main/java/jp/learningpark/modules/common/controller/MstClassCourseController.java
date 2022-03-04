/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstClassCourseEntity;
import jp.learningpark.modules.common.service.MstClassCourseService;
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
 * 教室コース関連管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mstclasscourse")
public class MstClassCourseController {
    @Autowired
    private MstClassCourseService mstClassCourseService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstclasscourse:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstClassCourseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstclasscourse:info")
    public R info(@PathVariable("id") Integer id){
        MstClassCourseEntity mstClassCourse = mstClassCourseService.getById(id);

        return R.ok().put("mstClassCourse", mstClassCourse);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstclasscourse:save")
    public R save(@RequestBody MstClassCourseEntity mstClassCourse){
        mstClassCourseService.save(mstClassCourse);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstclasscourse:update")
    public R update(@RequestBody MstClassCourseEntity mstClassCourse){
        ValidatorUtils.validateEntity(mstClassCourse);
        mstClassCourseService.updateById(mstClassCourse);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstclasscourse:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mstClassCourseService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstclasscourse:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstClassCourseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
