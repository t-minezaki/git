/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.common.entity.MstCrmschLearnPrdEntity;
import jp.learningpark.modules.common.service.MstFunService;
import jp.learningpark.modules.manager.dao.F20001Dao;
import jp.learningpark.modules.manager.dto.F20001Dto;
import jp.learningpark.modules.manager.service.F20001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>F20001_生徒一覧画面 ServiceImpl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/05 : gong: 新規<br />
 * @version 1.0
 */
@Service
public class F20001ServiceImpl implements F20001Service {
    /**
     * F20001_生徒一覧画面 Dao
     */
    @Autowired
    private F20001Dao f20001Dao;
    @Autowired
    private MstFunService mstFunService;

    /**
     * <p>生徒情報を取得し、一覧画面に表示する</p>
     *
     * @param map 条件
     * @return
     */
    @Override
    public List<F20001Dto> getInfo(Map<String, Object> map) {
        return f20001Dao.selectInfo(map);
    }
    @Override
    public List<F20001Dto> getManagerStu(Map<String, Object> map) {
        return f20001Dao.getManagerStu(map);
    }

    /**
     * <p>塾学習期間IDの取得</p>
     *
     * @param orgId 塾ID
     * @param stuId 生徒ID
     * @return
     */
    @Override
    public MstCrmschLearnPrdEntity getCrmLearnPrdId(String orgId, String stuId) {
        return f20001Dao.selectCrmLearnPrdId(orgId, stuId);
    }

    /**
     * <p>ログインユーザーの権利を取得する</p>
     *
     * @param userId ログインユーザーID
     * @return
     */
//    @Override
//    public List<String> getPermission(String userId) {
//        List<String> permissionList = f20001Dao.getPermissionFromMstUserNmFunList(userId);
//        String roleDiv = ShiroUtils.getUserEntity().getRoleDiv().trim();
//        List<MstFunEntity> mstFunEntityList = new ArrayList<>();
//        if (permissionList == null || permissionList.size() == 0) {
//            permissionList = f20001Dao.getPermissionFromMstOrgFunList(userId);
//        }
//        if (permissionList == null || permissionList.size() == 0) {
//            String[] funList = {"F20002","F20003","F20006","F20008","F20010"};
//            if (StringUtils.equals("1",roleDiv)){
//                mstFunEntityList = mstFunService.list(new QueryWrapper<MstFunEntity>().in("fun_id",funList).eq("del_flg",0).last("and (mgr_flg = '1' or ment_flg = '1')"));
//            }else if (StringUtils.equals("2",roleDiv)){
//                mstFunEntityList = mstFunService.list(new QueryWrapper<MstFunEntity>().in("fun_id",funList).eq("del_flg",0).last("and ( ment_flg = '1')"));
//            }
//            for (MstFunEntity dto:mstFunEntityList){
//                permissionList.add(dto.getFunId());
//            }
//        }
//        return permissionList;
//    }
}
