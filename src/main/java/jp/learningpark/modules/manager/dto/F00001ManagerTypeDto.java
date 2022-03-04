package jp.learningpark.modules.manager.dto;

import java.io.Serializable;
import java.util.List;

public class F00001ManagerTypeDto implements Serializable {
    /**
     * 管理分類名
     */
    private String name;
    /**
     * 画面.img
     */
    public String imgStr;

    /**
     * 機能分類一覧
     */
    private List<F00001FunctionTypeDto> functionTypeList;

    /**
     * 画面.管理分類名
     * @return 管理分類名
     */
    public String getName() {
        return name;
    }

    /**
     * 画面.管理分類名
     * @param name 画面.管理分類名
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 画面.機能分類一覧
     * @return 機能分類一覧
     */
    public List<F00001FunctionTypeDto> getFunctionTypeList() {
        return functionTypeList;
    }

    /**
     * 画面.機能分類一覧
     * @param functionTypeList 画面.機能分類一覧
     */
    public void setFunctionTypeList(List<F00001FunctionTypeDto> functionTypeList) {
        this.functionTypeList = functionTypeList;
    }

    /**
     * 画面.imgを取得する
     *
     * @return imgStr 画面.img
     */
    public String getImgStr() {
        return this.imgStr;
    }

    /**
     * 画面.imgを設定する
     *
     * @param imgStr 画面.img
     */
    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }
}
