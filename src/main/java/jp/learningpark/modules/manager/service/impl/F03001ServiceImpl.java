/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.ExcelUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.dao.MstLearnSeasnDao;
import jp.learningpark.modules.common.dao.MstTextbDao;
import jp.learningpark.modules.common.dao.MstUnitDao;
import jp.learningpark.modules.common.dao.TextbDefTimeInfoDao;
import jp.learningpark.modules.common.entity.MstLearnSeasnEntity;
import jp.learningpark.modules.common.entity.MstTextbEntity;
import jp.learningpark.modules.common.entity.MstUnitEntity;
import jp.learningpark.modules.common.entity.TextbDefTimeInfoEntity;
import jp.learningpark.modules.common.service.MstTextbService;
import jp.learningpark.modules.common.service.TextbDefTimeInfoService;
import jp.learningpark.modules.manager.dao.F03001Dao;
import jp.learningpark.modules.manager.dto.F03001Dto;
import jp.learningpark.modules.manager.service.F03001Service;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * <p>教科書一覧</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/12/26 : hujunjie: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F03001ServiceImpl implements F03001Service {
    /**
     * 教科書一覧Dao
     */
    @Autowired
    F03001Dao f03001Dao;

    /**
     * 教科書マスタService
     */
    @Autowired
    MstTextbService mstTextbService;

    /**
     * 教科書マスタDao
     */
    @Autowired
    MstTextbDao mstTextbDao;

    /**
     * 教科書デフォルトターム情報Service
     */
    @Autowired
    TextbDefTimeInfoService textbDefTimeInfoService;

    /**
     * 教科書デフォルトターム情報Dao
     */
    @Autowired
    TextbDefTimeInfoDao textbDefTimeInfoDao;

    /**
     * 学習時期
     */
    @Autowired
    MstLearnSeasnDao mstLearnSeasnDao;

    /**
     * 単元マスタ
     */
    @Autowired
    MstUnitDao mstUnitDao;

    /**
     * 学年リスト取得
     *
     * @return
     */
    @Override
    public List<F03001Dto> getSchyList() {
        return f03001Dao.getSchyList();
    }

    /**
     * 教科リスト取得
     *
     * @return
     */
    @Override
    public List<F03001Dto> getSubjtList() {
        return f03001Dao.getSubjtList();
    }

    /**
     * 出版社リスト取得
     *
     * @return
     */
    @Override
    public List<F03001Dto> getPublisherList() {
        return f03001Dao.getPublisherList();
    }

    /**
     * 教科書一覧カウント
     *
     * @param orgId        組織ID
     * @param orgIdList    組織リスト
     * @param schyDiv      学年区分
     * @param subjtDiv     教科書区分
     * @param publisherDiv 出版社区分
     * @param brandCd      ブランドコード
     * @param textbNm      教科書名
     * @param textbId      教科書ID
     * @return
     */
    @Override
    public Integer getTextbCount(String orgId, String schyDiv, String subjtDiv, String publisherDiv, String brandCd, String textbNm, Integer textbId) {
        return f03001Dao.getTextbCount(orgId, schyDiv, subjtDiv, publisherDiv, brandCd, textbNm, textbId);
    }

    /**
     * 教科書一覧外層リスト
     *
     * @param orgId        組織ID
     * @param schyDiv      学年区分
     * @param subjtDiv     教科書区分
     * @param publisherDiv 出版社区分
     * @param brandCd      ブランドコード
     * @param textbNm      教科書名
     * @return
     */
    @Override
    public List<F03001Dto> getTextbListUpLevel(String orgId, String schyDiv, String subjtDiv, String publisherDiv, String brandCd, Integer limit, Integer page, String textbNm) {
        return f03001Dao.getTextbListUpLevel(orgId, schyDiv, subjtDiv, publisherDiv, brandCd, limit, page, textbNm);
    }

    /**
     * 本組織と全部の上層組織Idを取得
     *
     * @param orgId   組織ID
     * @param brandCd ブランドコード
     * @return
     */
    @Override
    public List<F03001Dto> selectAllUpLevOrgByOrgId(String orgId, String brandCd) {
        return f03001Dao.selectAllUpLevOrgByOrgId(orgId, brandCd);
    }

    /**
     * 教科書削除
     *
     * @param f03001Dto 教科書一覧Dto
     * @return
     */
    @Override
    public R delBook(F03001Dto f03001Dto) {
        //教科書ID
        Integer textbId = f03001Dto.getTextbId();
        //教科書マスタ更新時間
        String mtUpdTm = f03001Dto.getMtUpdTm();

        //教科書デフォルト
        Integer termPlanList = f03001Dao.getTermPlanBook(textbId);

        //削除データ取得
        F03001Dto delData = f03001Dao.getDelData(f03001Dto.getOrgId(), f03001Dto.getSchyDiv(), f03001Dto.getSubjtDiv()
                , f03001Dto.getPublisherDiv(), ShiroUtils.getBrandcd(), f03001Dto.getTextbId());
        if (delData == null) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
        }
        TextbDefTimeInfoEntity textbDefTimeInfoEntity = null;

        //教科書デフォルト単元IDが生徒タームプラン設定に存在する判断
        if (termPlanList == 0) {
            String delTdtiUpdTm;
            String textbListUpdTm;
            //教科書マスタ
            MstTextbEntity mstTextbEntity = mstTextbService.getById(textbId);
            //DB当データ取得
            List<F03001Dto> textbList = f03001Dao.getTextbList(delData.getOrgId(), delData.getSchyDiv()
                    , delData.getSubjtDiv(), delData.getPublisherDiv(), ShiroUtils.getBrandcd(), null, null, null, delData.getTextbId());
            if (textbList == null) {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
            }
            //DB当データに存在する判断
            if (textbList.size() > 0) {
                //排他チェック
                if (mtUpdTm != null && delData.getTdtiUpdTmList().size() > 0) {
                    if (mstTextbEntity.getUpdDatime() == null) {
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
                    }
                    String mstTextbEntityUpdDatime = DateUtils.format(mstTextbEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
                    if (StringUtils.equals(mtUpdTm, mstTextbEntityUpdDatime)) {
                        //削除フラグは削除に更新する
                        mstTextbEntity.setDelFlg(1);
                        mstTextbDao.updateById(mstTextbEntity);
                        for (int i = 0; i < textbList.get(0).getTdtiUpdTmList().size(); i++) {
                            //画面・更新時間 = DB・更新時間 判断
                            //更新時間取得
                            if (delData.getTdtiUpdTmList().get(i).getTdtiUpdTm() == null) {
                                throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
                            }
                            delTdtiUpdTm = DateUtils.format(delData.getTdtiUpdTmList().get(i).getTdtiUpdTm(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
                            if (textbList.get(0).getTdtiUpdTmList().get(i).getTdtiUpdTm() == null) {
                                throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
                            }
                            textbListUpdTm = DateUtils.format(textbList.get(0).getTdtiUpdTmList().get(i).getTdtiUpdTm(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
                            if (StringUtils.equals(delTdtiUpdTm, textbListUpdTm)) {
                                //教科書ディフォルトターム情報テーブルの当IDデータ取得
                                textbDefTimeInfoEntity = textbDefTimeInfoService.getById(delData.getTdtiUpdTmList().get(i).getId());
                                //削除フラグは削除に更新する
                                textbDefTimeInfoEntity.setDelFlg(1);
                                textbDefTimeInfoDao.updateById(textbDefTimeInfoEntity);
                            } else {
                                throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
                            }
                        }
                        return R.ok();
                    } else {
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
                    }
                } else {
                    return R.ok();
                }
            } else {
                return R.ok();
            }
        } else {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0061", f03001Dto.getTextbNm()));
        }
    }

    /**
     * インポート
     *
     * @param file         ファイル
     * @param schyDiv      学年区分
     * @param subjtDiv     教科書区分
     * @param publisherDiv 出版社区分
     * @param textbNm      教科書名
     * @return
     */
    @Override
    public R importFile(MultipartFile file, String schyDiv, String subjtDiv, String publisherDiv, String textbNm) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ユーザId
        String usrId = ShiroUtils.getUserId();
        //ファイル名を取得
        String fileName = file.getOriginalFilename();
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        //塾学習期間ID
        Integer crmLearnPrdId = f03001Dao.selectMstCrmschLearnPrdIdToImport(schyDiv, orgId, brandCd);
        //判断ファイルのタイプ
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0076", "xlsx"));
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Workbook wb = null;
        if (isExcel2003) {
            try {
                wb = new HSSFWorkbook(is);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                wb = new XSSFWorkbook(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //アクセスアップロードファイルの最初のシート
        Sheet sheet = null;
        if (wb != null) {
            sheet = wb.getSheetAt(0);
        }
        //ファイルのチェック
        String[] title = new String[]{"単元ID\n※必須", "章NO", "節NO", "単元NO", "章名\n※参照だけ、更新項目ではない", "節名\n※参照だけ、更新項目ではない", "項目表示名\n※必須", "ページ（開始）\n※必須・数字", "ページ（終了）", "学習時期\n※必須・数字", "目標学習時間\n※必須・数字"};
        if (sheet != null && sheet.getLastRowNum() != 0) {
            if (!ExcelUtils.isApplicable(sheet.getRow(0), title)) {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0078", "教科書単元詳細"));
            }
            //教科書重複チェック
            MstTextbEntity mstTextbEntityByTextbNm = mstTextbDao.selectOne(new QueryWrapper<MstTextbEntity>()
                    .eq("subjt_div", subjtDiv).eq("org_id", orgId).eq("publisher_div", publisherDiv)
                    .eq("schy_div", schyDiv).eq("textb_nm", textbNm).eq("del_flg", 0));
            if (mstTextbEntityByTextbNm != null) {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0046", textbNm, "教科書マスタ"));
            }

            //教科書マスタ
            MstTextbEntity mstTextbEntity = new MstTextbEntity();
            //教科書区分
            mstTextbEntity.setSubjtDiv(subjtDiv);
            //組織ID
            mstTextbEntity.setOrgId(orgId);
            //教科書名
            mstTextbEntity.setTextbNm(textbNm);
            //出版社区分
            mstTextbEntity.setPublisherDiv(publisherDiv);
            //学年区分
            mstTextbEntity.setSchyDiv(schyDiv);
            //教科書廃止
            mstTextbEntity.setTextbAboltFlg("0");
            //作成日時
            mstTextbEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザID
            mstTextbEntity.setCretUsrId(usrId);
            //更新日時
            mstTextbEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザID
            mstTextbEntity.setUpdUsrId(usrId);
            //削除フラグ
            mstTextbEntity.setDelFlg(0);
            mstTextbDao.insert(mstTextbEntity);

            MstUnitEntity mstUnitEntity;
            MstLearnSeasnEntity mstLearnSeasnEntity;
            //2行をから遍歴し、論理を処理する
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row r = sheet.getRow(i);
                if (ExcelUtils.isEmptyRow(r, title.length)) {
                    break;
                }
                //教科書デフォルトターム情報
                TextbDefTimeInfoEntity textbDefTimeInfoEntity = new TextbDefTimeInfoEntity();
                //教科書ID
                textbDefTimeInfoEntity.setTextbId(mstTextbEntity.getTextbId());
                //教科区分
                textbDefTimeInfoEntity.setSubjtDiv(subjtDiv);
                //組織ID
                textbDefTimeInfoEntity.setOrgId(orgId);

                //インポートファイル
                //単元ID
                Cell unitId = r.getCell(0);
                //空白チェック
                if (unitId != null) {
                    unitId.setCellType(CellType.STRING);
                    if (StringUtils.isEmpty(unitId.getStringCellValue())) {
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0063", String.valueOf(i + 1), "単元ID"));
                    }
                    try {
                        mstUnitEntity = mstUnitDao.selectOne(new QueryWrapper<MstUnitEntity>()
                                .eq("id", Integer.parseInt(unitId.getStringCellValue())).eq("del_flg", 0));
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0079", String.valueOf(i + 1)));
                    }
                    //単元マスタ存在チェック
                    if (mstUnitEntity != null) {
                        try {
                            //setUnitId
                            textbDefTimeInfoEntity.setUnitId(Integer.parseInt(unitId.getStringCellValue()));
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0079", String.valueOf(i + 1)));
                        }
                    } else {
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0068", String.valueOf(i + 1), "単元ID"));
                    }
                } else {
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0063", String.valueOf(i + 1), "単元ID"));
                }

                //単元NO
                Cell unitNo = r.getCell(3);
                if (unitNo != null) {
                    unitNo.setCellType(CellType.STRING);
                    try {
                        //setUnitNo
                        textbDefTimeInfoEntity.setUnitNo(unitNo.getStringCellValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0079", String.valueOf(i + 1)));
                    }
                } else {
                    textbDefTimeInfoEntity.setUnitNo("");
                }

                //章NO
                Cell chaptNo = r.getCell(1);
                if (chaptNo != null) {
                    chaptNo.setCellType(CellType.STRING);
                    try {
                        //setChaptNo
                        textbDefTimeInfoEntity.setChaptNo(chaptNo.getStringCellValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0079", String.valueOf(i + 1)));
                    }
                } else {
                    textbDefTimeInfoEntity.setChaptNo("");
                }

                //節NO
                Cell sectnNo = r.getCell(2);
                if (sectnNo != null) {
                    sectnNo.setCellType(CellType.STRING);
                    try {
                        //setSectNo
                        textbDefTimeInfoEntity.setSectnNo(sectnNo.getStringCellValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0079", String.valueOf(i + 1)));
                    }
                } else {
                    textbDefTimeInfoEntity.setSectnNo("");
                }

                //単元表示名
                Cell unitDispyNm = r.getCell(6);
                //空白チェック
                if (unitDispyNm != null) {
                    unitDispyNm.setCellType(CellType.STRING);
                    if (!StringUtils.isEmpty(unitDispyNm.getStringCellValue())) {
                        try {
                            //setUnitDispyNm
                            textbDefTimeInfoEntity.setUnitDispyNm(unitDispyNm.getStringCellValue());
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0079", String.valueOf(i + 1)));
                        }
                    } else {
                        textbDefTimeInfoEntity.setUnitDispyNm(mstUnitEntity.getUnitNm());
                    }
                } else {
                    textbDefTimeInfoEntity.setUnitDispyNm(mstUnitEntity.getUnitNm());
                }

                //ページ
                Cell startPage = r.getCell(7);
                Cell endPage = r.getCell(8);
                if (startPage != null) {
                    startPage.setCellType(CellType.STRING);
                    if (StringUtils.isEmpty(startPage.getStringCellValue())) {
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0063", String.valueOf(i + 1), sheet.getRow(0).getCell(7).toString()));
                    }
                    if (endPage != null) {
                        endPage.setCellType(CellType.STRING);
                        if (!StringUtils.isEmpty(endPage.getStringCellValue())) {
                            //startPageとendPage全部は非空の場合
                            try {
                                //setTextbPage
                                textbDefTimeInfoEntity.setTextbPage(startPage.getStringCellValue() + "-" + endPage.getStringCellValue());
                            } catch (Exception e) {
                                e.printStackTrace();
                                throw new RRException(MessageUtils.getMessage("MSGCOMN0079", String.valueOf(i + 1)));
                            }
                        } else {
                            //endPageは空の場合
                            textbDefTimeInfoEntity.setTextbPage(startPage.getStringCellValue());
                        }
                    } else {
                        //setTextbPage
                        textbDefTimeInfoEntity.setTextbPage(startPage.getStringCellValue());
                    }
                } else {
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0063", String.valueOf(i + 1), sheet.getRow(0).getCell(7).toString()));
                }

                //計画学習時期
                Cell planLearnSeasn = r.getCell(9);
                //空白チェック
                if (planLearnSeasn != null) {
                    planLearnSeasn.setCellType(CellType.STRING);
                    if (!StringUtils.isEmpty(planLearnSeasn.getStringCellValue())) {
                        //学習時期存在する判定
                        try {
                            mstLearnSeasnEntity = mstLearnSeasnDao.selectOne(new QueryWrapper<MstLearnSeasnEntity>()
                                    .eq("plan_learn_seasn", Integer.parseInt(planLearnSeasn.getStringCellValue()))
                                    .eq("crm_learn_prd_id", crmLearnPrdId).eq("del_flg", 0));
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0079", String.valueOf(i)));
                        }
                        if (mstLearnSeasnEntity != null) {
                            try {
                                //setPlanLearnSeasn
                                textbDefTimeInfoEntity.setPlanLearnSeasn(Integer.parseInt(planLearnSeasn.getStringCellValue()));
                            } catch (Exception e) {
                                e.printStackTrace();
                                throw new RRException(MessageUtils.getMessage("MSGCOMN0079", String.valueOf(i + 1)));
                            }
                        } else {
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0068", String.valueOf(i + 1), "学習時期"));
                        }
                    } else {
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0063", String.valueOf(i + 1), "学習時期"));
                    }
                } else {
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0063", String.valueOf(i + 1), "学習時期"));
                }

                //計画学習時間（分）
                Cell planLearnTm = r.getCell(10);
                //空白チェック
                if (planLearnTm != null) {
                    planLearnTm.setCellType(CellType.STRING);
                    if (!StringUtils.isEmpty(planLearnTm.getStringCellValue())) {
                        try {
                            /* 2021/1/18 UT-143 cuikailin add start */
                            int learnTm = Integer.parseInt(planLearnTm.getStringCellValue());
                            if (learnTm % 15 != 0){
                                double num = learnTm/15;
                                learnTm = 15 * ((int)num+1);
                            }
                            /* 2021/1/18 UT-143 cuikailin add end */
                            //setPlanLearnTm
                            textbDefTimeInfoEntity.setPlanLearnTm(learnTm);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0079", String.valueOf(i + 1)));
                        }
                    } else {
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0063", String.valueOf(i + 1), "目標学習時間"));
                    }
                } else {
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0063", String.valueOf(i + 1), "目標学習時間"));
                }


                //表示順番
                textbDefTimeInfoEntity.setDispyOrder(i);
                //作成ユーザＩＤ
                textbDefTimeInfoEntity.setCretUsrId(usrId);
                //更新日時
                textbDefTimeInfoEntity.setCretDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                textbDefTimeInfoEntity.setUpdUsrId(usrId);
                //更新ユーザＩＤ
                textbDefTimeInfoEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //削除フラグ
                textbDefTimeInfoEntity.setDelFlg(0);
                try {
                    textbDefTimeInfoDao.insert(textbDefTimeInfoEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0079", String.valueOf(i + 1)));
                }
            }
            try {
                wb.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return R.ok();
    }

    /**
     * 数字判断
     *
     * @param str 文字列
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 塾学習期間IDの取得
     *
     * @param textbId 教科書ID
     * @param orgId   組織ID
     * @param brandCd ブランドコード
     * @return
     */
    @Override
    public Integer selectMstCrmschLearnPrdId(Integer textbId, String orgId, String brandCd) {
        return f03001Dao.selectMstCrmschLearnPrdId(textbId, orgId, brandCd);
    }
}
