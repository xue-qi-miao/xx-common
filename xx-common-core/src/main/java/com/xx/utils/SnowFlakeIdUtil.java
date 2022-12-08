package com.xx.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;

/**
 * @ClassName: SnowFlakeIdUtil
 * @Author: xueqimiao
 * @Date: 2021/3/3 14:45
 */
public class SnowFlakeIdUtil {

    private static long workerId = 0;
    private static long datacenterId = 1;

    private static Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);

    static {
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        } catch (Exception e) {
            e.printStackTrace();
            workerId = NetUtil.getLocalhostStr().hashCode();
        }
    }

    public synchronized static long generateId() {
        return snowflake.nextId();
    }

    public synchronized static long generateId(long workerId, long datacenterId) {
        Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);
        return snowflake.nextId();
    }

    public static void main(String[] args) {
        System.out.println(SnowFlakeIdUtil.generateId());
    }

}
