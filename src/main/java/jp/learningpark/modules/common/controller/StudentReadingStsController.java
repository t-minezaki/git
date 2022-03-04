/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.StudentReadingStsEntity;
import jp.learningpark.modules.common.service.StudentReadingStsService;
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
 * 生徒メッセージ閲覧状況
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/studentreadingsts")
public class StudentReadingStsController {
    @Autowired
    private StudentReadingStsService studentReadingStsService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:studentreadingsts:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = studentReadingStsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:studentreadingsts:info")
    public R info(@PathVariable("id") Integer id){
        StudentReadingStsEntity studentReadingSts = studentReadingStsService.getById(id);

        return R.ok().put("studentReadingSts", studentReadingSts);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:studentreadingsts:save")
    public R save(@RequestBody StudentReadingStsEntity studentReadingSts){
        studentReadingStsService.save(studentReadingSts);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:studentreadingsts:update")
    public R update(@RequestBody StudentReadingStsEntity studentReadingSts){
        ValidatorUtils.validateEntity(studentReadingSts);
        studentReadingStsService.updateById(studentReadingSts);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:studentreadingsts:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            studentReadingStsService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:studentreadingsts:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        studentReadingStsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
