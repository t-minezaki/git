package jp.learningpark.modules.manager.dto;

import java.io.Serializable;
import java.util.List;

public class F00001FunctionTypeDto implements Serializable {
    /**
     * 機能分類名
     */
    private String name;
    /**
     * 機能ID
     */
    private String funId;

    /**
     * 機能一覧
     */
    private List<F00001Dto> functionList;

    /**
     * 画面.機能分類名
     * @return 機能分類名
     */
    public String getName() {
        return name;
    }

    /**
     * 画面.機能分類名
     * @param name 画面.機能分類名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 画面.機能一覧
     * @return 機能一覧
     */
    public List<F00001Dto> getFunctionList() {
        return functionList;
    }

    /**
     * 画面.機能一覧
     * @param functionList 画面.機能一覧
     */
    public void setFunctionList(List<F00001Dto> functionList) {
        this.functionList = functionList;
    }

    /**
     * 機能IDを取得する
     *
     * @return funId 機能ID
     */
    public String getFunId() {
        return this.funId;
    }

    /**
     * 機能IDを設定する
     *
     * @param funId 機能ID
     */
    public void setFunId(String funId) {
        this.funId = funId;
    }
}
