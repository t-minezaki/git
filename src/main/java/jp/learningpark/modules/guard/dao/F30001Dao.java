/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dao;

import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstGuardEntity;
import jp.learningpark.modules.guard.dto.F30001Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * F30001保護者基本情報登録画面
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/11 : xiong: 新規<br />
 * @version 1.0R
 */
@Mapper
public interface F30001Dao {

   /**
    * コードマスタより、続柄リストを取得し
    * @return
    */
   List<MstCodDEntity> getReltnspDiv();

   /**
    * 保護者基本マスタから取得し
    * @param guardId
    * @return
    */
   MstGuardEntity getGuard(String guardId);

   /**
    * 住所マスタより、郵便番号を元に、住所情報を取得し、住所エリアに表示される
    * @param postcd
    * @return
    */
   List searchAddr(String postcd);

   /**
    *  子供個数の取得
    * @param afterUsrId
    * @return
    */
   F30001Dto stuNumber(String afterUsrId);

   Integer getCrmschId(String stuId);

   /**
    * 更新内容
    * @param entity
    * @return
    */
   Integer updateGuardInfo(@Param("entity")Map<String, Object> entity);
}
