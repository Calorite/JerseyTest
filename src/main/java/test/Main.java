package test;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import com.yidi.entity.Parameter;
import com.yidi.entity.ReturnInfo;
import com.yidi.interfactoty.ProcessFactory;
import com.yidi.service.MainService;

public class Main {
	public Main() {
		
	}
	public static void main(String[] args) throws SQLException {
		MainService mainservice=new MainService("", "", "狗狗不爱动");
	}
}
