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
   				<h3>Add Genre</h3>
   				
   				<!-- field error -->
				<s:if test="hasFieldErrors()">
					<div class="alert alert-error actionError">
						<a href="#" class="close" data-dismiss="alert">×</a>
		    				<s:fielderror/>
					</div>
				</s:if>
				
			
   				<s:form action="add" cssClass="form-horizontal" namespace="/admin/genre" theme="simple">
					<div class="control-group">
						<label class="control-label">Genre</label>
						<div class="controls">
							<s:textfield name="genre.genre"/>
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
							<s:submit cssClass="btn" value="add"/>
						</div>
					</div>
				</s:form>
   			</div>
   			
   		</div>
   		</div>
		

 	<script type="text/javascript" src="/js/jquery/jquery-1.7.2.min.js"></script>
 	<script type="text/javascript" src="/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>