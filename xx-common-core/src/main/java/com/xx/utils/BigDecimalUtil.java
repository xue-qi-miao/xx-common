package com.xx.utils;

import java.math.BigDecimal;

/**
 * @Author: xueqimiao
 * @Date: 2021/12/15 15:40
 */
public class BigDecimalUtil {


    public static BigDecimal MYRIAD = new BigDecimal("10000");
    public static BigDecimal HUNDRED = new BigDecimal("100");

    /**
     * 累加
     * @param values
     * @return
     */
    public static BigDecimal add(BigDecimal... values) {
        if (ValidationUtil.isEmpty(values) || values.length == 0) {
            return new BigDecimal(0);
        }
        if (values.length == 1) {
            return values[0];
        } else {
            BigDecimal returnNum = ValidationUtil.isEmpty(values[0]) ? new BigDecimal(0) : values[0];
            for (int i = 1; i < values.length; i++) {
                if (ValidationUtil.isEmpty(values[i])) {
                    continue;
                }
                BigDecimal b1 = returnNum;
                BigDecimal b2 = values[i];
                returnNum = b1.add(b2);
            }
            return returnNum;
        }
    }

    /**
     * 减法
     * @param param
     * @return
     */
    public static BigDecimal subtract(BigDecimal... param) {
        if (ValidationUtil.isEmpty(param) || param.length == 0) {
            return new BigDecimal(0);
        }
        BigDecimal sumLess = param[0];//被减数
        for (int i = 1; i < param.length; i++) {
            BigDecimal bigDecimal = param[i] == null ? new BigDecimal(0) : param[i];
            sumLess = sumLess.subtract(bigDecimal);
        }
        return sumLess;
    }

    /**
     * 乘以
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal multiply(BigDecimal v1,BigDecimal v2) {
        if (ValidationUtil.isEmpty(v1) || ValidationUtil.isEmpty(v2)) {
            return new BigDecimal(0);
        }
        BigDecimal multiply = v1.multiply(v2);
        return multiply;
    }

    /**
     * 除以
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal divide(BigDecimal v1,BigDecimal v2) {
        if (ValidationUtil.isEmpty(v1) || ValidationUtil.isEmpty(v2)||v2.compareTo(new BigDecimal("0"))==0) {
            return new BigDecimal(0);
        }
        return v1.divide(v2,3,BigDecimal.ROUND_HALF_DOWN);
    }
}
