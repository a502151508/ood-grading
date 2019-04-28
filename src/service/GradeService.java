package service;

import java.util.List;

import entity.Grade;
import entity.dto.StudentGradingDto;

public interface GradeService {
	//student lists with grade
	public List<StudentGradingDto> getGradingList(int classId);
	//grades of one student
	public List<Grade> getGrades(int stuId);
}