package com.lw.activiti;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ActivityTest02 {
	private ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void deployZIP() {
		InputStream is = getClass().getClassLoader().getResourceAsStream("buy.zip");
		
		RepositoryService rs = engine.getRepositoryService();
		Deployment dy = rs.createDeployment()
		.name("�ɹ�����")
		.category("�ɹ�����")
		.addZipInputStream(new ZipInputStream(is))
		.deploy();
		
		System.out.println("��������" + dy.getName());
		System.out.println("id: " + dy.getId());
	}
	
	@Test
	public void queryProcessDefination(){
		String processID = "buyBill:1:504" ;
		String processKey = "buyBill" ;
		String processName = "buyProcess" ;
		ProcessDefinitionQuery pd = engine.getRepositoryService().createProcessDefinitionQuery();
		List<ProcessDefinition> list = pd.processDefinitionId(processID).list();
		System.out.println(list.size());
		for (ProcessDefinition processDefinition : list) {
			System.out.println(processDefinition);
		}
	}
	
	/**
	 *  �鿴ͼƬ
	 * @throws Exception 
	 */
	@Test
	public void viewImage() throws Exception{
		String deployID = "501" ;
		List<String> list = engine.getRepositoryService().getDeploymentResourceNames(deployID);
		for (String string : list) {
			System.out.println(string);
		}
		String imageName = list.get(1) ;
		// ��ȡ��Դ
		InputStream is = engine.getRepositoryService().getResourceAsStream(deployID, imageName);
		// д�뱾���ļ�
		File file = new File("d:/" + imageName); 
		FileUtils.copyInputStreamToFile(is, file); 
	}
	
	/**
	 *  ɾ�����̶���
	 */
	@Test
	public void deleteProcessDefine(){
		String deployID=  "501" ;
		engine.getRepositoryService().deleteDeployment(deployID);
	}
}
