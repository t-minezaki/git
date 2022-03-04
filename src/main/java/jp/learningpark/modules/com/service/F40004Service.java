package jp.learningpark.modules.com.service;
/**
 * <p>F40004 保護者共通メニュー画面_ServiceImpl</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/03/16 : zpa: 新規<br />
 * @version 6.0
 */
public interface F40004Service {
    Integer getEventUnreadCount();
    Integer getNoticeUnreadCount();
    Integer getGKGCCount();

}
