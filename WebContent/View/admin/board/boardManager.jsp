<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*,jsp.admin.model.vo.*,jsp.board.model.vo.*" %>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/View/main/layout/cssjs.jsp"></jsp:include>
</head>
<body>
<!--  a 태그들은 나중에 서블릿을 만들어야 한다. -->
<!-- 
	1. 공지사항 (관리자가 작성한 글들만)
	2. 페이징 처리
	3. form을 추가할까? 
 -->
  <!-- 메뉴 바 -->
  <jsp:include page="/View/admin/layout/sideMenu.jsp"></jsp:include>
  <!-- 이 부분이 본문 -->
  <div class="ui pusher">
  <!-- 헤더 부분  -->
    <div class="ui segment">
      <h3 class="ui header">공지사항 관리</h3>
    </div>
    <!-- 본문 내용 시작-->
    <div class="ui container">
    	<!-- 테이블 시작 -->
    	<h1>공지사항</h1>
    	<%
				BoardAdminPageVo bpv = (BoardAdminPageVo)request.getAttribute("BoardPage");
				if(bpv != null){
			 	ArrayList<BoardVo> blist = bpv.getList();
			 	String pageNavi = bpv.getPageNavi();	// navi 리스트 
			 	String searchData = request.getParameter("searchData");
			 	
			%> 	
    	<table class="ui celled table">
    	
		  <thead>
		    <tr>
			    <th>게시글 번호</th>
			    <th>제목</th>
			    <th>작성일</th>
			    <th>조회수</th>
			    <th>추천수</th>
			    <th>상세보기</th>
		  	</tr>
		  </thead>
		  <!--  객실 관리   -->
		  <!--  이 부분에 추가  -->
		  
  		  <tbody>
		    <%if(blist != null && !blist.isEmpty()){ %>
		   	<%for(BoardVo b : blist){ %>
		   	<tr>
		   		<td><%= b.getBdNo() %></td>
		   		<td><%= b.getBdName() %></td>
		   		<td><%= b.getBdWriteDate() %></td>
		   		<td><%= b.getBdViewCount() %></td>
		   		<td><%= b.getBdRecommendCount() %></td>
		   		<td><!-- 이 부분에 상세보기  --></td>
		   		
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
		
		
		
    	<!--  테이블 끝 -->
    	<% }else{ %>
    	<hr/>
    	<h3>공지사항이 없습니다.</h3>
    	<hr/>
    	<% } %>
    	<div class="ui button">
			<a href="/View/admin/board/boardResist.jsp">공지 등록</a>
		</div>
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
