package jp.learningpark.modules.manager.dto;

import java.io.Serializable;
import java.util.List;

public class F40010ManagerTypeDto implements Serializable {

    /**
     * 管理分類名
     */
    private String name;

    /**
     * 機能分類一覧
     */
    private List<F40010FunctionTypeDto> functionTypeList;

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
    public List<F40010FunctionTypeDto> getFunctionTypeList() {
        return functionTypeList;
    }

    /**
     * 画面.機能分類一覧
     * @param functionTypeList 画面.機能分類一覧
     */
    public void setFunctionTypeList(List<F40010FunctionTypeDto> functionTypeList) {
        this.functionTypeList = functionTypeList;
    }
}
