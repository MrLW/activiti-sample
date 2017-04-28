package com.lw.activiti;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;

/**
 * ��ʾ����ʵ��������
 * 
 * @author lw
 */
public class ProcessInstanceAndTask {

	private ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * �������̣�
	 */
	@Test
	public void startProcess() {
		String processKey = "buyBill";
		ProcessInstance pi = engine.getRuntimeService().startProcessInstanceByKey(processKey);

		System.out.println("���̶���ID��" + pi.getId());
		System.out.println("����ʵ��ID��" + pi.getProcessInstanceId());
		System.out.println("���̶���ID��" + pi.getProcessDefinitionId());
	}

	/**
	 * ��ѯ����ִ�е�����
	 */
	@Test
	public void queryTask() {
		// ȥ�������
		TaskService ts = engine.getTaskService();
		// ������ѯ����
		TaskQuery tq = ts.createTaskQuery();
		// ��ѯ�б�
		List<Task> list = tq.list();

		for (Task task : list) {
			System.out.println("���������:" + task.getAssignee());
			System.out.println("����ID��" + task.getId());
			System.out.println("��������ƣ�" + task.getName());
		}
	}

	/**
	 * ִ������
	 */
	@Test
	public void finishTask() {
		//act_run_task��
		String taskID = "902";
		engine.getTaskService().complete(taskID);
	}

	/**
	 * �鿴��ǰ���̵�״̬
	 */
	@Test
	public void getProcessInstanceState() {
		String processInstanceId = "701";
		RuntimeService rs = engine.getRuntimeService();
		ProcessInstanceQuery piq = rs.createProcessInstanceQuery();
		ProcessInstance pi = piq.processInstanceId(processInstanceId).singleResult();
		// �ж�����ʵ����״̬
		if (pi != null) {
			System.out.println("������ʵ����" + processInstanceId + " ��������...��ǰ�������" + pi.getActivityId());
		} else {
			System.out.println("��ǰ����ʵ����" + processInstanceId + "���н���");
		}
	}

	/**
	 * �鿴��ʷִ������ʵ����Ϣ
	 */
	@Test
	public void queryHistoryProcInst() {
		List<HistoricProcessInstance> list = engine.getHistoryService().createHistoricProcessInstanceQuery().list();
		
		for (HistoricProcessInstance inst : list) {
			System.out.print("��ʷ����ʵ��ID:" + inst.getId());
			System.out.print("��ʷ���̶���ID " + inst.getProcessDefinitionId());
			System.out.print("��ʷ����ʵ����ʼʱ��--����ʱ�� "+ inst.getStartTime() + "-->" + inst.getEndTime());
			System.out.println();
		}
	}

}
