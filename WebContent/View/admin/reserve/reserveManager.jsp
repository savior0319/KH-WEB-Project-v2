<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*,jsp.admin.model.vo.*,jsp.reservation.model.vo.*" %>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/View/main/layout/cssjs.jsp"></jsp:include>
</head>
<body>
<!--  
	1. 달력 추가
	2. 클릭 시 상세 페이지 출력하도록 하자
	  -->
  <!-- 메뉴 바 -->
  <jsp:include page="/View/admin/layout/sideMenu.jsp"></jsp:include>
  <!-- 메뉴 끝  -->
  <!-- 본문 내용 시작 -->
  <div class="ui pusher">

  <!-- 헤더 부분  -->
    <div class="ui segment">
      <h3 class="ui header">예약 관리</h3>
    </div>
    <!-- 본문 내용 시작-->
	<div class="ui container">
	  <!-- 테이블 시작 -->
    	<h1>예약 리스트</h1>
    	   <%
				ReservePageVo rpv = (ReservePageVo)request.getAttribute("ReservePage");
				if(rpv != null){
			 	ArrayList<ReservationVo> rlist = rpv.getList();
			 	String pageNavi = rpv.getPageNavi();	// navi 리스트 
			 	String searchData = request.getParameter("searchData");
			 	
			%> 	
    	<table class="ui celled table">
		  <thead>
		    <tr>
			    <th>예약번호</th>
			    <th>객실이름</th>
			    <th>아이디</th>
			    <th>예약인원</th>
			    <th>입실일</th>
			    <th>퇴실일</th>
			    <th>요금</th>
			    
			    <!-- 상세보기를 넣을까? -->
			    <!-- <th>블랙 리스트 선정</th> -->
		  	</tr>
		  </thead>
		  <!--  객실 관리   -->
		  <!--  이 부분에 추가  -->
		   
  		  <tbody>
  		  <%if(rlist != null && !rlist.isEmpty()){ %>
		   	<%for(ReservationVo r : rlist){ %>
		   	<tr>
		   		<td><%= r.getResNo() %></td>
		   		<td><%= r.getResRoomName() %></td>
		   		<td><%= r.getResId() %></td>
		   		<td><%= r.getResPersonnel() %></td>
		   		<td><%= r.getResInDate()%></td>
		   		<td><%= r.getResOutDate() %></td>
		   		<td><%= r.getResPrice() %></td>
		   	</tr>
		   	<%} %>
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
		<label><%= pageNavi %></label><br>
		 <!-- <form action="searchNotice" method="post" style="display: inline;">
		 <input type="text" name="searchData" >
		 <input type="submit" value="검색">
		 </form> -->
		
    	<!--  테이블 끝 -->
    	<% }else{ %>
    	<hr/>
    	<h3>회원이 없습니다.</h3>
    	<hr/>
    	<% } %>
    </div>
    <!-- 본문 내용 끝  -->
  </div>

<script>
$(document).ready(function(){
	$('.visible.example .ui.sidebar')
	  .sidebar({
	    context: '.visible.example .bottom.segment'
	  })
	  .sidebar('hide');
});
</script>
</body>
</html>
