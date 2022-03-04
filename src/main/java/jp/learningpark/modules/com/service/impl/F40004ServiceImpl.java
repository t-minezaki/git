package jp.learningpark.modules.com.service.impl;

import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.com.dao.F40004Dao;
import jp.learningpark.modules.com.service.F40004Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * <p>F40004 保護者共通メニュー画面_ServiceImpl</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/03/16 : zpa: 新規<br />
 * @version 6.0
 */
@Service
public class F40004ServiceImpl implements F40004Service {
   @Autowired
    F40004Dao f40004Dao;



    @Override
    public Integer getEventUnreadCount() {
        //セッション・生徒Id
        String stuId = (String) ShiroUtils.getSessionAttribute("stuId");
        //セッション・保護者Id
        String guardId = ShiroUtils.getUserId();
        //セッション・生徒組織Id
//        String orgId = (String) ShiroUtils.getSessionAttribute("orgId");
        return f40004Dao.getEventUnreadCount(guardId,stuId);
    }

    @Override
    public Integer getNoticeUnreadCount() {
        //セッション・生徒Id
        String stuId = (String) ShiroUtils.getSessionAttribute("stuId");
        //セッション・保護者Id
        String guardId = ShiroUtils.getUserId();
        //セッション・生徒組織Id
        String orgId = (String) ShiroUtils.getSessionAttribute("orgId");
        return f40004Dao.getNoticeUnreadCount(orgId,guardId,stuId);
    }

    @Override
    public Integer getGKGCCount() {
        //セッション・生徒Id
        String stuId = (String) ShiroUtils.getSessionAttribute("stuId");
        //セッション・保護者Id
        String guardId = ShiroUtils.getUserId();
        //セッション・生徒組織Id
        String orgId = (String) ShiroUtils.getSessionAttribute("orgId");
        return f40004Dao.getGKGCCount(orgId, guardId, stuId);
    }
}
