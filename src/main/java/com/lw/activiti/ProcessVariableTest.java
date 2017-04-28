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
 * 测试流程变量
 * 
 * @author lw
 */
public class ProcessVariableTest {

	private ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void deploy() {

		// 获取仓库服务：管理流程定义
		RepositoryService rs = engine.getRepositoryService();

		Deployment deploy = rs.createDeployment().addClasspathResource("diagrams/ApplyBill.bpmn")
				.addClasspathResource("diagrams/ApplyBill.png").name("支付") // 设置部署名称
				.category("支付别名") // 设置类别
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
		// 流程实例
		ProcessInstance pi = rs.startProcessInstanceByKey(processDefiKey);
		System.out.println("id:" + pi.getId());
		System.out.println("流程定义id:" + pi.getProcessDefinitionId());

	}

	/**
	 * 处理任务
	 */
	@Test
	public void finish() {
		// 104 202 302
		String taskId = "1402";
		TaskService ts = engine.getTaskService();
		ts.complete(taskId);
		System.out.println("当前任务执行完毕");
	}
	
	/**
	 * 设置自定义类型的流程变量
	 */
	@Test
	public void setMyVariable(){
		String taskId = "1704" ;
		TaskService ts = engine.getTaskService();
		ApplyBillBean applyBillBean = new ApplyBillBean(3000L,"李文",new Date()) ;
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
	 *  设置普通类型的流程变量
	 */
	@Test
	public void setVariable(){
		String taskId = "1402" ;
		// 使用taskservice设置流程变量
		TaskService ts = engine.getTaskService();
		ts.setVariable(taskId, "cost", 1000); // 整个流程实例
		ts.setVariable(taskId, "申请事件", new Date());// 整个流程实例
		ts.setVariableLocal(taskId, "申请人", "文哥");// 该变量只有在本任务是有效的。
		System.out.println("设置成功"); 
	}
	
	/**
	 *  查询普通类型的流程变量
	 */
	@Test
	public void getVariable(){
		String taskId = "1402" ;
		TaskService ts = engine.getTaskService();
		Object cost = ts.getVariable(taskId, "cost"); // 全局有值
		Object localCost = ts.getVariableLocal(taskId, "cost"); 
		System.out.println("localCost:" + localCost); // null
		
		Object time = ts.getVariable(taskId, "申请事件"); // 有值
		
		Object man = ts.getVariableLocal(taskId, "申请人"); // 有值
		
		System.out.println(cost + ":" + time + ":" + man ); 
	}

	/**
	 * 模拟流程变量设置
	 */
	public void getAndSetProcessVariable() {
		// 有两种服务可以设置流程变量
		TaskService ts = engine.getTaskService();
		RuntimeService rs = engine.getRuntimeService();
		/**
		 * 
		 */
		// rs.setVariable(executionId, variableName, values);;
		// rs.setVariableLocal(executionId, variableName, values);
	}

}
