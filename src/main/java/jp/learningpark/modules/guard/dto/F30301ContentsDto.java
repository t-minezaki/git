package jp.learningpark.modules.guard.dto;

import java.util.Date;
import java.util.List;

public class F30301ContentsDto {

    // ニュースのコンテンツＩＤ
    String id;
    // ニュースのタイトル
    String title;
    // ニュースのコンテンツ
    String description;
    // ニュースの公開日時
    Date published_at;
    // ニュースの更新日時
    Date updated_at;
    // スコア
    String score;
    // ニュースの画像
    List<String> image_urls;
    // url
    String url;
    //ニュースの元サイト名(NWT譚新規)
    String sourceName;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublished_at() {
        return published_at;
    }

    public void setPublished_at(Date published_at) {
        this.published_at = published_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public List<String> getImage_urls() {
        return image_urls;
    }

    public void setImage_urls(List<String> image_urls) {
        this.image_urls = image_urls;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * ニュースの元サイト名(NWT譚新規)を取得する
     *
     * @return sourceName ニュースの元サイト名(NWT譚新規)
     */
    public String getSourceName() {
        return this.sourceName;
    }

    /**
     * ニュースの元サイト名(NWT譚新規)を設定する
     *
     * @param sourceName ニュースの元サイト名(NWT譚新規)
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
