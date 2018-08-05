package com.yidi.service;

import com.yidi.DaoImpl.DBService;
import com.yidi.DaoImpl.DefaultDaoFactory;
import com.yidi.interfactoty.AboutParametersDAO;
import com.yidi.interfactoty.AboutQuestionDAO;
import com.yidi.interfactoty.AboutSolutionDAO;
import com.yidi.interfactoty.DaoFactory;
import com.yidi.interfactoty.ParameterService;
import com.yidi.interfactoty.ServiceFactory;

public class DefaultServiceFactory implements ServiceFactory {
	private DaoFactory daoFactory;

	public DefaultServiceFactory() {
		this(new DefaultDaoFactory());
	}

	public DefaultServiceFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public ParameterService getparameterService() {
		// TODO Auto-generated method stub
		return daoFactory.getprocess();
	}

	@Override
	public AboutParametersDAO getparameterDao(DBService helper) {
		// TODO Auto-generated method stub
		return daoFactory.getparametersdao(helper);
	}

	@Override
	public DBService getDBhelper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AboutQuestionDAO getquestionDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AboutSolutionDAO getsolutionDao() {
		// TODO Auto-generated method stub
		return null;
	}


}
