package jp.learningpark.modules.manager.dto;

import java.io.Serializable;

public class F40010Dto implements Serializable {

    /**
     * ID
     */
    private Integer id;

    /**
     * 機能名
     */
    private String name;

    /**
     * タイプ
     */
    private String type;

    /**
     * 画面.ID
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 画面.ID
     * @param id 画面.ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 画面.機能名
     * @return 機能名
     */
    public String getName() {
        return name;
    }

    /**
     * 画面.機能名
     * @param name 画面.機能名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 画面.タイプ
     * @return タイプ
     */
    public String getType() {
        return type;
    }

    /**
     * 画面.タイプ
     * @param type 画面.タイプ
     */
    public void setType(String type) {
        this.type = type;
    }
}
