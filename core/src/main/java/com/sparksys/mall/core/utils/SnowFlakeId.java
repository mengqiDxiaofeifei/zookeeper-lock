package com.sparksys.mall.core.utils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * 中文类名：snowflake算法--用于分布式主键生成策略
 * 中文描述：snowflake算法--用于分布式主键生成策略
 *
 * @author zhouxinlei
 * @time 2019-07-23 03:01
 */
public class SnowFlakeId {

    /**
     * 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
     */
    private final static long TWELFTH = 1288834974657L;
    /**
     * 机器标识位数
     */
    private final static long workerIdBits = 5L;
    /**
     * 数据中心标识位数
     */
    private final static long DATA_CENTER_ID_BITS = 5L;
    /**
     * 机器ID最大值
     */
    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /**
     * 数据中心ID最大值
     */
    private final static long MAX_DATA_CENTER_ID = -1L ^ (-1L << DATA_CENTER_ID_BITS);
    /**
     * 毫秒内自增位
     */
    private final static long sequenceBits = 12L;
    /**
     * 机器ID偏左移12位
     */
    private final static long workerIdShift = sequenceBits;
    /**
     * 数据中心ID左移17位
     */
    private final static long DATA_CENTER_ID_SHIFT = sequenceBits + workerIdBits;
    /**
     * 时间毫秒左移22位
     */
    private final static long timestampLeftShift = sequenceBits + workerIdBits + DATA_CENTER_ID_BITS;
    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);
    /**
     * 上次生产id时间戳
     */
    private static long lastTimestamp = -1L;
    /**
     * 0，并发控制
     */
    private long sequence = 0L;
    private final long workerId;
    /**
     * 数据标识id部分
     */
    private final long DATA_CENTER_ID;

    public SnowFlakeId() {
        this.DATA_CENTER_ID = getDataCenterId(MAX_DATA_CENTER_ID);
        this.workerId = getMaxWorkerId(DATA_CENTER_ID, maxWorkerId);
    }

    /**
     * @param workerId     工作机器ID
     * @param datacenterId 序列号
     */
    public SnowFlakeId(long workerId, long datacenterId) {
        if ((workerId > maxWorkerId) || (workerId < 0)) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0",
                    maxWorkerId));
        }
        if ((datacenterId > MAX_DATA_CENTER_ID) || (datacenterId < 0)) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0",
                    MAX_DATA_CENTER_ID));
        }
        this.workerId = workerId;
        this.DATA_CENTER_ID = datacenterId;
    }

    /**
     * @param lastTimestamp
     * @return long
     * @author zhouxinlei
     * @time 2019-07-23 03:01
     */
    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    /**
     * @return long
     * @author zhouxinlei
     * @time 2019-07-23 03:01
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * <p>
     * 数据标识id部分
     * </p>
     *
     * @param maxDataCenterId
     * @return long
     */
    protected static long getDataCenterId(long maxDataCenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();

                id = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8)))
                        >> 6;
                id = id % (maxDataCenterId + 1);
            }
        } catch (Exception e) {
            System.out.println(" getDatacenterId: " + e.getMessage());
        }
        return id;
    }

    /**
     * <p>
     * 获取 maxWorkerId
     * </p>
     *
     * @param datacenterId
     * @param maxWorkerId
     * @return long
     */
    protected static long getMaxWorkerId(long datacenterId, long maxWorkerId) {
        StringBuffer mpid = new StringBuffer();
        mpid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            //GET jvmPid
            mpid.append(name.split("@")[0]);
        }
        //MAC + PID 的 hashcode 获取16个低位
        return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * 获取下一个ID
     *
     * @return
     */
    public synchronized long getNextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                            lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {

                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        long nextId = ((timestamp - TWELFTH) << timestampLeftShift) | (DATA_CENTER_ID << DATA_CENTER_ID_SHIFT)
                | (workerId << workerIdShift) | sequence;

        return nextId;
    }

    /**
     * @return Long
     * @author zhouxinlei
     * @time 2019-07-23 03:01
     */
    public static Long getSnowFlakeId() {
        SnowFlakeId snowFlakeId = new SnowFlakeId();
        return snowFlakeId.getNextId();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 9; i++) {
            System.out.println(SnowFlakeId.getSnowFlakeId());
        }
    }
}
