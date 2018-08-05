package com.yidi.JerseyTest;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yidi.DaoImpl.AboutParameterImpl;
import com.yidi.DaoImpl.AboutQuestionImpl;
import com.yidi.DaoImpl.DBService;
import com.yidi.Impl.DBupdate;
import com.yidi.Impl.Parama;
import com.yidi.algorithm.Parama2Json;
import com.yidi.entity.Parameter;
import com.yidi.entity.parameInQuestion;
import com.yidi.interfactoty.ParameterService;
import com.yidi.service.DefaultServiceFactory;


/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent
	 * to the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public void getIt(@Context HttpServletRequest request,@Context HttpServletResponse response) {
		try {
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Path("getparametes")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)  //接受的参数类型为表单信息
	@Produces({MediaType.APPLICATION_JSON})
	public String getParametes(@FormParam("description") String text) throws IOException {
		Set<Parama> parametes;
		if(text!=null){
			try {
				DefaultServiceFactory factory=new DefaultServiceFactory();
				ParameterService process=factory.getparameterService();
				DBService helper=new DBService();
				Map<Integer,Parameter> parametermap=process.getInitialParameters(text,factory.getparameterDao(factory.getDBhelper()));
				Set<Parameter> initalparameterset=new HashSet<Parameter>();
				for(Parameter p:parametermap.values()){
					initalparameterset.add(p);
				}
				return (Parama2Json.GsonListStr(initalparameterset));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return "";
	}

	@Path("question")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)  //接受的参数类型为表单信息
	public String getQuestion(@FormParam("quesid") String questionid) {
		String question=DBupdate.getQustionStr(questionid);
		return question;
	}

	@Path("getparamaterbyquestionid")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)  //接受的参数类型为表单信息
	@Produces({MediaType.APPLICATION_JSON})
	public String getQuestionParameters(@FormParam("quesid") String questionid) throws SQLException {	
		return Parama2Json.GsonListStr2(DBupdate.getParameterByqueid(questionid));
	}

	@Path("updateparamaters")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)  //接受的参数类型为表单信息
	@Produces({MediaType.APPLICATION_JSON})
	public String getUpdateParameters(@FormParam("parametersJSON") String parametersjson) throws SQLException {	
		Gson gson=new Gson();
		List<parameInQuestion> parameters=gson.fromJson(parametersjson, new TypeToken<List<parameInQuestion>>(){}.getType());
		int count=0;
		for(parameInQuestion pip:parameters){
			if(DBupdate.updateParameter(pip)){
				count++;
			}

		}
		if (count==parameters.size()) {
			return "true";
		}
		return "false";
	}
	
	@Path("insertnewparameter")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)  //接受的参数类型为表单信息
	@Produces({MediaType.APPLICATION_JSON})
	public String getNewParameterID(@FormParam("parame") String parameter) throws SQLException {	
		try {
			String id=String.valueOf(DBupdate.Insertnewparameter(parameter));
			return id;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("fail");
		}
		return "0";
	}
	
	
	@Path("insertsolution")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)  //接受的参数类型为表单信息
	@Produces({MediaType.APPLICATION_JSON})
	public String getsolutionID(@FormParam("solutiontext") String parameter) throws SQLException {	
		try {
			String id=String.valueOf(DBupdate.Insertsolution(parameter));
			System.out.println(id);
			return id;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("fail");
		}
		return "0";
	}
	
	@Path("putInparametersolution")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)  //接受的参数类型为表单信息
	@Produces({MediaType.APPLICATION_JSON})
	public String parametersolution(@FormParam("parameters") String parameters,@FormParam("solutionid") String solutionid) throws SQLException {	
		try {
			String parameStr=parameters.replace("[", "").replace("]", "").replace("\"","");
			System.out.println(parameters);
			System.out.println(solutionid);
			String soluid=String.valueOf(DBupdate.getSolutionid(solutionid));
			DBupdate.InsertSolution(parameStr,soluid);
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("fail");
		}
		return "0";
	}
	
	
	@Path("putNewparameter")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)  //接受的参数类型为表单信息
	@Produces({MediaType.APPLICATION_JSON})
	public String insertnewParameterANDQuestion(@FormParam("newparameter") String parameters,@FormParam("question") String solutionid) throws SQLException {	
		try {
			Gson gson=new Gson();
			Map<String, String> returnmap=new HashMap<>();
			AboutParameterImpl paImpl=new AboutParameterImpl();
			Parameter parater=paImpl.insertParametergetID(parameters);
			int questionid=paImpl.insertQuestionID(solutionid, String.valueOf(parater.getParameterid()),parameters);
			paImpl.updateParameterquestionid(parater.getParameterid(), questionid);
			parater.setQuestionid(questionid);
			List<Parameter> pmeters=new ArrayList<>();
			Map<Integer, Parameter> allparameters=paImpl.getparams();
			for(Parameter p: allparameters.values()){
				pmeters.add(p);
			}
			String returnstr=Parama2Json.listToJSON(pmeters);
			String returnparama=String.valueOf(parater.getParameterid())+';'+parater.getParameter();
			returnmap.put("parameterIN", returnparama);
			returnmap.put("parameterlist", returnstr);
			returnmap.put("questionid", String.valueOf(questionid));
			return gson.toJson(returnmap);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("fail");
		}
		return "0";
	}
	
	
	@Path("bundnewparameter")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)  //接受的参数类型为表单信息
	@Produces({MediaType.APPLICATION_JSON})
	public String updateParameterForquestion(@FormParam("parameter") String parameters,@FormParam("questionid") String id) throws SQLException {	
		try {
			String parameliststr=parameters.replace("[", "").replace("]", "").replace("\"","");
			if(AboutQuestionImpl.updateBundparameter(parameliststr, id)){
				return "1";
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return "0";
		}
	
}
