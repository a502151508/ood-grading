package entity;

import java.io.Serializable;


public class SubTask implements Serializable {
	private static final long serialVersionUID = 1L;

	private int subTaskId;

	private String subTaskName;

	private int taskId;

	private double weight;

	public SubTask() {
	}

	public int getSubTaskId() {
		return this.subTaskId;
	}

	public void setSubTaskId(int subTaskId) {
		this.subTaskId = subTaskId;
	}

	public String getSubTaskName() {
		return this.subTaskName;
	}

	public void setSubTaskName(String subTaskName) {
		this.subTaskName = subTaskName;
	}

	public int getTaskId() {
		return this.taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}