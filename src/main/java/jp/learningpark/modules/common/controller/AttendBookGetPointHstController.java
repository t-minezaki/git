/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.modules.common.entity.AttendBookGetPointHstEntity;
import jp.learningpark.modules.common.service.AttendBookGetPointHstService;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
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
 * 出席簿付与ポイント履歴
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/attendbookgetpointhst")
public class AttendBookGetPointHstController {
    @Autowired
    private AttendBookGetPointHstService attendBookGetPointHstService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:attendbookgetpointhst:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attendBookGetPointHstService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:attendbookgetpointhst:info")
    public R info(@PathVariable("id") Integer id){
        AttendBookGetPointHstEntity attendBookGetPointHst = attendBookGetPointHstService.getById(id);

        return R.ok().put("attendBookGetPointHst", attendBookGetPointHst);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:attendbookgetpointhst:save")
    public R save(@RequestBody AttendBookGetPointHstEntity attendBookGetPointHst){
        attendBookGetPointHstService.save(attendBookGetPointHst);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:attendbookgetpointhst:update")
    public R update(@RequestBody AttendBookGetPointHstEntity attendBookGetPointHst){
        ValidatorUtils.validateEntity(attendBookGetPointHst);
        attendBookGetPointHstService.updateById(attendBookGetPointHst);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:attendbookgetpointhst:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            attendBookGetPointHstService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:attendbookgetpointhst:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        attendBookGetPointHstService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
