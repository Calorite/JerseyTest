package com.yidi.interfactoty;

import java.sql.SQLException;
import java.util.Map;

import com.yidi.entity.Parameter;


public interface AboutParametersDAO {
	Map<Integer, Parameter> getparams() throws SQLException;

	boolean checkandpara(String para, String text);

	String checkParameterLine(String parameline, String text);
}
