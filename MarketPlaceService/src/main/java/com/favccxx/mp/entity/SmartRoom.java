package com.favccxx.mp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SmartRoom implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private long clubId;
	
	
	private String roomNum;
	
	
	private String roomName;
	
	
	private int bedNum;
	
	
	private String  chairNum;
	
	
	private String status;
	
	
	private Date startDate;
	
	
	private Date endDate;
	
	
	private String description;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getClubId() {
		return clubId;
	}


	public void setClubId(long clubId) {
		this.clubId = clubId;
	}


	public String getRoomNum() {
		return roomNum;
	}


	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}


	public String getRoomName() {
		return roomName;
	}


	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}


	public int getBedNum() {
		return bedNum;
	}


	public void setBedNum(int bedNum) {
		this.bedNum = bedNum;
	}


	public String getChairNum() {
		return chairNum;
	}


	public void setChairNum(String chairNum) {
		this.chairNum = chairNum;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	
}
