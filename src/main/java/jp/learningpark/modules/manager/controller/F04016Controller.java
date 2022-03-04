/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstNoticeService;
import jp.learningpark.modules.manager.dto.F04016Dto;
import jp.learningpark.modules.manager.service.F04016Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

/**
 * <p>保護者既読未読詳細一覧画面（マナミルチャンネル）Controller</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/02/10 : yang: 新規<br />
 * @version 6.0
 */
@RequestMapping("/manager/F04016")
@RestController
public class F04016Controller {
    /**
     * お知らせ Service
     */
    @Autowired
    MstNoticeService mstNoticeService;
    /**
     * f04016Service
     */
    @Autowired
    F04016Service f04016Service;
    /**
     * コードマスタ_明細
     */
    @Autowired
    MstCodDService mstCodDService;

    /**
     * 初期表示
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer noticeId,String flg,String orgId,Integer limit, Integer page) throws IOException {
        MstCodDEntity mstCodDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().eq("cod_key","READING_STS_DIV").eq("cod_cd",flg));
        //組織名
        String orgNm = ShiroUtils.getUserEntity().getOrgNm();
        // 引渡データ．IDを元に、下記条件、お知らせマスタを元に取得し、画面で表示される。
        MstNoticeEntity mstNoticeEntity = mstNoticeService.getOne(new QueryWrapper<MstNoticeEntity>().select("id","org_id","notice_title","pub_plan_start_dt","pub_plan_end_dt").eq("id",noticeId).eq("del_flg",0));
        mstNoticeEntity.setNoticeTitle(URLDecoder.decode(mstNoticeEntity.getNoticeTitle(), "UTF-8"));
        //下記条件で、お知らせ配送先、組織情報、保護者お知らせ閲覧状況より、取得する。　
        List<F04016Dto> f04016DtoList = f04016Service.init(noticeId,flg,orgId,limit,(page - 1) * limit);
        //件数の取得
        Integer count = f04016Service.getTotalCount(noticeId,flg,orgId);
        return R.ok().put("mstNoticeEntity",mstNoticeEntity).put("page",new PageUtils(f04016DtoList,count,limit, page)).put("orgNm",orgNm).put("orgId",orgId).put("title",mstCodDEntity.getCodValue());
    }
}
