# 一、activiti 初识
它是一项新的基于Apache许可的开源BPM平台，从基础开始构建，旨在提供支持新的BPMN 2.0标准，包括支持对象管理组（OMG），面对新技术的机遇，诸如互操作性和云架构，提供技术实现。(引用百度百科)
我们常见的activiti的工作有:OSWorkFlow、jBPM（jboss business process management）Activiti工作流（是对jBPM升级）

# 二、activiti 入门

## 1、常用概念

**工作流引擎（ProcessEngine）**
这个是activiti的核心，activiti就是由此引擎为入口进行工作的，通过该引擎可以获取到其它的服务(历史服务、仓库服务等)

**BPMN(业务流程建模与标注)**
这个是一个图，可以通过在eclipse中创建Activiti Diagrams，通过这些图来标识我们的一个具体业务流程。

**数据库**
Activiti的后台是有数据库的支持，所有的表都以ACT_开头。
   第二部分是表示表的用途的两个字母标识。 用途也和服务的API对应
   	Activiti的工作流数据库有23张表    
 要保存流程定义 
	- act_ge_*  ：通用表
	- act_hi_*  ：历史流程相关表 
	- act_re_*  ：仓库表：保存流程定义
	- act_ru_*  ：保存流程运行相关的表  
	- act_id_*  ：用户参与相关的表  
	
**activiti.cfg.xml**
activiti的核心配置文件，主要用来配置连接数据库的配置参数


## 2、环境搭建

**安装插件**
上面提到过BPMN图,要知道我们的eclipse默认情况下是不能创建activiti项目的,需要我们手动安装插件。
尽量在有网的情况下在线安装,安装地址为：http://activiti.org/designer/update/,重启即生效。

**注意 **安装完插件之后需要在preference->activiti->save actions 中的create ...打上勾，
让在保存bpmn图的时候也新生成png图。

**创建项目**
### 1、新建项目
在eclipse中新建项目,选择activiti project。注意,activiti默认情况下是使用maven管理的,所以最好
已经配置了maven。

### 2、新建核心配置文件

activiti.cfg.xml:

```

	<beans xmlns="http://www.springframework.org/schema/beans"
		xmlns:context="http://www.springframework.org/schema/context" 	xmlns:tx="http://www.springframework.org/schema/tx"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://www.springframework.org/schema/beans 	http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context 	http://www.springframework.org/schema/context/spring-context-2.5.xsd
		http://www.springframework.org/schema/tx 	http://www.springframework.org/schema/tx/spring-tx-3.0.xsd">

	<!-- 配置数据库连接 -->
	<bean id="processEngineConfiguration"
		class="org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration">
		<property name="jdbcDriver" value="com.mysql.jdbc.Driver"></property>
		<property name="jdbcUsername" value="root"></property>
		<property name="jdbcPassword" value="root"></property>
		<property name="jdbcUrl"
			value="jdbc:mysql://localhost:3306/activitiDB?createDatabaseIfNotExist=true&amp;useUnicode=true&amp;characterEncoding=utf8"></property>
		<!-- 
			配置表策略 
			false：不会创建表,没有表则异常 true:如果没有表自动创建 create-drop:先删除,再创建
		-->
		<property name="databaseSchemaUpdate" value="true"></property>
		
	</bean>


</beans>

```

### 3、新建BPMN图

这个就不贴图了,根据具体的逻辑来画具体的图。

### 4、部署流程

```
	
	ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
	
	// 获取仓库服务：管理流程定义
	RepositoryService rs = engine.getRepositoryService();
	
	Deployment deploy = rs.createDeployment()
			.addClasspathResource("diagrams/LeaveBill.bpmn")// 加载bpmn图
			.addClasspathResource("diagrams/LeaveBill.png") // 加载png,也可以不用加载
			.name("请假单") // 设置部署名称
			.category("办公") // 设置类别
			.deploy();// 真正部署
	System.out.println("id:" + deploy.getId());

```

这里的LeaveBill就是我创建的BPMN图。

### 5、开启流程

```
	
	//这个key其实就是bpmn的id
	String processDefiKey = "leaveBill"; 
	RuntimeService rs = engine.getRuntimeService();
	// 流程实例,通过流程定义的key开启并获取实例
	ProcessInstance pi = rs.startProcessInstanceByKey(processDefiKey);
	System.out.println("流程实例id:" + pi.getId());
	System.out.println("流程定义id:" + pi.getProcessDefinitionId());


```

开启流程之后,默认会执行第一个流程任务

### 6、完成任务
```
	
	String taskId = "302";
	TaskService ts = engine.getTaskService();
	// 根据任务Id来完成任务
	ts.complete(taskId);

```

## 3、activiti中其它的API介绍

**省去配置文件**
上面使用了activiti的核心配置文件,那么可不可以不用呢？当然是可以了。
```

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

```
**使用zip加载流程**

```

	//这个rs就是RepositoryService
	rs.createDeployment().name("采购流程").category("购销").addZipInputStream(new ZipInputStream(getClass().getClassLoader().getResourceAsStream("diagrams/buy.zip"))).deploy()

```

**流程变量**
作用：传递业务参数,指定办理人等
使用:TaskService 和  RuntimeService 服务设置变量
```
	
	/******************整个流程实例**************************/
	// 设置普通参数
	ts.setVariable(taskId, "cost", 1000);
	ts.getVariable(taskId, "cost"); // 整个流程实例
	// 设置对象:这个对象必须实现序列化接口
	ts.setVariable(taskId, "applyBillBean", applyBillBean);// 整个流程实例
	// 获取对象
	ts.getVariable(taskId, "applyBillBean")
	/******************当前任务对象**************************/
	ts.setVariableLocal(taskId, "person", "文哥");// 该变量只有在本任务是有效的。

```

**分支**

就是在我们完成一个任务之后可能会有分支,也就是"人"型结构,那么此时系统到底走哪条呢？

解决：在这个任务和下一个任务的连线着手配置。
1、双击连线->Main Config -> Condition 通过变量进行配置
```

	${message == '1'}
	
```

2、我们在代码中指定message的值
```

	Map<String, Object> params = new HashMap<>() ;
	params.put("message", "1");
	TaskService ts = engine.getTaskService();
	ts.complete(taskId,params);
	
```




