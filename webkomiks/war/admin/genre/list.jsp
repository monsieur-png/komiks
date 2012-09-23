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
	<s:include value="/admin/menu.jsp"/>

	<div class="container">
	<div class="row-fluid">
		<div class="span12">
			<h3>List Genre</h3>

	 		<!-- action message -->
			<s:if test="hasActionMessages()">
				<div class="alert alert-success actionMessage">
					<a href="#" class="close" data-dismiss="alert">×</a>
	    					<s:actionmessage/>
				</div>
			</s:if>
			
			<!-- action error -->
			<s:if test="hasActionErrors()">
				<div class="alert alert-error actionError">
					<a href="#" class="close" data-dismiss="alert">×</a>
	    				<s:actionerror/>
				</div>
			</s:if>
	 		 
	 		<table class="table table-bordered">
				<thead>
					<tr>
						<th>Genre</th>
						<th>Description</th>
						<th>Count</th>
						<th>Action</th>
					</tr>
				</thead>
	 			<tbody>
					<s:iterator value="list">
						<tr>
							<td><s:property value="genre"/></td>
							<td><s:property value="description"/></td>
							<td><s:property value="count"/></td>
							<td>
								<a href="/admin/genre/view/<s:property value="genre"/>">update</a>
								<a href="/admin/genre/delete/<s:property value="genre"/>">delete</a>
							</td>
						<tr>
	 				</s:iterator>
	 				</tbody>
	 		</table>

 		</div>
 			
 	</div>
	</div>

 	<script type="text/javascript" src="/js/jquery/jquery-1.7.2.min.js"></script>
 	<script type="text/javascript" src="/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>