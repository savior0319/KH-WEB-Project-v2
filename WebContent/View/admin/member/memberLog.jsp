<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*,jsp.admin.model.vo.*" %>
<!DOCTYPE html>
<html>

<head>

<!-- Global site tag (gtag.js) - Google Analytics -->
<script async
	src="https://www.googletagmanager.com/gtag/js?id=UA-120156974-1"></script>
<script>
	window.dataLayer = window.dataLayer || [];
	function gtag() {
		dataLayer.push(arguments);
	}
	gtag('js', new Date());
	gtag('config', 'UA-120156974-1');
</script>

<jsp:include page="/View/main/layout/cssjs.jsp"></jsp:include>

<title>title</title>
</head>

<body>
	<!-- 메뉴 바 -->
  <jsp:include page="/View/admin/layout/sideMenu.jsp"></jsp:include>
  <!-- 이 부분이 본문 -->
  <div class="ui pusher">
  <!-- 헤더 부분  -->
    <div class="ui segment">
      <h3 class="ui header">회원 로그인 내역</h3>
    </div>
    <!-- 본문 내용 시작-->
    <div class="ui container">
    	<!-- 테이블 시작 -->
    	<h1>로그인 내역</h1>
    	   <%
    	   ArrayList<MemberLoginLogVo> list = ( ArrayList<MemberLoginLogVo>)request.getAttribute("allMemberLog");			 	
			%> 	
    	<table class="ui celled table">
		  <thead>
		    <tr>
			    <th>회원 아아디</th>
			    <th>로그인 시간</th>
			    <th>접속 브라우저</th>
			    <th>접속 아이피</th>
			    <th>접속 지역 </th>
		  	</tr>
		  </thead>
		  <!--  객실 관리   -->
		  <!--  이 부분에 추가  -->
		   
  		  <tbody>
  		  <%if( list != null && !list.isEmpty()){ %>
		   	<%for(MemberLoginLogVo mllv : list){ %>
		   	<tr>
		   		<td><%= mllv.getMbLogId() %></td>
		   		<td><%= mllv.getMbLogTime() %></td>
		   		<td><%= mllv.getMbLogBrowser() %></td>
		   		<td><%= mllv.getMbLogIp() %></td>
		   		<td><%= mllv.getMbLogLocale() %></td>
		   		<!--  성별을 남여로  -->
		   	</tr>
		   	<%} %>
		 
		  </tbody>
		  <!--  페이지 처리를 하는 부분. -->
		 
		  <!-- <tfoot>
		    <tr>
		    <th colspan="3">
		      <div class="ui right floated pagination menu">
		        <a class="icon item">
		          <i class="left chevron icon"></i>
		        </a>
		        <a class="item">1</a>
		        <a class="item">2</a>
		        <a class="item">3</a>
		        <a class="item">4</a>
		        <a class="icon item">
		          <i class="right chevron icon"></i>
		        </a>
		      </div>
		    </th>
		  </tr>
		  </tfoot> -->
		</table>
		<%-- <label><%= pageNavi %></label><br> --%>
		 <!-- <form action="searchNotice" method="post" style="display: inline;">
		 <input type="text" name="searchData" >
		 <input type="submit" value="검색">
		 </form> -->
		
    	<!--  테이블 끝 -->
    	<% }else{ %>
    	<hr/>
    	<h3>기록이 없습니다.</h3>
    	<hr/>
    	<% } %>
    </div>
    <!-- 본문 내용 끝  -->
  </div>

<script>
</script>

</html>