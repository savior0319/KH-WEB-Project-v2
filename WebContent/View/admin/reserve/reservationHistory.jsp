<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*, jsp.admin.model.vo.*, jsp.reservation.model.vo.*" %>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/View/main/layout/cssjs.jsp"></jsp:include>
</head>
<body>
  <jsp:include page="/View/admin/layout/sideMenu.jsp"></jsp:include>
  <div class="ui pusher">
  <!-- 헤더 부분  -->
    <div class="ui segment">
      <h3 class="ui header">예약 관리</h3>
    </div>
    <!-- 본문 내용 시작-->
	<div class="ui container">
	  <!-- 테이블 시작 -->
    	<h1>지난 예약 보기</h1>
    	
    	 <form action="/adminReserveHistory" method="post" style="display: inline;">
		 	<select name="searchOption">
			    <option value="RES_HIS_ROOM_NAME">객실이름</option>
			    <option value="RES_HIS_ID">아이디</option>
			</select>
			 <input type="text" name="searchData" >
			 <input type="submit" value="검색">
		 </form>

    	<table class="ui celled table">
		  <thead>
		    <tr>
			    <th>번호</th>
			    <th>아이디</th>
			    <th>객실명</th>
			    <th>입실일</th>
			    <th>퇴실일</th>
			    <th>요금</th>
			    <th>결제일</th>
		  	</tr>
		  </thead>
		  <!--  객실 관리   -->
		  <!--  이 부분에 추가  -->
		  
		   <%
				ReserveHistoryPageVo rhpv = (ReserveHistoryPageVo)request.getAttribute("ReserveHistoryPage");
				if(rhpv != null){
			 	ArrayList<ReservationHistoryVo> rhlist = rhpv.getList();
			 	String pageNavi = rhpv.getPageNavi();	// navi 리스트 
			 	String searchData = request.getParameter("searchData");
			%> 	
		   
  		  <tbody>
  		  <%
  		  	if(rhlist != null && !rhlist.isEmpty()){ 
  		  %>
		   	<%for(ReservationHistoryVo rh : rhlist){ %>
		   	<tr>
		   		<td><%=rh.getResHisNo()%></td>
		   		<td><%=rh.getResHisId()%></td>
		   		<td><%=rh.getResHisRoomName()%></td>
				<td><%=rh.getResHisInDate()%></td>
				<td><%=rh.getResHisOutDate()%></td>
				<td><%=rh.getResHisPrice()%></td>
				<td><%=rh.getResHisPaymentDate()%></td>
		   	</tr>
		   	<% } %>
		   <%} %>
		  </tbody>
		  <!--  페이지 처리를 하는 부분. -->

		  <tfoot>
			    <tr>
			     <th colspan="7">
			      <div class="ui segment">
			       <%= pageNavi %>
			      </div>
			    </th>
			  </tr>
		  </tfoot>
		</table>
		
    	<!--  테이블 끝 -->
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
