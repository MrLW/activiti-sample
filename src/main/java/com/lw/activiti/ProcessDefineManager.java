package com.lw.activiti;

import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

/**
 * ���̶������
 * 
 * @author lw
 */
public class ProcessDefineManager {

	ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void deployProcessDefine() {
		RepositoryService rs = pe.getRepositoryService();
		// ��������
		Deployment deploy = rs.createDeployment().name("�ɹ�����").category("����").addClasspathResource("diagrams/BuyBill.bpmn")
				.addClasspathResource("diagrams/BuyBill.png").deploy();
		System.out.println("��������:" + deploy.getName());
		System.out.println("��������:" + deploy.getId());
	}
	
	/**
	 *  ʹ��zip��ʽ����
	 */
	@Test
	public void deployProcessDefine2() {
		RepositoryService rs = pe.getRepositoryService();
		// ��������
		Deployment deploy = rs.createDeployment().name("�ɹ�����2").category("����2").addClasspathResource("diagrams/BuyBill.bpmn")
				.addZipInputStream(new ZipInputStream(getClass().getClassLoader().getResourceAsStream("diagrams/buy.zip"))).deploy();
		System.out.println("��������:" + deploy.getName());
		System.out.println("��������:" + deploy.getId());
	}
}
