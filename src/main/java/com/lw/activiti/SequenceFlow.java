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
 *  演示连线分支
 * @author lw
 *
 */
public class SequenceFlow {

	private ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void deploy() {

		// 获取仓库服务：管理流程定义
		RepositoryService rs = engine.getRepositoryService();

		Deployment deploy = rs.createDeployment().addClasspathResource("diagrams/SequenceFlow.bpmn")
				.addClasspathResource("diagrams/SequenceFlow.png").name("连续流程") // 设置部署名称
				.category("连续流程别名") // 设置类别
				.deploy();
		System.out.println("id:" + deploy.getId());
	}

	/**
	 * 开始流程
	 */
	@Test
	public void startProcess() {
		String processDefiKey = "sequenceBill";
		RuntimeService rs = engine.getRuntimeService();
		// 流程实例
		ProcessInstance pi = rs.startProcessInstanceByKey(processDefiKey);
		System.out.println("id:" + pi.getId());
		System.out.println("流程定义id:" + pi.getProcessDefinitionId());

	}
	
	@Test
	public void queryTask() {
		TaskService ts = engine.getTaskService();
		// 创建查询对象
		TaskQuery tq = ts.createTaskQuery();
		// 获取任务对象
		List<Task> list = tq.list();
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
		Map<String, Object> params = new HashMap<>() ;
		params.put("message", "重要");
		TaskService ts = engine.getTaskService();
		ts.complete(taskId,params);
		System.out.println("当前任务执行完毕");
	}
}
