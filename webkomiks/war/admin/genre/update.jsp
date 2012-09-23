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
	
	<style type="text/css">

	</style>
	
</head>
<body>
	<s:include value="/admin/menu.jsp"/>

		<div class="container row-fluid">
   			<div class="span12">
   				<h3>Update Genre</h3>
   				<s:form action="update" cssClass="form-horizontal" namespace="/admin/genre" theme="simple">
					<div class="control-group">
						<label class="control-label">Genre</label>
						<div class="controls">
							<s:textfield name="genre.genre" readonly="true"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">Description</label>
						<div class="controls">
							<s:textarea name="genre.description"/>
						</div>
					</div>
					
					<div class="control-group">
						<div class="controls">
							<s:submit cssClass="btn" value="save"/>
						</div>
					</div>
				</s:form>
   			</div>
   			
   		</div>
		

 	<script type="text/javascript" src="/js/jquery/jquery-1.7.2.min.js"></script>
 	<script type="text/javascript" src="/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>