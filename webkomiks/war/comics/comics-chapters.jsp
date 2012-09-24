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
		#listChapters tbody tr{
			cursor: move;
		}
		
		table { counter-reset: row }
tbody tr { counter-increment: row }
tbody tr:before {
    content: counter(row);
    display: table-cell;
    vertical-align: middle;
}

tfoot tr:BEFORE {
	content: none;
}

		form .buttons{
			text-align: center;
		}

	</style>
	
</head>
<body>
	<s:include value="/content/menu1.jsp"/>

	<div class="container">
		<div class="row-fluid">
			<div class="span4">
				<img class="img-polaroid" src="<s:property value="@comics.util.BlobUtil@servingUrl(comics.coverBlobKey)"/>"/>
			</div>
			<div class="span8">
				<h1><s:property value="comics.title"/></h1>
				<p><s:property value="comics.description"/></p>
				
				<ul class="nav nav-tabs" id="chapterTab">
				  <li class="active"><a href="#chapters">Chapters</a></li>
				  <li><a href="#addChapter">Add Chapter</a></li>
				  <li><a href="#updateComics">Update</a></li>
				</ul>
	 
				<div class="tab-content">
					<div class="tab-pane active" id="chapters">
						<s:form action="save" namespace="/comics" theme="simple">
							<input type="hidden" name="key" value="<s:property value="@comics.util.Url@key(comics.key)"/>"/>
							<table id="listChapters" class="table table-hover">
						  		<thead>
						  			<tr>
						  				<td> </td>
						  				<td>Title</td>
						  				<td>Pages</td>
						  				<td>Action</td>
						  			</tr>
						  		</thead>
						  		<tbody>
						  			<s:iterator value="comics.chapters" status="stat">
						  				<tr>
						  					<td><input type="hidden" name="chKeys" value="<s:property value="@comics.util.Url@key(key)" />" /><a href="/chapter/update/<s:property value="@comics.util.Url@key(key)"/>"><s:property value="title"/></a></td>
						  					<td><s:property value="pageCount"/></td>
						  					<td>
						  						<a href="/chapter/update/<s:property value="@comics.util.Url@key(key)"/>">update</a>
						  						<a href="/chapter/delete/<s:property value="@comics.util.Url@key(key)"/>">delete</a>
						  					</td>
						  				</tr>
						  			</s:iterator>
						  			
						  			<s:if test="comics.chapters.size == 0">
						  					<tr><td> &nbsp; </td><td> </td><td> </td></tr>
						  			</s:if>
						  		</tbody>
						  		
							</table>
							<s:if test="comics.chapters.size != 0">
								<div class="span11 buttons"><s:submit cssClass="btn" value="save"/></div>
							</s:if>
						</s:form>
				  </div>
				  
				<div class="tab-pane" id="addChapter">
					<s:form action="add" cssClass="form-horizontal" namespace="/chapter" theme="simple">
						<input type="hidden" name="key" value="<s:property value="@comics.util.Url@key(comics.key)"/>"/>
						<div class="control-group">
							<label class="control-label">Chapter Title</label>
							<div class="controls">
								<input type="text" name="chapter.title" value="Chapter <s:property value="comics.chapterCount + 1"/>" />
							</div>
						</div>
						<div class="control-group">
							<div class="controls">
								<s:submit cssClass="btn" value="save"/>
							</div>
						</div>
					</s:form>
				</div>
				
				<div class="tab-pane" id="updateComics">
					<form class="form-horizontal" action="<s:property value="@comics.util.BlobUtil@uploadUrl('/upload/update')"/>" method="post" enctype="multipart/form-data">
						<input type="hidden" name="key" value="<s:property value="@comics.util.Url@key(comics.key)"/>"/>
						<s:iterator var="genre" value="comics.genre">
							<input type="hidden" name="oldGenre" value="<s:property value="genre"/>"/>
						</s:iterator>
						<div class="control-group">
							<label class="control-label">Title</label>
							<div class="controls">
								<input type="text" name="title" placeholder="title" value="<s:property value="comics.title"/>"/>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label">Description</label>
							<div class="controls">
								<textarea rows="5" name="description" placeholder="description"><s:property value="comics.description"/></textarea>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label">Genre</label>
							<div class="controls">
								<select name="genre" multiple="multiple">
									<s:iterator var="genre" value="@comics.admin.genre.GenreAccess@genreList()">
										<s:if test="%{comics.genre.contains(#genre)}">
											<option value="<s:property value="genre"/>" selected="selected"><s:property value="genre"/></option>
										</s:if>
										<s:else>
											<option value="<s:property value="genre"/>"><s:property value="genre"/></option>
										</s:else>
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
								<button type="submit" class="btn">Update</button>
							</div>
						</div>
						
					</form>
				</div>
				
				</div>
   			</div>
   		
   		</div>
  	</div>

	
	<script type="text/javascript" src="/js/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="/bootstrap/js/bootstrap.min.js"></script>

	<script type="text/javascript" src="/js/jquery/ui/jquery.ui.core.min.js"></script>
	<script type="text/javascript" src="/js/jquery/ui/jquery.ui.widget.min.js"></script>
	<script type="text/javascript" src="/js/jquery/ui/jquery.ui.mouse.min.js"></script>
	<script type="text/javascript" src="/js/jquery/ui/jquery.ui.sortable.min.js"></script>
		
	<script type="text/javascript">
		$('#chapterTab a').click(function (e) {
			e.preventDefault();
			$(this).tab('show');
		})
		
		$(function() {
			$( '#listChapters tbody' ).sortable({ cursor: 'move' });
		});
	</script>
	
</body>
</html>