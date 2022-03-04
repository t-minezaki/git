package jp.learningpark.framework.utils;
/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項   :
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jp.learningpark.framework.xss.SQLFilter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * クエリ
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2.0.0 2017-03-14
 */
public class Query<T> extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    /**
     * page
     */
    private Page<T> page;

    /**
     * currPage
     */
    private int currPage = 1;

    /**
     * page count
     */
    private int limit = 10;

    public Query(Map<String, Object> params) {
        this.putAll(params);

        if (params.get("page") != null) {
            currPage = Integer.parseInt((String) params.get("page"));
        }
        if (params.get("limit") != null) {
            limit = Integer.parseInt((String) params.get("limit"));
        }

        this.put("offset", (currPage - 1) * limit);
        this.put("page", currPage);
        this.put("limit", limit);

        String sidx = SQLFilter.sqlInject((String) params.get("sidx"));
        String order = SQLFilter.sqlInject((String) params.get("order"));
        this.put("sidx", sidx);
        this.put("order", order);

        this.page = new Page<>(currPage, limit);

        if (StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(order)) {
            if ("ASC".equalsIgnoreCase(order)) {
                this.page.setAsc(sidx);
            } else {
                this.page.setDesc(sidx);
            }
        }
    }

    public Page<T> getPage() {
        return page;
    }

    public int getCurrPage() {
        return currPage;
    }

    public int getLimit() {
        return limit;
    }
}
