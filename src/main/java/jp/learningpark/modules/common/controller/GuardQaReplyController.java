/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.GuardQaReplyEntity;
import jp.learningpark.modules.common.service.GuardQaReplyService;
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
 * 保護者質問回答
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/guardqareply")
public class GuardQaReplyController {
    @Autowired
    private GuardQaReplyService guardQaReplyService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:guardqareply:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = guardQaReplyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:guardqareply:info")
    public R info(@PathVariable("id") Integer id){
        GuardQaReplyEntity guardQaReply = guardQaReplyService.getById(id);

        return R.ok().put("guardQaReply", guardQaReply);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:guardqareply:save")
    public R save(@RequestBody GuardQaReplyEntity guardQaReply){
        guardQaReplyService.save(guardQaReply);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:guardqareply:update")
    public R update(@RequestBody GuardQaReplyEntity guardQaReply){
        ValidatorUtils.validateEntity(guardQaReply);
        guardQaReplyService.updateById(guardQaReply);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:guardqareply:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            guardQaReplyService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:guardqareply:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        guardQaReplyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
