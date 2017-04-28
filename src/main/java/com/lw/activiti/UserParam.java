package com.lw.activiti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
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
 * ��ָ̬����������
 * 
 * @author lw
 */
public class UserParam {
	
	private String assignee = "��ĳĳ";
	
	private ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void deploy() {

		// ��ȡ�ֿ���񣺹������̶���
		RepositoryService rs = engine.getRepositoryService();

		Deployment deploy = rs.createDeployment().addClasspathResource("diagrams/ApplyBill.bpmn")
				.addClasspathResource("diagrams/ApplyBill.png").name("֧������2") // ���ò�������
				.category("֧�����̱���2") // �������
				.deploy();
		System.out.println("id:" + deploy.getId());
	}

	/**
	 * ��ʼ����
	 */
	@Test
	public void startProcess() {
		String processDefiKey = "applyBill";
		RuntimeService rs = engine.getRuntimeService();
		// ���ô�����
		Map<String, Object> params = new HashMap<>() ;
		params.put("user", assignee);
		// ����ʵ��
		ProcessInstance pi = rs.startProcessInstanceByKey(processDefiKey,params);
		System.out.println("id:" + pi.getId());
		System.out.println("���̶���id:" + pi.getProcessDefinitionId());

	}
	/**
	 *  ָ�������˲�ѯ
	 */
	@Test
	public void queryTask() {
		TaskService ts = engine.getTaskService();
		// ������ѯ����
		TaskQuery tq = ts.createTaskQuery();
		// ��ȡ�������
		List<Task> list = tq.taskAssignee(assignee).list();
		for (Task task : list) {
			System.out.println("assignee:" + task.getAssignee());
			System.out.println("id:" + task.getId());
			System.out.println("name: " + task.getName());
		}
	}

	/**
	 * �������� , ����ָ������
	 */
	@Test
	public void finish() {
		String taskId = "2104";
		Map<String, Object> params = new HashMap<>();
		params.put("message", "��Ҫ");
		TaskService ts = engine.getTaskService();
		ts.complete(taskId, params);
		System.out.println("��ǰ����ִ�����");
	}
}
