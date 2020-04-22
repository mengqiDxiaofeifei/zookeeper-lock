package com.zhou.zookeeper.service;

import com.zhou.zookeeper.service.impl.ZookeeperDistrbuteLock;



public class OrderService implements Runnable {
	private OrderNumGenerator orderNumGenerator = new OrderNumGenerator();
	private static Object oj = new Object();
	private Lock lock = new ZookeeperDistrbuteLock();

	public void run() {
		getNumber();
	}

	public void getNumber() {
		// synchronized (oj) {
		lock.getLock();
		String orderNumber = orderNumGenerator.getOrderNumber();
		System.out.println("获取订单号:" + orderNumber);
		lock.unLock();
		// }

	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			new Thread(new OrderService()).start();
		}
	}

}