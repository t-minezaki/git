/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.MentorStuEntity;
import jp.learningpark.modules.common.service.MentorStuService;
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
 * メンター生徒管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/mentorstu")
public class MentorStuController {
    @Autowired
    private MentorStuService mentorStuService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:mentorstu:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mentorStuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:mentorstu:info")
    public R info(@PathVariable("id") Integer id){
        MentorStuEntity mentorStu = mentorStuService.getById(id);

        return R.ok().put("mentorStu", mentorStu);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:mentorstu:save")
    public R save(@RequestBody MentorStuEntity mentorStu){
        mentorStuService.save(mentorStu);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:mentorstu:update")
    public R update(@RequestBody MentorStuEntity mentorStu){
        ValidatorUtils.validateEntity(mentorStu);
        mentorStuService.updateById(mentorStu);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:mentorstu:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            mentorStuService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:mentorstu:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        mentorStuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
