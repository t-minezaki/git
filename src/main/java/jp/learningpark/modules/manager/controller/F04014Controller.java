package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstNoticeService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.service.F04014Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * <p>F04014_マナミルチャンネル参照画面</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/02/25 : yang: 新規<br />
 * @version 6.0
 */

@RequestMapping("/manager/F04014")
@RestController
public class F04014Controller extends AbstractController {

    /**
     * お知らせマスタ Service
     */
    @Autowired
    private MstNoticeService mstNoticeService;
    /**
     * f04014Service
     */
    @Autowired
    private F04014Service f04014Service;
    /**
     * 組織マスタ Service
     */
    @Autowired
    private MstOrgService mstOrgService;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f04014Init(Integer id) throws UnsupportedEncodingException {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().eq("org_id",orgId).eq("del_flg",0));
        //引渡データ．IDを元に、下記条件、お知らせマスタを元に取得し、画面で表示される。
        MstNoticeEntity mstNoticeEntity = mstNoticeService.getById(id);
        mstNoticeEntity.setNoticeTitle(URLDecoder.decode(mstNoticeEntity.getNoticeTitle(),"utf-8"));
        //該当お知らせIDによって、全体の送信組織を抽出して、画面中に表示する
        List<MstOrgEntity> orgList = f04014Service.getOrgList(id);
        return R.ok().put("mstNoticeEntity", mstNoticeEntity).put("orgList", orgList).put("mstOrgEntity",mstOrgEntity);
    }
}
