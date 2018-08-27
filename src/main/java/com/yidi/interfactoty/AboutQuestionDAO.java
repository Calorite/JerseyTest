package com.yidi.interfactoty;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yidi.DaoImpl.DBService;
import com.yidi.entity.Parameter;
import com.yidi.entity.Question;
import com.yidi.entity.UpperQuestion;

public interface AboutQuestionDAO {
	String getQustionStr(String id);
	List<Question> getQuestionlist(DBService helper);
	int getQuestionid(Set<Integer> set1,Map<Integer,Parameter> allparamenter) throws SQLException;
	boolean updateQuestionparametr(String questionid, String parameterid, String parameter);
}
