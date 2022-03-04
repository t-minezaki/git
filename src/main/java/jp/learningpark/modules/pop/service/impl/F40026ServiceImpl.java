/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service.impl;

import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.service.MstDeviceTokenService;
import jp.learningpark.modules.pop.dao.F40026Dao;
import jp.learningpark.modules.pop.dto.F40026Dto;
import jp.learningpark.modules.pop.service.F40026Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>通知プッシュ失敗一覧画面（共通）</p >
 *
 * @author NWT : yyb <br />
 * 変更履歴 <br />
 * 2020/07/17 : yyb: 新規<br />
 * @version 7.1
 */
@Service
public class F40026ServiceImpl implements F40026Service {
    /**
     * 通知プッシュ失敗一覧画面（共通） Dto
     */
    @Autowired
    private F40026Dao f40026Dao;
    /**
     * 通知プッシュ
     */
    @Autowired
    NoticeApiService noticeApiService;
    /**
     * 機器 service
     */
    @Autowired
    private MstDeviceTokenService mstDeviceTokenService;
    /**
     * 初期化
     *failedUserList
     * @param msgId
     * @param messageTypeDiv
     */
    @Override
    public F40026Dto getInfo(Integer msgId,String messageTypeDiv) {
        F40026Dto info = f40026Dao.getInfo(msgId, messageTypeDiv);

        if (Objects.nonNull(info) && info.getPubStartDt() != null && info.getPubEndDt() != null) {
            info.setPubStartDt(info.getPubStartDt().substring(0, 16).replace("-", "/"));
            info.setPubEndDt(info.getPubEndDt().substring(0, 16).replace("-", "/"));
        }
        return info;
    }
    /**
     *
     *
     * @param msgTypeDiv
     * @param msgId
     * @param orgId
     */
    @Override
    public List<F40026Dto> failedUserList(String msgTypeDiv, Integer msgId, String orgId,Integer limit, Integer offset) {
        return f40026Dao.failedUserList(msgTypeDiv, msgId, orgId, limit,  offset);
    }
    /**
     *
     *
     * @param id
     */
    @Override
    public void errDataUpdate(Integer id) {
        f40026Dao.errDataUpdate(id);
    }

    /**
     *
     * @param errorIdList
     * @return
     * modify at 2021/09/17 for V9.02 UT-0029 by NWT yang
     */
    @Override
    public List<F40026Dto> selectDeliverInfo(List<Integer> errorIdList) {
        return f40026Dao.selectDeliverInfo(errorIdList);
    }
}