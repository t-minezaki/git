/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.sys.service;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.modules.sys.entity.SysUserOnlineEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * システムユーザーオンライン
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
public interface SysUserOnlineService {
    
    /**
     * ページ処理
     * @param params　ページパラメータ
     * @return １ページ
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 通过会话序号查询信息
     * 
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    public SysUserOnlineEntity selectById(String sessionId);

    /**
     * 通过会话序号删除信息
     * 
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    public void deleteOnlineById(String sessionId);

    /**
     * 通过会话序号删除信息
     * 
     * @param sessions 会话ID集合
     * @return 在线用户信息
     */
    public void batchDeleteOnline(List<String> sessions);

    /**
     * 保存会话信息
     * 
     * @param online 会话信息
     */
    public void saveOnline(SysUserOnlineEntity online);

    /**
     * 查询会话集合
     * 
     * @param userOnline 分页参数
     * @return 会话集合
     */
    public List<SysUserOnlineEntity> selectUserOnlineList(SysUserOnlineEntity userOnline);

//    /**
//     * 强退用户
//     * 
//     * @param sessionId 会话ID
//     */
//    public void forceLogout(String sessionId);

    /**
     * 查询会话集合
     * 
     * @param expiredDate 有效期
     * @return 会话集合
     */
    public List<SysUserOnlineEntity> selectOnlineByExpired(Date expiredDate);

}
