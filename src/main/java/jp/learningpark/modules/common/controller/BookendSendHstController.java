/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.controller;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.validator.ValidatorUtils;
import jp.learningpark.modules.common.entity.BookendSendHstEntity;
import jp.learningpark.modules.common.service.BookendSendHstService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * ${comments}
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@RestController
@RequestMapping("common/bookendsendhst")
public class BookendSendHstController {
    @Autowired
    private BookendSendHstService bookendSendHstService;

    /**
     * リスト
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @RequiresPermissions("common:bookendsendhst:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bookendSendHstService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 明細情報
     */
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    @RequiresPermissions("common:bookendsendhst:info")
    public R info(@PathVariable("id") Integer id){
        BookendSendHstEntity bookendSendHst = bookendSendHstService.getById(id);

        return R.ok().put("bookendSendHst", bookendSendHst);
    }

    /**
     * 追加
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @RequiresPermissions("common:bookendsendhst:save")
    public R save(@RequestBody BookendSendHstEntity bookendSendHst){
        bookendSendHstService.save(bookendSendHst);

        return R.ok();
    }

    /**
     * 変更
     */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @RequiresPermissions("common:bookendsendhst:update")
    public R update(@RequestBody BookendSendHstEntity bookendSendHst){
        ValidatorUtils.validateEntity(bookendSendHst);
        bookendSendHstService.updateById(bookendSendHst);//全部更新
        
        return R.ok();
    }

    /**
     * 削除
     */
    @RequestMapping(value="/deleteOne",method = RequestMethod.GET)
    @RequiresPermissions("common:bookendsendhst:deleteOne")
    public R deletedeleteOne(@RequestParam("id") Integer id){
            bookendSendHstService.removeById(id);

        return R.ok();
    }

    /**
     * バッチ削除
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions("common:bookendsendhst:delete")
    public R delete(@RequestParam("ids") Integer[] ids){
        bookendSendHstService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
