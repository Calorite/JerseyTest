package com.yidi.DaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.yidi.interfactoty.AboutSolutionDAO;

public class AboutSolutionImpl implements AboutSolutionDAO {

	@Override
	public String getSolutinStr(String id) {
		String sql="SELECT question FROM ai_qanda.solution_tb where id=?";
		String[] params= {id};
		DBService helper=new DBService();
		Object returnlist=helper.executeQuerySingle(sql, params);
		return returnlist.toString();
	}

	@Override
	public Map<Set<Integer>, Integer> getsolutionlist() throws SQLException {
		Map<Set<Integer>,Integer> list1=new HashMap<Set<Integer>,Integer>();
		ResultSet rs;
		DBService helper=new DBService();
		String sql="SELECT * FROM ai_qanda.paramenter_solution where solutionid;";
		rs=helper.executeQueryRS(sql, null);
		while(rs.next()) {
			Set<Integer> set1=new HashSet<Integer>();
			String paramets=rs.getString(1);
			String[] pararray=paramets.split(",");
			Integer returnid=rs.getInt(2);
			for(String paramete:pararray) {
				set1.add(Integer.valueOf(paramete));
			}
			list1.put(set1, returnid);
		}
		return list1;
	}

}
