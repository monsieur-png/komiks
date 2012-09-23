<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>       
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<title>Insert title here</title>

	<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/bootstrap/css/bootstrap-responsive.min.css" />
	<link rel="stylesheet" href="/bootstrap/css/menu.css" />
	<link rel="stylesheet" href="/css/main.css" />

</head>
<body>
	<input id="nextListAction" type="hidden" value="1" />
	<s:hidden id="userName" name="user.userName"/>

	<div class="container">
		<div class="row">
			<s:include value="/content/menu1.jsp" />
		</div>
		
		
		<div class="row-fluid">
			<s:include value="/content/comics-list.jsp"/>
			
			<div id="rightBar" class="span4">
				<div class="well well-small">
					<h4><s:property value="user.fullName"/></h4>
					<p>
						<s:property value="user.aboutMySelf"/>
					</p>
				</div>
				
				<div class="well well-small">
					<h4><s:property value="user.userName"/>'s channel</h4>
					<table class="table">
						<tbody>
							<tr>
								<td>Last Activity</td>
								<td><s:date name="user.lastLogin" format="MMM dd, yyyy"/> </td>
							</tr>
							<tr>
								<td>Date Joined</td>
								<td><s:date name="user.dateCreated" format="MMM dd, yyyy"/> </td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

		</div>
	</div>

	<script type="text/javascript" src="/js/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/jquery/plugin/jquery.bottom-1.0.js"></script>
	<script type="text/javascript" src="/js/list.js"></script>
	<script type="text/javascript" src="/js/main.js"></script>

</body>
</html>