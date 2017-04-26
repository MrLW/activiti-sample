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
 * 模拟activiti 工作流框架执行
 */
public class ActivitiTest {

	private ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void createProcessEngineJava() {
		// 方式一、代码实现 这个类相当于activiti.cfg.xml文件
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration();
		// 设置数据库属性
		configuration.setJdbcDriver("com.mysql.jdbc.Driver");
		configuration.setJdbcUrl("jdbc:mysql://localhost:3306/activitiDB?createDatabaseIfNotExist=true"
				+ "&useUnicode=true&characterEncoding=utf8");
		configuration.setJdbcUsername("root");
		configuration.setJdbcPassword("root");
		// 设置创建表的策略
		/**
		 * false：不会创建表,没有表则异常 true:如果没有表自动创建 create-drop:先删除,再创建
		 */
		configuration.setDatabaseSchemaUpdate("true");
		// 创建流程引擎
		ProcessEngine engine = configuration.buildProcessEngine();// 开始创建数据库
		System.out.println("流程引擎创建成功");
	}

	/**
	 * 通过activiti.cfg.xml创建
	 */
	@Test
	public void createProcessEngineXml() {
		// 方式一、代码实现
		// ProcessEngineConfiguration configuration = ProcessEngineConfiguration
		// .createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		// ProcessEngine engine = configuration.buildProcessEngine();
		// 默认加载activiti.cfg.xml
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		System.out.println("流程引擎创建成功");
	}
	
	/**
	 *  部署流程定义
	 */
	@Test
	public void deploy(){
		
		
		// 获取仓库服务：管理流程定义
		RepositoryService rs = engine.getRepositoryService();
		
		Deployment deploy = rs.createDeployment()
				.addClasspathResource("diagrams/LeaveBill.bpmn")
				.addClasspathResource("diagrams/LeaveBill.png")
				.name("请假单") // 设置部署名称
				.category("办公") // 设置类别
				.deploy();
		System.out.println("id:" + deploy.getId());
	}
	
	/**
	 *  开始流程
	 */
	@Test
	public void startProcess(){
		String processDefiKey = "leaveBill"; 
		RuntimeService rs = engine.getRuntimeService();
		// 流程实例
		ProcessInstance pi = rs.startProcessInstanceByKey(processDefiKey);
		System.out.println("id:" + pi.getId());
		System.out.println("流程定义id:" + pi.getProcessDefinitionId());
		
	}
	
	
	/**
	 *  查询任务
	 */
	@Test
	public void queryTask(){
		// 处理任务的具体对象
		String assignee = "王五"; 
		TaskService ts = engine.getTaskService();
		// 任务查询对象
		TaskQuery tq = ts.createTaskQuery();
		tq.taskAssignee(assignee);
		List<Task> list = tq.list();
		for (Task task : list) {
			System.out.println("任务代理人:" + task.getAssignee() + ";id: " + task.getId() + ";name:" + task.getName());
		}
	}
	
	/**
	 *  处理任务
	 */
	@Test
	public void finish(){
		// 104   202   302
		String taskId = "302";
		TaskService ts = engine.getTaskService();
		ts.complete(taskId);
		System.out.println("当前任务执行完毕");
	}
}
