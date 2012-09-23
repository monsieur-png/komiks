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
		.comicsItem{
			text-align: center;
		}
		#pageCarousel{
			min-height: 200px;
		}
	</style>

</head>
<body>

	<div class="container">
		<div class="row">
			<s:include value="/content/menu1.jsp" />
		</div>
		
		<div class="row-fluid">
			<div class="span8">
				<div class="comicsItem row">
					<h3><a href="/comics/<s:property value="comics.random"/>/<s:property value="comics.url"/>"><s:property value="comics.title"/></a></h3>
					
					<s:form cssClass="form-inline" action="chapter" namespace="/comics" theme="simple">
						<input type="hidden" name="key" value="<s:property value="@comics.util.Url@key(comics.key)"/>" />
						<select name="chapterKey">
							<option value="cover">cover</option>
							<s:iterator value="comics.chapters">
								<s:if test="pageCount != 0">
									<option value="<s:property value="@comics.util.Url@key(key)"/>"><s:property value="title"/></option>
								</s:if> 
							</s:iterator>	
						</select>					
						<s:submit cssClass="btn" value="view"/>
					</s:form>
					
					<s:if test='chapterKey == null || chapterKey == "cover" '>
						<img src="<s:property value="@comics.util.BlobUtil@servingUrl(comics.coverBlobKey)"/>=s700"/>
					</s:if>
					<s:else>
					<div id="pageCarousel" class="carousel slide" >
						<div class="carousel-inner">
							<s:iterator value="chapter.pages">
								<div class="item">
									<img src="<s:property value="@comics.util.BlobUtil@servingUrl(pageBlobKey)"/>"/>
								</div>
							</s:iterator>
						</div>
						
						<!-- carousel nav -->
						<a class="carousel-control left" href="#pageCarousel" data-slide="prev">&lsaquo;</a>
						<a class="carousel-control right" href="#pageCarousel" data-slide="next">&rsaquo;</a>
					</div>
					</s:else>
				</div>
				
				<p><s:property value="comics.description"/></p>
					<p>by: <a href="/user/<s:property value="comics.user.userName"/>"><s:property value="comics.user.userName"/></a></p>
				<p>
				
				</p>
			</div>
			
			<s:include value="/content/rightbar.jsp"/>
		</div>

	</div>

	<script type="text/javascript" src="/js/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/jquery/plugin/jquery.bottom-1.0.js"></script>
	<script type="text/javascript" src="/js/main.js"></script>
	
	<script type="text/javascript">
		
		$(function() {
			$('#pageCarousel div:first-child').addClass('active');
			$('#pageCarousel').carousel({
				  interval: 15000
			})
			
		});
		
	</script>

</body>
</html>