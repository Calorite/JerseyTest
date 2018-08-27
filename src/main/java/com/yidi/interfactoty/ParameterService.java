package com.yidi.interfactoty;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;


import com.yidi.entity.Parameter;
import com.yidi.entity.ParameterSolution;
import com.yidi.entity.ReturnInfo;

public interface ParameterService {
	ReturnInfo getreturninfo(String text);
	String api(String text);
	String returnpassedrecord(int rows);
	int returnstatus(String senderid); //0还在话题中    1话题结束
	String returnsolution(Set<Integer> set);
	ReturnInfo getReturn(Map<Set<Integer>, Integer> parameter_solutionlist,Set<Parameter> initalparameters);//0还在话题中的对话
	Map<Integer,Parameter> getInitialParameters(String text,AboutParametersDAO parametersdao) throws SQLException;
	Map<Integer, Parameter> getValidparameters(Map<Set<Integer>, ParameterSolution> parameterlist,
			Set<Parameter> initalset);
	int getquestionidbyparameterid(int i);
	String getquestionbyid(String id);
}
