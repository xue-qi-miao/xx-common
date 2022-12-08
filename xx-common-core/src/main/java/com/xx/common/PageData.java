package com.xx.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xueqimiao
 * @Date: 2021/12/17 9:25
 */
@Data
@ApiModel("分页信息")
public class PageData implements Serializable {

    public static final int DEFAULT_NAVIGATE_PAGES = 8;

    @ApiModelProperty("当前页")
    private int pageNum;

    @ApiModelProperty("每页的数量")
    private int pageSize;

    @ApiModelProperty("总条数")
    private long total;

    @ApiModelProperty("当前页的数量")
    private int size;

    @ApiModelProperty("当前页面第一个元素在数据库中的行号")
    private long startRow;

    @ApiModelProperty("当前页面最后一个元素在数据库中的行号")
    private long endRow;

    @ApiModelProperty("总页数")
    private int pages;

    @ApiModelProperty("前一页")
    private int prePage;

    @ApiModelProperty("下一页")
    private int nextPage;

    @ApiModelProperty("是否为第一页")
    private boolean isFirstPage;

    @ApiModelProperty("是否为最后一页")
    private boolean isLastPage;

    @ApiModelProperty("是否有前一页")
    private boolean hasPreviousPage;

    @ApiModelProperty("是否有下一页")
    private boolean hasNextPage;

    @ApiModelProperty("导航页码数")
    private int navigatePages;

    @ApiModelProperty("所有导航页号")
    private int[] navigatepageNums;

    @ApiModelProperty("导航条上的第一页")
    private int navigateFirstPage;

    @ApiModelProperty("导航条上的最后一页")
    private int navigateLastPage;




}
