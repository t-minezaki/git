package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.common.entity.MstFunEntity;
import jp.learningpark.modules.manager.dao.F40010Dao;
import jp.learningpark.modules.manager.dto.F40010DspDto;
import jp.learningpark.modules.manager.dto.F40010Dto;
import jp.learningpark.modules.manager.dto.F40010FunctionTypeDto;
import jp.learningpark.modules.manager.dto.F40010ManagerTypeDto;
import jp.learningpark.modules.manager.service.F40010Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.RandomAccess;

import static java.util.Collections.swap;

@Service
public class F40010ServiceImpl implements F40010Service {

    /**
     * f40010Dao
     */
    @Autowired
    F40010Dao f40010Dao;

    /**
     * 木構造を取得する
     * @param mstFunEntityList
     * @return
     */
    @Override
    public List<F40010ManagerTypeDto> getDiv(List<MstFunEntity> mstFunEntityList) {

        // 機能分類名を抽出する
        Map<String, List<F40010Dto>> functionTypeMap = new HashMap<>();
        for (MstFunEntity mstFunEntity : mstFunEntityList) {

            F40010Dto f40010Dto = new F40010Dto();
            f40010Dto.setId(mstFunEntity.getId());
            f40010Dto.setName(mstFunEntity.getFunNm());
            if (mstFunEntity.getMgrFlg().equals("1")) {
                f40010Dto.setType("manager");
            }
            if (mstFunEntity.getMentFlg().equals("1")) {
                f40010Dto.setType("mentor");
            }
            if (mstFunEntity.getMgrFlg().equals("1") && mstFunEntity.getMentFlg().equals("1")) {
                f40010Dto.setType("manager"+" "+"mentor");
            }
            if (mstFunEntity.getFunDivNm().equals("")) {

                if (functionTypeMap.get(mstFunEntity.getMgrDivNm()) == null){

                    functionTypeMap.put(mstFunEntity.getMgrDivNm(), new ArrayList<F40010Dto>());
                }
            }else {

                if (functionTypeMap.get(mstFunEntity.getFunDivNm()) == null){

                    functionTypeMap.put(mstFunEntity.getFunDivNm(), new ArrayList<F40010Dto>());
                }
            }
            if (mstFunEntity.getFunDivNm().equals("")) {

                functionTypeMap.get(mstFunEntity.getMgrDivNm()).add(f40010Dto);
            } else {
                functionTypeMap.get(mstFunEntity.getFunDivNm()).add(f40010Dto);
            }
        }

        List<F40010FunctionTypeDto> functionTypeList = new ArrayList<>();

        // Mapオブジェクト
        for (Map.Entry<String, List<F40010Dto>> entry : functionTypeMap.entrySet()) {

            F40010FunctionTypeDto f40010FunctionTypeDto = new F40010FunctionTypeDto();
            f40010FunctionTypeDto.setName(entry.getKey());
            f40010FunctionTypeDto.setFunctionList(entry.getValue());

            functionTypeList.add(f40010FunctionTypeDto);
        }

        // 管理カテゴリ名を抽出する
        Map<String, List<String>> managerTypeNameMap = new HashMap<>();

        List<String> mgrNameList = new ArrayList<>();
        for (MstFunEntity mstFunEntity : mstFunEntityList) {

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

        List<F40010ManagerTypeDto> f40010ManagerTypeDtoList = new ArrayList<>();

        for (String name : mgrNameList) {

            F40010ManagerTypeDto f40010ManagerTypeDto = new F40010ManagerTypeDto();
            List<String> funNameList = managerTypeNameMap.get(name);
            f40010ManagerTypeDto.setName(name);
            List<F40010FunctionTypeDto> f40010FunctionTypeDtoList = new ArrayList<>();

            for (String funname : funNameList) {

                for (F40010FunctionTypeDto f40010FunctionTypeDto : functionTypeList) {

                    if (f40010FunctionTypeDto.getName().equals(funname)) {

                        f40010FunctionTypeDtoList.add(f40010FunctionTypeDto);
                    }
                }
            }
            f40010ManagerTypeDto.setFunctionTypeList(f40010FunctionTypeDtoList);
            f40010ManagerTypeDtoList.add(f40010ManagerTypeDto);
        }

        return f40010ManagerTypeDtoList;
    }

    /**
     * 組織の機能をリセットする
     * @param orgId
     * @param managerOrMentor
     * @param updUsrId
     * @param updDatime
     * @return
     */
    @Override
    public Integer updateMstOrgFunList(String orgId, Integer managerOrMentor, String updUsrId, Timestamp updDatime) {
        return f40010Dao.updateMstOrgFunList(orgId, managerOrMentor, updUsrId, updDatime);
    }

    /**
     * その組織のユーザを取得する
     * @param orgId 組織ID
     * @param roleDiv
     * @return
     */
    @Override
    public List<F40010DspDto> selectMst(String orgId, String roleDiv) {
        return f40010Dao.selectMst(orgId,roleDiv);
    }

    /**
     * リスト逆さま
     */
    private static final int REVERSE_THRESHOLD        =   18;
    public static List<F40010ManagerTypeDto> reverse(List<F40010ManagerTypeDto> list) {
        int size = list.size();
        if (size < REVERSE_THRESHOLD || list instanceof RandomAccess) {
            for (int i=0, mid=size>>1, j=size-1; i<mid; i++, j--)
                swap(list, i, j);
        } else {
            ListIterator fwd = list.listIterator();
            ListIterator rev = list.listIterator(size);
            for (int i=0, mid=list.size()>>1; i<mid; i++) {
                Object tmp = fwd.next();
                fwd.set(rev.previous());
                rev.set(tmp);
            }
        }
        return list;
    }
}
