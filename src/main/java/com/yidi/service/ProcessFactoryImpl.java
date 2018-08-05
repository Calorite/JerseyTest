package com.yidi.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.yidi.DaoImpl.DBService;
import com.yidi.entity.Parameter;
import com.yidi.entity.ReturnInfo;
import com.yidi.interfactoty.AboutParametersDAO;
import com.yidi.interfactoty.ProcessFactory;

public class ProcessFactoryImpl implements ProcessFactory {
	Map<Integer,Parameter> allparamenter=null;
	AboutParametersDAO parametersdao;
	@Override
	public String returnpassedrecord(int rows) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String returnsolution(Set<Integer> set) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String api(String text) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public  Map<Integer, Parameter> getValidparameters(Map<Set<Integer>, Integer> parameterlist,Set<Parameter> initalset){
		Set<Integer> getedpara=new HashSet<>();
		for (Parameter np:initalset) {
			getedpara.add(np.getParameterid());
		}
		Map<Integer,Parameter> validparameterset=new HashMap<Integer,Parameter>();
		Map<Set<Integer>,Integer> retainmap=new HashMap<Set<Integer>,Integer>();//parameter_solution中的parameter集合与Initial参数集合的并集
		for(Set<Integer> set:parameterlist.keySet()) {
			Set<Integer> retainset=new HashSet<Integer>();
			retainset.addAll(set);
			retainset.retainAll(getedpara);
			if(retainset.size()>0) {
				retainmap.put(retainset, retainset.size());
			}
		}
		Map<Set<Integer>,Integer> newretainmap=sortByValueDesc(retainmap);
		Map.Entry<Set<Integer>,Integer> entry = newretainmap.entrySet().iterator().next();
		Set<Integer> newset=entry.getKey();//并集最多的参数集合
		for(int id:newset) {
			for(Parameter thisp:initalset){
				if(id==thisp.getParameterid()) {
					validparameterset.put(thisp.getParameterid(),thisp);
				}
			}
		}
		return validparameterset;
	}

	@Override
	public Map<Integer,Parameter> getInitialParameters(String text) throws SQLException{
		allparamenter=parametersdao.getparams();
		Map<Integer,Parameter> targetpara=new HashMap<Integer,Parameter>();
		for(Parameter curtparamente:allparamenter.values()) {
			String targetparame=parametersdao.checkParameterLine(curtparamente.getParameter(),text);
			if(targetparame!=null) {
				curtparamente.setTargetparameitem(targetparame);
				targetpara.put(curtparamente.getParameterid(),curtparamente);
			}
		}
		return targetpara;
	}



	//降序
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDesc(Map<K, V> map) {

		List<Entry<K,V>> sortedEntries = new ArrayList<Entry<K,V>>(map.entrySet());
		Collections.sort(sortedEntries,
				new Comparator<Entry<K,V>>() {
			@Override
			public int compare(Entry<K,V> e1, Entry<K,V> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		}
				);
		Map<K, V> result = new LinkedHashMap<>();
		for (Entry<K, V> entry : sortedEntries) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	//升序
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


	@Override
	public int returnstatus(String senderid) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public ReturnInfo getReturn(Map<Set<Integer>, Integer> parameter_solutionlist, Set<Parameter> initalparameters) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
