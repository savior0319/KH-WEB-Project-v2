<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*,jsp.admin.model.vo.*,jsp.board.model.vo.*"%>
<%
	BoardAdminPageVo bpv = (BoardAdminPageVo) request.getAttribute("BoardPage");
	String searchData = request.getParameter("searchData");
	String searchOption = request.getParameter("searchOption");
%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/View/main/layout/cssjs.jsp"></jsp:include>
</head>
<body>

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
			<form class="ui segment " action="/adminBoardDetail" method="post"
				style="display: inline;">
<%-- 				<select name="searchOption">
					<% if(searchOption==null||searchOption.equals("BD_NAME")){ %>
					<option value="BD_NAME" selected="selected">아이디</option>
					<option value="MB_ADDRESS">주소</option>
					<option value="MB_NAME">이름</option>
					<%}else if(searchOption.equals("MB_ADDRESS")){%>
					<option value="BD_NAME">아이디</option>
					<option value="MB_ADDRESS" selected="selected">주소</option>
					<option value="MB_NAME">이름</option>
					<%}else{%>
					<option value="MB_ID" selected="selected">아이디</option>
					<option value="MB_ADDRESS">주소</option>
					<option value="MB_NAME" selected="selected">이름</option>
					<% } %>
				</select>  --%>
				<input type="text" name="searchData" value=<%=searchData %>>
				<input type="submit" value="검색">
			</form>

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
					<%
						if (bpv != null) {
							
							ArrayList<BoardVo> blist = bpv.getList();
							String pageNavi = bpv.getPageNavi(); // navi 리스트 
							
							if (blist != null && !blist.isEmpty()) {
					%>
					<%for(BoardVo b : blist){ %>
					<tr>
						<td><%= b.getBdNo() %></td>
						<td><%= b.getBdName() %></td>
						<td><%= b.getBdWriteDate() %></td>
						<td><%= b.getBdViewCount() %></td>
						<td><%= b.getBdRecommendCount() %></td>
						<td>
							<form action="/adminBoardDetail" method="post">
								<input type="hidden" name="bdNo" value="<%=b.getBdNo() %>">
								<input type="submit" value="상세보기">
							</form>
						</td>

					</tr>
					<%} %>
					<%} %>

				</tbody>
				<tfoot>
					<tr>
						<th colspan="8">
							<div class="ui segment">
								<%= pageNavi %>
							</div>
						</th>
					</tr>
				</tfoot>

			</table>
			<% } %>
			<!--  테이블 끝 -->

			<div class="ui segment">

				<a class="ui button" href="/View/admin/board/boardResist.jsp">공지
					등록</a>
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
