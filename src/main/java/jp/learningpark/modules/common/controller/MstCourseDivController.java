/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MstCourseDivEntity;
import jp.learningpark.modules.common.service.MstCourseDivService;
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
 * <p>コース区分管理 controller</p>
 * <p>详细描述</p>
 * <p></p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/26 : xie: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("common/mstcoursediv")
public class MstCourseDivController {
    @Autowired
    private MstCourseDivService mstCourseDivService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mstcoursediv:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mstCourseDivService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mstcoursediv:info")
    public R info(@PathVariable("id") Integer id){
        MstCourseDivEntity mstCourseDiv = mstCourseDivService.getById(id);
        return R.ok().put("mstCourseDiv", mstCourseDiv);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mstcoursediv:save")
    public R save(@RequestBody MstCourseDivEntity mstCourseDiv){
        mstCourseDivService.save(mstCourseDiv);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mstcoursediv:update")
    public R update(@RequestBody MstCourseDivEntity mstCourseDiv){
        ValidatorUtils.validateEntity(mstCourseDiv);
        mstCourseDivService.updateById(mstCourseDiv);//全部更新

        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mstcoursediv:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
        mstCourseDivService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mstcoursediv:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mstCourseDivService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
