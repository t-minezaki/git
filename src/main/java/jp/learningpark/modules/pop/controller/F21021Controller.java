/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p>スマホ_褒めスタンプ画面(POP)</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/02/18 : yang: 新規<br />
 * @version 6.0
 */

@RequestMapping("/pop/F21021")
@RestController
public class F21021Controller {
    @Autowired
    MstCodDService mstCodDService;
    /**
     * 初期化
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer pageNum, Integer limit) {
        //本画面で、スタンプを選択できるため、コートマスターからスタンプ情報を取得し、画面で表示する。
        List<MstCodDEntity> mstCodDEntityList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().eq("cod_key","PRAISE_STAMP_LIST").orderByAsc("sort").eq("del_flg",0));
        //取得件数9件
        List<MstCodDEntity> mstCodDEntityListLimit = mstCodDService.list(new QueryWrapper<MstCodDEntity>().eq("cod_key","PRAISE_STAMP_LIST").orderByAsc("sort").eq("del_flg",0).last("LIMIT " + limit + " OFFSET " + (pageNum - 1) * limit));
        return R.ok().put("mstCodDEntityList",mstCodDEntityList).put("mstCodDEntityListLimit",mstCodDEntityListLimit);
    }

}