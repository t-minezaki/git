package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F00001Dao;
import jp.learningpark.modules.manager.dto.F00001Dto;
import jp.learningpark.modules.manager.dto.F00001FunctionTypeDto;
import jp.learningpark.modules.manager.dto.F00001ManagerTypeDto;
import jp.learningpark.modules.manager.service.F00001Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.RandomAccess;

import static java.util.Collections.swap;

@Service
@Transactional
public class F00001ServiceImpl implements F00001Service {
    /**
     * リスト逆さま
     */
    private static final int REVERSE_THRESHOLD = 18;
    /**
     * F00001 Dao
     */
    @Autowired
    private F00001Dao f00001Dao;

    public static List<F00001ManagerTypeDto> reverse(List<F00001ManagerTypeDto> list) {
        int size = list.size();
        if (size < REVERSE_THRESHOLD || list instanceof RandomAccess) {
            for (int i = 0, mid = size >> 1, j = size - 1; i < mid; i++, j--)
                swap(list, i, j);
        } else {
            ListIterator fwd = list.listIterator();
            ListIterator rev = list.listIterator(size);
            for (int i = 0, mid = list.size() >> 1; i < mid; i++) {
                Object tmp = fwd.next();
                fwd.set(rev.previous());
                rev.set(tmp);
            }
        }
        return list;
    }

    /**
     * 管理者基本マスタから取得
     *
     * @param userId
     * @return
     */
    @Override
    public List<F00001Dto> getUserFunList(String userId) {
        return f00001Dao.getUserFunList(userId);
    }

    /**
     * メニューフォーマット
     *
     * @param mstFunEntityList
     * @return
     */
    @Override
    public List<F00001ManagerTypeDto> getDiv(List<F00001Dto> mstFunEntityList) {
        // 機能分類名を抽出する
        Map<String, List<F00001Dto>> functionTypeMap = new HashMap<>();
        for (F00001Dto mstFunEntity : mstFunEntityList) {

            F00001Dto f00001Dto = new F00001Dto();
            f00001Dto.setFunId(mstFunEntity.getFunId());
            f00001Dto.setName(mstFunEntity.getFunNm());
            if (mstFunEntity.getMgrFlg().equals("1")) {
                f00001Dto.setType("manager");
            }
//            if (mstFunEntity.getMentFlg().equals("1")) {
//                f00001Dto.setType("mentor");
//            }
            if (mstFunEntity.getFunDivNm().equals("")) {

                if (functionTypeMap.get(mstFunEntity.getMgrDivNm()) == null){

                    functionTypeMap.put(mstFunEntity.getMgrDivNm(), new ArrayList<F00001Dto>());
                }
            }else {

                if (functionTypeMap.get(mstFunEntity.getFunDivNm()) == null){

                    functionTypeMap.put(mstFunEntity.getFunDivNm(), new ArrayList<F00001Dto>());
                }
            }
            if (mstFunEntity.getFunDivNm().equals("")) {

                functionTypeMap.get(mstFunEntity.getMgrDivNm()).add(f00001Dto);
            } else {
                functionTypeMap.get(mstFunEntity.getFunDivNm()).add(f00001Dto);
            }
        }

        List<F00001FunctionTypeDto> functionTypeList = new ArrayList<>();
        // Mapオブジェクト
        for (Map.Entry<String, List<F00001Dto>> entry : functionTypeMap.entrySet()) {

            F00001FunctionTypeDto f00001FunctionTypeDto = new F00001FunctionTypeDto();
            f00001FunctionTypeDto.setName(entry.getKey());
            if (StringUtils.equals(entry.getValue().get(0).getType(), "mentor")) {
                f00001FunctionTypeDto.setFunId(entry.getValue().get(0).getFunId());
            }
            f00001FunctionTypeDto.setFunctionList(entry.getValue());

            functionTypeList.add(f00001FunctionTypeDto);
        }
        // 管理カテゴリ名を抽出する
        Map<String, List<String>> managerTypeNameMap = new HashMap<>();

        List<String> mgrNameList = new ArrayList<>();
        for (F00001Dto mstFunEntity : mstFunEntityList) {

            if (managerTypeNameMap.get(mstFunEntity.getMgrDivNm()) == null) {
                managerTypeNameMap.put(mstFunEntity.getMgrDivNm(), new ArrayList<String>());
                mgrNameList.add(mstFunEntity.getMgrDivNm());
            }

            if (mstFunEntity.getFunDivNm().equals("")) {

                if (functionTypeMap.containsKey(mstFunEntity.getMgrDivNm())) {

                    managerTypeNameMap.get(mstFunEntity.getMgrDivNm()).add(mstFunEntity.getMgrDivNm());
                    functionTypeMap.remove(mstFunEntity.getMgrDivNm());
                }
            }else {

                if (functionTypeMap.containsKey(mstFunEntity.getFunDivNm())) {

                    managerTypeNameMap.get(mstFunEntity.getMgrDivNm()).add(mstFunEntity.getFunDivNm());
                    functionTypeMap.remove(mstFunEntity.getFunDivNm());
                }
            }
        }

        List<F00001ManagerTypeDto> f00001ManagerTypeDtoList = new ArrayList<>();
        // Mapオブジェクト
        for (String name : mgrNameList) {

            F00001ManagerTypeDto f00001ManagerTypeDto = new F00001ManagerTypeDto();
            List<String> funNameList = managerTypeNameMap.get(name);
            f00001ManagerTypeDto.setName(name);
            List<F00001FunctionTypeDto> f00001FunctionTypeDtoList = new ArrayList<>();

            for (String funname : funNameList) {

                for (F00001FunctionTypeDto f00001FunctionTypeDto : functionTypeList) {

                    if (f00001FunctionTypeDto.getName().equals(funname)) {

                        f00001FunctionTypeDtoList.add(f00001FunctionTypeDto);
                    }
                }
            }
            f00001ManagerTypeDto.setFunctionTypeList(f00001FunctionTypeDtoList);
            f00001ManagerTypeDtoList.add(f00001ManagerTypeDto);
        }
        return f00001ManagerTypeDtoList;
    }

    /**
     * 上層組織リスト取得
     *
     * @param orgId
     * @param brandCd
     * @return
     */
    @Override
    public List<F00001Dto> getUpLvOrgList(String orgId, String brandCd) {
        return f00001Dao.getUpLvOrgList(orgId, brandCd);
    }

    /**
     * 組織別機能一覧取得処理
     *
     * @param orgIdList
     * @return
     */
    @Override
    public List<F00001Dto> getOrgFunList(String orgIdList,String roleDiv) {
        return f00001Dao.getOrgFunList(orgIdList,roleDiv);
    }

    /**
     * 機能マスタから全機能一覧を取得する
     *
     * @return
     */
    @Override
    public List<F00001Dto> getAllFunList(String roleDiv) {
        return f00001Dao.getAllFunList(roleDiv);
    }

    @Override
    public List<F00001Dto> getThisOrgId(String brandCd, String afterUsrId,String manaFlag,String gid,String tchCd,String gidPk) {
        return f00001Dao.getThisOrgId(brandCd,afterUsrId, manaFlag, gid, tchCd, gidPk);
    }
}
