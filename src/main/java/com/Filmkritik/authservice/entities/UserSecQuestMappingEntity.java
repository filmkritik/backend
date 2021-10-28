package com.Filmkritik.authservice.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserSecquesMap")
public class UserSecQuestMappingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private long uid;
	
	@Column
	private long sid;
	
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
		return uid;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(long user_id) {
		this.uid = user_id;
	}
	/**
	 * @return the sQ_id
	 */
	public long getSQ_id() {
		return sid;
	}
	/**
	 * @param sQ_id the sQ_id to set
	 */
	public void setSQ_id(long sQ_id) {
		sid = sQ_id;
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
