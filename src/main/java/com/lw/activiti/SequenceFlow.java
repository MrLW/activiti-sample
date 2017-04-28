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
 *  ��ʾ���߷�֧
 * @author lw
 *
 */
public class SequenceFlow {

	private ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void deploy() {

		// ��ȡ�ֿ���񣺹������̶���
		RepositoryService rs = engine.getRepositoryService();

		Deployment deploy = rs.createDeployment().addClasspathResource("diagrams/SequenceFlow.bpmn")
				.addClasspathResource("diagrams/SequenceFlow.png").name("��������") // ���ò�������
				.category("�������̱���") // �������
				.deploy();
		System.out.println("id:" + deploy.getId());
	}

	/**
	 * ��ʼ����
	 */
	@Test
	public void startProcess() {
		String processDefiKey = "sequenceBill";
		RuntimeService rs = engine.getRuntimeService();
		// ����ʵ��
		ProcessInstance pi = rs.startProcessInstanceByKey(processDefiKey);
		System.out.println("id:" + pi.getId());
		System.out.println("���̶���id:" + pi.getProcessDefinitionId());

	}
	
	@Test
	public void queryTask() {
		TaskService ts = engine.getTaskService();
		// ������ѯ����
		TaskQuery tq = ts.createTaskQuery();
		// ��ȡ�������
		List<Task> list = tq.list();
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
		Map<String, Object> params = new HashMap<>() ;
		params.put("message", "��Ҫ");
		TaskService ts = engine.getTaskService();
		ts.complete(taskId,params);
		System.out.println("��ǰ����ִ�����");
	}
}
