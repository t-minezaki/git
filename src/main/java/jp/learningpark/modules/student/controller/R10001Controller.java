package jp.learningpark.modules.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDiv;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.FileUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.StuFixdSchdlEntity;
import jp.learningpark.modules.common.service.StuFixdSchdlService;
import jp.learningpark.modules.common.utils.dto.SchdlDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.student.dto.F1030104Dto;
import jp.learningpark.modules.student.dto.F1030105Dto;
import jp.learningpark.modules.student.service.F10301Service;
import jp.learningpark.modules.xapi.Activitys;
import jp.learningpark.modules.xapi.Extensions;
import jp.learningpark.modules.xapi.Verbs;
import jp.learningpark.modules.xapi.XApiConstant;
import jp.learningpark.modules.xapi.XApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * R10001_????????????????????????????????????????????? Controller?????????
 * </p>
 *
 * @author NWT : gaoli <br />
 * ???????????? <br />
 * 2018/10/09 : gaoli: ??????<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/student")
public class R10001Controller {

    // ?????????????????????
    private static BaseFont baseFont;

    // ??????
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * ???????????? Service?????????
     */
    @Autowired
    private CommonService commonService;

    /**
     * ????????????????????????????????????????????? Service???
     */
    @Autowired
    private F10301Service f10301Service;

    /**
     * ????????????????????????????????????
     */
    @Autowired
    private StuFixdSchdlService stuFixdSchdlService;

    // ID???????????????
    private Map<Integer, F1030104Dto> dataMap = new HashMap<Integer, F1030104Dto>();

    /**
     * ?????????????????????
     *
     * @param tgtYmd ????????????
     * @param url
     * @return ?????????????????????
     */
    @RequestMapping(value = "/F10301/print", method = RequestMethod.POST)
    public R print(@RequestParam String tgtYmd, String url) throws Exception {

        //??????????????????
        Extensions extensions = new Extensions();
        //????????????????????????ID
        extensions.put(XApiConstant.EXT_KEY_USER_ID, ShiroUtils.getUserId());
        //?????????URL
        extensions.put(XApiConstant.EXT_KEY_URL, url);
        //?????????????????????
        extensions.put(XApiConstant.EXT_KEY_VISIT_TIME, DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
        XApiUtils.saveStatement(Verbs.launched(), Activitys.schedule(), extensions);

        if (StringUtils.isEmpty(tgtYmd)) {
            return R.error(MessageUtils.getMessage("MSGCOMD0001", "???????????????"));
        }
        Date tgtYmdDate = DateUtils.parse(tgtYmd, Constant.DATE_FORMAT_YYYYMMDD);
        // ????????????
        Date monday = DateUtils.getMonday(tgtYmdDate);
        Date before = DateUtils.addDays(monday, -1);
        // ????????????
        Date sunday = DateUtils.getSunday(tgtYmdDate);

        // ??????????????????????????????
        Map<String, String> stuInfo = f10301Service.getStudentInfo();

        //0705  ?????????3.5 no9 no10
        // ????????????????????????????????????
        List<StuFixdSchdlEntity> stuFixdSchdlEntityList = stuFixdSchdlService.list(
                new QueryWrapper<StuFixdSchdlEntity>().eq("stu_id", ShiroUtils.getUserId()).eq("del_flg", 0));
        List<SchdlDto> schdlDtoList = new ArrayList<SchdlDto>();
        Date tgtTime = DateUtils.addHours(monday, 2);
        if (stuFixdSchdlEntityList != null) {
            for (int i = 0; i < stuFixdSchdlEntityList.size(); i++) {
                boolean checkStart = false;
                boolean checkEnd = false;
                String one = stuFixdSchdlEntityList.get(i).getBlockStartTime() + "";
                String two = stuFixdSchdlEntityList.get(i).getBlockEndTime() + "";
                one = one.split(" ")[0];
                two = two.split(" ")[0];
                if (DateUtils.parse(one, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS).compareTo(stuFixdSchdlEntityList.get(i).getBlockStartDate()) > 0) {
                    checkStart = true;
                }
                if (DateUtils.parse(two, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS).compareTo(stuFixdSchdlEntityList.get(i).getBlockEndDate()) > 0) {
                    checkEnd = true;
                }
                schdlDtoList.addAll(
                        commonService.getPrintFixedBlockList(stuFixdSchdlEntityList.get(i).getId(), ShiroUtils.getUserId(), before, checkStart, checkEnd));
            }
            schdlDtoList.addAll(commonService.getPrintPlanBlockList(ShiroUtils.getUserId(), before, sunday, "all"));
            for (int i = 0; i < schdlDtoList.size(); i++) {
                //????????????????????????????????????????????????????????????
                if (DateUtils.parse(schdlDtoList.get(i).getStartTime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).compareTo(tgtTime) < 0) {
                    schdlDtoList.get(i).setStartTime(DateUtils.format(tgtTime, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO));
                }
            }
        }
        // ??????????????????????????????????????????
        List<F1030105Dto> plannedList = f10301Service.getPrintPlannedBlock(monday, sunday);

        // ?????????????????????????????????
        String fileName = "R10001_" + DateUtils.format(DateUtils.getSysTimestamp(), Constant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".pdf";

        File file = FileUtils.getStorageFile(MessageUtils.getMessage("path.pdf"), ShiroUtils.getUserId(), fileName);
        file.getParentFile().mkdirs();

        logger.info("createPdf start");
        createPdf(monday, stuInfo, schdlDtoList, plannedList, file.getPath());
        logger.info("createPdf end");

        return R.ok().put("fileName", file.getName());
    }

    /**
     * ?????????????????????????????????
     *
     * @param url ?????????URL
     * @return ?????????????????????
     */
    @RequestMapping(value = "/F10301/todayPrint", method = RequestMethod.POST)
    public R todayPrint(String url) throws Exception {

        //??????????????????
        Extensions extensions = new Extensions();
        //????????????????????????ID
        extensions.put(XApiConstant.EXT_KEY_USER_ID, ShiroUtils.getUserId());
        //?????????URL
        extensions.put(XApiConstant.EXT_KEY_URL, url);
        //?????????????????????
        extensions.put(XApiConstant.EXT_KEY_VISIT_TIME, DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
        XApiUtils.saveStatement(Verbs.launched(), Activitys.schedule(), extensions);

        // ????????????
        Date monday = DateUtils.parse(DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS),
                GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        Date before = DateUtils.addDays(monday, -1);
        // ????????????
        Date sunday = DateUtils.addDays(monday, 6);

        // ??????????????????????????????
        Map<String, String> stuInfo = f10301Service.getStudentInfo();

        //0705  ?????????3.5 no9 no10
        // ????????????????????????????????????
        List<StuFixdSchdlEntity> stuFixdSchdlEntityList = stuFixdSchdlService.list(
                new QueryWrapper<StuFixdSchdlEntity>().eq("stu_id", ShiroUtils.getUserId()).eq("del_flg", 0));
        List<SchdlDto> schdlDtoList = new ArrayList<SchdlDto>();
        Date tgtTime = DateUtils.addHours(monday, 2);
        if (stuFixdSchdlEntityList != null) {
            for (int i = 0; i < stuFixdSchdlEntityList.size(); i++) {
                boolean checkStart = false;
                boolean checkEnd = false;
                String one = stuFixdSchdlEntityList.get(i).getBlockStartTime() + "";
                String two = stuFixdSchdlEntityList.get(i).getBlockEndTime() + "";
                one = one.split(" ")[0];
                two = two.split(" ")[0];
                if (DateUtils.parse(one, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS).compareTo(stuFixdSchdlEntityList.get(i).getBlockStartDate()) > 0) {
                    checkStart = true;
                }
                if (DateUtils.parse(two, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS).compareTo(stuFixdSchdlEntityList.get(i).getBlockEndDate()) > 0) {
                    checkEnd = true;
                }
                schdlDtoList.addAll(
                        commonService.getPrintFixedBlockList(stuFixdSchdlEntityList.get(i).getId(), ShiroUtils.getUserId(), before, checkStart, checkEnd));
            }
            schdlDtoList.addAll(commonService.getPrintPlanBlockList(ShiroUtils.getUserId(), before, sunday, "all"));
            for (int i = 0; i < schdlDtoList.size(); i++) {
                //????????????????????????????????????????????????????????????
                if (DateUtils.parse(schdlDtoList.get(i).getStartTime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).compareTo(tgtTime) < 0) {
                    schdlDtoList.get(i).setStartTime(DateUtils.format(tgtTime, GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO));
                }
            }
        }
        // ??????????????????????????????????????????
        List<F1030105Dto> plannedList = f10301Service.getPrintPlannedBlock(monday, sunday);

        // ?????????????????????????????????
        String fileName = "R10001_" + DateUtils.format(DateUtils.getSysTimestamp(), Constant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".pdf";

        File file = FileUtils.getStorageFile(MessageUtils.getMessage("path.pdf"), ShiroUtils.getUserId(), fileName);

        file.getParentFile().mkdirs();

        logger.info("createPdf start");
        createPdf(monday, stuInfo, schdlDtoList, plannedList, file.getPath());
        logger.info("createPdf end");

        return R.ok().put("fileName", file.getName());
    }

    /**
     * PDF???????????????????????????
     *
     * @param response ?????????????????????????????????
     * @param fileName ???????????????
     * @return ?????????????????????
     */
    @RequestMapping(value = "/F10301/download", method = RequestMethod.POST)
    public void download(HttpServletResponse response, @RequestParam String fileName) throws Exception {
        BufferedOutputStream outBuffer = null;
        BufferedInputStream inBuffer = null;
        File file = null;
        try {
            file = FileUtils.getStorageFile(MessageUtils.getMessage("path.pdf"), ShiroUtils.getUserId(), fileName);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            outputStream = new ByteArrayOutputStream();
            inBuffer = new BufferedInputStream(new FileInputStream(file));
            int line = 0;
            // ????????????????????????-1?????????????????????
            while ((line = inBuffer.read()) >= 0) {
                // ??????????????????????????????
                outputStream.write(line);
            }
            byte[] buffer = outputStream.toByteArray();
            response.reset();
            response.addHeader("Content-Length", "" + outputStream.size());
            // ?????????????????????????????????
            String downloadFileName = "?????????????????????.pdf";
            response.setHeader(
                    "Content-Disposition", "attachment; fileName=" + downloadFileName + ";filename*=utf-8''" + URLEncoder.encode(downloadFileName, "UTF-8"));
            outBuffer = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outBuffer.write(buffer);
            //???????????????
            outBuffer.flush();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (outBuffer != null) {
                outBuffer.close();
            }
            if (inBuffer != null) {
                inBuffer.close();
            }
            if (file != null && file.exists()) {
                // ??????????????????????????????????????????????????????
                file.delete();
            }
        }
    }

    /**
     * PDF???????????????
     *
     * @param tgtYmd ????????????
     * @param stuInfo ??????????????????????????????
     * @param schdlList ????????????????????????????????????
     * @param plannedList ??????????????????????????????????????????
     * @param outPath ??????????????????
     * @return ?????????????????????
     */
    private void createPdf(Date tgtYmd, Map<String, String> stuInfo, List<SchdlDto> schdlList, List<F1030105Dto> plannedList, String outPath) {
        PdfReader reader = null;
        PdfStamper stamper = null;
        Document document = null;
        PdfContentByte cb = null;
        try {
            baseFont = BaseFont.createFont(AsianFontMapper.JapaneseFont_Go, AsianFontMapper.JapaneseEncoding_H, BaseFont.NOT_EMBEDDED);
            // ?????????????????? ???????????????
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            reader = new PdfReader(this.getClass().getResourceAsStream("/templates/pdf/R10001.pdf"));
            stamper = new PdfStamper(reader, baos);
            // ???????????????????????????
            AcroFields form = stamper.getAcroFields();
            form.addSubstitutionFont(baseFont);
            // ?????????
            form.setField("stuNm", stuInfo.get("stu_nm"));
            // ??????ID
            form.setField("stuId", stuInfo.get("stu_id"));
            // ??????
            form.setField("schy", stuInfo.get("schy"));
            // ?????????
            form.setField("prtDt", DateUtils.format(DateUtils.getSysDate(), Constant.DATE_FORMAT_YYYYMD));
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();

            // PDF????????????
            reader = new PdfReader(baos.toByteArray());
            Rectangle pageSize = reader.getPageSize(1);
            document = new Document(PageSize.A4.rotate());
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outPath));
            // ????????????
            document.open();

            // ?????????????????????????????????
            PdfImportedPage page = writer.getImportedPage(reader, 1);
            cb = writer.getDirectContent();
            // PDF?????????????????????????????????
            PdfTemplate template = cb.createTemplate(pageSize.getWidth(), pageSize.getHeight());
            template.eoClip();
            template.newPath();
            template.addTemplate(page, 0, 0);
            cb.addTemplate(template, 0, 0);

            // ??????????????????????????????
            PdfPTable table = new PdfPTable(8);
            table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            table.setWidthPercentage(new float[] {6.9f, 13.3f, 13.3f, 13.3f, 13.3f, 13.3f, 13.3f, 13.3f}, pageSize);
            // ????????????
            PdfPCell cell = new PdfPCell(new Phrase(DateUtils.format(tgtYmd, Constant.DATE_FORMAT_YYYY)));
            cell.setColspan(8);
            cell.setFixedHeight(30f);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setPaddingLeft(10f);
            cell.setBorderWidth(0.2f);
            table.addCell(cell);
            // ????????????
            table.addCell(this.getTitleCell("??????", new int[] {0, 1, 1, 1}));

            // ???????????????????????????
            table.addCell(this.getTitleCell(DateUtils.format(tgtYmd, Constant.DATE_FORMAT_M_D_E_SLASH, Locale.JAPANESE), new int[] {0, 2, 1, 0}));
            table.addCell(this.getTitleCell(DateUtils.format(DateUtils.addDays(tgtYmd, 1), Constant.DATE_FORMAT_M_D_E_SLASH, Locale.JAPANESE),
                    new int[] {0, 2, 1, 0}));
            table.addCell(this.getTitleCell(DateUtils.format(DateUtils.addDays(tgtYmd, 2), Constant.DATE_FORMAT_M_D_E_SLASH, Locale.JAPANESE),
                    new int[] {0, 2, 1, 0}));
            table.addCell(this.getTitleCell(DateUtils.format(DateUtils.addDays(tgtYmd, 3), Constant.DATE_FORMAT_M_D_E_SLASH, Locale.JAPANESE),
                    new int[] {0, 2, 1, 0}));
            table.addCell(this.getTitleCell(DateUtils.format(DateUtils.addDays(tgtYmd, 4), Constant.DATE_FORMAT_M_D_E_SLASH, Locale.JAPANESE),
                    new int[] {0, 2, 1, 0}));
            table.addCell(this.getTitleCell(DateUtils.format(DateUtils.addDays(tgtYmd, 5), Constant.DATE_FORMAT_M_D_E_SLASH, Locale.JAPANESE),
                    new int[] {0, 2, 1, 0}));
            table.addCell(this.getTitleCell(DateUtils.format(DateUtils.addDays(tgtYmd, 6), Constant.DATE_FORMAT_M_D_E_SLASH, Locale.JAPANESE),
                    new int[] {0, 1, 1, 0}));
            // ???????????????????????????
            Calendar cal = Calendar.getInstance();

            // ?????????ID?????????
            Map<String, List<Integer>> timeMap = new HashMap<String, List<Integer>>();
            // ?????????ID?????????
            dataMap = new HashMap<Integer, F1030104Dto>();

            // ????????????????????????
            for (int id = 0; id < schdlList.size(); id++) {
                SchdlDto dto = schdlList.get(id);
                // ????????????
                Date startTime = DateUtils.parse(dto.getStartTime(), Constant.DATE_FORMAT_YYYYMMDDHHMM_ISO);
                // ????????????
                Date endTime = DateUtils.parse(dto.getEndTime(), Constant.DATE_FORMAT_YYYYMMDDHHMM_ISO);

                F1030104Dto data = new F1030104Dto(id, 1, 1, dto.getBlockDispyNm());
                cal.setTime(startTime);

                if (cal.get(Calendar.MINUTE) < 30) {
                    cal.set(Calendar.MINUTE, 0);
                } else if (cal.get(Calendar.MINUTE) > 30) {
                    cal.set(Calendar.MINUTE, 30);
                }
                // ?????????????????????
                //?????????3.5 0705
                Date next = DateUtils.addDays(tgtYmd, 7);
                next = DateUtils.addHours(next, 1);
                if (endTime.compareTo(next) > 0) {
                    endTime = next;
                }
                long halfHourCnt = getHalfHourCnt(startTime, endTime);
                // ?????????????????????????????????
                boolean indexSetFlg = false;

                for (int i = 0; i < halfHourCnt; i++) {
                    String key = createKey(cal);
                    if (!timeMap.containsKey(key)) {
                        timeMap.put(key, new ArrayList<Integer>());
                    } else {
                        List<Integer> idList = timeMap.get(key);
                        List<Integer> usedIdx = new ArrayList<Integer>();

                        for (Integer dataId : idList) {
                            if (dataMap.get(dataId).getCount() <= idList.size()) {
                                dataMap.get(dataId).setCount(dataMap.get(dataId).getCount() + 1);
                            }
                            // ????????????????????????????????????????????????
                            data.setCount(dataMap.get(dataId).getCount());
                            // ????????????????????????????????????????????????
                            usedIdx.add(dataMap.get(dataId).getIndex());
                        }
                        // ?????????????????????????????????????????????
                        if (indexSetFlg == false) {
                            List<Integer> index = Stream.iterate(1, item->item + 1).limit(data.getCount()).collect(Collectors.toList());
                            index.removeAll(usedIdx);

                            if (!index.isEmpty()) {
                                data.setIndex(index.get(0));
                                indexSetFlg = true;
                            }
                        }
                    }
                    timeMap.get(key).add(id);

                    cal.add(Calendar.MINUTE, 30);
                }

                dataMap.put(id, data);
            }

            // ?????????????????????????????????
            cal.setTime(DateUtils.getSysDate());
            cal.set(Calendar.HOUR_OF_DAY, 5);
            cal.set(Calendar.MINUTE, 0);

            for (int i = 0; i < 40; i++) {
                int[] dashLine = {0, 2, 2, 0};
                int[] line = {0, 1, 2, 2};
                if (i == 39) {
                    dashLine[2] = 1;
                    line[2] = 1;
                }

                if (i % 2 == 0) {
                    PdfPCell calCell;
                    if (i != 38) {
                        calCell = this.getCell(DateUtils.format(cal.getTime(), Constant.DATE_FORMAT_H_MM), new int[] {0, 1, 2, 1});
                    } else {
                        calCell = this.getCell(DateUtils.format(cal.getTime(), Constant.DATE_FORMAT_H_MM), new int[] {0, 1, 1, 1});
                    }
                    calCell.setRowspan(2);
                    calCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    calCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    table.addCell(calCell);
                }
                //?????????3.5 no9
                if (i<38){
                    table.addCell(this.getCell(dashLine, timeMap.get(createKey(cal, getDayOfWeek(tgtYmd, 0)))));
                    table.addCell(this.getCell(dashLine, timeMap.get(createKey(cal, getDayOfWeek(tgtYmd, 1)))));
                    table.addCell(this.getCell(dashLine, timeMap.get(createKey(cal, getDayOfWeek(tgtYmd, 2)))));
                    table.addCell(this.getCell(dashLine, timeMap.get(createKey(cal, getDayOfWeek(tgtYmd, 3)))));
                    table.addCell(this.getCell(dashLine, timeMap.get(createKey(cal, getDayOfWeek(tgtYmd, 4)))));
                    table.addCell(this.getCell(dashLine, timeMap.get(createKey(cal, getDayOfWeek(tgtYmd, 5)))));
                    table.addCell(this.getCell(line, timeMap.get(createKey(cal, getDayOfWeek(tgtYmd, 6)))));
                }else {
                    table.addCell(this.getCell(dashLine, timeMap.get(createKey(cal, getDayOfWeek(tgtYmd, 1)))));
                    table.addCell(this.getCell(dashLine, timeMap.get(createKey(cal, getDayOfWeek(tgtYmd, 2)))));
                    table.addCell(this.getCell(dashLine, timeMap.get(createKey(cal, getDayOfWeek(tgtYmd, 3)))));
                    table.addCell(this.getCell(dashLine, timeMap.get(createKey(cal, getDayOfWeek(tgtYmd, 4)))));
                    table.addCell(this.getCell(dashLine, timeMap.get(createKey(cal, getDayOfWeek(tgtYmd, 5)))));
                    table.addCell(this.getCell(dashLine, timeMap.get(createKey(cal, getDayOfWeek(tgtYmd, 6)))));
                    table.addCell(this.getCell(line, timeMap.get(createKey(cal, getDayOfWeek(tgtYmd, 7)))));
                }

                cal.add(Calendar.MINUTE, 30);
            }

            // ?????????????????????????????????
            List<PdfPTable> prefTableList = this.createRightTable(plannedList, pageSize);

            for (int i = 0; i < prefTableList.size(); i++) {
                if (i != 0) {
                    document.newPage();
                    page = writer.getImportedPage(reader, 1);
                    template = cb.createTemplate(pageSize.getWidth(), pageSize.getHeight());
                    template.eoClip();
                    template.newPath();
                    template.addTemplate(page, 0, 0);
                    cb.addTemplate(template, 0, 0);
                }

                PdfPTable ctxTable = new PdfPTable(2);
                ctxTable.setWidthPercentage(100);
                ctxTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

                cell = new PdfPCell(table);
                cell.setPaddingRight(14);
                cell.setBorder(Rectangle.NO_BORDER);
                ctxTable.addCell(cell);

                cell = new PdfPCell(prefTableList.get(i));
                cell.setPaddingLeft(14);
                cell.setBorder(Rectangle.NO_BORDER);
                ctxTable.addCell(cell);
                PdfDiv div = new PdfDiv();
                div.setPosition(PdfDiv.PositionType.RELATIVE);
                div.setTop(66.5f);
                div.addElement(ctxTable);
                document.add(div);
                setFooter(cb, pageSize.getWidth(), stuInfo.get("stu_id"), stuInfo.get("stu_nm"), i + 1, prefTableList.size());
            }

            cb.closePath();

            document.close();
            reader.close();

        } catch (IOException | DocumentException e) {
            logger.error(e.getMessage());
        } finally {

            if (cb != null) {
                cb.closePath();
            }
            if (document != null) {
                document.close();
            }
            if (stamper != null) {
                try {
                    stamper.close();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            if (reader != null) {
                reader.close();
            }
        }

    }

    /**
     * PdfPCell???????????????
     *
     * @param value ??????
     * @param borderStyles ????????????????????????(1:?????? 2:?????? 0:??????)
     * @return PdfPCell
     */
    private PdfPCell getCell(String value, int[] borderStyles) throws DocumentException, IOException {
        Font font = new Font(baseFont, 6, Font.NORMAL);
        PdfPCell cell = new PdfPCell(new Phrase(value, font));
        cell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setFixedHeight(10.2f);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setCellEvent(new CellEvent(borderStyles));
        return cell;
    }

    /**
     * PdfPCell???????????????
     *
     * @param borderStyles ????????????????????????(1:?????? 2:?????? 0:??????)
     * @param eventIds ????????????ID?????????
     * @return PdfPCell
     */
    private PdfPCell getCell(int[] borderStyles, List<Integer> eventIds) throws DocumentException, IOException {

        PdfPTable ctxTable = null;
        PdfPCell cell;
        int cnt = 0;
        if (eventIds != null && !eventIds.isEmpty()) {
            cnt = dataMap.get(eventIds.get(0)).getCount();
            ctxTable = new PdfPTable(cnt);
            Map<Integer, F1030104Dto> map = new HashMap<Integer, F1030104Dto>();
            for (Integer id : eventIds) {
                map.put(dataMap.get(id).getIndex(), dataMap.get(id));
            }

            Font font = new Font(baseFont, 6, Font.NORMAL);
            if (cnt == 3 || cnt == 4) {
                font = new Font(baseFont, 5, Font.NORMAL);
            } else if (cnt > 4) {
                font = new Font(baseFont, 4, Font.NORMAL);
            }
            for (int i = 1; i <= cnt; i++) {
                if (!map.containsKey(i)) {
                    PdfPCell child = new PdfPCell();
                    child.setBorder(Rectangle.NO_BORDER);
                    ctxTable.addCell(child);
                } else {
                    F1030104Dto data = map.get(i);
                    PdfPCell child = null;
                    if (!StringUtils.isEmpty(data.getDispyNm())) {
                        child = new PdfPCell(new Phrase(data.getDispyNm(), font));
                        child.setCellEvent(new EventCellEvent(1));
                        dataMap.get(data.getId()).setDispyNm("");
                    } else {
                        child = new PdfPCell();
                        child.setCellEvent(new EventCellEvent(0));

                    }
                    child.setPadding(1.5f);
                    child.setBorder(Rectangle.NO_BORDER);
                    ctxTable.addCell(child);
                }
            }

            cell = new PdfPCell(ctxTable);
        } else {
            cell = new PdfPCell();
        }

        cell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setFixedHeight(10.2f);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setCellEvent(new CellEvent(borderStyles));

        return cell;
    }

    /**
     * ????????????PdfPCell???????????????
     *
     * @param value ??????
     * @param borderStyles ????????????????????????(1:?????? 2:?????? 0:??????)
     * @return PdfPCell
     */
    private PdfPCell getTitleCell(String value, int[] borderStyles) {
        Font font = new Font(baseFont, 7, Font.NORMAL);
        PdfPCell cell = new PdfPCell(new Phrase(value, font));
        cell.setBackgroundColor(new BaseColor(217, 217, 217));
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setFixedHeight(18);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setCellEvent(new CellEvent(borderStyles));
        return cell;
    }

    /**
     * ?????????????????????PdfPCell???????????????
     *
     * @param value ??????
     * @param borderStyles ????????????????????????(1:?????? 2:?????? 0:??????)
     * @return PdfPCell
     */
    private PdfPCell getRCell(String value, int[] borderStyles) throws DocumentException, IOException {
        return this.getRCell(value, borderStyles, PdfPCell.ALIGN_MIDDLE, PdfPCell.ALIGN_CENTER);
    }

    /**
     * ?????????????????????PdfPCell???????????????
     *
     * @param value ??????
     * @param borderStyles ????????????????????????(1:?????? 2:?????? 0:??????)
     * @param verticalAlignment ????????????????????????
     * @param horizontalAlignment ???????????????
     * @param fontSize
     * @return PdfPCell
     */
    private PdfPCell getRCell(String value, int[] borderStyles, int verticalAlignment, int horizontalAlignment, int fontSize) throws DocumentException, IOException {
        PdfPCell cell;
        Font font = new Font(baseFont, fontSize, Font.NORMAL);
        if (!StringUtils.isEmpty(value)) {
            cell = new PdfPCell(new Phrase(value, font));
        } else {
            cell = new PdfPCell();
        }
        cell.setVerticalAlignment(verticalAlignment);
        cell.setHorizontalAlignment(horizontalAlignment);
        cell.setFixedHeight(15.6f);
        if (borderStyles != null && borderStyles.length == 4) {
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setCellEvent(new CellEvent(borderStyles));
        }
        return cell;
    }

    /**
     * ?????????????????????PdfPCell???????????????
     *
     * @param value ??????
     * @param borderStyles ????????????????????????(1:?????? 2:?????? 0:??????)
     * @param verticalAlignment ????????????????????????
     * @param horizontalAlignment ???????????????
     * @return PdfPCell
     */
    private PdfPCell getRCell(String value, int[] borderStyles, int verticalAlignment, int horizontalAlignment) throws DocumentException, IOException {
        return this.getRCell(value, borderStyles, verticalAlignment, horizontalAlignment, 6);
    }

    /**
     * show line, units of fromX, fromY, toX and toY are centimeter, unit of lineWidth is point
     *
     * @param cb
     * @param lineWidth
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     */
    public static void drawLine(PdfContentByte cb, float lineWidth, float fromX, float fromY, float toX, float toY) {
        drawLine(cb, lineWidth, fromX, fromY, toX, toY, new BaseColor(0, 0, 0));
    }

    /**
     * show line, units of fromX, fromY, toX and toY are centimeter, unit of lineWidth is point
     *
     * @param cb
     * @param lineWidth
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @param color
     */
    public static void drawLine(PdfContentByte cb, float lineWidth, float fromX, float fromY, float toX, float toY, BaseColor color) {
        cb.setLineWidth(lineWidth);
        cb.setLineDash(1f);
        cb.moveTo(fromX, fromY);
        cb.lineTo(toX, toY);
        if (color != null) {
            cb.setColorStroke(color);
        }
        cb.stroke();
    }

    /**
     * show line, units of fromX, fromY, toX and toY are centimeter, unit of lineWidth is point
     *
     * @param cb
     * @param lineWidth
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     */
    public static void drawDashLine(PdfContentByte cb, float lineWidth, float fromX, float fromY, float toX, float toY) {
        cb.setLineWidth(lineWidth);
        cb.setLineDash(1f, 1f, 0f);
        cb.moveTo(fromX, fromY);
        cb.lineTo(toX, toY);
        cb.stroke();
    }

    /**
     * ?????????????????????????????????
     *
     * @param perfList ??????????????????
     * @param pageSize
     * @return PDF??????????????????
     */
    private List<PdfPTable> createRightTable(List<F1030105Dto> perfList, Rectangle pageSize) throws DocumentException, IOException {
        List<PdfPTable> tableList = new ArrayList<PdfPTable>();
        PdfPTable table = createTable(pageSize);
        int rowCount = 0;
        // ????????????????????????
        if (perfList.isEmpty()) {
            table = createTable(pageSize);
            // ????????????????????????
            for (int i = rowCount; i < rowCount + 14; i++) {
                addRow(table, new F1030105Dto());
            }
            tableList.add(table);
        } else {
            // ??????????????????????????????????????????
            while (rowCount < perfList.size()) {
                table = createTable(pageSize);
                for (int i = rowCount; i < rowCount + 14; i++) {
                    if (i < perfList.size()) {
                        addRow(table, perfList.get(i));
                    } else {
                        addRow(table, new F1030105Dto());
                    }
                }
                tableList.add(table);
                rowCount += 14;
            }
        }

        return tableList;
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param pageSize
     */
    private PdfPTable createTable(Rectangle pageSize) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(45);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        //        table.setWidthPercentage(new float[] { 8f, 10f, 6f, 10f, 24f, 9f, 7f, 5.2f, 1.8f, 19f }, pageSize);
        table.setWidthPercentage(new float[] {8f, 10f, 6f, 34f, 9f, 7f, 5.2f, 1.8f, 19f}, pageSize);
        // ????????????
        PdfPCell cell = this.getTitleCell("????????????", new int[] {1, 1, 1, 1});
        cell.setColspan(2);
        cell.setFixedHeight(19f);
        table.addCell(cell);
        table.addCell(this.getTitleCell("??????", new int[] {1, 2, 1, 1}));
        //        table.addCell(this.getTitleCell("???", new int[] { 1, 2, 1, 2 }));
        table.addCell(this.getTitleCell("??????", new int[] {1, 2, 1, 2}));
        table.addCell(this.getTitleCell("?????????", new int[] {1, 1, 1, 2}));
        table.addCell(this.getTitleCell("??????", new int[] {1, 2, 1, 1}));
        cell = this.getTitleCell("??????", new int[] {1, 2, 1, 1});
        cell.setColspan(2);
        table.addCell(cell);
        table.addCell(this.getTitleCell("?????????", new int[] {1, 1, 1, 1}));

        return table;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param table PDF??????????????????????????????
     * @param dto ??????????????????
     */
    private void addRow(PdfPTable table, F1030105Dto dto) throws DocumentException, IOException {
        // ???????????????
        PdfPCell cell = this.getRCell(null, new int[] {1, 1, 1, 1});
        if (dto.getPlanYmd() != null) {
            cell.addElement(createElement(DateUtils.format(dto.getPlanYmd(), Constant.DATE_FORMAT_M_D_SLASH)));
            cell.addElement(createElement("(" + DateUtils.format(dto.getPlanYmd(), Constant.DATE_FORMAT_E, Locale.JAPANESE) + ")"));
        } else {
            cell.addElement(createElement(""));
            cell.addElement(createElement(""));
        }

        cell.setRowspan(2);
        table.addCell(cell);
        // ?????????????????????
        cell = this.getRCell(null, new int[] {1, 1, 1, 1});
        if (dto.getStartTime() != null) {
            cell.addElement(createElement(DateUtils.format(dto.getStartTime(), Constant.DATE_FORMAT_YYYYMMDDHHMM_ISO, Constant.DATE_FORMAT_HH_MM) + "???"));
            //?????????3.5 no21
            Date checkStartTime = DateUtils.parse(dto.getCheckStartTime(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
            Date checkEndTime = DateUtils.parse(dto.getCheckEndTime(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
            if (checkStartTime.compareTo(checkEndTime) < 0) {
                cell.addElement(createElement(DateUtils.format(dto.getEndTime(), Constant.DATE_FORMAT_YYYYMMDDHHMM_ISO, Constant.DATE_FORMAT_HH_MM) + "(??????)"));
            } else {
                cell.addElement(createElement(DateUtils.format(dto.getEndTime(), Constant.DATE_FORMAT_YYYYMMDDHHMM_ISO, Constant.DATE_FORMAT_HH_MM)));
            }
        } else {
            cell.addElement(createElement(""));
            cell.addElement(createElement(""));
        }

        cell.setRowspan(2);
        table.addCell(cell);
        // ??????
        table.addCell(this.getRCell(dto.getSubjtNm(), new int[] {1, 2, 1, 1}));
        //        // ??????
        //        table.addCell(this.getRCell(dto.getChaptNm(), new int[] { 1, 2, 1, 2 }, PdfPCell.ALIGN_MIDDLE, PdfPCell.ALIGN_LEFT));
        if (GakkenConstant.BLOCK_TYPE_DIV_S1.equals(dto.getBlockTypeDiv())) {
            String blockDispyNm = dto.getBlockDispyNm();
//            if (!StringUtils.isEmpty(blockDispyNm) && StringUtils.getStringCnt(blockDispyNm, " ") > 1) {
//                int blankIndex = 0;
//                blankIndex = StringUtils.indexOf(blockDispyNm, " ");
//                blankIndex = StringUtils.indexOf(blockDispyNm, " ", blankIndex + 1);
//                blockDispyNm = blockDispyNm.substring(blankIndex + 1);
//            }
            // ????????? ?????????no21
            if (!StringUtils.isEmpty(blockDispyNm) && StringUtils.getStringCnt(blockDispyNm, " ") > 1) {
                blockDispyNm = blockDispyNm.split(" ",2)[1];
            } else {
                blockDispyNm = "";
            }
            table.addCell(this.getRCell(blockDispyNm, new int[] {1, 2, 1, 2}, PdfPCell.ALIGN_MIDDLE, PdfPCell.ALIGN_LEFT, 5));
        } else {
            // ?????????
            table.addCell(this.getRCell(dto.getBlockDispyNm(), new int[] {1, 2, 1, 2}, PdfPCell.ALIGN_MIDDLE, PdfPCell.ALIGN_LEFT, 5));
        }

        // ?????????
        table.addCell(this.getRCell(dto.getTextbPage(), new int[] {1, 1, 1, 2}));
        // ??????
        cell = this.getRCell("???", new int[] {1, 2, 1, 1}, PdfPCell.ALIGN_BOTTOM, PdfPCell.ALIGN_RIGHT, 4);
        cell.setPaddingBottom(5f);
        table.addCell(cell);
        // ????????????
        String planTimes = dto.getStuPlanLearnTm();
        if (StringUtils.isEmpty(planTimes)) {
            planTimes = "0";
        }
        cell = this.getRCell(planTimes, new int[] {1, 0, 1, 1}, PdfPCell.ALIGN_BOTTOM, PdfPCell.ALIGN_RIGHT, 7);
        cell.setPaddingRight(0);
        cell.setPaddingBottom(5f);
        table.addCell(cell);
        cell = this.getRCell("???", new int[] {1, 1, 1, 0}, PdfPCell.ALIGN_BOTTOM, PdfPCell.ALIGN_LEFT, 4);
        cell.setPaddingLeft(0);
        cell.setPaddingBottom(5f);
        table.addCell(cell);
        // ?????????
        cell = this.getRCell(null, new int[] {1, 1, 1, 1}, PdfPCell.ALIGN_TOP, PdfPCell.ALIGN_LEFT);
        Paragraph element = createElement("???90%   ???75%", 5, Element.ALIGN_LEFT);
        element.setLeading(5.8f);
        cell.addElement(element);
        element = createElement("???60%   ???59%??????", 5, Element.ALIGN_LEFT);
        element.setLeading(5.8f);
        cell.addElement(element);
        table.addCell(cell);
        // memo
        cell = this.getRCell("memo", new int[] {1, 0, 1, 1});
        cell.setBackgroundColor(new BaseColor(220, 220, 220));
        table.addCell(cell);
        // ??????
        cell = this.getRCell("", new int[] {1, 1, 1, 0});
        cell.setColspan(7);
        table.addCell(cell);
    }

    /**
     * ?????????????????????
     *
     * @param text ??????
     * @param fontSize ?????????????????????
     * @param alignment ??????????????????
     * @return ??????
     */
    private Paragraph createElement(String text, int fontSize, int alignment) {
        Paragraph paragraph = new Paragraph(text, new Font(baseFont, fontSize, Font.NORMAL));
        paragraph.setAlignment(alignment);
        return paragraph;
    }

    /**
     * ?????????????????????
     *
     * @param text ??????
     * @param fontSize ?????????????????????
     * @return ??????
     */
    private Paragraph createElement(String text, int fontSize) {
        return this.createElement(text, fontSize, Element.ALIGN_CENTER);
    }

    /**
     * ?????????????????????
     *
     * @param text ??????
     * @return ??????
     */
    private Paragraph createElement(String text) {
        return this.createElement(text, 6);
    }

    /**
     * ?????????????????????????????????
     *
     * @param cb ??????
     * @param width ???
     * @param stuId ??????ID
     * @param stuNm ?????????
     * @param pageNum ?????????
     * @param pageCount ?????????????????????
     */
    public void setFooter(PdfContentByte cb, float width, String stuId, String stuNm, int pageNum, int pageCount) {
        StringBuffer footer = new StringBuffer();
        int size = 12;
        footer.append(StringUtils.rightPad(stuId, size));
        footer.append("  ");
        String name = stuNm.replace(" ", "???");
        int left = (size - name.length()) / 2;
        name = StringUtils.leftPad(name, left + name.length());
        name = StringUtils.rightPad(name, size);
        footer.append(name);
        footer.append("  ???");
        footer.append(pageNum);
        footer.append("/");
        footer.append(pageCount);
        footer.append("???");

        cb.saveState();
        cb.beginText();
        cb.setFontAndSize(baseFont, 8);
        cb.setTextMatrix(width - 160, 25);
        cb.showText(footer.toString());
        cb.endText();
        cb.stroke();
        cb.restoreState();
    }

    /**
     * <p>
     * ?????????????????????????????????
     * </p>
     *
     * @param cal ??????
     * @param week
     * @return ???????????????
     */
    private String createKey(Calendar cal, Integer week) {
        StringBuffer key = new StringBuffer();
        if (week == null) {
            key.append(cal.get(Calendar.DAY_OF_WEEK));
        } else {
            key.append(week);
        }
        key.append("_");
        key.append(cal.get(Calendar.HOUR_OF_DAY));
        key.append("_");
        if (cal.get(Calendar.MINUTE) == 30) {
            key.append(0);
        } else {
            key.append(1);
        }
        return key.toString();
    }

    /**
     * <p>
     * ?????????????????????????????????
     * </p>
     *
     * @param cal ??????
     * @return ???????????????
     */
    private String createKey(Calendar cal) {
        return createKey(cal, null);
    }

    /**
     * <p>
     * ?????????????????????
     * </p>
     *
     * @param startDate ????????????
     * @param endDate ????????????
     * @return ?????????????????????
     */
    private long getHalfHourCnt(Date startDate, Date endDate) {
        long cnt = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 15);
        if (cnt % 2 == 1) {
            cnt = cnt + 1;
        }
        return cnt / 2;
    }

    /**
     * <p>
     * ???????????????????????????
     * </p>
     *
     * @param tgtYmd ?????????
     */
    private int getDayOfWeek(Date tgtYmd, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.addDays(tgtYmd, count));
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * <p>
     * Cell???????????? ?????????
     * </p>
     */
    public class CellEvent implements PdfPCellEvent {

        //  ????????????????????????(1:?????? 2:?????? 0:??????)
        private int[] borderStyles;

        public CellEvent(int[] borderStyles) {
            this.borderStyles = borderStyles;
        }

        /**
         * <p>
         * Cell???????????? ?????????
         * </p>
         */
        @Override
        public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
            float lineWidth = 0.2f;
            PdfContentByte cb = canvases[PdfPTable.LINECANVAS];
            cb.saveState();

            // top
            if (borderStyles[0] == 2) {
                drawDashLine(cb, lineWidth, position.getLeft(), position.getTop(), position.getRight(), position.getTop());
            } else if (borderStyles[0] == 1) {
                drawLine(cb, lineWidth, position.getLeft(), position.getTop(), position.getRight(), position.getTop());
            }
            // right
            if (borderStyles[1] == 2) {
                drawDashLine(cb, lineWidth, position.getRight(), position.getTop(), position.getRight(), position.getBottom());
            } else if (borderStyles[1] == 1) {
                drawLine(cb, lineWidth, position.getRight(), position.getTop(), position.getRight(), position.getBottom());
            }
            // Bottom
            if (borderStyles[2] == 2) {
                drawDashLine(cb, lineWidth, position.getLeft(), position.getBottom(), position.getRight(), position.getBottom());
            } else if (borderStyles[2] == 1) {
                drawLine(cb, lineWidth, position.getLeft(), position.getBottom(), position.getRight(), position.getBottom());
            }

            // left
            if (borderStyles[3] == 2) {
                drawDashLine(cb, lineWidth, position.getLeft(), position.getTop(), position.getLeft(), position.getBottom());
            } else if (borderStyles[3] == 1) {
                drawLine(cb, lineWidth, position.getLeft(), position.getTop(), position.getLeft(), position.getBottom());
            }
            cb.restoreState();
        }
    }

    /**
     * <p>
     * ????????????Cell???????????? ?????????
     * </p>
     */
    public class EventCellEvent implements PdfPCellEvent {

        // ?????????
        private int bgStyles;

        public EventCellEvent(int borderStyles) {
            this.bgStyles = borderStyles;
        }

        @Override
        public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
            PdfContentByte cb = canvases[PdfPTable.BACKGROUNDCANVAS];
            cb.saveState();
            float width = 1.5f;
            if (bgStyles == 1) {
                // top
                cb.setColorFill(new BaseColor(169, 169, 169));
                cb.rectangle(position.getLeft(), position.getTop(), position.getRight() - position.getLeft() - width, position.getBottom() - position.getTop());
                cb.fill();
                cb.setColorFill(new BaseColor(220, 220, 220));
                cb.rectangle(position.getLeft() + width, position.getTop() - width, position.getRight() - position.getLeft() - width * 2,
                        position.getBottom() - position.getTop() + width);
                cb.fill();
            } else {
                // left
                cb.setColorFill(new BaseColor(169, 169, 169));
                cb.rectangle(position.getLeft(), position.getTop(), width, position.getBottom() - position.getTop());
                cb.fill();
                cb.setColorFill(new BaseColor(220, 220, 220));
                cb.rectangle(position.getLeft() + width, position.getTop(), position.getRight() - position.getLeft() - width * 2,
                        position.getBottom() - position.getTop());
                cb.fill();
            }
            cb.restoreState();
        }
    }
}
