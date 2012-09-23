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
		#pages div{
			float: left;
			margin: 5px;
		}
		form .buttons{
			text-align: center;
		}
		.close{
			color: red;
		}
	</style>
	
</head>
<body>
	<s:include value="/content/menu1.jsp"/>

	<div class="container">
		<div class="row-fluid">
			<div class="span4">
				<img class="img-polaroid" src="<s:property value="@comics.util.BlobUtil@servingUrl(chapter.comics.coverBlobKey)"/>"/>
			</div>
			<div class="span8">
				<h1><a href="/comics/update/<s:property value="@comics.util.Url@key(chapter.comics.key)"/>"><s:property value="chapter.comics.title"/></a></h1>
				<h2><s:property value="chapter.title"/></h2>
				<ul class="nav nav-tabs" id="pageTab">
					  <li class="active"><a href="#pages">Pages</a></li>
					  <li><a href="#addPage">Add Pages</a></li>
					  <li><a href="#updateChapter">Update</a></li>
				</ul>
	 
				<div class="tab-content">
				
					<div class="tab-pane active" id="pages">
						<s:form action="save" namespace="/chapter" theme="simple">
							<input type="hidden" name="key" value="<s:property value="@comics.util.Url@key(chapter.key)"/>"/>
							<s:iterator value="chapter.pages">
								<input type="hidden" name="allPageBlobKeys" value="<s:property value="pageBlobKey" />"/>
							</s:iterator>
							<div id="pagesThumbnails">
								<s:iterator value="chapter.pages" status="stat">
									<div>
										<a href="#" class="close" data-dismiss="alert">×</a>
										<input type="hidden" name="pageBlobKeys" value="<s:property value="pageBlobKey" />" />
										<img class="img-polaroid" src="<s:property value="@comics.util.BlobUtil@servingUrl(pageBlobKey)"/>=s150-c">
									</div>
								</s:iterator>
							</div>
							
							<div class="span11 buttons"><s:submit cssClass="btn" value="save"/></div>
						</s:form>
					</div>
				  
					<div class="tab-pane" id="addPage">
						<form class="form-horizontal" action="<s:property value="@comics.util.BlobUtil@uploadUrl('/upload/addpage')"/>" method="post" enctype="multipart/form-data">
							<input type="hidden" name="key" value="<s:property value="@comics.util.Url@key(chapter.key)"/>"/>
							<div class="control-group">
								<label class="control-label">Pages</label>
								<div class="controls">
									<input type="file" name="page" placeholder="pages" multiple="multiple"/>
								</div>
							</div>
							
							<div class="control-group">
								<div class="controls">
									<button type="submit" class="btn">Add</button>
								</div>
							</div>
							
						</form>
					</div>
					
					
					<div class="tab-pane" id="updateChapter">
						<s:form cssClass="form-horizontal" action="editInfo" namespace="/chapter" theme="simple">
							<input type="hidden" name="key" value="<s:property value="@comics.util.Url@key(chapter.key)"/>"/>
							<div class="control-group">
								<label class="control-label">Title</label>
								<div class="controls">
									<input type="text" name="chapter.title" value="<s:property value="chapter.title"/>"/>
								</div>
							</div>
							
							<div class="control-group">
								<div class="controls">
									<button type="submit" class="btn">Update</button>
								</div>
							</div>
							
						</s:form>
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
		$('#pageTab a').click(function (e) {
			e.preventDefault();
			$(this).tab('show');
		})
		
		$(function() {
			$( '#pagesThumbnails' ).sortable();
		});
		
	</script>
	
</body>
</html>