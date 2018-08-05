package com.yidi.interfactoty;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import com.yidi.entity.Parameter;

public interface AboutQuestionDAO {
	String getQustionStr(String id);
	int getQuestionid(Set<Integer> set1,Map<Integer,Parameter> allparamenter) throws SQLException;
}
