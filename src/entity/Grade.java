package entity;

import java.io.Serializable;


/**
 * The persistent class for the grade database table.
 * 
 */
public class Grade implements Serializable {
	private static final long serialVersionUID = 1L;

	private int gradeId;

	private double score;

	private int subTaskId;

	public Grade() {
	}
	
	public Grade(int gradeId, double score, int subTaskId) {
		super();
		this.gradeId = gradeId;
		this.score = score;
		this.subTaskId = subTaskId;
	}

	public int getGradeId() {
		return this.gradeId;
	}

	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}

	public double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getSubTaskId() {
		return this.subTaskId;
	}

	public void setSubTaskId(int subTaskId) {
		this.subTaskId = subTaskId;
	}

}