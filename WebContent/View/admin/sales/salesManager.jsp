<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*,jsp.admin.model.vo.*,jsp.reservation.model.vo.*" %>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/View/main/layout/cssjs.jsp"></jsp:include>
</head>
<body>
<!--  a 태그들은 나중에 서블릿을 만들어야 한다. -->
<!--  
	1. 매출 통계 어떻게 보여줄것인가?
		- 일단은 테이블로 나타내자.
		- 예약 번호, 객실코드, 아이디, 예약인원,요금... -> 모든 것을 나타낸느 것이 나은가?
	2. 통계를 낼때 어느것을 기준으로 값을 매겨야 하나?
	3. 엑셀 기능 추가
	
 -->
  <!-- 메뉴 바 -->
  <jsp:include page="/View/admin/layout/sideMenu.jsp"></jsp:include>
  <!-- 이 부분이 본문 -->
  <div class="ui pusher">
  <!-- 헤더 부분  -->
    <div class="ui segment">
      <h3 class="ui header">매출 현황</h3>
    </div>
    <!-- 본문 내용 시작-->
    <div class="ui container">
	  <!-- 테이블 시작 -->
    	<h1>매출 리스트</h1>
    	   <%
				SalesPageVo spv = (SalesPageVo)request.getAttribute("SalesPage");
				if(spv != null){
			 	ArrayList<SalesVo> slist = spv.getList();
			 	String pageNavi = spv.getPageNavi();	// navi 리스트 
			 	String searchData = request.getParameter("searchData");
			 	
			%> 	
    	<table class="ui celled table">
		  <thead>
		    <tr>
			    <th>매출번호</th>
			    <th>아이디</th>
			    <th>입금금액</th>
			    <th>예약번호</th>
			    
		  	</tr>
		  </thead>
		  <!--  객실 관리   -->
		  <!--  이 부분에 추가  -->
		   
  		  <tbody>
  		  <%if(slist != null && !slist.isEmpty()){ %>
		   	<%for(SalesVo s : slist){ %>
		   	<tr>
		   		<td><%= s.getSalesNo() %></td>
		   		<td><%= s.getSalesId() %></td>
		   		<td><%= s.getSalesPaymentPrice() %></td>
		   		<td><%= s.getSalesReservationNo() %></td>
		   		
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
    	<h3>정보가 없습니다.</h3>
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
	  .sidebar('hide')
	;
});
</script>
</body>
</html>
