<%@ taglib prefix="s" uri="/struts-tags"%>   
<div class="span8">
	<s:iterator value="list">
		<h1 class="row">
			<a href="/comics/<s:property value="random"/>/<s:property value="url"/>" title="<s:property value="title"/>" target="_blank">
				<s:property value="title"/>
			</a>
		</h1>
		<div class="comicsItem row">
			<div class="span8">
				<a href="/comics/<s:property value="random"/>/<s:property value="url"/>" target="_blank">
					<img src="<s:property value="@comics.util.BlobUtil@servingUrl(coverBlobKey)"/>=s700" alt="<s:property value="title"/>"/>
				</a>
			</div>
			<div class="span4">
				<p><s:property value="description"/></p>
				<div>genre: <s:property value="@comics.util.Url@genreUrl(genre)" escape="false"/></div>
				<span>by: <a href="/user/<s:property value="user.userName"/>"><s:property value="user.userName"/></a></span>
			</div>
		</div>
	</s:iterator>
	
	<div id="loading" class="row"><s:hidden id="nextCursor" name="nextCursor" /></div>
</div>