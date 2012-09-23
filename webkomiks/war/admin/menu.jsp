<!-- menu in admin console -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<a data-target=".nav-collapse" data-toggle="collapse" class="btn btn-navbar">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</a>
			<a href="/admin" class="brand">Admin Console</a>
			<div class="nav-collapse">
				<ul class="nav">
					<li class="active"><a href="/admin">Home</a></li>
					<li class="dropdown">
	              		<a data-toggle="dropdown" class="dropdown-toggle" href="#">Genre<b class="caret"></b></a>
	              		<ul class="dropdown-menu">
							<li><a href="/admin/genre/new.jsp">Add</a></li>
							<li><a href="/admin/genre/list">List</a></li>
						</ul>
					</li>
					<li class="dropdown">
	              		<a data-toggle="dropdown" class="dropdown-toggle" href="#">Development<b class="caret"></b></a>
	              		<ul class="dropdown-menu">
							<li><a href="/admin/resetFTS">Reset FTS Index</a></li>
						</ul>
					</li>
				</ul>
			</div>
			
			<!--  admin logout-->
			<ul class="nav pull-right">				
				<li class="divider-vertical"></li>
				<li class="dropdown">
					<a href="<s:property value="@comics.util.Url@logoutUrl('/comics/list')"/>">Log Out</a>
				</li>
			</ul>
		</div>
	</div>
</div>