package com.yidi.interfactoty;

import com.yidi.DaoImpl.DBService;

public interface ServiceFactory {
	ParameterService getparameterService();
	AboutParametersDAO getparameterDao(DBService helper);
	DBService getDBhelper();
	AboutQuestionDAO getquestionDao(DBService helper);
	AboutSolutionDAO getsolutionDao(DBService helper);
}
