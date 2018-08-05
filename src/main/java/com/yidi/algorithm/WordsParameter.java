package com.yidi.algorithm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;
import com.yidi.Impl.Parama;
import com.yidi.entity.Parameter;


public class WordsParameter {

	public static Set<Parameter> getParameterWords(Set<Parama> getedparama,String text){
		ParameterProcess pprocess=new ParameterProcess();
		Set<Parameter> set=new HashSet<Parameter>();
		JiebaSegmenter segmenter = new JiebaSegmenter();
		List<SegToken> list=segmenter.process(text, SegMode.INDEX);
		for(SegToken token:list) {
			for(Parama parameter:getedparama) {
			
			}

		}
		return set;

	}
}
