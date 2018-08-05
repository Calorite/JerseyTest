package com.yidi.DaoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.yidi.interfactoty.AboutQuestionDAO;
import com.yidi.entity.Parameter;


public class AboutQuestionImpl implements AboutQuestionDAO {

	@Override
	public String getQustionStr(String id) {
		DBService helper=new DBService();
		String sql="SELECT question FROM ai_qanda.paramenterques_tb where id=?";
		String[] params= {id};
		Object returnlist=helper.executeQuerySingle(sql, params);
		return returnlist.toString();
	}

	@Override
	public int getQuestionid(Set<Integer> set1, Map<Integer, Parameter> allparamenter) throws SQLException {
		Map<Integer,Integer> map=new HashMap<Integer,Integer>();
		for(int id:set1) {
			Parameter paramenter=allparamenter.get(id);
			map.put(id,paramenter.getRank());
		}
		Map<Integer,Integer> soredmap=sortByValue(map);
		Map.Entry<Integer,Integer> entry = soredmap.entrySet().iterator().next();
		Integer firstone=entry.getKey();
		if(firstone!=null) {
			Parameter paramenter=allparamenter.get(firstone);
			return paramenter.getQuestionid();
		}
		return 0;
	}

	public static boolean updateBundparameter(String parameter,String questionid) {
		DBService helper=new DBService();
		String sql="UPDATE ai_qanda.paramenterques_tb SET returnparameter=? WHERE id=?;";
		String[] paramr={parameter,questionid};
		int rows=helper.executeUpdate(sql, paramr);
		if(rows>0){
			return true;
		}
		return false;
	}
	
	
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {

		List<Entry<K,V>> sortedEntries = new ArrayList<Entry<K,V>>(map.entrySet());
		Collections.sort(sortedEntries,
				new Comparator<Entry<K,V>>() {
			@Override
			public int compare(Entry<K,V> e1, Entry<K,V> e2) {
				return e1.getValue().compareTo(e2.getValue());
			}
		}
				);
		Map<K, V> result = new LinkedHashMap<>();
		for (Entry<K, V> entry : sortedEntries) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}
