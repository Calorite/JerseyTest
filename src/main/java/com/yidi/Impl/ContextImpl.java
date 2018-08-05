package com.yidi.Impl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;





public class ContextImpl {

//	Logger log= LoggerFactory.getLogger(ContextImpl.class);
	public static  Set<String> findLastParamenters(String userid) throws IOException {
		// TODO Auto-generated method stub
		DBService helper=new DBService();
		String sql="SELECT * FROM ai_qanda.user_dialogue_tb where username='?' order by datetime desc limit 1;";
		try {
			String[] params= {userid};
			List<Object> returnlist=helper.excuteQuery(sql, params);
			if(returnlist.size()>0) {
				int index=0;
				String setstr="";
				int replyid=0;
				for(Object obj:returnlist) {
					index++;
					if(index==5) {
						replyid=(int)obj;
					}
					if(index==6) {
						setstr=String.valueOf(obj);
					}
					if(index==7&&(int)obj==0) {//type=0未完成的谈话
						Set<String> parameterset=new HashSet<String>();
						String[] arry=setstr.split(",");
						for(String item:arry) {
							parameterset.add(String.valueOf(item));
						}
						if(parameterset.size()>0) {
							return parameterset;
						}
					}
				}
			}
		}catch(Exception e) {
			//log.trace("SELECT SQL = {}", sql);
			return null;
		}
		return null;
	}

	public static boolean insert(String userid,String receive,String reply,Set<String> paramenters,int replyid,int type) throws IOException {
		// TODO Auto-generated method stub
		DBService helper=new DBService();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql="INSERT INTO `ai_qanda`.`user_dialogue_tb` (`username`, `receive`, `reply`, `datetime`, `replyid`, `parameters`, `type`) VALUES ('？', '？', '？', '？', '？', '？', '？');";
		String parameterstr=StringUtils.join(paramenters.toArray(), ";");
		Object[] params= {userid,receive,reply,df,replyid,parameterstr,type};
		try {
			int returnrows=helper.executeUpdate(sql, params);
			if(returnrows==1) {
				return true;
			}
		}catch(Exception e) {
			//log.trace("insert SQL = {}", sql);
		}
		return false;
	}


	public static boolean updateStatu(String userid) throws IOException {
		// TODO Auto-generated method stub
		DBService helper=new DBService();
		String sql="UPDATE `ai_qanda`.`user_dialogue_tb` SET `type` = '1' WHERE (`username` = '?')";
		Object[] params= {userid};
		try {
			int returnrows=helper.executeUpdate(sql, params);
			if(returnrows==1) {
				return true;
			}
		}catch(Exception e) {
			//log.trace("update SQL = {}", sql);
		}
		return false;
	}

}
