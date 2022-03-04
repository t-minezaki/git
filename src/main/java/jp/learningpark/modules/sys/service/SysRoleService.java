package jp.learningpark.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.modules.sys.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 角色
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:52
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);

    /**
     * ロール登録処理
     */
    void saveRole(SysRoleEntity role);

    /**
     * ロール更新処理
     */
    void updateRole(SysRoleEntity role);

    /**
     * ロール削除処理
     */
    void deleteRole(Long[] roleIds);

}
