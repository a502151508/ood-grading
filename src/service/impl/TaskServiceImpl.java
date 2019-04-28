package service.impl;

import java.util.List;

import dao.TaskDao;
import dao.impl.TaskDaoImpl;
import entity.Student;
import entity.SubTask;
import entity.Task;
import entity.dto.TaskDto;
import service.TaskService;

public class TaskServiceImpl implements TaskService {
	private TaskDao td = new TaskDaoImpl();
	@Override
	public List<TaskDto> getTaskList(int classId) {
		String sql = "select * from task where class_id = ?";
		return td.getTaskList(sql, classId);
	}
	@Override
	public boolean addTask(Task t) {
			String sql="INSERT INTO task (class_id, task_name, weight)  VALUES (?, ? ,?);";
			Object[] params = {t.getClassId(),t.getTaskName(),t.getWeight()};
			return td.insert(sql, params);
	}
	@Override
	public boolean addSubTask(SubTask st) {
		String sql="INSERT INTO sub_task (task_id, sub_task_name, weight)  VALUES (?, ? ,?);";
		Object[] params = {st.getTaskId(),st.getSubTaskName(),st.getWeight()};
		return td.insert(sql, params);
	}

}
