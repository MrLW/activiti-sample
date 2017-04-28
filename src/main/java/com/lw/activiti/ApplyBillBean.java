package com.lw.activiti;

import java.io.Serializable;
import java.util.Date;
/**
 *  如果设置的变量为对象,则需要序列化
 * @author lw
 *
 */
public class ApplyBillBean implements Serializable{

	private Long cost ;
	private String name; 
	private Date date ;
	
	public ApplyBillBean(Long cost, String name, Date date) {
		super();
		this.cost = cost;
		this.name = name;
		this.date = date;
	}
	public Long getCost() {
		return cost;
	}
	public void setCost(Long cost) {
		this.cost = cost;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "ApplyBillBean [cost=" + cost + ", name=" + name + ", date=" + date + "]";
	}
	
}
