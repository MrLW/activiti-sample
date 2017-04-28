package com.lw.activiti;

import java.util.Date;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * �������̱���
 * 
 * @author lw
 */
public class ProcessVariableTest {

	private ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void deploy() {

		// ��ȡ�ֿ���񣺹������̶���
		RepositoryService rs = engine.getRepositoryService();

		Deployment deploy = rs.createDeployment().addClasspathResource("diagrams/ApplyBill.bpmn")
				.addClasspathResource("diagrams/ApplyBill.png").name("֧��") // ���ò�������
				.category("֧������") // �������
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
		// ����ʵ��
		ProcessInstance pi = rs.startProcessInstanceByKey(processDefiKey);
		System.out.println("id:" + pi.getId());
		System.out.println("���̶���id:" + pi.getProcessDefinitionId());

	}

	/**
	 * ��������
	 */
	@Test
	public void finish() {
		// 104 202 302
		String taskId = "1402";
		TaskService ts = engine.getTaskService();
		ts.complete(taskId);
		System.out.println("��ǰ����ִ�����");
	}
	
	/**
	 * �����Զ������͵����̱���
	 */
	@Test
	public void setMyVariable(){
		String taskId = "1704" ;
		TaskService ts = engine.getTaskService();
		ApplyBillBean applyBillBean = new ApplyBillBean(3000L,"����",new Date()) ;
		ts.setVariable(taskId, "applyBillBean", applyBillBean);
	}
	
	@Test
	public void getMyVariable(){
		String taskId = "1704" ;
		TaskService ts = engine.getTaskService();
		ApplyBillBean applyBillBean= (ApplyBillBean) ts.getVariable(taskId, "applyBillBean");
		System.out.println(applyBillBean);
	}
	
	/**
	 *  ������ͨ���͵����̱���
	 */
	@Test
	public void setVariable(){
		String taskId = "1402" ;
		// ʹ��taskservice�������̱���
		TaskService ts = engine.getTaskService();
		ts.setVariable(taskId, "cost", 1000); // ��������ʵ��
		ts.setVariable(taskId, "�����¼�", new Date());// ��������ʵ��
		ts.setVariableLocal(taskId, "������", "�ĸ�");// �ñ���ֻ���ڱ���������Ч�ġ�
		System.out.println("���óɹ�"); 
	}
	
	/**
	 *  ��ѯ��ͨ���͵����̱���
	 */
	@Test
	public void getVariable(){
		String taskId = "1402" ;
		TaskService ts = engine.getTaskService();
		Object cost = ts.getVariable(taskId, "cost"); // ȫ����ֵ
		Object localCost = ts.getVariableLocal(taskId, "cost"); 
		System.out.println("localCost:" + localCost); // null
		
		Object time = ts.getVariable(taskId, "�����¼�"); // ��ֵ
		
		Object man = ts.getVariableLocal(taskId, "������"); // ��ֵ
		
		System.out.println(cost + ":" + time + ":" + man ); 
	}

	/**
	 * ģ�����̱�������
	 */
	public void getAndSetProcessVariable() {
		// �����ַ�������������̱���
		TaskService ts = engine.getTaskService();
		RuntimeService rs = engine.getRuntimeService();
		/**
		 * 
		 */
		// rs.setVariable(executionId, variableName, values);;
		// rs.setVariableLocal(executionId, variableName, values);
	}

}
