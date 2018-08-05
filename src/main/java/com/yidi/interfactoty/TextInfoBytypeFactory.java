package com.yidi.interfactoty;

import java.util.Map;
import java.util.Set;

import com.yidi.entity.Parameter;
import com.yidi.entity.ReturnInfo;

public interface TextInfoBytypeFactory {
	ReturnInfo dog();
	ReturnInfo cat();
	ReturnInfo getReturnMSG(Map<Set<Integer>, Integer> parameter_solutionlist, Map<Integer, Parameter> parameters);
}
