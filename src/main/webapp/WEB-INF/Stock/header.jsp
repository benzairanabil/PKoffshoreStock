<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.navbar-dark .navbar-nav .nav-link{
color:#d78817 !important;
border: solid 2px white;
border-radius: 20px;
background-color: white; 
}
.navbar-dark .navbar-nav .nav-link:hover{
text-decoration: underline;
 color: #ea148b;
 
}
.navbar-dark .navbar-nav .nav-link:active{
text-decoration: underline;
}

</style>
</head>
<body>
<nav class="navbar navbar-expand-sm bg-light navbar-dark">

  <ul class="navbar-nav navbar-right mr-auto">
  <li class="nav-item disabled" >
      <img style="   height: 100%;   width: 100%;padding-right:500px;" src="./assets/images/logo.svg" alt="logo">
    </li>
    <c:if test="${profile=='DEPOT'}">    
	    <li class="nav-item " style="margin-right:10px;" >
	      <a class="nav-link" href="scandepot">Scan Depot</a>
	    </li>
    </c:if>
      <c:if test="${profile=='PRODSCAN'}">
    <li class="nav-item" style="margin-right:10px;">
      <a class="nav-link" href="scanproduction">Scan Production</a>
    </li>
     </c:if> 
      <c:if test="${profile=='PRODCTRL'}">
    <li class="nav-item" style="margin-right:10px;">
      <a class="nav-link " href="ControleProduction">Controle Production</a>
    </li>
    </c:if>
      <li class="nav-item">
      <a class="nav-link" href="ConsultationCarton">Consultation</a>
    </li>
       <c:if test="${profile=='ADMIN'}">    
    <li class="nav-item" style="margin-left:10px;">
      <a class="nav-link " href="integration">Integration</a>
    </li>
    </c:if>
    <li class="nav-item" style="margin-left:330px; ">
      <a class="nav-link " href="logout"> 
      <img style="    width: 25px;" src="./assets/images/logout.png" alt="logout"></a>
    </li>
  </ul>
</nav>
</body>
</html>