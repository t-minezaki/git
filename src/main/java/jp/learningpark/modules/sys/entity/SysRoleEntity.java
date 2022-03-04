package jp.learningpark.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ロール
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:27:38
 */
@TableName("sys_role")
public class SysRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * ロールID
	 */
	@TableId(type = IdType.AUTO)
	@NotNull
	private Long roleId;

	/**
	 * ロール名
	 */
	private String roleName;

	/**
	 * 備考
	 */
	private String remark;
	
	/**
	 * 作成者ID
	 */
	private Integer createUserId;

	@TableField(exist=false)
	private List<Long> menuIdList;
	
	/**
	 * 作成時間
	 */
	private Date createTime;

	/**
	 * 設定：
	 * @param roleId 
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * 取得：
	 * @return Long
	 */
	public Long getRoleId() {
		return roleId;
	}
	
	/**
	 * 設定：ロール名
	 * @param roleName ロール名
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 取得：ロール名
	 * @return String
	 */
	public String getRoleName() {
		return roleName;
	}
	
	/**
	 * 設定：備考
	 * @param remark 備考
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 取得：備考
	 * @return String
	 */
	public String getRemark() {
		return remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<Long> getMenuIdList() {
		return menuIdList;
	}

	public void setMenuIdList(List<Long> menuIdList) {
		this.menuIdList = menuIdList;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	
}
