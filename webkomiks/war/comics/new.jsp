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
	<s:include value="/content/menu1.jsp" />

		<div class="container">
		<div class="row-fluid">
   			<div class="span12">
   				<h3>Add Comics</h3>
   				
   				<!-- field error -->
	   			<s:if test="%{#attr['errorMessages'].size != 0}">
					<div class="alert alert-error actionError">
						<a href="#" class="close" data-dismiss="alert">×</a>
						<ul>
		    				<s:iterator var="message" value="%{#attr['errorMessages']}">
		    					<li><s:property value="message" /></li>	
		    				</s:iterator>
		    			</ul>
					</div>
				</s:if>
   				
   				<form class="form-horizontal" action="<s:property value="@comics.util.BlobUtil@uploadUrl('/upload/add')"/>" method="post" enctype="multipart/form-data">
					<div class="control-group">
						<label class="control-label">Title</label>
						<div class="controls">
							<input type="text" name="title" placeholder="title" value="${title }"/>
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label">Description</label>
						<div class="controls">
							<textarea rows="5" name="description" placeholder="description">${description }</textarea>
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label">Genre</label>
						<div class="controls">
							<select name="genre" multiple="multiple">
								<s:iterator var="genre" value="@comics.admin.genre.GenreAccess@genreList()">
									<option value="<s:property value="genre"/>"><s:property value="genre"/></option>
								</s:iterator>
							</select>
						</div>
					</div>

					<div class="control-group">
						<label class="control-label">Cover</label>
						<div class="controls">
							<input type="file" name="cover" placeholder="cover" />
						</div>
					</div>
					
					<div class="control-group">
						<div class="controls">
							<button type="submit" class="btn">Add</button>
						</div>
					</div>
					
				</form>
   			</div>
   			
   		</div>
   		</div>
	
	
	<script type="text/javascript" src="/js/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>