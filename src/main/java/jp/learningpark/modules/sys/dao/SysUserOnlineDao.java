/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.sys.dao;

import jp.learningpark.framework.dao.SqlMapper;
import jp.learningpark.modules.sys.entity.SysUserOnlineEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * システムユーザーオンライン
 * 
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@Mapper
public interface SysUserOnlineDao extends SqlMapper<SysUserOnlineEntity> {

//    /**
//     * 通过会话序号查询信息
//     * 
//     * @param sessionId 会话ID
//     * @return 在线用户信息
//     */
//    public SysUserOnlineEntity selectOnlineById(String sessionId);

//    /**
//     * 通过会话序号删除信息
//     * 
//     * @param sessionId 会话ID
//     * @return 在线用户信息
//     */
//    public int deleteOnlineById(String sessionId);

//    /**
//     * 保存会话信息
//     * 
//     * @param online 会话信息
//     * @return 结果
//     */
//    public int saveOnline(SysUserOnlineEntity online);

    /**
     * 查询会话集合
     * 
     * @param userOnline 会话参数
     * @return 会话集合
     */
    public List<SysUserOnlineEntity> selectUserOnlineList(SysUserOnlineEntity userOnline);

    /**
     * 查询过期会话集合
     * 
     * @param lastAccessTime 过期时间
     * @return 会话集合
     */
    public List<SysUserOnlineEntity> selectOnlineByExpired(Date lastAccessTime);
}
