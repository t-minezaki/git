/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.*;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.manager.dao.F00004Dao;
import jp.learningpark.modules.manager.dao.F00007Dao;
import jp.learningpark.modules.manager.dto.*;
import jp.learningpark.modules.manager.service.F00004Service;
import jp.learningpark.modules.sys.dao.SysRoleDao;
import jp.learningpark.modules.sys.dao.SysUserRoleDao;
import jp.learningpark.modules.sys.entity.SysRoleEntity;
import jp.learningpark.modules.sys.entity.SysUserRoleEntity;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>F00004 利用者基本情報設定・修正 ServiceImpl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/25 : gong: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F00004ServiceImpl implements F00004Service {
    /**
     * ユーザ基本マスタ Dao
     */
    @Autowired
    MstUsrDao mstUsrDao;

    /**
     * 管理者基本マスタ Dao
     */
    @Autowired
    MstManagerDao mstManagerDao;

    /**
     * 先生基本マスタ Dao
     */
    @Autowired
    MstMentorDao mstMentorDao;

    /**
     * 保護者基本マスタ Dao
     */
    @Autowired
    MstGuardDao mstGuardDao;

    /**
     * 生徒基本マスタ Dao
     */
    @Autowired
    MstStuDao mstStuDao;

    /**
     * 利用者基本情報設定・修正 Dao
     */
    @Autowired
    F00004Dao f00004Dao;

    /**
     * 採番マスタ Dao
     */
    @Autowired
    MstNumassDao mstNumassDao;

    /**
     * 角色 dao
     */
    @Autowired
    SysRoleDao sysRoleDao;

    /**
     * ユーザ角色 Dao
     */
    @Autowired
    SysUserRoleDao sysUserRoleDao;

    /**
     * mst_usr_firt_pw Dao
     */
    @Autowired
    MstUsrFirtPwDao mstUsrFirtPwDao;

    /**
     * f00007Dao
     */
    @Autowired
    private F00007Dao f00007Dao;

    /**
     * mstOrgDao
     */
    @Autowired
    private MstOrgDao mstOrgDao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * <p>エクスポート処理、管理者が選択した場合</p>
     *
     * @param orgId 組織ID
     * @return
     */
    @Override
    public List<F00004ManagerDto> getExcelDataOfManager(String orgId) {
        return f00004Dao.selectExcelDataOfManager(orgId);
    }

    /**
     * <p>エクスポート処理、先生が選択した場合</p>
     *
     * @param orgId 組織ID
     * @return
     */
    @Override
    public List<F00004MentorDto> getExcelDataOfMentor(String orgId) {
        return f00004Dao.selectExcelDataOfMentor(orgId);
    }

    /**
     * <p>エクスポート処理、保護者が選択した場合</p>
     *
     * @param orgId 組織ID
     * @return
     */
    @Override
    public List<F00004GuardDto> getExcelDataOfGuard(String orgId) {
        return f00004Dao.selectExcelDataOfGuard(orgId);
    }

    /**
     * <p>エクスポート処理、生徒が選択した場合</p>
     *
     * @param orgId 組織ID
     * @return
     */
    @Override
    public List<F00004StuDto> getExcelDataOfStu(String orgId) {
        return f00004Dao.selectExcelDataOfStu(orgId);
    }

    /**
     * <p>生徒・保護者の依存関係情報エクスポート処理、生徒が選択した場合</p>
     *
     * @param orgId 組織ID
     * @return
     */
    @Override
    public List<F00004StuDto> getExcelDataOfStuWithGuard(String orgId) {
        return f00004Dao.selectExcelDataOfStuWithGuard(orgId);
    }

    /**
     * @param r 　行
     * @param maxIdOfManager 　管理者ID採番
     * @param orgIdList 　組織ID
     * @param i 　セル
     * @param brandCd 　ブランドコード
     * @param usrId 　ユーザーID
     * @param roleId 　ルールID
     * @param sheet 　シート
     * @param div 　エラーとする or 上書きする
     */
    public int importManager(Row r, int maxIdOfManager, List<String> orgIdList, int i, String brandCd, String usrId, Long roleId, Sheet sheet, Integer div, String afterUsrId) {
        MstUsrEntity mstUsrEntity;
        MstUsrFirtPwEntity mstUsrFirtPwEntity;
        SysUserRoleEntity sysUserRoleEntity;
        //新規
        if (r.getCell(0) == null || StringUtils.isEmpty(r.getCell(0).toString())) {
          for (int j = 0; j < orgIdList.size(); j++) {
              maxIdOfManager++;
              mstUsrEntity = new MstUsrEntity();
              mstUsrFirtPwEntity = new MstUsrFirtPwEntity();
              //ユーザID
              mstUsrEntity.setUsrId("a" + maxIdOfManager);
              //ユーザID
              mstUsrFirtPwEntity.setUsrId("a" + maxIdOfManager);
              //ユーザIDのAsciiコードを62binaryに変更して、
              //ユーザログインPW
              if (r.getCell(2) != null && !StringUtils.isEmpty(r.getCell(2).toString())) {
                  if (StringUtils.equals(r.getCell(2).getCellType().toString(), "NUMERIC")) {
                      r.getCell(2).setCellType(CellType.STRING);
                  }
                  if (StringUtils.PWDCheck(r.getCell(2).toString())) {
                      mstUsrEntity.setUsrPassword(ShiroUtils.sha256(r.getCell(2).toString(), "a" + maxIdOfManager));
                      mstUsrFirtPwEntity.setUsrFstPassword(ShiroUtils.sha256(r.getCell(2).toString(), "a" + maxIdOfManager));
                  } else {
                      throw new RRException(MessageUtils.getMessage("MSGCOMN0162", "パスワード"));
                  }
              } else {
                  mstUsrEntity.setUsrPassword(ShiroUtils.sha256(ShiroUtils.stringToAscii("a" + maxIdOfManager), "a" + maxIdOfManager));
                  mstUsrFirtPwEntity.setUsrFstPassword(ShiroUtils.sha256(ShiroUtils.stringToAscii("a" + maxIdOfManager), "a" + maxIdOfManager));
              }

              //初回登録フラグ
              mstUsrEntity.setFstLoginFlg("0");// 2020/04/16 mod by zhangpuao 0->1; 20200731 wen 1 -> 0
              //ユーザ名
              String usrNm = "";
              if (r.getCell(3) != null && !StringUtils.isEmpty(r.getCell(3).toString())) {
                  r.getCell(3).setCellType(CellType.STRING);
                  usrNm += r.getCell(3).toString() + " ";
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(3).toString()+"は必須入力項目です。");
              }
              if (r.getCell(4) != null && !StringUtils.isEmpty(r.getCell(4).toString())) {
                  r.getCell(4).setCellType(CellType.STRING);
                  usrNm += r.getCell(4).toString();
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(4).toString()+"は必須入力項目です。");
              }
              mstUsrEntity.setUsrNm(usrNm);
              //ロール区分
              mstUsrEntity.setRoleDiv("1");
              //ロール区分
              mstUsrFirtPwEntity.setRoleDiv("1");
              mstUsrFirtPwEntity.setSysDiv("0");
              //組織ID
              mstUsrEntity.setOrgId(orgIdList.get(j));
              //PW更新フラグ
              if (r.getCell(2) != null && !StringUtils.isEmpty(r.getCell(2).toString())) {
                  mstUsrEntity.setPwUpFlg("2");
              } else {
                  mstUsrEntity.setPwUpFlg("0");
              }
              //ユーザステータス
              mstUsrEntity.setUsrSts("1");
              //特殊権限フラグ
              mstUsrEntity.setSpecAuthFlg("0");
              //変更後ユーザーID
              mstUsrEntity.setAfterUsrId(afterUsrId);
              //所属組織フラグ
              mstUsrEntity.setOwnerOrgFlg(j==0?"1":"0");
              //作成日時
              mstUsrEntity.setCretDatime(DateUtils.getSysTimestamp());
              //作成日時
              mstUsrFirtPwEntity.setCretDatime(DateUtils.getSysTimestamp());
              //作成ユーザＩＤ
              mstUsrEntity.setCretUsrId(usrId);
              //作成ユーザＩＤ
              mstUsrFirtPwEntity.setCretUsrId(usrId);
              //エラー回数
              mstUsrEntity.setErrorCount(0);
              //ロックフラグ
              mstUsrEntity.setLockFlg("0");
              //GIDフラグ
              if (r.getCell(10) != null && !StringUtils.isEmpty(r.getCell(10).toString())) {
                  if (StringUtils.equals(r.getCell(10).getCellType().toString(), "STRING")) {
                      mstUsrEntity.setGidFlg(r.getCell(10).toString());
                  } else {
                      r.getCell(10).setCellType(CellType.STRING);
                      mstUsrEntity.setGidFlg(r.getCell(10).toString());
                  }
              }
              //組織共用キー
              mstUsrEntity.setOrgCommKey(orgIdList.get(j) + "_key");
              //更新日時
              mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
              //更新日時
              mstUsrFirtPwEntity.setUpdDatime(DateUtils.getSysTimestamp());
              //更新ユーザＩＤ
              mstUsrEntity.setUpdUsrId(usrId);
              //更新ユーザＩＤ
              mstUsrFirtPwEntity.setUpdUsrId(usrId);
              //del_flg
              mstUsrEntity.setDelFlg(0);
              //del_flg
              mstUsrFirtPwEntity.setDelFlg(0);
              try {
                  mstUsrDao.insert(mstUsrEntity);
                  mstUsrFirtPwDao.insert(mstUsrFirtPwEntity);
              } catch (Exception e) {
                  logger.error(e.getMessage());
                  throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
              }

              //角色の追加
              sysUserRoleEntity = new SysUserRoleEntity();
              sysUserRoleEntity.setRoleId(roleId);
              sysUserRoleEntity.setUserId(mstUsrEntity.getId().longValue());
              sysUserRoleDao.insert(sysUserRoleEntity);

              //管理者基本マスタ
              MstManagerEntity mstManagerEntity = new MstManagerEntity();
              //管理者ID
              mstManagerEntity.setMgrId("a" + maxIdOfManager);
              //メールアドレス
              if (r.getCell(7) != null) {
                  if (StringUtils.equals(r.getCell(7).getCellType().toString(), "STRING")) {
                      mstManagerEntity.setMailad(r.getCell(7).getStringCellValue());
                  } else {
                      mstManagerEntity.setMailad(r.getCell(7).getNumericCellValue() + "");
                  }
              }

              //姓名_姓
              if (r.getCell(3) != null && !StringUtils.isEmpty(r.getCell(3).toString())) {
                  mstManagerEntity.setFlnmNm(r.getCell(3).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(3).toString()+"は必須入力項目です。");
              }
              //姓名_名
              if (r.getCell(4) != null && !StringUtils.isEmpty(r.getCell(4).toString())) {
                  mstManagerEntity.setFlnmLnm(r.getCell(4).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(4).toString()+"は必須入力項目です。");
              }
              //姓名_カナ姓
              if (r.getCell(5) != null && !StringUtils.isEmpty(r.getCell(5).toString())) {
                  r.getCell(5).setCellType(CellType.STRING);
                  mstManagerEntity.setFlnmKnNm(r.getCell(5).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(5).toString()+"は必須入力項目です。");
              }
              //姓名_カナ名
              if (r.getCell(6) != null && !StringUtils.isEmpty(r.getCell(6).toString())) {
                  r.getCell(6).setCellType(CellType.STRING);
                  mstManagerEntity.setFlnmKnLnm(r.getCell(6).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(6).toString()+"は必須入力項目です。");
              }
              //電話番号
              if (r.getCell(8) != null) {
                  if (StringUtils.equals(r.getCell(8).getCellType().toString(), "STRING")) {
                      mstManagerEntity.setTelnum(r.getCell(8).getStringCellValue());
                  } else {
                      r.getCell(8).setCellType(CellType.STRING);
                      mstManagerEntity.setTelnum(r.getCell(8).toString());
                  }
              }
              //作成日時
              mstManagerEntity.setCretDatime(DateUtils.getSysTimestamp());
              //作成ユーザＩＤ
              mstManagerEntity.setCretUsrId(usrId);
              //更新日時
              mstManagerEntity.setUpdDatime(DateUtils.getSysTimestamp());
              //更新ユーザＩＤ
              mstManagerEntity.setUpdUsrId(usrId);
              //del_flg
              mstManagerEntity.setDelFlg(0);
              try {
                  mstManagerDao.insert(mstManagerEntity);
              } catch (Exception e) {
                  logger.error(e.getMessage());
                  throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
              }
          }

        }
        //更新
        else {
          for (int j = 0; j < orgIdList.size(); j++) {
              if (StringUtils.equals(j==0?"1":"0", "0")){
                  return maxIdOfManager;
              }
              //(A列)システム発番ID＜＞空白　かつ　(B列)ニックID＝空白の場合
              if (r.getCell(1) == null || StringUtils.isEmpty(r.getCell(1).toString())) {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(1).toString()+"は必須入力項目です。");
              }
              //システム発番のユーザーIDの存在チェック
              MstUsrEntity mstUsrEntity1 = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().and(w->w.eq("usr_id", r.getCell(0).toString())));
              if (mstUsrEntity1 == null) {
                  throw new RRException(MessageUtils.getMessage("MSGCOMN0064", (i + 1) + ""));
              }
              //検索結果件数＞0　かつ　④で　エラーとする　を選択された場合、
              if ( div == 1) {
                  throw new RRException(MessageUtils.getMessage("MSGCOMN0065", (i + 1) + "", "システム発番のユーザーID"));
              }
              //ユーザマスタ
              MstUsrEntity updateMstUsrEntity = new MstUsrEntity();
              //ユーザ名
              String usrNm = "";
              if (r.getCell(3) != null && !StringUtils.isEmpty(r.getCell(3).toString())) {
                  r.getCell(3).setCellType(CellType.STRING);
                  usrNm += r.getCell(3).toString()  + " ";
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(3).toString()+"は必須入力項目です。");
              }
              if (r.getCell(4) != null && !StringUtils.isEmpty(r.getCell(4).toString())) {
                  r.getCell(4).setCellType(CellType.STRING);
                  usrNm += r.getCell(4).toString();
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(4).toString()+"は必須入力項目です。");
              }
              updateMstUsrEntity.setUsrNm(usrNm);
              //変更後ユーザーID
              updateMstUsrEntity.setAfterUsrId(afterUsrId);
              //ユーザログインPW
              String password = "";
              if (r.getCell(2) != null && !StringUtils.isEmpty(r.getCell(2).toString())) {
                  if (StringUtils.equals(r.getCell(2).getCellType().toString(), "NUMERIC")) {
                      r.getCell(2).setCellType(CellType.STRING);
                  }
                  if (StringUtils.equals(r.getCell(0).getCellType().toString(), "NUMERIC")) {
                      r.getCell(0).setCellType(CellType.STRING);
                  }
                  //PW更新フラグ
                  updateMstUsrEntity.setPwUpFlg("2");
                  if (StringUtils.PWDCheck(r.getCell(2).toString())) {
                      password = r.getCell(2).toString();
                  } else {
                      throw new RRException(MessageUtils.getMessage("MSGCOMN0162", "パスワード"));
                  }
              }
              //GIDフラグ
              if (r.getCell(10) != null && !StringUtils.isEmpty(r.getCell(10).toString())) {
                  if (StringUtils.equals(r.getCell(10).getCellType().toString(), "STRING")) {
                      updateMstUsrEntity.setGidFlg(r.getCell(10).toString());
                  } else {
                      r.getCell(10).setCellType(CellType.STRING);
                      updateMstUsrEntity.setGidFlg(r.getCell(10).toString());
                  }
              }
              //更新日時
              updateMstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
              //更新ユーザＩＤ
              updateMstUsrEntity.setUpdUsrId(usrId);
              List<MstUsrEntity> mstUsrEntityList = mstUsrDao.selectList(new QueryWrapper<MstUsrEntity>().eq("del_flg", 0).eq("role_div", "1")
                      .eq("usr_sts", "1").eq("after_usr_id", mstUsrEntity1.getAfterUsrId()));
              for (MstUsrEntity usrEntity : mstUsrEntityList) {
                  if (!StringUtils.isEmpty(password)) {
                      updateMstUsrEntity.setUsrPassword(ShiroUtils.sha256(password, usrEntity.getUsrId()));
                  }
                  mstUsrDao.update(
                          updateMstUsrEntity,
                          new QueryWrapper<MstUsrEntity>().and(w->w.eq("usr_id", usrEntity.getUsrId()).eq("role_div", "1").eq("usr_sts", "1").eq("del_flg", 0)));
              }

              //管理者基本マスタ
              MstManagerEntity updateMstManagerEntity = new MstManagerEntity();
              //メールアドレス
              if (r.getCell(7) != null) {
                  if (StringUtils.equals(r.getCell(7).getCellType().toString(), "STRING")) {
                      updateMstManagerEntity.setMailad(r.getCell(7).getStringCellValue());
                  } else {
                      r.getCell(7).setCellType(CellType.STRING);
                      updateMstManagerEntity.setMailad(r.getCell(7).toString());
                  }
              }
              //姓名_姓
              if (r.getCell(3) != null && !StringUtils.isEmpty(r.getCell(3).toString())) {
                  updateMstManagerEntity.setFlnmNm(r.getCell(3).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(3).toString()+"は必須入力項目です。");
              }
              //姓名_名
              if (r.getCell(4) != null && !StringUtils.isEmpty(r.getCell(4).toString())) {
                  updateMstManagerEntity.setFlnmLnm(r.getCell(4).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(4).toString()+"は必須入力項目です。");
              }
              //姓名_カナ姓
              if (r.getCell(5) != null && !StringUtils.isEmpty(r.getCell(5).toString())) {
                  r.getCell(5).setCellType(CellType.STRING);
                  updateMstManagerEntity.setFlnmKnNm(r.getCell(5).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(5).toString()+"は必須入力項目です。");
              }
              //姓名_カナ名
              if (r.getCell(6) != null && !StringUtils.isEmpty(r.getCell(6).toString())) {
                  r.getCell(6).setCellType(CellType.STRING);
                  updateMstManagerEntity.setFlnmKnLnm(r.getCell(6).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(6).toString()+"は必須入力項目です。");
              }
              //電話番号
              if (r.getCell(8) != null) {
                  if (StringUtils.equals(r.getCell(8).getCellType().toString(), "STRING")) {
                      updateMstManagerEntity.setTelnum(r.getCell(8).getStringCellValue());
                  } else {
                      r.getCell(8).setCellType(CellType.STRING);
                      updateMstManagerEntity.setTelnum(r.getCell(8).toString());
                  }
              }
              //更新日時
              updateMstManagerEntity.setUpdDatime(DateUtils.getSysTimestamp());
              //更新ユーザＩＤ
              updateMstManagerEntity.setUpdUsrId(usrId);
              try {
                  for (MstUsrEntity usrEntity : mstUsrEntityList) {
                      mstManagerDao.update(updateMstManagerEntity, new QueryWrapper<MstManagerEntity>().and(w->w.eq("mgr_id", usrEntity.getUsrId())));
                  }
              } catch (Exception e) {
                  logger.error(e.getMessage());
                  throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
              }
          }
        }
        return maxIdOfManager;
    }

    /**
     * @param r 　行
     * @param maxIdOfMentor 　先生ID採番
     * @param orgIdList 　組織ID
     * @param i 　セル
     * @param brandCd 　ブランドコード
     * @param usrId 　ユーザーID
     * @param roleId 　ルールID
     * @param sheet 　シート
     * @param div 　エラーとする or 上書きする
     */
    public int importMentor(Row r, int maxIdOfMentor, List<String> orgIdList, int i, String brandCd, String usrId, Long roleId, Sheet sheet, Integer div, String afterUsrId) {
        MstUsrEntity mstUsrEntity;
        MstUsrFirtPwEntity mstUsrFirtPwEntity;
        SysUserRoleEntity sysUserRoleEntity;
        //新規
        if (r.getCell(0) == null || StringUtils.isEmpty(r.getCell(0).toString())) {
          for (int j = 0; j < orgIdList.size(); j++) {
              maxIdOfMentor++;
              mstUsrEntity = new MstUsrEntity();
              mstUsrFirtPwEntity = new MstUsrFirtPwEntity();
              //ユーザID
              mstUsrEntity.setUsrId("m" + maxIdOfMentor);
              //ユーザID
              mstUsrFirtPwEntity.setUsrId("m" + maxIdOfMentor);
              //ユーザIDのAsciiコードを62binaryに変更して、
              //ユーザログインPW
              if (r.getCell(2) != null && !StringUtils.isEmpty(r.getCell(2).toString())) {
                  if (StringUtils.equals(r.getCell(2).getCellType().toString(), "NUMERIC")) {
                      r.getCell(2).setCellType(CellType.STRING);
                  }
                  if (StringUtils.PWDCheck(r.getCell(2).toString())) {
                      mstUsrEntity.setUsrPassword(ShiroUtils.sha256(r.getCell(2).toString(), "m" + maxIdOfMentor));
                      mstUsrFirtPwEntity.setUsrFstPassword(ShiroUtils.sha256(r.getCell(2).toString(), "m" + maxIdOfMentor));
                  } else {
                      throw new RRException(MessageUtils.getMessage("MSGCOMN0162", "パスワード"));
                  }
              } else {
                  mstUsrEntity.setUsrPassword(ShiroUtils.sha256(ShiroUtils.stringToAscii("m" + maxIdOfMentor), "m" + maxIdOfMentor));
                  mstUsrFirtPwEntity.setUsrFstPassword(ShiroUtils.sha256(ShiroUtils.stringToAscii("m" + maxIdOfMentor), "m" + maxIdOfMentor));
              }
              //初回登録フラグ
              mstUsrEntity.setFstLoginFlg("0");// 2020/04/16 mod by zhangpuao 0->1;20200731 wen 1 -> 0
              //ユーザ名
              String usrNm = "";
              if (r.getCell(3) != null && !StringUtils.isEmpty(r.getCell(3).toString())) {
                  r.getCell(3).setCellType(CellType.STRING);
                  usrNm += r.getCell(3).toString() + " ";
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(3).toString()+"は必須入力項目です。");
              }
              if (r.getCell(4) != null && !StringUtils.isEmpty(r.getCell(4).toString())) {
                  r.getCell(4).setCellType(CellType.STRING);
                  usrNm += r.getCell(4).toString();
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(4).toString()+"は必須入力項目です。");
              }
              mstUsrEntity.setUsrNm(usrNm);
              //ロール区分
              mstUsrEntity.setRoleDiv("2");
              //ロール区分
              mstUsrFirtPwEntity.setRoleDiv("2");
              mstUsrFirtPwEntity.setSysDiv("0");
              //組織ID
              mstUsrEntity.setOrgId(orgIdList.get(j));
              //PW更新フラグ
              if (r.getCell(2) != null && !StringUtils.isEmpty(r.getCell(2).toString())) {
                  mstUsrEntity.setPwUpFlg("2");
              } else {
                  mstUsrEntity.setPwUpFlg("0");
              }

              //ユーザステータス
              mstUsrEntity.setUsrSts("1");
              //特殊権限フラグ
              mstUsrEntity.setSpecAuthFlg("0");
              //変更後ユーザーID
              mstUsrEntity.setAfterUsrId(afterUsrId);
              //所属組織フラグ
              mstUsrEntity.setOwnerOrgFlg(j==0?"1":"0");
              //エラー回数
              mstUsrEntity.setErrorCount(0);
              //ロックフラグ
              mstUsrEntity.setLockFlg("0");
              //GIDフラグ
              if (r.getCell(10) != null && !StringUtils.isEmpty(r.getCell(10).toString())) {
                  if (StringUtils.equals(r.getCell(10).getCellType().toString(), "STRING")) {
                      mstUsrEntity.setGidFlg(r.getCell(10).toString());
                  } else {
                      r.getCell(10).setCellType(CellType.STRING);
                      mstUsrEntity.setGidFlg(r.getCell(10).toString());
                  }
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(10).toString()+"は必須入力項目です。");
              }
              //組織共用キー
              mstUsrEntity.setOrgCommKey(orgIdList.get(j) + "_key");
              //作成日時
              mstUsrEntity.setCretDatime(DateUtils.getSysTimestamp());
              mstUsrFirtPwEntity.setCretDatime(DateUtils.getSysTimestamp());
              //作成ユーザＩＤ
              mstUsrEntity.setCretUsrId(usrId);
              mstUsrFirtPwEntity.setCretUsrId(usrId);
              //更新日時
              mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
              mstUsrFirtPwEntity.setUpdDatime(DateUtils.getSysTimestamp());
              //更新ユーザＩＤ
              mstUsrEntity.setUpdUsrId(usrId);
              mstUsrFirtPwEntity.setUpdUsrId(usrId);
              //del_flg
              mstUsrEntity.setDelFlg(0);
              mstUsrFirtPwEntity.setDelFlg(0);
              try {
                  mstUsrDao.insert(mstUsrEntity);
                  mstUsrFirtPwDao.insert(mstUsrFirtPwEntity);
              } catch (Exception e) {
                  logger.error(e.getMessage());
                  throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
              }

              //角色の追加
              sysUserRoleEntity = new SysUserRoleEntity();
              sysUserRoleEntity.setRoleId(roleId);
              sysUserRoleEntity.setUserId(mstUsrEntity.getId().longValue());
              sysUserRoleDao.insert(sysUserRoleEntity);

              //先生基本マスタ
              MstMentorEntity mstMentorEntity = new MstMentorEntity();
              //先生ID
              mstMentorEntity.setMentorId("m" + maxIdOfMentor);
              //メールアドレス
              if (r.getCell(7) != null) {
                  if (StringUtils.equals(r.getCell(7).getCellType().toString(), "STRING")) {
                      mstMentorEntity.setMailad(r.getCell(7).getStringCellValue());
                  } else {
                      r.getCell(7).setCellType(CellType.STRING);
                      mstMentorEntity.setMailad(r.getCell(7).toString());
                  }
              }
              //姓名_姓
              if (r.getCell(3) != null && !StringUtils.isEmpty(r.getCell(3).toString())) {
                  mstMentorEntity.setFlnmNm(r.getCell(3).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(3).toString()+"は必須入力項目です。");
              }
              //姓名_名
              if (r.getCell(4) != null && !StringUtils.isEmpty(r.getCell(4).toString())) {
                  mstMentorEntity.setFlnmLnm(r.getCell(4).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(4).toString()+"は必須入力項目です。");
              }
              //姓名_カナ姓
              if (r.getCell(5) != null && !StringUtils.isEmpty(r.getCell(5).toString())) {
                  r.getCell(5).setCellType(CellType.STRING);
                  mstMentorEntity.setFlnmKnNm(r.getCell(5).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(5).toString()+"は必須入力項目です。");
              }
              //姓名_カナ名
              if (r.getCell(6) != null && !StringUtils.isEmpty(r.getCell(6).toString())) {
                  r.getCell(6).setCellType(CellType.STRING);
                  mstMentorEntity.setFlnmKnLnm(r.getCell(6).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(6).toString()+"は必須入力項目です。");
              }
              //電話番号
              if (r.getCell(8) != null) {
                  if (StringUtils.equals(r.getCell(8).getCellType().toString(), "STRING")) {
                      mstMentorEntity.setTelnum(r.getCell(8).getStringCellValue());
                  } else {
                      r.getCell(8).setCellType(CellType.STRING);
                      mstMentorEntity.setTelnum(r.getCell(8).toString());
                  }
              }

              //作成日時
              mstMentorEntity.setCretDatime(DateUtils.getSysTimestamp());
              //作成ユーザＩＤ
              mstMentorEntity.setCretUsrId(usrId);
              //更新日時
              mstMentorEntity.setUpdDatime(DateUtils.getSysTimestamp());
              //更新ユーザＩＤ
              mstMentorEntity.setUpdUsrId(usrId);
              //del_flg
              mstMentorEntity.setDelFlg(0);
              try {
                  mstMentorDao.insert(mstMentorEntity);
              } catch (Exception e) {
                  logger.error(e.getMessage());
                  throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
              }
          }
        }
        //更新
        else {
          for (int j = 0; j < orgIdList.size(); j++) {
              if (StringUtils.equals(j == 0 ? "1" : "0", "0")){
                  return maxIdOfMentor;
              }
              //(A列)システム発番ID＜＞空白　かつ　(B列)ニックID＝空白の場合
              if (r.getCell(1) == null || StringUtils.isEmpty(r.getCell(1).toString())) {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(1).toString()+"は必須入力項目です。");
              }
              //システム発番のユーザーIDの存在チェック
              MstUsrEntity mstUsrEntity1 = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().and(w->w.eq("usr_id", r.getCell(0).toString())));
              if (mstUsrEntity1 == null) {
                  throw new RRException(MessageUtils.getMessage("MSGCOMN0064", (i + 1) + ""));
              }
              //検索結果件数＞0　かつ　④で　エラーとする　を選択された場合、
              if (div == 1) {
                  throw new RRException(MessageUtils.getMessage("MSGCOMN0065", (i + 1) + "", "システム発番のユーザーID"));
              }
              //ユーザマスタ
              MstUsrEntity updateMstUsrEntity = new MstUsrEntity();
              //ユーザ名
              String usrNm = "";
              if (r.getCell(3) != null && !StringUtils.isEmpty(r.getCell(3).toString())) {
                  usrNm += r.getCell(3).toString()  + " ";
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(3).toString()+"は必須入力項目です。");
              }
              if (r.getCell(4) != null && !StringUtils.isEmpty(r.getCell(4).toString())) {
                  usrNm += r.getCell(4).toString();
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(4).toString()+"は必須入力項目です。");
              }
              updateMstUsrEntity.setUsrNm(usrNm);
              //PW更新フラグ
              updateMstUsrEntity.setPwUpFlg("2");
              //変更後ユーザーID
              updateMstUsrEntity.setAfterUsrId(afterUsrId);
              //ユーザログインPW
              String password = "";
              if (r.getCell(2) != null && !StringUtils.isEmpty(r.getCell(2).toString())) {
                  if (StringUtils.equals(r.getCell(2).getCellType().toString(), "NUMERIC")) {
                      r.getCell(2).setCellType(CellType.STRING);
                  }
                  if (StringUtils.equals(r.getCell(0).getCellType().toString(), "NUMERIC")) {
                      r.getCell(0).setCellType(CellType.STRING);
                  }
                  //PW更新フラグ
                  updateMstUsrEntity.setPwUpFlg("2");
                  if (StringUtils.PWDCheck(r.getCell(2).toString())) {
                      password = r.getCell(2).toString();
                  } else {
                      throw new RRException(MessageUtils.getMessage("MSGCOMN0162", "パスワード"));
                  }
              }
              //GIDフラグ
              if (r.getCell(10) != null && !StringUtils.isEmpty(r.getCell(10).toString())) {
                  if (StringUtils.equals(r.getCell(10).getCellType().toString(), "STRING")) {
                      updateMstUsrEntity.setGidFlg(r.getCell(10).toString());
                  } else {
                      r.getCell(10).setCellType(CellType.STRING);
                      updateMstUsrEntity.setGidFlg(r.getCell(10).toString());
                  }
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(10).toString()+"は必須入力項目です。");
              }
              //更新日時
              updateMstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
              //更新ユーザＩＤ
              updateMstUsrEntity.setUpdUsrId(usrId);
              List<MstUsrEntity> mstUsrEntityList = mstUsrDao.selectList(new QueryWrapper<MstUsrEntity>().eq("del_flg", 0).eq("role_div", "2")
                      .eq("usr_sts", "1").eq("after_usr_id", mstUsrEntity1.getAfterUsrId()));
              for (MstUsrEntity usrEntity : mstUsrEntityList) {
                  if (!StringUtils.isEmpty(password)) {
                      updateMstUsrEntity.setUsrPassword(ShiroUtils.sha256(password, usrEntity.getUsrId()));
                  }
                  mstUsrDao.update(
                          updateMstUsrEntity,
                          new QueryWrapper<MstUsrEntity>().and(w -> w.eq("usr_id", usrEntity.getUsrId()).eq("role_div", "2").eq("usr_sts", "1").eq("del_flg", 0)));
              }
              //先生基本マスタ
              MstMentorEntity updateMstMentor = new MstMentorEntity();
              //メールアドレス
              if (r.getCell(7) != null) {
                  if (StringUtils.equals(r.getCell(7).getCellType().toString(), "STRING")) {
                      updateMstMentor.setMailad(r.getCell(7).getStringCellValue());
                  } else {
                      r.getCell(7).setCellType(CellType.STRING);
                      updateMstMentor.setMailad(r.getCell(7).toString());
                  }
              }

              //姓名_姓
              if (r.getCell(3) != null && !StringUtils.isEmpty(r.getCell(3).toString())) {
                  updateMstMentor.setFlnmNm(r.getCell(3).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(3).toString()+"は必須入力項目です。");
              }
              //姓名_名
              if (r.getCell(4) != null && !StringUtils.isEmpty(r.getCell(4).toString())) {
                  updateMstMentor.setFlnmLnm(r.getCell(4).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(4).toString()+"は必須入力項目です。");
              }
              //姓名_カナ姓
              if (r.getCell(5) != null && !StringUtils.isEmpty(r.getCell(5).toString())) {
                  r.getCell(5).setCellType(CellType.STRING);
                  updateMstMentor.setFlnmKnNm(r.getCell(5).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(5).toString()+"は必須入力項目です。");
              }
              //姓名_カナ名
              if (r.getCell(6) != null && !StringUtils.isEmpty(r.getCell(6).toString())) {
                  r.getCell(6).setCellType(CellType.STRING);
                  updateMstMentor.setFlnmKnLnm(r.getCell(6).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(6).toString()+"は必須入力項目です。");
              }
              //電話番号
              if (r.getCell(8) != null) {
                  if (StringUtils.equals(r.getCell(8).getCellType().toString(), "STRING")) {
                      updateMstMentor.setTelnum(r.getCell(8).getStringCellValue());
                  } else {
                      r.getCell(8).setCellType(CellType.STRING);
                      updateMstMentor.setTelnum(r.getCell(8).toString());
                  }
              }

              //更新日時
              updateMstMentor.setUpdDatime(DateUtils.getSysTimestamp());
              //更新ユーザＩＤ
              updateMstMentor.setUpdUsrId(usrId);
              try {
                  for (MstUsrEntity usrEntity : mstUsrEntityList) {
                      mstMentorDao.update(updateMstMentor, new QueryWrapper<MstMentorEntity>().and(w -> w.eq("mentor_id", usrEntity.getUsrId())));
                  }
              } catch (Exception e) {
                  logger.error(e.getMessage());
                  throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
              }
          }

        }
        return maxIdOfMentor;
    }

    /**
     * @param r 　行
     * @param maxIdOfGuard 　保護者ID採番
     * @param orgIdList 　組織ID
     * @param i 　セル
     * @param brandCd 　ブランドコード
     * @param usrId 　ユーザーID
     * @param roleId 　ルールID
     * @param sheet 　シート
     * @param div 　エラーとする or 上書きする
     */
    public int importGuard(Row r, int maxIdOfGuard, List<String> orgIdList, int i, String brandCd, String usrId, Long roleId, Sheet sheet, Integer div, String afterUsrId) {
        MstUsrEntity mstUsrEntity;
        MstUsrFirtPwEntity mstUsrFirtPwEntity;
        SysUserRoleEntity sysUserRoleEntity;
        //新規
        if (r.getCell(0) == null || StringUtils.isEmpty(r.getCell(0).toString())) {
          for (int j = 0; j < orgIdList.size(); j++) {
              maxIdOfGuard++;
              mstUsrEntity = new MstUsrEntity();
              mstUsrFirtPwEntity = new MstUsrFirtPwEntity();
              //ユーザID
              mstUsrEntity.setUsrId("p" + maxIdOfGuard);
              mstUsrFirtPwEntity.setUsrId("p" + maxIdOfGuard);
              //ユーザIDのAsciiコードを62binaryに変更して、
              //ユーザログインPW
              if (r.getCell(2) != null && !StringUtils.isEmpty(r.getCell(2).toString())) {
                  if (StringUtils.equals(r.getCell(2).getCellType().toString(), "NUMERIC")) {
                      r.getCell(2).setCellType(CellType.STRING);
                  }
                  if (StringUtils.PWDCheck(r.getCell(2).toString())) {
                      mstUsrEntity.setUsrPassword(ShiroUtils.sha256(r.getCell(2).toString(), "p" + maxIdOfGuard));
                      mstUsrFirtPwEntity.setUsrFstPassword(ShiroUtils.sha256(r.getCell(2).toString(), "p" + maxIdOfGuard));
                  } else {
                      throw new RRException(MessageUtils.getMessage("MSGCOMN0162", "パスワード"));
                  }
              } else {
                  mstUsrEntity.setUsrPassword(ShiroUtils.sha256(ShiroUtils.stringToAscii("p" + maxIdOfGuard), "p" + maxIdOfGuard));
                  mstUsrFirtPwEntity.setUsrFstPassword(ShiroUtils.sha256(ShiroUtils.stringToAscii("p" + maxIdOfGuard), "p" + maxIdOfGuard));
              }
              //初回登録フラグ
              mstUsrEntity.setFstLoginFlg("0");// 2020/04/16 mod by zhangpuao 0->1;20200731 wen 1 -> 0
              //ユーザ名
              String usrNm = "";
              if (r.getCell(3) != null && !StringUtils.isEmpty(r.getCell(3).toString())) {
                  r.getCell(3).setCellType(CellType.STRING);
                  usrNm += r.getCell(3).toString() + " ";
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(3).toString()+"は必須入力項目です。");
              }
              if (r.getCell(4) != null && !StringUtils.isEmpty(r.getCell(4).toString())) {
                  r.getCell(4).setCellType(CellType.STRING);
                  usrNm += r.getCell(4).toString();
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(4).toString()+"は必須入力項目です。");
              }
              mstUsrEntity.setUsrNm(usrNm);
              //ロール区分
              mstUsrEntity.setRoleDiv("3");
              mstUsrFirtPwEntity.setRoleDiv("3");
              mstUsrFirtPwEntity.setSysDiv("0");
              //組織ID
              mstUsrEntity.setOrgId(orgIdList.get(j));
              //PW更新フラグ
              if (r.getCell(2) != null && !StringUtils.isEmpty(r.getCell(2).toString())) {
                  mstUsrEntity.setPwUpFlg("2");
              } else {
                  mstUsrEntity.setPwUpFlg("0");
              }
              //ユーザステータス
              mstUsrEntity.setUsrSts("1");
              //特殊権限フラグ
              mstUsrEntity.setSpecAuthFlg("0");
              //変更後ユーザーID
              mstUsrEntity.setAfterUsrId(afterUsrId);
              //所属組織フラグ
              mstUsrEntity.setOwnerOrgFlg(j == 0 ? "1" : "0");
              //エラー回数
              mstUsrEntity.setErrorCount(0);
              //ロックフラグ
              mstUsrEntity.setLockFlg("0");
              //GIDフラグ
              if (r.getCell(15) != null && !StringUtils.isEmpty(r.getCell(15).toString())) {
                  if (StringUtils.equals(r.getCell(15).getCellType().toString(), "STRING")) {
                      mstUsrEntity.setGidFlg(r.getCell(15).toString());
                  } else {
                      r.getCell(15).setCellType(CellType.STRING);
                      mstUsrEntity.setGidFlg(r.getCell(15).toString());
                  }
              }else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(15).toString()+"は必須入力項目です。");
              }
              //組織共用キー
              mstUsrEntity.setOrgCommKey(orgIdList.get(j) + "_key");
              //作成日時
              mstUsrEntity.setCretDatime(DateUtils.getSysTimestamp());
              mstUsrFirtPwEntity.setCretDatime(DateUtils.getSysTimestamp());
              //作成ユーザＩＤ
              mstUsrEntity.setCretUsrId(usrId);
              mstUsrFirtPwEntity.setCretUsrId(usrId);
              //更新日時
              mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
              mstUsrFirtPwEntity.setUpdDatime(DateUtils.getSysTimestamp());
              //更新ユーザＩＤ
              mstUsrEntity.setUpdUsrId(usrId);
              mstUsrFirtPwEntity.setUpdUsrId(usrId);
              //del_flg
              mstUsrEntity.setDelFlg(0);
              mstUsrFirtPwEntity.setDelFlg(0);
              try {
                  mstUsrDao.insert(mstUsrEntity);
                  mstUsrFirtPwDao.insert(mstUsrFirtPwEntity);
              } catch (Exception e) {
                  logger.error(e.getMessage());
                  throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
              }

              //角色の追加
              sysUserRoleEntity = new SysUserRoleEntity();
              sysUserRoleEntity.setRoleId(roleId);
              sysUserRoleEntity.setUserId(mstUsrEntity.getId().longValue());
              sysUserRoleDao.insert(sysUserRoleEntity);

              //保護者基本マスタ
              MstGuardEntity mstGuardEntity = new MstGuardEntity();
              //保護者ID
              mstGuardEntity.setGuardId("p" + maxIdOfGuard);
              //姓名_姓
              if (r.getCell(3) != null && !StringUtils.isEmpty(r.getCell(3).toString())) {
                  mstGuardEntity.setFlnmNm(r.getCell(3).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(3).toString()+"は必須入力項目です。");
              }
              //姓名_名
              if (r.getCell(4) != null && !StringUtils.isEmpty(r.getCell(4).toString())) {
                  mstGuardEntity.setFlnmLnm(r.getCell(4).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(4).toString()+"は必須入力項目です。");
              }
              //姓名_カナ姓
              if (r.getCell(5) != null && !StringUtils.isEmpty(r.getCell(5).toString())) {
                  r.getCell(5).setCellType(CellType.STRING);
                  mstGuardEntity.setFlnmKnNm(r.getCell(5).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(5).toString()+"は必須入力項目です。");
              }
              //姓名_カナ名
              if (r.getCell(6) != null && !StringUtils.isEmpty(r.getCell(6).toString())) {
                  r.getCell(6).setCellType(CellType.STRING);
                  mstGuardEntity.setFlnmKnLnm(r.getCell(6).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(6).toString()+"は必須入力項目です。");
              }
              //続柄区分
              if (r.getCell(7) != null && !StringUtils.isEmpty(r.getCell(7).toString())) {
                  if (StringUtils.equals(r.getCell(7).getCellType().toString(), "STRING")) {
                      mstGuardEntity.setReltnspDiv(r.getCell(7).getStringCellValue());
                  } else {
                      r.getCell(7).setCellType(CellType.STRING);
                      mstGuardEntity.setReltnspDiv(r.getCell(7).toString());
                  }
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(7).toString()+"は必須入力項目です。");
              }
              //メールアドレス
              if (r.getCell(8) != null && !StringUtils.isEmpty(r.getCell(8).toString())) {
                  if (StringUtils.equals(r.getCell(8).getCellType().toString(), "STRING")) {
                      mstGuardEntity.setMailad(r.getCell(8).getStringCellValue());
                  } else {
                      r.getCell(8).setCellType(CellType.STRING);
                      mstGuardEntity.setMailad(r.getCell(8).toString());
                  }
              }
              //電話番号
              if (r.getCell(9) != null && !StringUtils.isEmpty(r.getCell(9).toString())) {
                  if (StringUtils.equals(r.getCell(9).getCellType().toString(), "STRING")) {
                      mstGuardEntity.setTelnum(r.getCell(9).getStringCellValue());
                  } else {
                      r.getCell(9).setCellType(CellType.STRING);
                      mstGuardEntity.setTelnum(r.getCell(9).toString());
                  }
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(9).toString()+"は必須入力項目です。");
              }
              //郵便番号_主番
              if (r.getCell(10) != null && !StringUtils.isEmpty(r.getCell(10).toString())) {
//                  if (!r.getCell(10).toString().contains("-")) {
//                      throw new RRException(MessageUtils.getMessage("MSGCOMN0098", (i + 1) + "", sheet.getRow(1).getCell(10).toString()));
//                  }
                  mstGuardEntity.setPostcdMnum(r.getCell(10).toString().substring(0, 3));
                  //郵便番号_枝番
                  mstGuardEntity.setPostcdBnum(r.getCell(10).toString().substring(3, 7));
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(10).toString()+"は必須入力項目です。");
              }
              //住所１
              if (r.getCell(11) != null && !StringUtils.isEmpty(r.getCell(11).toString())) {
                  mstGuardEntity.setAdr1(r.getCell(11).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(11).toString()+"は必須入力項目です。");
              }
              //住所２
              if (r.getCell(12) != null && !StringUtils.isEmpty(r.getCell(12).toString())) {
                  mstGuardEntity.setAdr2(r.getCell(12).toString());
              }
              //緊急電話番号
              if (r.getCell(13) != null && !StringUtils.isEmpty(r.getCell(13).toString())) {
                  if (StringUtils.equals(r.getCell(13).getCellType().toString(), "STRING")) {
                      mstGuardEntity.setUrgTelnum(r.getCell(13).getStringCellValue());
                  } else {
                      r.getCell(13).setCellType(CellType.STRING);
                      mstGuardEntity.setUrgTelnum(r.getCell(13).getStringCellValue());
                  }
              }
              //作成日時
              mstGuardEntity.setCretDatime(DateUtils.getSysTimestamp());
              //作成ユーザＩＤ
              mstGuardEntity.setCretUsrId(usrId);
              //更新日時
              mstGuardEntity.setUpdDatime(DateUtils.getSysTimestamp());
              //更新ユーザＩＤ
              mstGuardEntity.setUpdUsrId(usrId);
              //del_flg
              mstGuardEntity.setDelFlg(0);
              try {
                  mstGuardDao.insert(mstGuardEntity);
              } catch (Exception e) {
                  logger.error(e.getMessage());
                  throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
              }
          }
        }
        //更新
        else {
          for (int j = 0; j < orgIdList.size(); j++) {
              if (StringUtils.equals(j == 0 ? "1" : "0", "0")){
                  return maxIdOfGuard;
              }
              //(A列)システム発番ID＜＞空白　かつ　(B列)ニックID＝空白の場合
              if (r.getCell(1) == null || StringUtils.isEmpty(r.getCell(1).toString())) {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(1).toString()+"は必須入力項目です。");
              }
              //システム発番のユーザーIDの存在チェック
              MstUsrEntity mstUsrEntity1 = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().and(w->w.eq("usr_id", r.getCell(0).toString())));
              if (mstUsrEntity1 == null) {
                  throw new RRException(MessageUtils.getMessage("MSGCOMN0064", (i + 1) + ""));
              }
              //検索結果件数＞0　かつ　④で　エラーとする　を選択された場合、
              if (div == 1) {
                  throw new RRException(MessageUtils.getMessage("MSGCOMN0065", (i + 1) + "", "システム発番のユーザーID"));
              }

              //ユーザマスタ
              MstUsrEntity updateMstUsrEntity = new MstUsrEntity();
              //ユーザ名
              String usrNm = "";
              if (r.getCell(3) != null && !StringUtils.isEmpty(r.getCell(3).toString())) {
                  r.getCell(3).setCellType(CellType.STRING);
                  usrNm += r.getCell(3).toString() + " ";
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(3).toString()+"は必須入力項目です。");
              }
              if (r.getCell(4) != null && !StringUtils.isEmpty(r.getCell(4).toString())) {
                  r.getCell(4).setCellType(CellType.STRING);
                  usrNm += r.getCell(4).toString();
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(4).toString()+"は必須入力項目です。");
              }
              updateMstUsrEntity.setUsrNm(usrNm);
              //変更後ユーザーID
              updateMstUsrEntity.setAfterUsrId(afterUsrId);
              //ユーザログインPW
              String password = "";
              if (r.getCell(2) != null && !StringUtils.isEmpty(r.getCell(2).toString())) {
                  if (StringUtils.equals(r.getCell(2).getCellType().toString(), "NUMERIC")) {
                      r.getCell(2).setCellType(CellType.STRING);
                  }
                  if (StringUtils.equals(r.getCell(0).getCellType().toString(), "NUMERIC")) {
                      r.getCell(0).setCellType(CellType.STRING);
                  }
                  //PW更新フラグ
                  updateMstUsrEntity.setPwUpFlg("2");
                  if (StringUtils.PWDCheck(r.getCell(2).toString())) {
                      password = r.getCell(2).toString();
                  } else {
                      throw new RRException(MessageUtils.getMessage("MSGCOMN0162", "パスワード"));
                  }
              }
              //GIDフラグ
              if (r.getCell(15) != null && !StringUtils.isEmpty(r.getCell(15).toString())) {
                  if (StringUtils.equals(r.getCell(15).getCellType().toString(), "STRING")) {
                      updateMstUsrEntity.setGidFlg(r.getCell(15).toString());
                  } else {
                      r.getCell(15).setCellType(CellType.STRING);
                      updateMstUsrEntity.setGidFlg(r.getCell(15).toString());
                  }
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(15).toString()+"は必須入力項目です。");
              }
              //更新日時
              updateMstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
              //更新ユーザＩＤ
              updateMstUsrEntity.setUpdUsrId(usrId);
              List<MstUsrEntity> mstUsrEntityList = mstUsrDao.selectList(new QueryWrapper<MstUsrEntity>().eq("del_flg", 0).eq("role_div", "3")
                      .eq("usr_sts", "1").eq("after_usr_id", mstUsrEntity1.getAfterUsrId()));
              try {
                  for (MstUsrEntity usrEntity : mstUsrEntityList) {
                      if (!StringUtils.isEmpty(password)) {
                          updateMstUsrEntity.setUsrPassword(ShiroUtils.sha256(password, usrEntity.getUsrId()));
                      }
                      mstUsrDao.update(updateMstUsrEntity, new QueryWrapper<MstUsrEntity>().and(
                              w -> w.eq("usr_id", usrEntity.getUsrId()).eq("role_div", "3").eq("usr_sts", "1").eq("del_flg", 0)));
                  }
              } catch (Exception e) {
                  logger.error(e.getMessage());
                  throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
              }

              //保護者基本マスタ
              MstGuardEntity updateMstGuardEntity = new MstGuardEntity();
              //メールアドレス
              if (r.getCell(8) != null) {
                  if (StringUtils.equals(r.getCell(8).getCellType().toString(), "STRING")) {
                      updateMstGuardEntity.setMailad(r.getCell(8).getStringCellValue());
                  } else {
                      r.getCell(8).setCellType(CellType.STRING);
                      updateMstGuardEntity.setMailad(r.getCell(8).toString());
                  }
              }

              //姓名_姓
              if (r.getCell(3) != null && !StringUtils.isEmpty(r.getCell(3).toString())) {
                  updateMstGuardEntity.setFlnmNm(r.getCell(3).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(3).toString()+"は必須入力項目です。");
              }
              //姓名_名
              if (r.getCell(4) != null && !StringUtils.isEmpty(r.getCell(4).toString())) {
                  updateMstGuardEntity.setFlnmLnm(r.getCell(4).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(4).toString()+"は必須入力項目です。");
              }
              //姓名_カナ姓
              if (r.getCell(5) != null && !StringUtils.isEmpty(r.getCell(5).toString())) {
                  r.getCell(5).setCellType(CellType.STRING);
                  updateMstGuardEntity.setFlnmKnNm(r.getCell(5).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(5).toString()+"は必須入力項目です。");
              }
              //姓名_カナ名
              if (r.getCell(6) != null && !StringUtils.isEmpty(r.getCell(6).toString())) {
                  r.getCell(6).setCellType(CellType.STRING);
                  updateMstGuardEntity.setFlnmKnLnm(r.getCell(6).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(6).toString()+"は必須入力項目です。");
              }
              //郵便番号_主番
              if (r.getCell(10) != null && !StringUtils.isEmpty(r.getCell(10).toString())) {
//                  if (!r.getCell(10).toString().contains("-")) {
//                      throw new RRException(MessageUtils.getMessage("MSGCOMN0098", (i + 1) + "", sheet.getRow(1).getCell(10).toString()));
//                  }
                  updateMstGuardEntity.setPostcdMnum(r.getCell(10).toString().substring(0, 3));
                  //郵便番号_枝番
                  updateMstGuardEntity.setPostcdBnum(r.getCell(10).toString().substring(3, 7));
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(10).toString()+"は必須入力項目です。");
              }
              //住所１
              if (r.getCell(11) != null && !StringUtils.isEmpty(r.getCell(11).toString())) {
                  updateMstGuardEntity.setAdr1(r.getCell(11).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(10).toString()+"は必須入力項目です。");
              }
              //住所２
              if (r.getCell(12) != null && !StringUtils.isEmpty(r.getCell(12).toString())) {
                  updateMstGuardEntity.setAdr2(r.getCell(12).toString());
              }
              //電話番号
              if (r.getCell(9) != null  && !StringUtils.isEmpty(r.getCell(9).toString())) {
                  if (StringUtils.equals(r.getCell(9).getCellType().toString(), "STRING")) {
                      updateMstGuardEntity.setTelnum(r.getCell(9).getStringCellValue());
                  } else {
                      r.getCell(9).setCellType(CellType.STRING);
                      updateMstGuardEntity.setTelnum(r.getCell(9).toString());
                  }
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(9).toString()+"は必須入力項目です。");
              }
              //続柄区分
              if (r.getCell(7) != null  && !StringUtils.isEmpty(r.getCell(7).toString())) {
                  if (StringUtils.equals(r.getCell(7).getCellType().toString(), "STRING")) {
                      updateMstGuardEntity.setReltnspDiv(r.getCell(7).getStringCellValue());
                  } else {
                      r.getCell(7).setCellType(CellType.STRING);
                      updateMstGuardEntity.setReltnspDiv(r.getCell(7).toString());
                  }
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(7).toString()+"は必須入力項目です。");
              }
              //緊急電話番号
              if (r.getCell(13) != null && !StringUtils.isEmpty(r.getCell(13).toString())) {
                  if (StringUtils.equals(r.getCell(13).getCellType().toString(), "STRING")) {
                      updateMstGuardEntity.setUrgTelnum(r.getCell(13).getStringCellValue());
                  } else {
                      r.getCell(13).setCellType(CellType.STRING);
                      updateMstGuardEntity.setUrgTelnum(r.getCell(13).getStringCellValue());
                  }
              }
              //更新日時
              updateMstGuardEntity.setUpdDatime(DateUtils.getSysTimestamp());
              //更新ユーザＩＤ
              updateMstGuardEntity.setUpdUsrId(usrId);
              try {
                  for (MstUsrEntity usrEntity : mstUsrEntityList) {
                      mstGuardDao.update(updateMstGuardEntity, new QueryWrapper<MstGuardEntity>().and(w -> w.eq("guard_id", usrEntity.getUsrId())));
                  }
              } catch (Exception e) {
                  logger.error(e.getMessage());
                  throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
              }
          }

        }
        return maxIdOfGuard;
    }

    /**
     * @param r 　行
     * @param maxIdOfStu 　生徒ID採番
     * @param orgIdList 　組織IDlist
     * @param i 　セル
     * @param brandCd 　ブランドコード
     * @param usrId 　ユーザーID
     * @param roleId 　ルールID
     * @param sheet 　シート
     * @param div 　エラーとする or 上書きする
     */
    public int importStudent(Row r, int maxIdOfStu, List<String> orgIdList, int i, String brandCd, String usrId, Long roleId, Sheet sheet, Integer div, String afterUsrId, int index) {
        MstUsrEntity mstUsrEntity;
        MstUsrFirtPwEntity mstUsrFirtPwEntity;
        SysUserRoleEntity sysUserRoleEntity;
        //新規
        if (r.getCell(0) == null || StringUtils.isEmpty(r.getCell(0).toString())) {
          for (int j = 0; j < orgIdList.size(); j++) {
            //
              maxIdOfStu++;
              mstUsrEntity = new MstUsrEntity();
              mstUsrFirtPwEntity = new MstUsrFirtPwEntity();
              //ユーザID
              mstUsrEntity.setUsrId("s" + maxIdOfStu);
              mstUsrFirtPwEntity.setUsrId("s" + maxIdOfStu);
              //ユーザIDのAsciiコードを62binaryに変更して、
              //ユーザログインPW
              if (r.getCell(2) != null && !StringUtils.isEmpty(r.getCell(2).toString())) {
                  if (StringUtils.equals(r.getCell(2).getCellType().toString(), "NUMERIC")) {
                      r.getCell(2).setCellType(CellType.STRING);
                  }
                  if (StringUtils.PWDCheck(r.getCell(2).toString())) {
                      mstUsrEntity.setUsrPassword(ShiroUtils.sha256(r.getCell(2).toString(), "s" + maxIdOfStu));
                      mstUsrFirtPwEntity.setUsrFstPassword(ShiroUtils.sha256(r.getCell(2).toString(), "s" + maxIdOfStu));
                  } else {
                      throw new RRException(MessageUtils.getMessage("MSGCOMN0162", "パスワード"));
                  }
              } else {
                  mstUsrEntity.setUsrPassword(ShiroUtils.sha256(ShiroUtils.stringToAscii("s" + maxIdOfStu), "s" + maxIdOfStu));
                  mstUsrFirtPwEntity.setUsrFstPassword(ShiroUtils.sha256(ShiroUtils.stringToAscii("s" + maxIdOfStu), "s" + maxIdOfStu));
              }
              //初回登録フラグ
              mstUsrEntity.setFstLoginFlg("0");// 2020/04/16 mod by zhangpuao 0->1;20200731 wen 1 -> 0
              //ユーザ名
              String usrNm = "";
              if (r.getCell(3) != null && !StringUtils.isEmpty(r.getCell(3).toString())) {
                  r.getCell(3).setCellType(CellType.STRING);
                  usrNm += r.getCell(3).toString() + " ";
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(3).toString()+"は必須入力項目です。");
              }
              if (r.getCell(4) != null && !StringUtils.isEmpty(r.getCell(4).toString())) {
                  r.getCell(4).setCellType(CellType.STRING);
                  usrNm += r.getCell(4).toString();
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(4).toString()+"は必須入力項目です。");
              }
              mstUsrEntity.setUsrNm(usrNm);
              //ロール区分
              mstUsrEntity.setRoleDiv("4");
              mstUsrFirtPwEntity.setRoleDiv("4");
              mstUsrFirtPwEntity.setSysDiv("0");
              //組織ID
              mstUsrEntity.setOrgId(orgIdList.get(j));
              //PW更新フラグ
              if (r.getCell(2) != null && !StringUtils.isEmpty(r.getCell(2).toString())) {
                  mstUsrEntity.setPwUpFlg("2");
              } else {
                  mstUsrEntity.setPwUpFlg("0");
              }
              //ユーザステータス
              mstUsrEntity.setUsrSts("1");
              //特殊権限フラグ
              mstUsrEntity.setSpecAuthFlg("0");
              //変更後ユーザーID
              mstUsrEntity.setAfterUsrId(afterUsrId);
              //所属組織フラグ
              mstUsrEntity.setOwnerOrgFlg(j == 0 ? "1" : "0");
              //エラー回数
              mstUsrEntity.setErrorCount(0);
              //ロックフラグ
              mstUsrEntity.setLockFlg("0");
              //GIDフラグ
              if (r.getCell(13) != null && !StringUtils.isEmpty(r.getCell(13).toString())) {
                  if (StringUtils.equals(r.getCell(13).getCellType().toString(), "STRING")) {
                      mstUsrEntity.setGidFlg(r.getCell(13).toString());
                  } else {
                      r.getCell(13).setCellType(CellType.STRING);
                      mstUsrEntity.setGidFlg(r.getCell(13).toString());
                  }
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(13).toString()+"は必須入力項目です。");
              }
              //組織共用キー
              mstUsrEntity.setOrgCommKey(orgIdList.get(j) + "_key");
              //作成日時
              mstUsrEntity.setCretDatime(DateUtils.getSysTimestamp());
              mstUsrFirtPwEntity.setCretDatime(DateUtils.getSysTimestamp());
              //作成ユーザＩＤ
              mstUsrEntity.setCretUsrId(usrId);
              mstUsrFirtPwEntity.setCretUsrId(usrId);
              //更新日時
              mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
              mstUsrFirtPwEntity.setUpdDatime(DateUtils.getSysTimestamp());
              //更新ユーザＩＤ
              mstUsrEntity.setUpdUsrId(usrId);
              mstUsrFirtPwEntity.setUpdUsrId(usrId);
              //del_flg
              mstUsrEntity.setDelFlg(0);
              mstUsrFirtPwEntity.setDelFlg(0);
              try {
                  mstUsrDao.insert(mstUsrEntity);
                  mstUsrFirtPwDao.insert(mstUsrFirtPwEntity);
              } catch (Exception e) {
                  logger.error(e.getMessage());
                  throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
              }

              //角色の追加
              sysUserRoleEntity = new SysUserRoleEntity();
              sysUserRoleEntity.setRoleId(roleId);
              sysUserRoleEntity.setUserId(mstUsrEntity.getId().longValue());
              sysUserRoleDao.insert(sysUserRoleEntity);

              //生徒基本マスタ
              MstStuEntity mstStuEntity = new MstStuEntity();
              //生徒ID
              mstStuEntity.setStuId("s" + maxIdOfStu);
              //学校コード
              if (r.getCell(10) != null) {
                  r.getCell(10).setCellType(CellType.STRING);
                  if (StringUtils.equals(r.getCell(10).getCellType().toString(), "STRING")) {
                      mstStuEntity.setSch(r.getCell(10).getStringCellValue());
                  } else {
                      mstStuEntity.setSch(r.getCell(10).getNumericCellValue() + "");
                  }
              }

              //保護者ID
              mstStuEntity.setGuardId("");
              //姓名_姓
              if (r.getCell(3) != null && !StringUtils.isEmpty(r.getCell(3).toString())) {
                  mstStuEntity.setFlnmNm(r.getCell(3).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(3).toString()+"は必須入力項目です。");
              }
              //姓名_名
              if (r.getCell(4) != null && !StringUtils.isEmpty(r.getCell(4).toString())) {
                  mstStuEntity.setFlnmLnm(r.getCell(4).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(4).toString()+"は必須入力項目です。");
              }
              //姓名_カナ姓
              if (r.getCell(5) != null && !StringUtils.isEmpty(r.getCell(5).toString())) {
                  r.getCell(5).setCellType(CellType.STRING);
                  mstStuEntity.setFlnmKnNm(r.getCell(5).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(5).toString()+"は必須入力項目です。");
              }
              //姓名_カナ名
              if (r.getCell(6) != null && !StringUtils.isEmpty(r.getCell(6).toString())) {
                  r.getCell(6).setCellType(CellType.STRING);
                  mstStuEntity.setFlnmKnLnm(r.getCell(6).toString());
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(6).toString()+"は必須入力項目です。");
              }
              //性別区分
              if (r.getCell(8) != null && !StringUtils.isEmpty(r.getCell(8).toString())) {
                  if (StringUtils.equals(r.getCell(8).getCellType().toString(), "STRING")) {
                      mstStuEntity.setGendrDiv(r.getCell(8).getStringCellValue());
                  } else {
                      r.getCell(8).setCellType(CellType.STRING);
                      mstStuEntity.setGendrDiv(r.getCell(8).toString());
                  }
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(8).toString()+"は必須入力項目です。");
              }
              //生年月日
              if (r.getCell(7) != null && !StringUtils.isEmpty(r.getCell(7).toString())) {
                  if (StringUtils.equals(r.getCell(7).getCellType().toString(), "STRING")) {
                      mstStuEntity.setBirthd(DateUtils.parse(r.getCell(7).getStringCellValue(), GakkenConstant.DATE_FORMAT_YYYYMMDD));
                  } else {
                      r.getCell(7).setCellType(CellType.STRING);
                      mstStuEntity.setBirthd(DateUtils.parse(r.getCell(7).toString(), GakkenConstant.DATE_FORMAT_YYYYMMDD));

                  }
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(7).toString()+"は必須入力項目です。");
              }

              //写真パス
              mstStuEntity.setPhotPath("");
              //学年区分
              if (r.getCell(9) != null && !StringUtils.isEmpty(r.getCell(9).toString())) {
                  if (StringUtils.equals(r.getCell(9).getCellType().toString(), "STRING")) {
                      mstStuEntity.setSchyDiv(r.getCell(9).getStringCellValue());
                  } else {
                      r.getCell(9).setCellType(CellType.STRING);
                      mstStuEntity.setSchyDiv(r.getCell(9).toString());
                  }
              } else {
                  throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(9).toString()+"は必須入力項目です。");
              }
              //QRコード and オリジナルCD
              if (StringUtils.equals("1", j == 0 ? "1" : "0")) {
                  if (r.getCell(11) != null && !StringUtils.isEmpty(r.getCell(11).toString())) {
                      if (!StringUtils.equals(r.getCell(11).getCellType().toString(), "STRING")) {
                          r.getCell(11).setCellType(CellType.STRING);
                      }
                      String cds = r.getCell(11).getStringCellValue().toString();
                      String[] split = cds.split(",", -1);
                      if (index >= split.length) {
                          throw new RRException("組織の数がCDの数と一致していません。");
                      }
                      if (StringUtils.isEmpty(split[index])) {
                          mstStuEntity.setQrCod("s" + maxIdOfStu);
                      } else {
                          if (split[index].contains("s")) {
                              throw new RRException(MessageUtils.getMessage("MSGCOMD0021", (i + 1) + "", "s"));
                          }
                          MstStuEntity mstStuEntityCheck = mstStuDao.selectOne(new QueryWrapper<MstStuEntity>().eq("oria_cd", split[index]).eq("del_flg", 0));
                          if (mstStuEntityCheck != null) {
                              throw new RRException(MessageUtils.getMessage("MSGCOMN0065", StringUtils.defaultString(i + 1), "オリジナルCD"));
                          }
                          mstStuEntity.setQrCod(split[index]);
                          mstStuEntity.setOriaCd(split[index]);
                      }
                  } else {
                      mstStuEntity.setQrCod("s" + maxIdOfStu);
                  }
              }
              //作成日時
              mstStuEntity.setCretDatime(DateUtils.getSysTimestamp());
              //作成ユーザＩＤ
              mstStuEntity.setCretUsrId(usrId);
              //更新日時
              mstStuEntity.setUpdDatime(DateUtils.getSysTimestamp());
              //更新ユーザＩＤ
              mstStuEntity.setUpdUsrId(usrId);
              //del_flg
              mstStuEntity.setDelFlg(0);
              try {
                  mstStuDao.insert(mstStuEntity);
              } catch (Exception e) {
                  logger.error(e.getMessage());
                  throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
              }
          }


        }
        //更新
        else {
            for (int j = 0; j < orgIdList.size(); j++) {
                if (StringUtils.equals(j == 0 ? "1" : "0", "0")){
                    return maxIdOfStu;
                }
                //(A列)システム発番ID＜＞空白　かつ　(B列)ニックID＝空白の場合
                if (r.getCell(1) == null || StringUtils.isEmpty(r.getCell(1).toString())) {
                    throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(1).toString()+"は必須入力項目です。");
                }
                //システム発番のユーザーIDの存在チェック
                MstUsrEntity mstUsrEntity1 = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().and(w->w.eq("usr_id", r.getCell(0).toString())));
                if (mstUsrEntity1 == null) {
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0064", (i + 1) + ""));
                }
                //検索結果件数＞0　かつ　④で　エラーとする　を選択された場合、
                if (div == 1) {
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0065", (i + 1) + "", "システム発番のユーザーID"));
                }

                //ユーザマスタ
                MstUsrEntity updateMstUsrEntity = new MstUsrEntity();
                //ユーザ名
                String usrNm = "";
                if (r.getCell(3) != null && !StringUtils.isEmpty(r.getCell(3).toString())) {
                    r.getCell(3).setCellType(CellType.STRING);
                    usrNm += r.getCell(3).toString() + " ";
                } else {
                    throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(3).toString()+"は必須入力項目です。");
                }
                if (r.getCell(4) != null && !StringUtils.isEmpty(r.getCell(4).toString())) {
                    r.getCell(4).setCellType(CellType.STRING);
                    usrNm += r.getCell(4).toString();
                } else {
                    throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(4).toString()+"は必須入力項目です。");
                }
                updateMstUsrEntity.setUsrNm(usrNm);
                //変更後ユーザーID
                updateMstUsrEntity.setAfterUsrId(afterUsrId);
                //ユーザログインPW
                String password = "";
                if (r.getCell(2) != null && !StringUtils.isEmpty(r.getCell(2).toString())) {
                    if (StringUtils.equals(r.getCell(2).getCellType().toString(), "NUMERIC")) {
                        r.getCell(2).setCellType(CellType.STRING);
                    }
                    if (StringUtils.equals(r.getCell(0).getCellType().toString(), "NUMERIC")) {
                        r.getCell(0).setCellType(CellType.STRING);
                    }
                    //PW更新フラグ
                    updateMstUsrEntity.setPwUpFlg("2");
                    if (StringUtils.PWDCheck(r.getCell(2).toString())) {
                        password = r.getCell(2).toString();
                    } else {
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0162", "パスワード"));
                    }
                }
                //GIDフラグ
                if (r.getCell(13) != null && !StringUtils.isEmpty(r.getCell(13).toString())) {
                    if (StringUtils.equals(r.getCell(13).getCellType().toString(), "STRING")) {
                        updateMstUsrEntity.setGidFlg(r.getCell(13).toString());
                    } else {
                        r.getCell(13).setCellType(CellType.STRING);
                        updateMstUsrEntity.setGidFlg(r.getCell(13).toString());
                    }
                } else {
                    throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(13).toString()+"は必須入力項目です。");
                }
                //更新日時
                updateMstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                updateMstUsrEntity.setUpdUsrId(usrId);
                List<MstUsrEntity> mstUsrEntityList = mstUsrDao.selectList(new QueryWrapper<MstUsrEntity>().eq("del_flg", 0).eq("role_div", "4")
                        .eq("usr_sts", "1").eq("after_usr_id", mstUsrEntity1.getAfterUsrId()));
                try {
                    for (MstUsrEntity usrEntity : mstUsrEntityList) {
                        if (!StringUtils.isEmpty(password)) {
                            updateMstUsrEntity.setUsrPassword(ShiroUtils.sha256(password, usrEntity.getUsrId()));
                        }
                        mstUsrDao.update(updateMstUsrEntity, new QueryWrapper<MstUsrEntity>().and(
                                w -> w.eq("usr_id", usrEntity.getUsrId()).eq("role_div", "4").eq("usr_sts", "1").eq("del_flg", 0)));
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
                }

                //生徒基本マスタ
                MstStuEntity mstStuEntity = new MstStuEntity();
                //姓名_姓
                if (r.getCell(3) != null && !StringUtils.isEmpty(r.getCell(3).toString())) {
                    mstStuEntity.setFlnmNm(r.getCell(3).toString());
                } else {
                    throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(3).toString()+"は必須入力項目です。");
                }
                //姓名_名
                if (r.getCell(4) != null && !StringUtils.isEmpty(r.getCell(4).toString())) {
                    mstStuEntity.setFlnmLnm(r.getCell(4).toString());
                } else {
                    throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(4).toString()+"は必須入力項目です。");
                }
                //姓名_カナ姓
                if (r.getCell(5) != null && !StringUtils.isEmpty(r.getCell(5).toString())) {
                    r.getCell(5).setCellType(CellType.STRING);
                    mstStuEntity.setFlnmKnNm(r.getCell(5).toString());
                } else {
                    throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(5).toString()+"は必須入力項目です。");
                }
                //姓名_カナ名
                if (r.getCell(6) != null && !StringUtils.isEmpty(r.getCell(6).toString())) {
                    r.getCell(6).setCellType(CellType.STRING);
                    mstStuEntity.setFlnmKnLnm(r.getCell(6).toString());
                } else {
                    throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(6).toString()+"は必須入力項目です。");
                }
                //生年月日
                if (r.getCell(7) != null && !StringUtils.isEmpty(r.getCell(7).toString())) {
                    if (StringUtils.equals(r.getCell(7).getCellType().toString(), "STRING")) {
                        mstStuEntity.setBirthd(DateUtils.parse(r.getCell(7).getStringCellValue(), GakkenConstant.DATE_FORMAT_YYYYMMDD));
                    } else {
                        r.getCell(7).setCellType(CellType.STRING);
                        mstStuEntity.setBirthd(DateUtils.parse(r.getCell(7).toString(), GakkenConstant.DATE_FORMAT_YYYYMMDD));
                    }
                } else {
                    throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(7).toString()+"は必須入力項目です。");
                }
                //性別区分
                if (r.getCell(8) != null && !StringUtils.isEmpty(r.getCell(8).toString())) {
                    if (StringUtils.equals(r.getCell(8).getCellType().toString(), "STRING")) {
                        mstStuEntity.setGendrDiv(r.getCell(8).getStringCellValue());
                    } else {
                        r.getCell(8).setCellType(CellType.STRING);
                        mstStuEntity.setGendrDiv(r.getCell(8).toString());
                    }
                } else {
                    throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(8).toString()+"は必須入力項目です。");
                }
                //学年区分
                if (r.getCell(9) != null && !StringUtils.isEmpty(r.getCell(9).toString())) {
                    if (StringUtils.equals(r.getCell(9).getCellType().toString(), "STRING")) {
                        mstStuEntity.setSchyDiv(r.getCell(9).getStringCellValue());
                    } else {
                        r.getCell(9).setCellType(CellType.STRING);
                        mstStuEntity.setSchyDiv(r.getCell(9).toString());
                    }
                } else {
                    throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(9).toString()+"は必須入力項目です。");
                }
                //学校コード
                if (r.getCell(10) != null) {
                    r.getCell(10).setCellType(CellType.STRING);
                    if (StringUtils.equals(r.getCell(10).getCellType().toString(), "STRING")) {
                        mstStuEntity.setSch(r.getCell(10).getStringCellValue());
                    } else {
                        mstStuEntity.setSch(r.getCell(10).getNumericCellValue() + "");
                    }
                }

                //更新日時
                mstStuEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                mstStuEntity.setUpdUsrId(usrId);
                try {
                    for (MstUsrEntity usrEntity : mstUsrEntityList) {
                        mstStuDao.update(mstStuEntity, new QueryWrapper<MstStuEntity>().and(w -> w.eq("stu_id", usrEntity.getUsrId())));
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
                }
            }
        }

        return maxIdOfStu;
    }

    /**
     * インポート
     *
     * @param file ファイル
     * @param type 選択したロール
     * @param div エラーとする or 上書きする
     * @return
     */
    @Override
    public R importFile(MultipartFile file, Integer type, Integer div) {
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        //組織ID
//        String orgId = ShiroUtils.getUserEntity().getOrgId();
        String loginId = ShiroUtils.getUserEntity().getAfterUsrId();
        //ユーザId
        String usrId = ShiroUtils.getUserId();
        //ファイル名を取得
        String fileName = file.getOriginalFilename();
        //同じブランドですべての組織IDを取得する
        Set<String> orgIdSet = new HashSet<>();
        List<MstOrgEntity> mstOrgEntities = mstOrgDao.selectList(new QueryWrapper<MstOrgEntity>().eq("brand_cd", brandCd).eq("del_flg", 0));
        mstOrgEntities.forEach(mstOrgEntity -> orgIdSet.add(mstOrgEntity.getOrgId()));
        //ログイン管理者のログインIDが属する組織の組織IDを取得します。 そして所属組織フラグは1です
        Set<String> loginUsrOrgIdSet = new HashSet<>();
        List<MstUsrEntity> mstUsrEntityList = mstUsrDao.selectList(new QueryWrapper<MstUsrEntity>().eq("del_flg", 0)
                .eq("owner_org_flg", "1").eq("after_usr_id", loginId));
        //共通部品を利用して、ログイン者の所属組織の下層組織を取得し、本組織の下層組織リストに設定する。
        Set<String> lowerLevOrgIdSet = new HashSet<>();
        mstUsrEntityList.forEach(mstUsrEntity -> {
            loginUsrOrgIdSet.add(mstUsrEntity.getOrgId());
            List<F00007Dto> lowerLevOrg = f00007Dao.selectLowerLevOrg(brandCd, mstUsrEntity.getOrgId());
            lowerLevOrg.forEach(org -> lowerLevOrgIdSet.add(org.getOrgId()));
        });
        //判断ファイルのタイプ
        if (!fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0076", "xlsx"));
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RRException(MessageUtils.getMessage("MSGCOMN0091"));
        }
        Workbook wb = null;
        if (isExcel2003) {
            try {
                wb = new HSSFWorkbook(inputStream);
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RRException(MessageUtils.getMessage("MSGCOMN0091"));
            }

        } else {
            try {
                wb = new XSSFWorkbook(inputStream);
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RRException(MessageUtils.getMessage("MSGCOMN0091"));
            }
        }

        //アクセスアップロードファイルの最初のシート
        Sheet sheet = wb.getSheetAt(0);
        //ファイルのチェック
        String[] title = null;
        switch (type) {
            //管理者
            case 0:
                title = new String[] {"管理者ID（システム発番）", "管理者ID（ニックID）", "初期パスワード", "名前_姓", "名前_名", "名前_姓_カナ", "名前_名_カナ", "メールアドレス", "電話番号", "所属組織ID", "GIDフラグ"};
                if (sheet == null || !ExcelUtils.isApplicable(sheet.getRow(1), title)) {
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0078", "管理者"));
                }
                break;
            //先生
            case 1:
                title = new String[] {"先生ID（システム発番）", "先生ID（ニックID）", "初期パスワード", "名前_姓", "名前_名", "名前_姓_カナ", "名前_名_カナ", "メールアドレス", "電話番号", "所属組織ID", "GIDフラグ"};
                if (sheet == null || !ExcelUtils.isApplicable(sheet.getRow(1), title)) {
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0078", "先生"));
                }
                break;
            //保護者
            case 2:
                title = new String[] {"保護者ID（システム発番）", "保護者ID（ニックID）", "初期パスワード"
                        , "保護者名前_姓", "保護者名前_名", "保護者名前_姓_カナ", "保護者名前_名_カナ"
                        , "子供の続柄", "メールアドレス", "電話番号", "郵便番号", "住所1", "住所2", "緊急電話番号", "所属組織ID", "GIDフラグ"};
                if (sheet == null || !ExcelUtils.isApplicable(sheet.getRow(1), title)) {
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0078", "保護者"));
                }
                break;
            //生徒
            case 3:
                title = new String[] {"会員ID（システム発番）", "会員ID", "初期パスワード"
                        , "子供名前_姓", "子供名前_名", "子供名前_姓（カナ）", "子供名前_名（カナ）"
                        , "子供生年月日", "性別", "学年ID", "学校名", "オリジナルCD", "所属組織ID", "GIDフラグ"};
                if (sheet == null || !ExcelUtils.isApplicable(sheet.getRow(1), title)) {
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0078", "生徒"));
                }
                break;
            default:
                break;
        }
        //実際の行数を取得
        int actRows = 0;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row r = sheet.getRow(i);
            if (ExcelUtils.isEmptyRow(r, title.length)) {
                break;
            } else {
                actRows++;
            }
        }
        if (actRows >= 3) {
            //MaxId
            List<F00004Dto> f00004Dtos = f00004Dao.selectMax(brandCd);
            Map<String, Integer> maxIdMap = new HashMap<>();
            for (F00004Dto dto : f00004Dtos) {
                if (dto.getMax() != null) {
                    maxIdMap.put(dto.getRoleDiv().trim(), dto.getMax());
                }
            }
            int maxIdOfManager;
            int maxIdOfMentor;
            int maxIdOfGuard;
            int maxIdOfStu;

            //採番マスタ
            MstNumassEntity mstNumassEntity = new MstNumassEntity();
            //ブランドコード
            mstNumassEntity.setBrandCd("");
            //作成日時
            mstNumassEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            mstNumassEntity.setCretUsrId(usrId);
            //更新日時
            mstNumassEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            mstNumassEntity.setUpdUsrId(usrId);
            //del_flg
            mstNumassEntity.setDelFlg(0);
            if (maxIdMap.get("1") == null) {
                //ロール区分
                mstNumassEntity.setRoleDiv("1");
                //MAXID
                mstNumassEntity.setMaxId("a1");
                mstNumassDao.insert(mstNumassEntity);
                maxIdOfManager = 0;
            } else {
                maxIdOfManager = maxIdMap.get("1");
            }
            if (maxIdMap.get("2") == null) {
                //ロール区分
                mstNumassEntity.setRoleDiv("2");
                //MAXID
                mstNumassEntity.setMaxId("m1");
                mstNumassDao.insert(mstNumassEntity);
                maxIdOfMentor = 0;
            } else {
                maxIdOfMentor = maxIdMap.get("2");
            }
            if (maxIdMap.get("3") == null) {
                //ロール区分
                mstNumassEntity.setRoleDiv("3");
                //MAXID
                mstNumassEntity.setMaxId("p1");
                mstNumassDao.insert(mstNumassEntity);
                maxIdOfGuard = 0;
            } else {
                maxIdOfGuard = maxIdMap.get("3");
            }
            if (maxIdMap.get("4") == null) {
                //ロール区分
                mstNumassEntity.setRoleDiv("4");
                //MAXID
                mstNumassEntity.setMaxId("s1");
                mstNumassDao.insert(mstNumassEntity);
                maxIdOfStu = 0;
            } else {
                maxIdOfStu = maxIdMap.get("4");
            }
            Long roleId;
            //2行をから遍歴し、論理を処理する
            for (int i = 2; i <= actRows - 1; i++) {
                Row r = sheet.getRow(i);
                //2.2.4.0　入力した組織ID存在チェック
                //1）入力した組織IDをもとに、組織マスタを取得する。　※複数組織あり
                String[] strings = getTargetOrgIdList(type, r,i);
                int len = strings.length;
                orgPermissionCheck(orgIdSet, loginUsrOrgIdSet, lowerLevOrgIdSet, i, strings, r);
                //変更後ユーザーID
                String prefix = null;
                int maxUsrId = 0;
                switch (type){
                    case 0:
                        prefix = "a";
                        maxUsrId = maxIdOfManager;
                        break;
                    case 1:
                        prefix = "m";
                        maxUsrId = maxIdOfMentor;
                        break;
                    case 2:
                        prefix = "p";
                        maxUsrId = maxIdOfGuard;
                        break;
                    case 3:
                        prefix = "s";
                        maxUsrId = maxIdOfStu;
                        break;
                    default:
                        break;
                }
                String afterUsrId = checkAfterUsrId(maxUsrId, r, brandCd, i, prefix,sheet);
                roleId = checkRoleType(type);
                for (int index = 0; index < len; index ++) {
                    List<F00007Dto> f00007Dtos = f00007Dao.selectLowerLevOrg(brandCd, strings[index]);
                    List<String> lowerOrgIdList = new ArrayList<>();
                    final int currentIndex = index;
                    f00007Dtos.forEach(f00007Dto -> {
                        if (StringUtils.equals(strings[currentIndex], f00007Dto.getOrgId())) {
                            lowerOrgIdList.add(0, f00007Dto.getOrgId());
                        }else {
                            lowerOrgIdList.add(f00007Dto.getOrgId());
                        }
                    });
//                    for (int i1 = 0; i1 < lowerOrgIdList.size(); i1++) {
                        if (r.getCell(0) == null || StringUtils.isEmpty(r.getCell(0).toString())) {
                            MstUsrEntity usrEntity = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().eq("after_usr_id", afterUsrId).eq("del_flg", 0)
                                    .in("org_id", lowerOrgIdList).last("limit 1"));
                            if (usrEntity != null) {
                                throw new RRException("組織が重複しています。組織を確認してください。");
                            }
                        }
                        switch (type) {
                            //管理者
                            case 0:
                                maxIdOfManager = importManager(r, maxIdOfManager, lowerOrgIdList, i, brandCd, usrId, roleId, sheet, div,  afterUsrId);
                                break;
                            //先生
                            case 1:
                                maxIdOfMentor = importMentor(r, maxIdOfMentor, lowerOrgIdList, i, brandCd, usrId, roleId, sheet, div, afterUsrId);
                                break;
                            //保護者
                            case 2:
                                maxIdOfGuard = importGuard(r, maxIdOfGuard, lowerOrgIdList, i, brandCd, usrId, roleId, sheet, div,  afterUsrId);
                                break;
                            //生徒
                            case 3:
                                maxIdOfStu = importStudent(r, maxIdOfStu, lowerOrgIdList, i, brandCd, usrId, roleId, sheet, div,  afterUsrId, index);
                                break;
                            default:
                                break;
                        }
//                    }
                }
            }
            //全ファイルのデータLOOP処理完了後、採番マスタに最大MAXIDを更新する
            MstNumassEntity updateMst = new MstNumassEntity();
            //更新日時
            updateMst.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            updateMst.setUpdUsrId(usrId);
            switch (type) {
                case 0:
                    //maxId
                    updateMst.setMaxId("a" + maxIdOfManager);
                    mstNumassDao.update(updateMst, new QueryWrapper<MstNumassEntity>().and(w->w.eq("role_div", "1").eq("del_flg", 0)));
                    break;
                case 1:
                    //maxId
                    updateMst.setMaxId("m" + maxIdOfMentor);
                    mstNumassDao.update(updateMst, new QueryWrapper<MstNumassEntity>().and(w->w.eq("role_div", "2").eq("del_flg", 0)));
                    break;
                case 2:
                    //maxId
                    updateMst.setMaxId("p" + maxIdOfGuard);
                    mstNumassDao.update(updateMst, new QueryWrapper<MstNumassEntity>().and(w->w.eq("role_div", "3").eq("del_flg", 0)));
                    break;
                case 3:
                    //maxId
                    updateMst.setMaxId("s" + maxIdOfStu);
                    mstNumassDao.update(updateMst, new QueryWrapper<MstNumassEntity>().and(w->w.eq("role_div", "4").eq("del_flg", 0)));
                    break;
                default:
                    break;
            }
        }
        try {
            if (wb != null) {
                wb.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return R.ok();
    }

    private Long checkRoleType(Integer type) {
        Long roleId;
        SysRoleEntity sysRoleEntity;
        AtomicReference<String> roleName = new AtomicReference<>();
        switch (type) {
            case 0:
                roleName.set("manager");
                break;
            case 1:
                roleName.set("mentor");
                break;
            case 2:
                roleName.set("guard");
                break;
            case 3:
                roleName.set("student");
                break;
            default:
                break;
        }

        sysRoleEntity = sysRoleDao.selectOne(new QueryWrapper<SysRoleEntity>().and(w->w.eq("role_name", roleName.get())));
        if (sysRoleEntity != null) {
            roleId = sysRoleEntity.getRoleId();
        } else {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0017", "システムの角色テーブルのデータ"));
        }
        return roleId;
    }

    /**
     * 組織の許可の確認
     * @param orgIdSet
     * @param loginUsrOrgIdSet
     * @param lowerLevOrgIdSet
     * @param i
     * @param strings
     * @param r
     */
    private void orgPermissionCheck(Set<String> orgIdSet, Set<String> loginUsrOrgIdSet, Set<String> lowerLevOrgIdSet, int i, String[] strings, Row r) {
        boolean isUsrIdEmpty = (r.getCell(0) == null || StringUtils.isEmpty(r.getCell(0).toString()));
        boolean isLoginIdEmpty = (r.getCell(1) == null || StringUtils.isEmpty(r.getCell(1).toString()));
        int len = strings.length;
        for (int j = 0; j < len; j++) {
            //①上記取得できない場合、
            if (!orgIdSet.contains(strings[j])){
                //画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0165）を表示する。
                throw new RRException(MessageUtils.getMessage("MSGCOMN0165", Integer.toString(i + 1), "組織ID"));
            }
            if (isUsrIdEmpty && !lowerLevOrgIdSet.contains(strings[j])){
                //画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0166）を表示する。
                throw new RRException(MessageUtils.getMessage("MSGCOMN0166", Integer.toString(i + 1), "所属組織ID"));
            }
            if (!isUsrIdEmpty && !isLoginIdEmpty && !loginUsrOrgIdSet.contains(strings[j])){
                //画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0167）を表示する。
                throw new RRException(MessageUtils.getMessage("MSGCOMN0167", Integer.toString(i + 1)));
            }
        }
    }

    /**
     * ユーザーを生成したターゲット組織を取得します
     * @param type
     * @param r
     * @return
     */
    private String[] getTargetOrgIdList(Integer type, Row r,Integer i) {
        int index;
        switch (type) {
            case 0:
            case 1:
                index = 9;
                break;
            case 2:
                index = 14;
                break;
            case 3:
                index = 12;
                break;
            default:
                throw new RRException("タイプエラー");
        }
        if (r.getCell(index) == null || StringUtils.isEmpty(r.getCell(index).toString())) {
            throw new RRException((i + 1) + "行目の所属組織IDは必須入力項目です。");
        }
        r.getCell(index).setCellType(CellType.STRING);
        String orgIds = r.getCell(index).toString();
        String[] strings = orgIds.split(",", -1);
        if (type == 3){
            if (r.getCell(11) != null && !StringUtils.isEmpty(r.getCell(11).toString())) {
                r.getCell(11).setCellType(CellType.STRING);
                String cds = r.getCell(11).toString();
                String[] split = cds.split(",", -1);
                if (split.length != strings.length) {
                    throw new RRException("組織の数がCDの数と一致していません。");
                }
            }
        }
        return strings;
    }

    /**
     * マナミル一意IDチェック
     * @param maxUsrId
     * @param r
     * @return
     */
    private String checkAfterUsrId(int maxUsrId, Row r, String brandCd, int i, String prefix ,Sheet sheet) {
        String afterUsrId = null;
        if (r.getCell(1) == null || StringUtils.isEmpty(r.getCell(1).toString())) {
//            afterUsrId = prefix + (maxUsrId + 1);
            throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(1).toString()+"は必須入力項目です。");
        } else {
            r.getCell(1).setCellType(CellType.STRING);
            if (!r.getCell(1).toString().matches("[a-zA-Z0-9]*")) {
                throw new RRException(MessageUtils.getMessage("MSGCOMD0004", "利用者ＩＤ"));
            } else if (r.getCell(1).toString().length() < 8 || r.getCell(1).toString().length() > 32) {
                throw new RRException(MessageUtils.getMessage("MSGCOMD0022", "利用者ＩＤ"));
            }
            afterUsrId = r.getCell(1).toString();
        }
        //ニックIDの一意チェック
        MstUsrEntity checkEntity = f00004Dao.checkFnOfSave(brandCd, afterUsrId);
        if (checkEntity != null) {
            if (r.getCell(0) != null && !StringUtils.isEmpty(r.getCell(0).toString())){
                MstUsrEntity mstUsrEntity = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", r.getCell(0).toString()).eq("del_flg", 0));
                if (!StringUtils.equals(mstUsrEntity.getAfterUsrId(), afterUsrId)){
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0066", (i + 1) + "", "利用者ID"));
                }
            }else {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0066", (i + 1) + "", "利用者ID"));
            }
        }
        return afterUsrId;
    }

    /**
     * @param file ファイル
     * @return
     */
    @Override
    public R guardWithStuImprot(MultipartFile file) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ユーザＩＤ
        String usrId = ShiroUtils.getUserId();
        //ファイル名を取得
        String fileName = file.getOriginalFilename();
        //判断ファイルのタイプ
        if (!fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0064", "xlsx"));
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RRException(MessageUtils.getMessage("MSGCOMN0091"));
        }
        Workbook wb = null;
        if (isExcel2003) {
            try {
                wb = new HSSFWorkbook(inputStream);
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RRException(MessageUtils.getMessage("MSGCOMN0091"));
            }
        } else {
            try {
                wb = new XSSFWorkbook(inputStream);
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RRException(MessageUtils.getMessage("MSGCOMN0091"));
            }
        }
        //アクセスアップロードファイルの最初のシート
        Sheet sheet = wb.getSheetAt(0);
        String[] title = new String[] {"会員ID", "保護者ID（ニックID）"};
        if (sheet == null || !ExcelUtils.isApplicable(sheet.getRow(1), title)) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0078", "生徒と保護者の依存関係"));
        }

        //チェック
        MstUsrEntity mstUsrEntityOfStu;
        MstUsrEntity mstUsrEntityOfGuard;
        MstUsrEntity mstUsrEntityOfGuard1;
        MstUsrEntity mstUsrEntityOfGuard2;
        MstUsrEntity mstUsrEntityOfGuard3;
        MstUsrEntity mstUsrEntityOfGuard4;

        //実際の行数を取得
        int actRows = 0;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row r = sheet.getRow(i);
            if (ExcelUtils.isEmptyRow(r, title.length)) {
                break;
            } else {
                actRows++;
            }
        }

        if (actRows >= 3) {
            for (int i = 2; i <= actRows - 1; i++) {
                Row r = sheet.getRow(i);
                if (r.getCell(0) != null && !StringUtils.isEmpty(r.getCell(0).toString())) {
                    r.getCell(0).setCellType(CellType.STRING);
                }
                if (r.getCell(1) != null && !StringUtils.isEmpty(r.getCell(1).toString())) {
                    r.getCell(1).setCellType(CellType.STRING);
                }
                if (r.getCell(2) != null && !StringUtils.isEmpty(r.getCell(2).toString())) {
                    r.getCell(2).setCellType(CellType.STRING);
                }
                if (r.getCell(3) != null && !StringUtils.isEmpty(r.getCell(3).toString())) {
                    r.getCell(3).setCellType(CellType.STRING);
                }
                if (r.getCell(4) != null && !StringUtils.isEmpty(r.getCell(4).toString())) {
                    r.getCell(4).setCellType(CellType.STRING);
                }
                if (r.getCell(5) != null && !StringUtils.isEmpty(r.getCell(5).toString())) {
                    r.getCell(5).setCellType(CellType.STRING);
                }
                if (ExcelUtils.isEmptyRow(r, title.length)) {
                    return R.ok();
                }
                //必須チェック
                if (r.getCell(0) == null || StringUtils.isEmpty(r.getCell(0).toString())) {
                    throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(0).toString()+"は必須入力項目です。");
                }
                if (r.getCell(1) == null || StringUtils.isEmpty(r.getCell(1).toString())) {
                    throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(1).toString()+"は必須入力項目です。");
                }
                //（A列）会員IDの存在チェック
                mstUsrEntityOfStu = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().and(
                        w->w.eq("after_usr_id", r.getCell(0).toString()).eq("role_div", "4").eq("org_id", orgId).eq("usr_sts", "1").eq("del_flg", 0)));
                if (mstUsrEntityOfStu == null) {
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0068", (i + 1) + "", "会員ID"));
                }
                String stuId = mstUsrEntityOfStu.getUsrId();
                //（B列）保護者ID（ニックID）の存在チェック
                mstUsrEntityOfGuard = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().and(
                        w->w.eq("after_usr_id", r.getCell(1).toString()).eq("role_div", "3").eq("org_id", orgId).eq("usr_sts", "1").eq("del_flg", 0)));
                if (r.getCell(2) != null && r.getCell(2).toString().isEmpty()) {

                    mstUsrEntityOfGuard1 = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().and(
                            w->w.eq("after_usr_id", r.getCell(2).toString()).eq("role_div", "3").eq("org_id", orgId).eq("usr_sts", "1").eq("del_flg", 0)));
                }else {
                    mstUsrEntityOfGuard1 = null;
                }
                if (r.getCell(3) != null && r.getCell(3).toString().isEmpty()) {
                    mstUsrEntityOfGuard2 = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().and(
                            w->w.eq("after_usr_id", r.getCell(3).toString()).eq("role_div", "3").eq("org_id", orgId).eq("usr_sts", "1").eq("del_flg", 0)));
                }else {
                    mstUsrEntityOfGuard2 = null;
                }
                if (r.getCell(4) != null && r.getCell(4).toString().isEmpty()) {
                    mstUsrEntityOfGuard3 = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().and(
                            w->w.eq("after_usr_id", r.getCell(4).toString()).eq("role_div", "3").eq("org_id", orgId).eq("usr_sts", "1").eq("del_flg", 0)));
                }else {
                    mstUsrEntityOfGuard3 = null;
                }
                if (r.getCell(5) != null && r.getCell(5).toString().isEmpty()) {
                    mstUsrEntityOfGuard4 = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().and(
                            w->w.eq("after_usr_id", r.getCell(5).toString()).eq("role_div", "3").eq("org_id", orgId).eq("usr_sts", "1").eq("del_flg", 0)));
                }else {
                    mstUsrEntityOfGuard4 = null;
                }

                if (mstUsrEntityOfGuard == null) {
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0068", (i + 1) + "", "保護者ID（ニックID）"));
                }
                //生徒基本マスタに生徒と保護者の依存関係更新する。
                MstStuEntity mstStuEntity = new MstStuEntity();
                //更新ユーザＩＤ
                mstStuEntity.setUpdUsrId(usrId);
                //生徒基本マスタに生徒と保護者の依存関係更新する。
                mstStuEntity.setGuardId(mstUsrEntityOfGuard.getUsrId());
                if (mstUsrEntityOfGuard1 != null) {
                    mstStuEntity.setGuard1Id(mstUsrEntityOfGuard1.getUsrId());
                }
                if (mstUsrEntityOfGuard2 != null) {
                    mstStuEntity.setGuard2Id(mstUsrEntityOfGuard2.getUsrId());
                }
                if (mstUsrEntityOfGuard3 != null) {
                    mstStuEntity.setGuard3Id(mstUsrEntityOfGuard3.getUsrId());
                }
                if (mstUsrEntityOfGuard4 != null) {
                    mstStuEntity.setGuard4Id(mstUsrEntityOfGuard4.getUsrId());
                }

                //更新日時
                mstStuEntity.setUpdDatime(DateUtils.getSysTimestamp());
                try {
                    mstStuDao.update(mstStuEntity, new QueryWrapper<MstStuEntity>().and(w->w.eq("stu_id", stuId).eq("del_flg", 0)));
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
                }
            }
        }
        try {
            if (wb != null) {
                wb.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return R.ok();
    }
}
