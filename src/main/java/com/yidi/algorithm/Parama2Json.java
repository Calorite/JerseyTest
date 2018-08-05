package com.yidi.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.yidi.Impl.ParamaJson;
import com.yidi.entity.Parameter;
import com.yidi.entity.parameInQuestion;


public class Parama2Json {

	public static String GsonListStr(Set<Parameter> initalparameterset) {
		Set<ParamaJson> parameterset=new HashSet<>();
		for(Parameter p:initalparameterset){
			parameterset.add(new ParamaJson(p));
		}
		Gson gson = new Gson();
		return gson.toJson(parameterset);
	}
	
	public static String GsonListStr2(List<parameInQuestion> parametes) {
		Gson gson = new Gson();
		return gson.toJson(parametes);
	}
	
	public static String strToJSON(String text){
		Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        map.put("info", text);     
        return gson.toJson(map);
	}
	
	public static String listToJSON(List<Parameter> parameterlist){
		Gson gson = new Gson();
		return gson.toJson(parameterlist);
	}
}
