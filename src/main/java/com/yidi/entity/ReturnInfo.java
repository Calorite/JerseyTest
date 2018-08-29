package com.yidi.entity;

import java.util.Date;

public class ReturnInfo {
	private int id;
	private String username;
	private int status;//1:solution
	private String info;
	private Date datetime;
	private String recieved;
	private String parameter;
	public ReturnInfo(int id,int status,String info) {
		this.id=id;
		this.status=status;
		this.info=info;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getRecieved() {
		return recieved;
	}

	public void setRecieved(String recieved) {
		this.recieved = recieved;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
