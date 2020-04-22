package com.zhou.zookeeper.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNumGenerator {
    private static int count = 0;

    //生成订单号
    public String getOrderNumber() {
        SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return smt.format(new Date()) + "-" + ++count;
    }

}