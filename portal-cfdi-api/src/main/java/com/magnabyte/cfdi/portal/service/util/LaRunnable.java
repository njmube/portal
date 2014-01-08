package com.magnabyte.cfdi.portal.service.util;


public class LaRunnable implements Runnable {

	private String name;
	
	public LaRunnable(String name) {
		super();
		this.name = name;
	}

	@Override
	public void run() {
		System.out.println(name + " is running");
		System.out.println(Thread.currentThread().getName() + " is running");
		try {
			for (int i = 0; i < 50; i++) {
				Thread.sleep(2000);
				System.out.println(name + " " + i);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 
		System.out.println(name + " finished");
	}

}
