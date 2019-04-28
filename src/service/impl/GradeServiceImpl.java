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

public class GradeServiceImpl implements GradeService {
	StudentService ss = new StudentServiceImpl();
	GradeDao gd = new GradeDaoImpl();
	@Override
	public List<StudentGradingDto> getGradingList(int classId) {
		List<Student> sl = ss.getStudentList(classId);
		 List<StudentGradingDto> sgl = new ArrayList<StudentGradingDto>();
		for (Student s : sl) {
			StudentGradingDto sgDto = new StudentGradingDto(s, gd.getGradeOfStudent("select * from grade where stu_id = ?", s.getStuId()));
			sgl.add(sgDto);
		}
		return sgl;
	}
	@Override
	public List<Grade> getGrades(int stuId) {
		return gd.getGradeOfStudent("select * from grade where stu_id = ?", stuId);
	}

}
