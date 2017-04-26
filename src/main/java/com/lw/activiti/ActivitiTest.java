package com.lw.activiti;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;

/**
 * ģ��activiti ���������ִ��
 */
public class ActivitiTest {

	private ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void createProcessEngineJava() {
		// ��ʽһ������ʵ�� ������൱��activiti.cfg.xml�ļ�
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration();
		// �������ݿ�����
		configuration.setJdbcDriver("com.mysql.jdbc.Driver");
		configuration.setJdbcUrl("jdbc:mysql://localhost:3306/activitiDB?createDatabaseIfNotExist=true"
				+ "&useUnicode=true&characterEncoding=utf8");
		configuration.setJdbcUsername("root");
		configuration.setJdbcPassword("root");
		// ���ô�����Ĳ���
		/**
		 * false�����ᴴ����,û�б����쳣 true:���û�б��Զ����� create-drop:��ɾ��,�ٴ���
		 */
		configuration.setDatabaseSchemaUpdate("true");
		// ������������
		ProcessEngine engine = configuration.buildProcessEngine();// ��ʼ�������ݿ�
		System.out.println("�������洴���ɹ�");
	}

	/**
	 * ͨ��activiti.cfg.xml����
	 */
	@Test
	public void createProcessEngineXml() {
		// ��ʽһ������ʵ��
		// ProcessEngineConfiguration configuration = ProcessEngineConfiguration
		// .createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		// ProcessEngine engine = configuration.buildProcessEngine();
		// Ĭ�ϼ���activiti.cfg.xml
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		System.out.println("�������洴���ɹ�");
	}
	
	/**
	 *  �������̶���
	 */
	@Test
	public void deploy(){
		
		
		// ��ȡ�ֿ���񣺹������̶���
		RepositoryService rs = engine.getRepositoryService();
		
		Deployment deploy = rs.createDeployment()
				.addClasspathResource("diagrams/LeaveBill.bpmn")
				.addClasspathResource("diagrams/LeaveBill.png")
				.name("��ٵ�") // ���ò�������
				.category("�칫") // �������
				.deploy();
		System.out.println("id:" + deploy.getId());
	}
	
	/**
	 *  ��ʼ����
	 */
	@Test
	public void startProcess(){
		String processDefiKey = "leaveBill"; 
		RuntimeService rs = engine.getRuntimeService();
		// ����ʵ��
		ProcessInstance pi = rs.startProcessInstanceByKey(processDefiKey);
		System.out.println("id:" + pi.getId());
		System.out.println("���̶���id:" + pi.getProcessDefinitionId());
		
	}
	
	
	/**
	 *  ��ѯ����
	 */
	@Test
	public void queryTask(){
		// ��������ľ������
		String assignee = "����"; 
		TaskService ts = engine.getTaskService();
		// �����ѯ����
		TaskQuery tq = ts.createTaskQuery();
		tq.taskAssignee(assignee);
		List<Task> list = tq.list();
		for (Task task : list) {
			System.out.println("���������:" + task.getAssignee() + ";id: " + task.getId() + ";name:" + task.getName());
		}
	}
	
	/**
	 *  ��������
	 */
	@Test
	public void finish(){
		// 104   202   302
		String taskId = "302";
		TaskService ts = engine.getTaskService();
		ts.complete(taskId);
		System.out.println("��ǰ����ִ�����");
	}
}
