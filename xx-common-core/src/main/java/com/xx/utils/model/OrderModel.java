package com.xx.utils.model;

/**
 * @ClassName: OrderModel
 * @Author: xueqimiao
 * @Date: 2021/11/19 14:17
 */
public class OrderModel implements Comparable<OrderModel> {
    private int index;
    private String field;
    private String orderBy;

    public OrderModel(int index, String field, String orderBy)
    {
        this.index = index;
        this.field = field;
        this.orderBy = orderBy;
    }

    public int getIndex()
    {
        return index;
    }

    public String getField()
    {
        return field;
    }

    public String getOrderBy()
    {
        return orderBy;
    }

    @Override
    public int compareTo(OrderModel order)
    {
        if (this.getIndex() > order.getIndex())
        {
            return 1;
        }
        else if (this.getIndex() < order.getIndex())
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
}
