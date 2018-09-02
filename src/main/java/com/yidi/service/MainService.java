package com.yidi.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.Map.Entry;

import com.yidi.entity.MaxUpperQuestion;
import com.yidi.entity.PSranklist;
import com.yidi.entity.Parameter;
import com.yidi.entity.ParameterSolution;
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
	private Map<Integer,Parameter> allparamenter;
	Map<Integer, Parameter> initalparameters=new HashMap<>();
	Set<Integer> uncheckparamerters=new HashSet<>();

	public MainService(String senderid,String tousr,String text) throws SQLException {
		DefaultServiceFactory factory=new DefaultServiceFactory();
		this.process=factory.getparameterService();
		this.parametersdao=factory.getparameterDao(factory.getDBhelper());
		this.solutiondao=factory.getsolutionDao(factory.getDBhelper());
		this.questiondao=factory.getquestionDao(factory.getDBhelper());
		this.senderid=senderid;
		this.tousr=tousr;
		this.text=text;
		this.allparamenter=parametersdao.getparams();
		initalparameters=process.getInitialParameters(allparamenter, text,parametersdao);
		//查询历史纪录   宠物类型之类的处理
		if(text.contains("猫")) {

		}else {
			List<ReturnInfo> lastRecord=process.returnpassedrecord(1, senderid);
			if (lastRecord.isEmpty()) {//新对话...
				if(initalparameters.size()==0){//没有参数
					//API
				}else {
					ReturnInfo infotag=dog();
					infotag.setRecieved(text);
					infotag.setUsername(senderid);
					if (process.insertReturnInfo(infotag)) {

					}else {

					}
				}
			}else if (lastRecord.get(0).getStatus()==0) {//话题中...
				if (lastRecord.get(0).getId().contains("A")) {
					
				}else {
					ReturnInfo infotag=dog();
					infotag.setRecieved(text);
					infotag.setUsername(senderid);
					if (process.insertReturnInfo(infotag)) {
						
					}else {
						
					}
				}
				
			}else{//新话题...
				if(initalparameters.size()==0){//没有参数
					//API
				}else {
					ReturnInfo infotag=dog();
					infotag.setRecieved(text);
					infotag.setUsername(senderid);
					if (process.insertReturnInfo(infotag)) {
						
					}else {
						
					}
				}
			}
		}
	}

	@Override
	public ReturnInfo dog() {
		try {
			String targetparamters="";
			String targetparamters2="";
			Map<Set<Integer>, ParameterSolution> parameter_solutionlist=solutiondao.getsolutionlist();
			ReturnInfo infotag=getReturnMSG(parameter_solutionlist, initalparameters);

			Set<Parameter> initalparameterset=new HashSet<Parameter>();
			for (int id:initalparameters.keySet()) {
				if(targetparamters.equals("")){
					targetparamters=String.valueOf(id);
				}else {
					targetparamters=targetparamters+","+String.valueOf(id);
				}
				initalparameterset.add(initalparameters.get(id));
			}
			infotag.setParameter(targetparamters);
			if (infotag!=null) {
				return infotag;
			}
			Map<Integer, Parameter> vaildparameters=process.getValidparameters(parameter_solutionlist, initalparameterset);
			for (int id:vaildparameters.keySet()) {
				if (targetparamters2.equals("")) {
					targetparamters2=String.valueOf(id);
				}else {
					targetparamters2=targetparamters2+","+String.valueOf(id);
				}
			}
			ReturnInfo infotag2=getReturnMSG(parameter_solutionlist, vaildparameters);
			infotag2.setParameter(targetparamters2);
			return infotag2;
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

	
	public ReturnInfo answerUpperquestion(ReturnInfo infoinstance,String text) {
		Map<Integer, Parameter> targetparameters=process.parameterInupperquestion(infoinstance.getId());
		Map<Integer, Parameter> parameterin=
		return infoinstance;
	}
	
	public  ReturnInfo getReturnMSG(Map<Set<Integer>, ParameterSolution> parameter_solutionlist,Map<Integer, Parameter> parameters) {
		Set<Integer> parameteridset= new HashSet<Integer>();
		for (int id:parameters.keySet()) {
			parameteridset.add(id);
		}
		for (Set<Integer> key : parameter_solutionlist.keySet()) {
			if(key.equals(parameteridset)) {
				ParameterSolution pSolution=parameter_solutionlist.get(key);
				int solutionid=pSolution.getSolution();
				return new ReturnInfo(String.valueOf(solutionid), 1, solutiondao.getSolutinStr(String.valueOf(solutionid)));
			}
		}
		Map<Integer,Integer> idrankmap=new HashMap<>();
		//
		List<String> uncheckupperquestion=new LinkedList<>();
		Set<Integer> upcheckparameterid=new HashSet<>();
		Map<Set<Integer>, Integer> parametersolutionnewlist=new HashMap<>();
		for(Set<Integer> key: parameter_solutionlist.keySet()){
			ParameterSolution thisPS=parameter_solutionlist.get(key);
			if(key.containsAll(parameteridset)){
				parametersolutionnewlist.put(key, thisPS.getSolutionrank());
			}
		}
		parametersolutionnewlist=sortByValueDesc(parametersolutionnewlist);
		Entry<Set<Integer>, Integer> entry = parametersolutionnewlist.entrySet().iterator().next();
		ParameterSolution firstPS=parameter_solutionlist.get(entry.getKey());
		parametersolutionnewlist.remove(entry.getKey());
		List<PSranklist> nowpsranklist=sortByrank(firstPS.getParameterset());
		int index=0;
		int questionid=0;
		String question="";
		for(PSranklist thisp:nowpsranklist){
			if(parameteridset.contains(thisp.getId())){

			}else {
				index++;
				if(index==1) {
					questionid=process.getquestionidbyparameterid(thisp.getId());
					question=process.getquestionbyid(String.valueOf(questionid));
				}else {
					uncheckupperquestion.add(allparamenter.get(thisp.getId()).getUpperquestion());
					upcheckparameterid.add(allparamenter.get(thisp.getId()).getParameterid());
				}
			}
		}
		for(Set<Integer> key:parametersolutionnewlist.keySet()) {
				for (Integer id:key) {
					if (parameteridset.contains(id)) {
						
					}else {
						Parameter parame=allparamenter.get(id);
						if (parame!=null) {
							uncheckupperquestion.add(parame.getUpperquestion());
							upcheckparameterid.add(parame.getParameterid());
						}
					}				
			}
		}
		MaxUpperQuestion maxtimesquestion=getMaxString(uncheckupperquestion);
		ReturnInfo infotag=null;
		if(maxtimesquestion.getCount()>1) {
			String id=maxtimesquestion.getQuestionid();
			infotag=new ReturnInfo(id, 0, questiondao.getUpperquestionbyid(id));
		}else {
			infotag=new ReturnInfo(String.valueOf(questionid), 0, question);
		}
		infotag.setUncheckparameter(upcheckparameterid);
		return infotag;
	}

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

	public static List<PSranklist> sortByrank(List<PSranklist> list1) {
		List<PSranklist> newlist=new LinkedList<>();
		newlist=list1.stream().sorted((u1, u2) -> u2.getRank()-(u1.getRank())).collect(Collectors.toList());
		return newlist;
	}

	public MaxUpperQuestion getMaxString(List<String> list1) {
		String regex;
        Pattern p;
        Matcher m;
		String tmp = "";
        String tot_str = list1.toString();
        int max_cnt = 0;
        String max_str = "";
        for(String str : list1) {
            if (tmp.equals(str)) continue;
            tmp = str;
            regex = str;
            p = Pattern.compile(regex);
            m = p.matcher(tot_str);
            int cnt = 0;
            while(m.find()) {
                cnt++;
            }
            if (cnt > max_cnt) {
                max_cnt = cnt;
                max_str = str;
            }
        }
		return new MaxUpperQuestion(max_str,max_cnt);
	}
}
