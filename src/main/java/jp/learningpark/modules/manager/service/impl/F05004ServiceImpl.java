/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.utils.dao.CommonDao;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.manager.dao.F05004Dao;
import jp.learningpark.modules.manager.dto.F05004Dto;
import jp.learningpark.modules.manager.dto.F05004DtoStu;
import jp.learningpark.modules.manager.service.F05004Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>F05004_知らせ照会画面 ServiceImpl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/3/14 : gong: 新規<br />
 * 2019/06/18 : hujiaxing: mod<br />
 * @version 1.0
 */
@Service
public class F05004ServiceImpl implements F05004Service {

    /**
     * F05004_知らせ照会画面 Dao
     */
    @Autowired
    private F05004Dao f05004Dao;

    /**
     * 共通処理 Dao
     */
    @Autowired
    private CommonDao commonDao;

    /**
     * 画面．配信先組織を取得し表示する。
     *
     * @param noticeId お知らせＩＤ
     * @return
     */
    @Override
    public List<F05004Dto> getOrgList(Integer noticeId,String orgId){
        //セッションデータ．組織ID
        String ownerOrgId = ShiroUtils.getUserEntity().getOrgId();

        //セッションデータ．ブランドコード
        String brandCd=ShiroUtils.getBrandcd();

        //本組織及び下層組織リストの取得
        List<OrgAndLowerOrgIdDto> orgs=null;

        List<F05004Dto> f05004DtoList=new ArrayList<>();
        //「組織ID」＝セッションデータ．組織IDの場合
        if(StringUtils.equals(ownerOrgId,orgId)){
            f05004DtoList=f05004Dao.selectOrgsByNoticeId(noticeId);
        }
        //「組織ID」≠セッションデータ．組織ID
        else{
            orgs=commonDao.selectThisAndLowerOrgId(brandCd,ownerOrgId);
            f05004DtoList=f05004Dao.selectOrgsByNoticeIdWithOrgIds(noticeId,orgs);
        }
        return f05004DtoList;
    }

    /**
     * get student list by notice id (and org id)
     * @param noticeId お知らせＩＤ
     * @param orgId 組織ID
     * @return
     */
    @Override
    public List<F05004DtoStu> getStuListByNoticeId(Integer noticeId,String orgId){
        return f05004Dao.selectStuListByNoticeId(noticeId,orgId);
    }
}
