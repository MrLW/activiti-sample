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
 * 动态指定任务处理人
 * 
 * @author lw
 */
public class UserParam {
	
	private String assignee = "李某某";
	
	private ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void deploy() {

		// 获取仓库服务：管理流程定义
		RepositoryService rs = engine.getRepositoryService();

		Deployment deploy = rs.createDeployment().addClasspathResource("diagrams/ApplyBill.bpmn")
				.addClasspathResource("diagrams/ApplyBill.png").name("支付流程2") // 设置部署名称
				.category("支付流程别名2") // 设置类别
				.deploy();
		System.out.println("id:" + deploy.getId());
	}

	/**
	 * 开始流程
	 */
	@Test
	public void startProcess() {
		String processDefiKey = "applyBill";
		RuntimeService rs = engine.getRuntimeService();
		// 设置处理人
		Map<String, Object> params = new HashMap<>() ;
		params.put("user", assignee);
		// 流程实例
		ProcessInstance pi = rs.startProcessInstanceByKey(processDefiKey,params);
		System.out.println("id:" + pi.getId());
		System.out.println("流程定义id:" + pi.getProcessDefinitionId());

	}
	/**
	 *  指定处理人查询
	 */
	@Test
	public void queryTask() {
		TaskService ts = engine.getTaskService();
		// 创建查询对象
		TaskQuery tq = ts.createTaskQuery();
		// 获取任务对象
		List<Task> list = tq.taskAssignee(assignee).list();
		for (Task task : list) {
			System.out.println("assignee:" + task.getAssignee());
			System.out.println("id:" + task.getId());
			System.out.println("name: " + task.getName());
		}
	}

	/**
	 * 处理任务 , 并且指定参数
	 */
	@Test
	public void finish() {
		String taskId = "2104";
		Map<String, Object> params = new HashMap<>();
		params.put("message", "重要");
		TaskService ts = engine.getTaskService();
		ts.complete(taskId, params);
		System.out.println("当前任务执行完毕");
	}
}
