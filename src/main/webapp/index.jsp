<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8" import="com.yidi.Impl.Parama" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<script src="../lib/jquery.js"></script>
<script src="../lib/jquery.cookie.js"></script>
<script src="../lib/bootstrap.js"></script>
<link href="../css/bootstrap.min.css" rel="stylesheet" />
<link href="../css/bootstrap.css" rel="stylesheet" />
<link href="../css/myshow.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="../css/listswap.css" />
</head>
<body>
	<br>
	<div>
		<div class="leftitem form-group">
			<textarea id="textn" name="description" rows="15" cols="30"
				onClick="SelectText()"></textarea>
		</div>
		<button type="button" class="leftitem" id="tijiao">提交</button>
		<div class="leftitem">
				<label for="solution">Solution:</label>
				<textarea id="solution" rows="15" cols="30"></textarea>
			<!--	<button id="zhunbei" class="btn btn-default">准备</button>-->
				<button id="tianjia" class="btn btn-default">添加新的Solution</button>
		</div>
		<div class="endleft form-group" id="parameterlist">
			<table id="tbe" class="table"
				style="border-collapse: separate; border-spacing: 0px 7px;">
			</table>
		</div>
	</div>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="../js/jquery.listswap.js"></script>
	<script src="../js/index.js" charset="utf-8"></script>
</body>
</html>