package entity;

import java.io.Serializable;


/**
 * The persistent class for the classes database table.
 * 
 */
public class Classes implements Serializable {
	private static final long serialVersionUID = 1L;

	private int classId;

	private String className;

	private String semester;

	public Classes() {
	}

	public int getClassId() {
		return this.classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSemester() {
		return this.semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

}