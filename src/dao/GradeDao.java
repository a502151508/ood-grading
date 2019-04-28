package dao;

import java.util.List;

import entity.Grade;
import entity.dto.StudentGradingDto;

public interface GradeDao extends BaseDao {
	public List<Grade> getGradeOfStudent(String sql,int stuId);
}
