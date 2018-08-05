package com.yidi.interfactoty;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

public interface AboutSolutionDAO {
	String getSolutinStr(String id);
	Map<Set<Integer>,Integer> getsolutionlist() throws SQLException;
}
