package service.impl;

import java.util.ArrayList;
import java.util.List;

import dao.GradeDao;
import dao.impl.GradeDaoImpl;
import entity.Grade;
import entity.Student;
import entity.dto.StudentGradingDto;
import service.GradeService;
import service.StudentService;
import tools.DBUtil;

public class GradeServiceImpl implements GradeService {
	StudentService ss = new StudentServiceImpl();
	GradeDao gd = new GradeDaoImpl();
	@Override
	public List<StudentGradingDto> getGradingList(int classId) {
		List<Student> sl = ss.getStudentList(classId);
		 List<StudentGradingDto> sgl = new ArrayList<StudentGradingDto>();
		for (Student s : sl) {
			List<Grade> gL = getGrades(s.getStuId());
			for(Grade  g: gL) {
				giveGrade(g.getStuId(), g.getSubTaskId(), 0);
			}
			StudentGradingDto sgDto = new StudentGradingDto(s, getGrades( s.getStuId()));
			sgl.add(sgDto);
		}
		return sgl;
	}
	@Override
	public List<Grade> getGrades(int stuId) {
		return gd.getGradeOfStudent("select * from grade where stu_id = ?", stuId);
	}
	@Override
	public boolean giveGrade(int stuId, int subTaskID,double score) {
		String sql="INSERT INTO grade (sub_task_id, score, stu_id)  VALUES (?, ?, ?);";
		Object[] params = {subTaskID,score,stuId};
		return gd.insert(sql, params);
	}
	@Override
	public boolean changeGrade(Grade g) {
		String sql = "UPDATE grade SET score = ? WHERE grade_id = ?;";
		Object[] params = { g.getScore(),g.getGradeId() };
		return gd.update(sql, params);
	}

}
