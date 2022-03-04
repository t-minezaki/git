package jp.learningpark.modules.manager.dto;

import java.io.Serializable;
import java.util.List;

public class F40010FunctionTypeDto implements Serializable {

    /**
     * 機能分類名
     */
    private String name;

    /**
     * 機能一覧
     */
    private List<F40010Dto> functionList;

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
    public List<F40010Dto> getFunctionList() {
        return functionList;
    }

    /**
     * 画面.機能一覧
     * @param functionList 画面.機能一覧
     */
    public void setFunctionList(List<F40010Dto> functionList) {
        this.functionList = functionList;
    }
}