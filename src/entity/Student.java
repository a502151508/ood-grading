package entity;

import java.io.Serializable;


/**
 * The persistent class for the student database table.
 * 
 */
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;

	private int stuId;

	private int classId;

	private String firstName;

	private String lastName;

	private int stuType;

	public Student() {
	}

	public int getStuId() {
		return this.stuId;
	}

	public void setStuId(int stuId) {
		this.stuId = stuId;
	}

	public int getClassId() {
		return this.classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getStuType() {
		return this.stuType;
	}

	public void setStuType(int stuType) {
		this.stuType = stuType;
	}

}