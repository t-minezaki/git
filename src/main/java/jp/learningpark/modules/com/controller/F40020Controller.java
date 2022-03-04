package jp.learningpark.modules.com.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.com.dao.F40020Dao;
import jp.learningpark.modules.com.service.F40020Service;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstUsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>ワンタイムパスワード認証結果（成功）画面</p>
 * <p>Controller</p>
 *
 * @author NWT : wang <br />
 * 変更履歴 <br />
 * 2020/8/6 : wang: 新規<br />
 * @version 8.0
 */

@RequestMapping("/common/F40020")
@RestController
public class F40020Controller {

    @Autowired
    F40020Service f40020Service;
    @Autowired
    MstUsrService mstUsrService;
    @Autowired
    F40020Dao f40020Dao;

    /**
     * @return confirm
     * init
     */
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public R getCompare() {
        return f40020Service.confirmId();
    }
    /**
     * @return confirm
     * init
     */
    @RequestMapping(value = "/getUpdateTime", method = RequestMethod.GET)
    public R getUpdateTime() {
        MstUsrEntity mstusrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", ShiroUtils.getUserId()));
        return R.ok().put("updDatime",mstusrEntity.getUpdDatime());
    }
//update
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public R Update(String updDatime){
        MstUsrEntity mstusrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", ShiroUtils.getUserId()));
//        排他チェックエラーの場合
        if (!DateUtils.format(mstusrEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).equals(updDatime)) {
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        return f40020Service.update();
    }

}
