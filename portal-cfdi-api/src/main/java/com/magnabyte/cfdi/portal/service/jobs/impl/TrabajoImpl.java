package com.magnabyte.cfdi.portal.service.jobs.impl;

import org.springframework.stereotype.Service;

import com.magnabyte.cfdi.portal.service.jobs.Trabajo;

@Service("trabajo")
public class TrabajoImpl implements Trabajo {

	@Override
	public void executeInternal() {
		System.out.println("SDF");
	}
}
