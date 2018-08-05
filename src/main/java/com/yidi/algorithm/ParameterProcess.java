package com.yidi.algorithm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import java.util.Set;

import com.yidi.Impl.DBupdate;
import com.yidi.Impl.Parama;

public class ParameterProcess {
	private static Set<Integer> stopwords=new HashSet<Integer>(187);
	private static Map<Integer,Parama> allparamenter=null;
	public boolean checkandpara(String para,String text) {
		if(para.contains("&")) {
			String[] pararray=para.split("&");
			int count=0;
			for(String parameter:pararray) {
				if(parameter.contains("!")){
					if(text.contains(parameter)) {

					}else {
						count++;
					}
				}
				else if(text.contains(parameter)) {
					count++;
				}
			}
			if(count==pararray.length) {
				return true;
			}
		}else {
			if(text.contains(para)) {
				return true;
			}
		}
		return false;
	}


	public boolean checkParamete(String parameline,String text) {
		if(parameline.contains("/")) {//有多个参
			String[] paramelist=parameline.split("/");
			for(String parameter:paramelist) {
				if(checkandpara(parameter,text)) {
					return true;
				}else {
					return false;
				}
			}
		}else {
			if(checkandpara(parameline,text)) {
				return true;
			}else {
				return false;
			}
		}
		return false;
	}

	public static Set<Integer> getValidparameters(Map<Set<Integer>, Integer> parameterlist,Set<Integer> getedpara){
		Map<Set<Integer>,Integer> retainmap=new HashMap<Set<Integer>,Integer>();
		//Map<Set<Integer>,Set<Integer>> originalset=new HashMap<Set<Integer>,Set<Integer>>();
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
		Set<Integer> newset=entry.getKey();
		return newset;
	}

	public Set<Parama> getParemetes(String text) throws SQLException{
		allparamenter=DBupdate.getparams();
		Set<Parama> targetpara=new HashSet<Parama>();
		for(Parama curtparamente:allparamenter.values()) {
			if(checkParamete(curtparamente.getParama(),text)) {
				targetpara.add(curtparamente);
			}
		}
		return targetpara;
	}

	public static boolean compareSet(Set<Integer> set1,Set<Integer> set2) {
		if(set1.equals(set2)) {
			return true;
		}else {
			return false;
		}

	}

	public static Set<Integer> containsSet(Set<Integer> set1,Set<Integer> set2){
		if(set1.containsAll(set2)) {
			Set<Integer> set3 = new HashSet<Integer>();
			set3.addAll(set1);
			set3.removeAll(set2);
			return set3;
		}else {
			return null;
		}
	}

	public List<String> returnidbyVildparameters(Map<Set<Integer>, Integer> parameterlist,Set<Integer> newset) throws SQLException{
		List<String> returnlist=new LinkedList<String>();
		Set<Integer> leave=new HashSet<Integer>();
		if(newset.size()>0) {
			for(Set<Integer> set:parameterlist.keySet()) {
				if(compareSet(set,newset)) {//全包含返回solutionid
					returnlist.add("solution");
					returnlist.add(String.valueOf(parameterlist.get(set)));
					return returnlist;
				}else {
					if(set.containsAll(newset)) {
						Set<Integer> inset=containsSet(set,newset);//包含返回set
						if(inset!=null) {
							leave.addAll(inset);
						}
					}
				}
			}
			if(leave.size()>0) {
				int returnid=DBupdate.getQuestionid(0, leave,allparamenter);
				if(returnid!=1000) {
					returnlist.add("question");
					returnlist.add(String.valueOf(returnid));
					return returnlist;
				}else {
					return null;
				}
			}
		}
		return null;
	}



	public List<String> returnquesid(Map<Set<Integer>, Integer> parameterlist,Set<Parama> parametes) throws SQLException {//返回questionid或者solutionid
		List<String> returnlist=new LinkedList<String>();
		Set<Integer> leave=new HashSet<Integer>();
		Set<Integer> getedpara=new HashSet<Integer>();
		for(Parama curpara:parametes) {
			getedpara.add(curpara.getId());
		}
		for(Set<Integer> set:parameterlist.keySet()) {
			if(compareSet(set,getedpara)) {//全包含返回solutionid
				returnlist.add("solution");
				returnlist.add(String.valueOf(parameterlist.get(set)));
				return returnlist;
			}else {
				Set<Integer> inset=containsSet(set,getedpara);//包含返回set
				if(inset!=null) {
					leave.addAll(inset);
				}
			}
		}
		if(leave.size()>0) {
			int returnid=DBupdate.getQuestionid(0, leave,allparamenter);
			if(returnid!=1000) {
				returnlist.add("question");
				returnlist.add(String.valueOf(returnid));
				return returnlist;
			}else {
				return null;
			}
		}else {//没有包含集合查找最相似集合
			return null;
		}
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


//	public static void main(String[] args) throws SQLException {
//		ParameterProcess pp=new ParameterProcess();
//		Set<Parama> parametes=pp.getParemetes("狗狗能吃瓜子吗");
//		Set<Integer> getedpara=new HashSet<Integer>();
//		Map<Set<Integer>, Integer> parameterlist=DBupdate.getsolutionlist();
//		for(Parama curpara:parametes) {
//			getedpara.add(curpara.getId());
//		}
//		if(getedpara.size()==1&&stopwords.containsAll(getedpara)) {
//			//API
//		}else {
//			if(parametes.size()>0) {
//				List<String> returnnum=pp.returnquesid(parameterlist,parametes);
//				if(returnnum==null) {
//					Set<Integer> vildparameters=getValidparameters(parameterlist,getedpara);
//					if(vildparameters.size()==1&&stopwords.containsAll(vildparameters)) {
//						//API
//					}else {
//						List<String> returnlist=pp.returnidbyVildparameters(parameterlist,vildparameters);
//						if(returnlist.size()>0) {
//							if(returnlist.get(0)=="solutin") {
//								System.out.println(DBupdate.getSolutinStr(returnlist.get(1)));
//							}else if(returnlist.get(0)=="question") {
//								System.out.println(DBupdate.getQustionStr(returnlist.get(1)));
//							}
//						}else {
//							//API
//						}
//					}
//				}else {
//					if(returnnum.get(0)=="solutin") {
//						System.out.println(DBupdate.getSolutinStr(returnnum.get(1)));
//					}else if(returnnum.get(0)=="question") {
//						System.out.println(DBupdate.getQustionStr(returnnum.get(1)));
//					}
//				}
//			}else {
//				//API
//			}
//		}
//	}
}
