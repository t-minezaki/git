package jp.learningpark.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.Query;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.sys.dao.SysRoleDao;
import jp.learningpark.modules.sys.entity.SysRoleEntity;
import jp.learningpark.modules.sys.service.SysMenuService;
import jp.learningpark.modules.sys.service.SysRoleMenuService;
import jp.learningpark.modules.sys.service.SysRoleService;
import jp.learningpark.modules.sys.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 角色
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:45:12
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String roleName = (String) params.get("roleName");
        Long createUserId = (Long) params.get("createUserId");

        IPage<SysRoleEntity> page = baseMapper.selectPage(new Query<SysRoleEntity>(params).getPage(),
                new QueryWrapper<SysRoleEntity>().like(StringUtils.isNotBlank(roleName), "role_name", roleName));

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRoleEntity role) {
        role.setCreateTime(DateUtils.getSysDate());
        role.setCreateUserId(ShiroUtils.getUserEntity().getId());
        baseMapper.insert(role);
//
//        //检查权限是否越权
//        checkPrems(role);

        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(SysRoleEntity role) {
        this.updateById(role);

//        //检查权限是否越权
//        checkPrems(role);

        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long[] roleIds) {
        //删除角色
        baseMapper.deleteBatchIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);
    }

    @Override
    public List<Long> queryRoleIdList(Long createUserId) {
        return baseMapper.queryRoleIdList(createUserId);
    }

//    /**
//     * 检查权限是否越权
//     */
//    private void checkPrems(SysRoleEntity role) {
//
//        //查询用户所拥有的菜单列表
//        List<SysMenuEntity> dataList = sysMenuService.getUserMenuList(role.getCreateUserId().longValue());
//        
//        List<Long> menuIdList = new ArrayList<Long>();
//        if (!menuIdList.isEmpty()) {
//            for (SysMenuEntity entity : dataList) {
//                menuIdList.add(entity.getMenuId());
//            }
//        }
//
//        //判断是否越权
//        if (!menuIdList.containsAll(role.getMenuIdList())) {
//            throw new RRException("ロール");
//        }
//    }
}
