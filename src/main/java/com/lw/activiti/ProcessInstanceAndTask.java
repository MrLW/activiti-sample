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
 * 演示流程实例和任务
 * 
 * @author lw
 */
public class ProcessInstanceAndTask {

	private ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 开启流程：
	 */
	@Test
	public void startProcess() {
		String processKey = "buyBill";
		ProcessInstance pi = engine.getRuntimeService().startProcessInstanceByKey(processKey);

		System.out.println("流程对象ID：" + pi.getId());
		System.out.println("流程实例ID：" + pi.getProcessInstanceId());
		System.out.println("流程定义ID：" + pi.getProcessDefinitionId());
	}

	/**
	 * 查询正在执行的任务
	 */
	@Test
	public void queryTask() {
		// 去任务服务
		TaskService ts = engine.getTaskService();
		// 创建查询对象
		TaskQuery tq = ts.createTaskQuery();
		// 查询列表
		List<Task> list = tq.list();

		for (Task task : list) {
			System.out.println("任务办理人:" + task.getAssignee());
			System.out.println("任务ID：" + task.getId());
			System.out.println("任务的名称：" + task.getName());
		}
	}

	/**
	 * 执行任务
	 */
	@Test
	public void finishTask() {
		//act_run_task表
		String taskID = "902";
		engine.getTaskService().complete(taskID);
	}

	/**
	 * 查看当前流程的状态
	 */
	@Test
	public void getProcessInstanceState() {
		String processInstanceId = "701";
		RuntimeService rs = engine.getRuntimeService();
		ProcessInstanceQuery piq = rs.createProcessInstanceQuery();
		ProcessInstance pi = piq.processInstanceId(processInstanceId).singleResult();
		// 判断流程实例的状态
		if (pi != null) {
			System.out.println("该流程实例：" + processInstanceId + " 正在运行...当前活动的任务：" + pi.getActivityId());
		} else {
			System.out.println("当前流程实例：" + processInstanceId + "运行结束");
		}
	}

	/**
	 * 查看历史执行流程实例信息
	 */
	@Test
	public void queryHistoryProcInst() {
		List<HistoricProcessInstance> list = engine.getHistoryService().createHistoricProcessInstanceQuery().list();
		
		for (HistoricProcessInstance inst : list) {
			System.out.print("历史流程实例ID:" + inst.getId());
			System.out.print("历史流程定义ID " + inst.getProcessDefinitionId());
			System.out.print("历史流程实例开始时间--结束时间 "+ inst.getStartTime() + "-->" + inst.getEndTime());
			System.out.println();
		}
	}

}
