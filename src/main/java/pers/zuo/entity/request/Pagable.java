package pers.zuo.entity.request;

/**
 * @author 刘德云
 * @version V1.0
 * @title: PagableVO
 * @package com.itiaoling.vos.core.web.request
 * @description vo 基类
 * @date 2017/8/6
 */
public class Pagable {

    private int page;

    private int limit;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
