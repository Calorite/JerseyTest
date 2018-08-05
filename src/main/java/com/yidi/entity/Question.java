package com.yidi.entity;

public class Question {
	private int id;
	private String question;
	private int chioces;
	private String answer;
	private String parameters;
	public Question(int id,String question,int chioces,String answer,String parameters) {
		this.id=id;
		this.question=question;
		this.chioces=chioces;
		this.answer=answer;
		this.parameters=parameters;
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
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}
	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	/**
	 * @return the chioces
	 */
	public int getChioces() {
		return chioces;
	}
	/**
	 * @param chioces the chioces to set
	 */
	public void setChioces(int chioces) {
		this.chioces = chioces;
	}
	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/**
	 * @return the parameters
	 */
	public String getParameters() {
		return parameters;
	}
	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

}
