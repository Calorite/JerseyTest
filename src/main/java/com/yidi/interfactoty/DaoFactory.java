package com.yidi.interfactoty;

import com.yidi.DaoImpl.DBService;

public interface DaoFactory {
	ParameterService getprocess();
	AboutParametersDAO getparametersdao(DBService helper);
	AboutQuestionDAO getquestiondao();
	AboutSolutionDAO getsolution();
}
