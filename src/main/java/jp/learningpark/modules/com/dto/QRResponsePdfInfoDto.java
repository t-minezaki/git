package jp.learningpark.modules.com.dto;

/**
 * <p>
 * QR解答相关 Dto
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/24 ： NWT)hxl ： 新規作成
 * @date 2020/02/24 16:46
 */
public class QRResponsePdfInfoDto {

    /**
     * 教材コード
     */
    private String bookcd;

    /**
     * Pdfファイル名
     */
    private String pdfname;

    /**
     * 級
     */
    //modify at 2021/08/19 for V9.02 by NWT LiGX START
    private String grade;

    /**
     * 集
     */
    private String stage;
   // modify at 2021/08/19 for V9.02 by NWT LiGX END

    /**
     * 枝番
     */
    private Integer no;

    /**
     * ページ
     */
    private Integer page;

    //add at 2021/09/08 for V9.02 by NWT LiGX START
    /**
     * 表示順_級
     */
    private Integer gradesort;

    /**
     * 表示順_集
     */
    private Integer stagesort;
    //add at 2021/09/08 for V9.02 by NWT LiGX END

    /**
     * 教材コードを取得する
     *
     * @return bookcd 教材コード
     */
    public String getBookcd() {
        return this.bookcd;
    }

    /**
     * 教材コードを設定する
     *
     * @param bookcd 教材コード
     */
    public void setBookcd(String bookcd) {
        this.bookcd = bookcd;
    }

    /**
     * Pdfファイル名を取得する
     *
     * @return pdfname Pdfファイル名
     */
    public String getPdfname() {
        return this.pdfname;
    }

    /**
     * Pdfファイル名を設定する
     *
     * @param pdfname Pdfファイル名
     */
    public void setPdfname(String pdfname) {
        this.pdfname = pdfname;
    }

    /**
     * 級を取得する
     *
     * @return grade 級
     */
    //modify at 2021/08/19 for V9.02 by NWT LiGX START
    public String getGrade() {
        return this.grade;
    }

    /**
     * 級を設定する
     *
     * @param grade 級
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * 集を取得する
     *
     * @return stage 集
     */
    public String getStage() {
        return this.stage;
    }

    /**
     * 集を設定する
     *
     * @param stage 集
     */
    public void setStage(String stage) {
        this.stage = stage;
    }
    //modify at 2021/08/19 for V9.02 by NWT LiGX END
    /**
     * 枝番を取得する
     *
     * @return no 枝番
     */
    public Integer getNo() {
        return this.no;
    }

    /**
     * 枝番を設定する
     *
     * @param no 枝番
     */
    public void setNo(Integer no) {
        this.no = no;
    }

    /**
     * ページを取得する
     *
     * @return page ページ
     */
    public Integer getPage() {
        return this.page;
    }

    /**
     * ページを設定する
     *
     * @param page ページ
     */
    public void setPage(Integer page) {
        this.page = page;
    }
    //add at 2021/09/08 for V9.02 by NWT LiGX START
    /**
     * 表示順_級を取得する
     *
     * @return gradesort 表示順_級
     */
    public Integer getGradesort() {
        return this.gradesort;
    }

    /**
     * 表示順_級を設定する
     *
     * @param gradesort 表示順_級
     */
    public void setGradesort(Integer gradesort) {
        this.gradesort = gradesort;
    }

    /**
     * 表示順_集を取得する
     *
     * @return stagesort 表示順_集
     */
    public Integer getStagesort() {
        return this.stagesort;
    }

    /**
     * 表示順_集を設定する
     *
     * @param stagesort 表示順_集
     */
    public void setStagesort(Integer stagesort) {
        this.stagesort = stagesort;
    }
    //add at 2021/09/08 for V9.02 by NWT LiGX END
}
