package com.minjinsuo.zhongchou.service;

import com.squareup.otto.Bus;

public class BusProvider {

	private static final Bus bus = new Bus();

	public static Bus getInstance() {
		return bus;
	}

	public BusProvider() {
		// TODO Auto-generated constructor stub
	}
}
