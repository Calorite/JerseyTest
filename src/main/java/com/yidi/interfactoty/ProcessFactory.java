package com.yidi.interfactoty;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import com.yidi.entity.Parameter;
import com.yidi.entity.ReturnInfo;

public interface ProcessFactory {
	String api(String text);
	String returnpassedrecord(int rows);
	int returnstatus(String senderid); //0还在话题中    1话题结束
	String returnsolution(Set<Integer> set);
	ReturnInfo getReturn(Map<Set<Integer>, Integer> parameter_solutionlist,Set<Parameter> initalparameters);//0还在话题中的对话
	Map<Integer,Parameter> getValidparameters(Map<Set<Integer>, Integer> parameterlist, Set<Parameter> initalset);
	Map<Integer,Parameter> getInitialParameters(String text) throws SQLException;
}
