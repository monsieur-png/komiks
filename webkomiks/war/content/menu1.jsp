<!-- menu for private pages -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
		<a data-target=".nav-collapse" data-toggle="collapse" class="btn btn-navbar">
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		</a>
		<a href="/" class="brand">Web Komiks</a>
		<div class="nav-collapse">
			<ul class="nav">
				<!-- home -->
				<s:if test="#session.userName != null">
					<li><a href="/user/home.jsp">Home</a></li>
				</s:if>
				
				<!-- genre -->
				<li class="dropdown">
              		<a data-toggle="dropdown" class="dropdown-toggle" href="#">Genre<b class="caret"></b></a>
	            	<ul class="dropdown-menu">
	            		<s:iterator var="genre" value="@comics.admin.genre.GenreAccess@genreList()">
							<li><a href="/genre/<s:property value="genre"/>"><s:property value="genre"/></a></li>
						</s:iterator>
	           		</ul>
				</li>
				
				<!-- comics management -->
				<s:if test="#session.userName != null">
					<li class="dropdown">
	              		<a data-toggle="dropdown" class="dropdown-toggle" href="#">Comics<b class="caret"></b></a>
	              		<ul class="dropdown-menu">
							<li><a href="/comics/new.jsp">Add</a></li>
							<li><a href="/comics/mycomics">My Comics</a></li>
						</ul>
					</li>
				</s:if>
			</ul>
			
			
			<form action="" class="navbar-search pull-left">
				<input type="text" placeholder="Search" class="search-query span2">
			</form>
		</div>
		
		<!-- sign up / log in -->
		<ul class="nav pull-right">
			<s:if test="#session.userName == null">
				<li><a href="#">Sign Up</a></li>
				<li class="divider-vertical"></li>
				<li class="drop down">
					<a class="dropdown-toggle" href="#" data-toggle="dropdown">Log In <strong class="caret"></strong></a>
				
					<div id="signinForm" class="dropdown-menu">
						<form action="#" method="post">
							<input id="userName" type="text" name="userName" />
							<input id="password" type="password" name="password" />
							<input class="btn" type="submit" value="Log In" />
							<a class="btn" href="<s:property value="@comics.util.Url@loginUrl('/login')"/>">Log in w/ Google</a>
							<a class="btn" href="#">Log in w/ Facebook</a>
						</form>
	           		</div>
				</li>
			</s:if>
			
			<s:else>
				<!--  user, log out-->
				<li><a href="/account/view"><s:property value="#session.userName" /></a></li>
				<li class="divider-vertical"></li>
				<li class="dropdown">
					<a href="/logout">Log Out</a>
				</li>
			</s:else>
			
		</ul>
	
		
		</div>
		
		
	</div>
</div>