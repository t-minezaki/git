/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.Query;
import jp.learningpark.modules.sys.dao.SysUserOnlineDao;
import jp.learningpark.modules.sys.entity.SysUserOnlineEntity;
import jp.learningpark.modules.sys.service.SysUserOnlineService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("sysUserOnlineService")
@Transactional
public class SysUserOnlineServiceImpl implements SysUserOnlineService {

    @Autowired
    private SysUserOnlineDao userOnlineDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");

        IPage<SysUserOnlineEntity> page = userOnlineDao.selectPage(new Query<SysUserOnlineEntity>(params).getPage(),
                new QueryWrapper<SysUserOnlineEntity>().like(StringUtils.isNotBlank(key), "ip_addr", key));

        return new PageUtils(page);
    }

    /**
     * 通过会话序号查询信息
     * 
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    @Override
    public SysUserOnlineEntity selectById(String sessionId) {
        return userOnlineDao.selectOne(new QueryWrapper<SysUserOnlineEntity>().eq("session_id", sessionId));
    }

    /**
     * 通过会话序号删除信息
     * 
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    @Override
    public void deleteOnlineById(String sessionId) {
        //        SysUserOnlineEntity userOnline = selectById(sessionId);
        //        if (userOnline != null) {
        userOnlineDao.delete(new QueryWrapper<SysUserOnlineEntity>().eq("session_id", sessionId));
        //        }
    }

    /**
     * 通过会话序号删除信息
     * 
     * @param sessions 会话ID集合
     * @return 在线用户信息
     */
    @Override
    public void batchDeleteOnline(List<String> sessions) {
        userOnlineDao.delete(new QueryWrapper<SysUserOnlineEntity>().in("session_id", sessions));
        //        for (String sessionId : sessions) {
        ////            SysUserOnlineEntity userOnline = selectOnlineById(sessionId);
        ////            if (userOnline != null) {
        //                deleteOnlineById(sessionId);
        ////            }
        //        }
    }

    /**
     * 保存会话信息
     * 
     * @param online 会话信息
     */
    @Override
    public void saveOnline(SysUserOnlineEntity online) {
        SysUserOnlineEntity userOnline = selectById(online.getSessionId());
        if (userOnline == null) {
            userOnlineDao.insert(online);
        } else {
            userOnlineDao.update(online, new QueryWrapper<SysUserOnlineEntity>().eq("session_id", online.getSessionId()));
        }
    }

    /**
     * 查询会话集合
     * 
     * @param userOnline 在线用户
     */
    @Override
    public List<SysUserOnlineEntity> selectUserOnlineList(SysUserOnlineEntity userOnline) {
        return userOnlineDao.selectUserOnlineList(userOnline);
    }

    //    /**
    //     * 强退用户
    //     * 
    //     * @param sessionId 会话ID
    //     */
    //    @Override
    //    public void forceLogout(String sessionId) {
    //        userOnlineDao.deleteOnlineById(sessionId);
    //    }

    /**
     * 查询会话集合
     * 
     * @param expiredDate 失效日期
     */
    @Override
    public List<SysUserOnlineEntity> selectOnlineByExpired(Date expiredDate) {
        return userOnlineDao.selectOnlineByExpired(expiredDate);
    }

}
