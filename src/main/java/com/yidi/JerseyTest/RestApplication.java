package com.yidi.JerseyTest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class RestApplication extends ResourceConfig {
	public RestApplication() { 
	packages("com.yidi.JerseyTest");  
    //注册JSON转换器  
    register(JacksonJsonProvider.class); 
    register(JspMvcFeature.class);                                  //注册MVC支持
    property(JspMvcFeature.TEMPLATE_BASE_PATH, "/");//jsp文件所在位置，当前JSP文件是在项目根目录下
   }  
}
