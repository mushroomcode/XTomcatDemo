package com.UUID;

/**
 * 2021/04/19
 * Gargantua
 * 学习雪花算法
 */
public class SnowFlakeDemo {
    // 工作机器的Id
    private long workerId;
    // 数据中心的Id
    private long datacenterId;
    // 序列号
    private long sequence;

    /**
     * 1288834974657 是 (Thu, 04 Nov 2010 01:42:54 GMT), 好家伙, 祖传代码
     * 这一时刻到1970-01-01 00:00:00时刻所经过的毫秒数。
     * 当前时刻减去1288834974657 的值刚好在2^41 里，因此占41位。
     * 所以这个数是为了让时间戳占41位才特地算出来的。
     */
    private long twepoch = 1288834974657L;
    private long workerIdBits = 5L;
    private long datacenterIdBits = 5L;
    // 表示可以有 31 个主机Id， 31个数据中心（5位数）
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    // 序列号位12位数
    private long sequenceBits = 12L;

    /**
     * 从 sequenceId 开始计算的，左移动 <<
     */
    // 工作Id 左移12位
    private long workerIdShift = sequenceBits;
    // 数据中心的Id 向左移动12 + 5 = 17 位
    private long datacenterIdShift = sequenceBits + workerIdBits;
    // 时间戳需要左移位数 12+5+5=22 位
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    // 序列号最大值
    private long sequenceMask = -1L ^ (-1L << sequenceBits);
    // 初始化时间戳，-1
    private long lastTimestamp = -1L;

    public SnowFlakeDemo(long workerId, long datacenterId, long sequence) {
        if(workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0",
                    maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }

    public long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public synchronized long nextId() {
        long timeNow = System.currentTimeMillis();

        // 获取当前时间戳如果小于上次时间戳，则表示时间戳获取出现异常
        if (timeNow < lastTimestamp) {
            throw new RuntimeException();
        }

        /**
         * 获取当前时间戳如果等于上次时间戳（同一毫秒内），
         * 则在序列号加一；否则序列号赋值为0，从0开始。
         */
        if(lastTimestamp == timeNow) {
            // 防止溢出，表示 Mask 数字为 0-4095，
            /**
             *  前面表示 12 位的能表示的最大数字
             *  比如 ：最大31,来了一个33
             *     1,1111
             *  & 10,0001
             *  = 00,0001 = 1 (防止溢出)
             */
            sequence = (sequence + 1) & sequenceMask;
            if(sequence == 0) {
                timeNow = tilNextMillis(lastTimestamp);
            }
        }
        else  {
            sequence = 0;
        }
        lastTimestamp = timeNow;

        /**
         * 返回结果： (妙啊)
         * (timestamp - twepoch) << timestampLeftShift)
         * 表示将时间戳减去初始时间戳，再左移相应位数
         * (datacenterId << datacenterIdShift) 表示将数据id左移相应位数
         * (workerId << workerIdShift) 表示将工作id左移相应位数
         * | 是按位或运算符，例如：x | y，只有当x，y都为0的时候结果才为0，其它情况结果都为1。
         * 因为个部分只有相应位上的值有意义，其它位上都是0，所以将各部分的值进行 | 运算就能得到最终拼接好的id
         */
        return ((timeNow - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    public static void main(String[] args) {
        SnowFlakeDemo snowFlakeDemo = new SnowFlakeDemo(
                4,5,106
        );
        long resId = snowFlakeDemo.nextId();
        System.out.println(resId);
    }

}
