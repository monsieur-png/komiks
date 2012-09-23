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
	<s:include value="/content/menu1.jsp"/>

	<div class="container">
	<div class="row-fluid">
		<div class="span12">
			<h3>My Comics</h3>

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
	 		 
	 		<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th> </th>
						<th>Title</th>
						<th>Description</th>
						<th>Genre</th>
						<th>Date Added</th>
						<th>Date Last Updated</th>
						<th>Views</th>
						<th>Votes</th>
						<th>Chapters</th>
						<th>Action</th>
					</tr>
				</thead>
	 			<tbody>
					<s:iterator value="list">
						<tr>
							<td><a href="/comics/update/<s:property value="@comics.util.Url@key(key)"/>"><img class="img-polaroid" src="<s:property value="@comics.util.BlobUtil@servingUrl(coverBlobKey)"/>=s100-c" /></a></td>
							<td><s:property value="title"/></td>
							<td><s:property value="description"/></td>
							<td><s:property value="@comics.util.Url@genreUrl(genre)" escape="false"/></td>
							<td><s:date name="dateAdded" format="MMM d, yyyy"/></td>
							<td><s:date name="dateLastUpdated" format="MMM d, yyyy"/></td>
							<td><s:property value="views"/></td>
							<td><s:property value="votes"/></td>
							<td><s:property value="chapterCount"/></td>
							<td>
								<a href="/comics/update/<s:property value="@comics.util.Url@key(key)"/>">update</a>
								<a href="/comics/delete/<s:property value="@comics.util.Url@key(key)"/>">delete</a>
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