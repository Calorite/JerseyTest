package com.yidi.service;

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


import com.yidi.entity.Parameter;
import com.yidi.entity.ReturnInfo;
import com.yidi.interfactoty.AboutParametersDAO;
import com.yidi.interfactoty.AboutQuestionDAO;
import com.yidi.interfactoty.AboutSolutionDAO;
import com.yidi.interfactoty.ParameterService;
import com.yidi.interfactoty.TextInfoBytypeFactory;

public class MainService implements TextInfoBytypeFactory {
	private String senderid;
	private String tousr;
	private String text;
	private ParameterService process;
	private AboutParametersDAO parametersdao;
	private AboutSolutionDAO solutiondao;
	private AboutQuestionDAO questiondao;
	TextInfoBytypeFactory factory;
	public MainService(String senderid,String tousr,String text) throws SQLException {
		DefaultServiceFactory factory=new DefaultServiceFactory();
		this.process=factory.getparameterService();
		this.parametersdao=factory.getparameterDao(factory.getDBhelper());
		this.senderid=senderid;
		this.tousr=tousr;
		this.text=text;

		//查询历史纪录   宠物类型之类的处理
		if(text.contains("猫")) {

		}else {
			dog();
		}


	}

	@Override
	public ReturnInfo dog() {
		// TODO Auto-generated method stub
		try {
			Map<Integer, Parameter> initalparameters=process.getInitialParameters(text,parametersdao);
			Map<Set<Integer>, Integer> parameter_solutionlist=solutiondao.getsolutionlist();
			factory.getReturnMSG(parameter_solutionlist, initalparameters);
			Set<Parameter> initalparameterset=new HashSet<Parameter>();
			for (int id:initalparameters.keySet()) {
				initalparameterset.add(initalparameters.get(id));
			}
			Map<Integer, Parameter> vaildparameters=process.getValidparameters(parameter_solutionlist, initalparameterset);
			factory.getReturnMSG(parameter_solutionlist, vaildparameters);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ReturnInfo cat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public  ReturnInfo getReturnMSG(Map<Set<Integer>, Integer> parameter_solutionlist,Map<Integer, Parameter> parameters) {
		Set<Integer> parameteridset= new HashSet<Integer>();
		for (int id:parameters.keySet()) {
			parameteridset.add(id);
		}
		for (Set<Integer> key : parameter_solutionlist.keySet()) {
			if(key.containsAll(parameteridset)) {
				int solutionid=parameter_solutionlist.get(key);
				return new ReturnInfo(solutionid, 1, solutiondao.getSolutinStr(String.valueOf(solutionid)));
			}
		}
		Map<Integer,Integer> idrankmap=new HashMap<>();
		for (Set<Integer> key : parameter_solutionlist.keySet()) {
			Set<Integer> retainset=new HashSet<Integer>();
			retainset.addAll(key);
			retainset.retainAll(parameteridset);
			if(retainset.size()>0) {//有并集
				Set<Integer> newretainset=new HashSet<Integer>();
				newretainset.addAll(key);
				newretainset.retainAll(retainset);//并集在key集合内的补集
				for(int id:newretainset) {
					idrankmap.put(id, parameters.get(id).getRank());
				}
			}
		}
		idrankmap=sortByValue(idrankmap);
		if(idrankmap.size()>0) {
			Entry<Integer, Integer> entry = idrankmap.entrySet().iterator().next();
			int id=entry.getKey();
			int questionid=parameters.get(id).getQuestionid();
			return new ReturnInfo(questionid, 0, questiondao.getQustionStr(String.valueOf(questionid)));
		}else return null;
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
