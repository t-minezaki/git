package jp.learningpark.modules.manager.dto;

import java.util.Comparator;

/**
 * <p>
 * F21014LineDto
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/12/05 ： NWT)hxl ： 新規作成
 * @date 2019/12/05 16:19
 */
public class F21014LineDto {
    /**
     * 項目
     */
    private String content;
    /**
     * 教科
     */
    private String subject;
    /**
     * 31日間のコンテンツ
     */
    private String[] day = new String[31];

    /**
     * 項目を取得する
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * 項目を設定する
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 教科を取得する
     *
     * @return
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 教科を設定する
     *
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 31日間のコンテンツを取得する
     *
     * @return
     */
    public String[] getDay() {
        return day;
    }

    /**
     * 31日間のコンテンツを設定する
     *
     * @param day
     */
    public void setDay(String[] day) {
        this.day = day;
    }

    /**
     * Comparator for F21014LineDto.class
     */
    public static class SortByContent implements Comparator<F21014LineDto>
    {
        @Override
        public int compare(F21014LineDto a, F21014LineDto b)
        {
            if (!a.content.equals(b.content)){
                return a.content.compareTo(b.content);
            }else {
                return a.subject.compareTo(b.subject);
            }
        }
    }
}
