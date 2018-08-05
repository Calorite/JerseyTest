package com.yidi.Impl;

import java.util.Set;

import com.yidi.entity.Parameter;



public class ReturnObj {
	private int id;
	private String question;
	private int type;//0 question 1 solution
	private Set<Integer> set;
	private Set<Parameter> words;

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
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the set
	 */
	public Set<Integer> getSet() {
		return set;
	}
	/**
	 * @param set the set to set
	 */
	public void setSet(Set<Integer> set) {
		this.set = set;
	}
	/**
	 * @return the words
	 */
	public Set<Parameter> getWords() {
		return words;
	}
	/**
	 * @param words the words to set
	 */
	public void setWords(Set<Parameter> words) {
		this.words = words;
	}

}
