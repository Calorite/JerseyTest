package com.yidi.application;

import com.yidi.interfactoty.ConsultationFactory;

public class Application {
	ConsultationFactory consule;
	public Application(String sendusr,String tousr,String text) {
		consule.text(text);
	}
}
