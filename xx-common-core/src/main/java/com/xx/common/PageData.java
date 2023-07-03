package com.xx.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xueqimiao
 * @Date: 2021/12/17 9:25
 */
@Data
public class PageData implements Serializable {

    public static final int DEFAULT_NAVIGATE_PAGES = 8;

    @Schema(description = "当前页")
    private int pageNum;

    @Schema(description = "每页的数量")
    private int pageSize;

    @Schema(description = "总条数")
    private long total;

    @Schema(description = "当前页的数量")
    private int size;

    @Schema(description = "当前页面第一个元素在数据库中的行号")
    private long startRow;

    @Schema(description = "当前页面最后一个元素在数据库中的行号")
    private long endRow;

    @Schema(description = "总页数")
    private int pages;

    @Schema(description = "前一页")
    private int prePage;

    @Schema(description = "下一页")
    private int nextPage;

    @Schema(description = "是否为第一页")
    private boolean isFirstPage;

    @Schema(description = "是否为最后一页")
    private boolean isLastPage;

    @Schema(description = "是否有前一页")
    private boolean hasPreviousPage;

    @Schema(description = "是否有下一页")
    private boolean hasNextPage;

    @Schema(description = "导航页码数")
    private int navigatePages;

    @Schema(description = "所有导航页号")
    private int[] navigatepageNums;

    @Schema(description = "导航条上的第一页")
    private int navigateFirstPage;

    @Schema(description = "导航条上的最后一页")
    private int navigateLastPage;




}
