package com.lw.activiti;

import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

/**
 * 流程定义管理
 * 
 * @author lw
 */
public class ProcessDefineManager {

	ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void deployProcessDefine() {
		RepositoryService rs = pe.getRepositoryService();
		// 创建部署
		Deployment deploy = rs.createDeployment().name("采购流程").category("购销").addClasspathResource("diagrams/BuyBill.bpmn")
				.addClasspathResource("diagrams/BuyBill.png").deploy();
		System.out.println("部署名称:" + deploy.getName());
		System.out.println("部署名称:" + deploy.getId());
	}
	
	/**
	 *  使用zip方式部署
	 */
	@Test
	public void deployProcessDefine2() {
		RepositoryService rs = pe.getRepositoryService();
		// 创建部署
		Deployment deploy = rs.createDeployment().name("采购流程2").category("购销2").addClasspathResource("diagrams/BuyBill.bpmn")
				.addZipInputStream(new ZipInputStream(getClass().getClassLoader().getResourceAsStream("diagrams/buy.zip"))).deploy();
		System.out.println("部署名称:" + deploy.getName());
		System.out.println("部署名称:" + deploy.getId());
	}
}
