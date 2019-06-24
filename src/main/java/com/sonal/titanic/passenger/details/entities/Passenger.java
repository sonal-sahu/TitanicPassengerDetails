package com.sonal.titanic.passenger.details.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="passenger")
public class Passenger {

	@Id
	@Column(name="passenger_id")
	private int passenger_id;
	
	@Column(name="class")
    private String passenger_class;
	
	@Column(name="name")
    private String name;
	
	@Column(name="sex")
    private String sex;
	
	@Column(name="age")
    private String age;
	
	@Column(name="sib_sp")
    private String sib_sp;
	
	@Column(name="parch")
    private String parch;
	
	@Column(name="ticket")
    private String ticket;
	
	@Column(name="fare")
    private String fare;
	
	@Column(name="cabin")
    private String cabin;
	
	@Column(name="embarked")
    private String embarked;
    
    public Passenger() {
    	
    }
    
	public Passenger(int passenger_id, String passenger_class, String name, String sex, String age, String sib_sp,
			String parch, String ticket, String fare, String cabin, String embarked) {
		super();
		this.passenger_id = passenger_id;
		this.passenger_class = passenger_class;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.sib_sp = sib_sp;
		this.parch = parch;
		this.ticket = ticket;
		this.fare = fare;
		this.cabin = cabin;
		this.embarked = embarked;
	}
	
	
	public int getPassenger_id() {
		return passenger_id;
	}
	public void setPassenger_id(int passenger_id) {
		this.passenger_id = passenger_id;
	}
	public String getPassenger_class() {
		return passenger_class;
	}
	public void setPassenger_class(String passenger_class) {
		this.passenger_class = passenger_class;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSib_sp() {
		return sib_sp;
	}
	public void setSib_sp(String sib_sp) {
		this.sib_sp = sib_sp;
	}
	public String getParch() {
		return parch;
	}
	public void setParch(String parch) {
		this.parch = parch;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getFare() {
		return fare;
	}
	public void setFare(String fare) {
		this.fare = fare;
	}
	public String getCabin() {
		return cabin;
	}
	public void setCabin(String cabin) {
		this.cabin = cabin;
	}
	public String getEmbarked() {
		return embarked;
	}
	public void setEmbarked(String embarked) {
		this.embarked = embarked;
	}
    
    
}
