/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.AbsQaReplyEntity;
import jp.learningpark.modules.common.service.AbsQaReplyService;
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
 * 出席質問回答
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/absqareply")
public class AbsQaReplyController {
    @Autowired
    private AbsQaReplyService absQaReplyService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:absqareply:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = absQaReplyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:absqareply:info")
    public R info(@PathVariable("id") Integer id){
        AbsQaReplyEntity absQaReply = absQaReplyService.getById(id);

        return R.ok().put("absQaReply", absQaReply);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:absqareply:save")
    public R save(@RequestBody AbsQaReplyEntity absQaReply){
        absQaReplyService.save(absQaReply);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:absqareply:update")
    public R update(@RequestBody AbsQaReplyEntity absQaReply){
        ValidatorUtils.validateEntity(absQaReply);
        absQaReplyService.updateById(absQaReply);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:absqareply:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            absQaReplyService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:absqareply:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        absQaReplyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
