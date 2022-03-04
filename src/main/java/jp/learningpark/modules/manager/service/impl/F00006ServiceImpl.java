/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.dao.MentorStuDao;
import jp.learningpark.modules.common.dao.MstUsrDao;
import jp.learningpark.modules.common.entity.MentorStuEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.manager.dao.F00006Dao;
import jp.learningpark.modules.manager.dto.F00006Dto;
import jp.learningpark.modules.manager.service.F00006Service;
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
import java.util.List;

/**
 * <p>F00006 メンター生徒管理画面 ServiceImpl</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/12/26 : wen: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F00006ServiceImpl implements F00006Service {

    /**
     * F00006 Dao
     */
    @Autowired
    F00006Dao f00006Dao;

    /**
     * メンター生徒管理画面 Dao
     */
    @Autowired
    MentorStuDao mentorStuDao;

    /**
     * ユーザー基本マスタ Dao
     */
    @Autowired
    MstUsrDao mstUsrDao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * <p>インポート</p>
     *
     * @param file ファイル
     * @param type 新規・修正（エラーとする）、新規・修正（上書き）、削除選択フラッグ
     * @return
     */
    @Override
    public R importFile(MultipartFile file, Integer type) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ユーザId
        String usrId = ShiroUtils.getUserId();
        //ファイル名を取得
        String fileName = file.getOriginalFilename();
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

        String title[] = new String[] {"先生ー生徒ID\n（システム採番）", "先生ID\n（ニックID）", "生徒会員ID"};
        if (!ExcelUtils.isApplicable(sheet.getRow(1), title)) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0078", "先生・生徒情報"));
        }
        MentorStuEntity mentorStuEntity = null;
        MstUsrEntity mstUsrEntityMentorId = null;
        MstUsrEntity mstUsrEntityStuId = null;

        //実際の行数を取得
        int actRows = 0;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row r = sheet.getRow(i);
            if (ExcelUtils.isEmptyRow(r,title.length)){
                break;
            } else {
                actRows++;
            }
        }

        if (actRows != 0) {
            //2行をから遍歴し、論理を処理する
            for (int i = 2; i <= actRows - 1; i++) {
                Row row = sheet.getRow(i);
                if (ExcelUtils.isEmptyRow(row, title.length)) {
                    return R.ok();
                }
                List<MentorStuEntity> mentorStuEntityList;

                if(row.getCell(1) == null || StringUtils.isEmpty(row.getCell(1).toString())){
                    throw new RRException((i + 1) + "行目の先生IDは必須入力項目です。");
                }
                if(row.getCell(2) == null || StringUtils.isEmpty(row.getCell(2).toString())){
                    throw new RRException((i + 1) + "行目の生徒IDは必須入力項目です。");
                }
//                if (row.getCell(1) == null || StringUtils.isEmpty(row.getCell(1).toString()) || row.getCell(2) == null || StringUtils.isEmpty(
//                        row.getCell(2).toString())) {
//                    //メンターIDまたは生徒会員ID空白の場合
//                    throw new RRException(MessageUtils.getMessage("MSGCOMN0063", (i + 1) + "",
//                            sheet.getRow(1).getCell(1).toString() + "または" + sheet.getRow(1).getCell(2).toString()));
//                }
                //5.20  wen   セルの種類を設定
                row.getCell(1).setCellType(CellType.STRING);
                row.getCell(2).setCellType(CellType.STRING);
                //ユーザーマスタユーザID　as メンターID
                mstUsrEntityMentorId = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().select("usr_id").and(
                        wrapper->wrapper.eq("after_usr_id", row.getCell(1).getStringCellValue()).eq("role_div","2").eq("org_id", orgId).eq("usr_sts", "1").eq("del_flg", 0)));
                if (mstUsrEntityMentorId == null) {
                    //メンターID（ニックID）の存在チェック行う
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0068", (i + 1) + "", "先生ID"));
                }
                //ユーザーマスタユーザID　as 生徒会員ID
                mstUsrEntityStuId = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().select("usr_id").and(
                        wrapper->wrapper.eq("after_usr_id", row.getCell(2).getStringCellValue()).eq("role_div","4").eq("org_id", orgId).eq("usr_sts", "1").eq("del_flg", 0)));
                if (mstUsrEntityStuId == null) {
                    //生徒会員IDの存在チェック行う
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0068", (i + 1) + "", "生徒ID"));
                }
                if (type != 2) {//削除しない
                    //重複チェック
                    MentorStuEntity mentorStuEntityToCheck = mentorStuDao.selectOne(
                            new QueryWrapper<MentorStuEntity>().eq("stu_id", mstUsrEntityStuId.getUsrId()).eq("mentor_id", mstUsrEntityMentorId.getUsrId()).eq(
                                    "crmsch_id", orgId).eq("del_flg", 0));
                    if (mentorStuEntityToCheck != null) {
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0065", (i + 1) + "", "先生IDと生徒会員ID"));
                    }
                    //新規
                    if (row.getCell(0) == null || StringUtils.isEmpty(row.getCell(0).toString())) {
                        mentorStuEntity = new MentorStuEntity();
                        //塾ID
                        mentorStuEntity.setCrmschId(orgId);
                        //生徒ID
                        mentorStuEntity.setStuId(mstUsrEntityStuId.getUsrId());
                        //メンターID
                        mentorStuEntity.setMentorId(mstUsrEntityMentorId.getUsrId());
                        //作成日時
                        mentorStuEntity.setCretDatime(DateUtils.getSysTimestamp());
                        //作成ユーザＩＤ
                        mentorStuEntity.setCretUsrId(usrId);
                        //更新日時
                        mentorStuEntity.setUpdDatime(DateUtils.getSysTimestamp());
                        //更新ユーザＩＤ
                        mentorStuEntity.setUpdUsrId(usrId);
                        //削除フラグ
                        mentorStuEntity.setDelFlg(0);
                        try {
                            mentorStuDao.insert(mentorStuEntity);
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
                        }

                    } else {
                        //                        if (StringUtils.equals(row.getCell(i).getCellType().toString(), "STRING")) {
                        //                            mentorStuEntity = mentorStuDao.selectById(Integer.parseInt(row.getCell(0).getStringCellValue()));
                        //                        } else {
                        //                            mentorStuEntity = mentorStuDao.selectById((int) row.getCell(0).getNumericCellValue());
                        //                        }
                        if (StringUtils.equals(row.getCell(0).getCellType().toString(), "STRING")) {
                            mentorStuEntity = mentorStuDao.selectById(Integer.parseInt(row.getCell(0).getStringCellValue()));
                        } else {
                            mentorStuEntity = mentorStuDao.selectById((int)(row.getCell(0).getNumericCellValue()));
                        }
                        if (mentorStuEntity == null) {
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0068", (i + 1) + "", sheet.getRow(1).getCell(0).toString()));
                        }
                        //エラーとする
                        if (type == 0) {
                            //id
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0065", (i + 1) + "", sheet.getRow(1).getCell(0).toString()));
                        }
                        //上書き
                        if (type == 1) {
                            //塾ID
                            mentorStuEntity.setCrmschId(orgId);
                            //生徒ID
                            mentorStuEntity.setStuId(mstUsrEntityStuId.getUsrId());
                            //メンターID
                            mentorStuEntity.setMentorId(mstUsrEntityMentorId.getUsrId());
                            //作成日時
                            mentorStuEntity.setCretDatime(DateUtils.getSysTimestamp());
                            //作成ユーザＩＤ
                            mentorStuEntity.setCretUsrId(usrId);
                            //更新日時
                            mentorStuEntity.setUpdDatime(DateUtils.getSysTimestamp());
                            //更新ユーザＩＤ
                            mentorStuEntity.setUpdUsrId(usrId);
                            //削除フラグ
                            mentorStuEntity.setDelFlg(0);
                            try {
                                mentorStuDao.updateById(mentorStuEntity);
                            } catch (Exception e) {
                                logger.error(e.getMessage());
                                throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
                            }
                        }
                    }
                }
                //削除選択した場合、
                else {
                    //削除の場合
                    if (row.getCell(0) == null || StringUtils.isEmpty(row.getCell(0).toString())) {
                        //⑧で削除選択した場合、(A列)メンターー生徒ID（システム採番）空白なら
                        throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(0).toString()+"は必須入力項目です。");
                    } else {
                        MentorStuEntity mentorStuEntityOld;
                        if (StringUtils.equals(row.getCell(0).getCellType().toString(), "STRING")) {
                            mentorStuEntityOld = mentorStuDao.selectById(Integer.parseInt(row.getCell(0).getStringCellValue()));
                            mentorStuEntityList = mentorStuDao.selectList(new QueryWrapper<MentorStuEntity>().select().and(
                                    wrapper->wrapper.eq("id", Integer.parseInt(row.getCell(0).getStringCellValue())).eq("crmsch_id", orgId).eq("del_flg", 0)));
                        } else {
                            mentorStuEntityOld = mentorStuDao.selectById((int)(row.getCell(0).getNumericCellValue()));
                            mentorStuEntityList = mentorStuDao.selectList(new QueryWrapper<MentorStuEntity>().select().and(
                                    wrapper->wrapper.eq("id", (int)(row.getCell(0).getNumericCellValue())).eq("crmsch_id", orgId).eq("del_flg", 0)));
                        }
                        if (mentorStuEntityList.size() == 0) {
                            //DBへメンターー生徒ID（システム採番）の存在チェック行う
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0068", (i + 1) + "", sheet.getRow(1).getCell(0).toString()));
                        } else {
                            mentorStuEntityOld.setUpdDatime(DateUtils.getSysTimestamp());
                            mentorStuEntityOld.setUpdUsrId(usrId);
                            mentorStuEntityOld.setDelFlg(1);
                            try {
                                mentorStuDao.updateById(mentorStuEntityOld);
                            } catch (Exception e) {
                                logger.error(e.getMessage());
                            }
                        }

                    }
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

    /**
     * <p>ユーザーIDと生徒IDを取得</p>
     *
     * @param orgId 前画面から受け取ったログイン管理者の組織ID
     * @return ユーザーIDと生徒ID
     */
    @Override
    public List<F00006Dto> getAfterUsrIdAndStuId(String orgId) {
        return f00006Dao.selectAfterUsrIdAndStuId(orgId);
    }
}
