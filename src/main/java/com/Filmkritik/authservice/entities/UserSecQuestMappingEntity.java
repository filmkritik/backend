package com.Filmkritik.authservice.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserSecurityQuestionMapping")
public class UserSecQuestMappingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private long user_id;
	
	@Column
	private long SQ_id;
	
	@Column
	private String Answer;
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the user_id
	 */
	public long getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	/**
	 * @return the sQ_id
	 */
	public long getSQ_id() {
		return SQ_id;
	}
	/**
	 * @param sQ_id the sQ_id to set
	 */
	public void setSQ_id(long sQ_id) {
		SQ_id = sQ_id;
	}
	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return Answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		Answer = answer;
	}
	
	
}
