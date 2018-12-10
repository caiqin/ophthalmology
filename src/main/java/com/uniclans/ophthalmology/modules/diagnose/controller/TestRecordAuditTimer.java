package com.uniclans.ophthalmology.modules.diagnose.controller;

import java.util.TimerTask;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uniclans.ophthalmology.modules.diagnose.service.component.ITestRecordService;
public class TestRecordAuditTimer extends TimerTask {
	private static Logger logger = LoggerFactory.getLogger(TestRecordAuditTimer.class);
	@Resource
	private ITestRecordService testRecordService;
	
	public TestRecordAuditTimer(ITestRecordService testRecordService) {
		this.testRecordService = testRecordService;
	}

	@Override
	public void run() {
		try {
			testRecordService.updateState();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("修改医生诊断状态失败",e);
		}
	}
	
}
