package service;

import java.util.List;

import entity.Student;
import entity.SubTask;
import entity.Task;
import entity.dto.TaskDto;

public interface TaskService {
	public List<TaskDto> getTaskList(int classId);
	public boolean addTask(Task t);
	public boolean addSubTask(SubTask t);
}
