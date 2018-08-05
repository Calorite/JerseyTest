package com.yidi.Impl;

import com.yidi.entity.Parameter;

public class ParamaJson {
	private String id;
	private String questionid;
	private String parameter;
	private String rank;
	public ParamaJson(Parameter p){
		this.id=String.valueOf(p.getParameterid());
		this.questionid=String.valueOf(p.getQuestionid());
		this.parameter=p.getParameter();
		this.rank=String.valueOf(p.getRank());
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestionid() {
		return questionid;
	}
	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	
}
