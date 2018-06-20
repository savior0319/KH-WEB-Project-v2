<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page errorPage="/View/error/searchError.jsp"%>
<%@ page import="jsp.board.model.vo.*" import="java.util.*"%>

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
<title>공지사항</title>
</head>

<style>
</style>

<body>
<%
	Page pagev = (Page)request.getAttribute("Page");
	if(pagev != null && !pagev.getList().isEmpty()){
	ArrayList<BoardVo> list = pagev.getList();
	String getPageCount = pagev.getPageCount();
%>
	<!-- 헤더 시작  -->
	<jsp:include page="/View/main/layout/header.jsp"></jsp:include>
	<!-- 헤더 끝 -->

	<!-- 본문 시작 -->
	<br>
	<div class="ui container">
		<!-- 여기에 본문 내용 작성하면 됨 -->
		<div class="ui center aligned segment ">
			<div id="board" style="width: 100%; height: 800px;">
				<table class="ui table" width="100%" cellpadding="0" cellspacing="0"
					border="0">
					<tr height="5">
						<td width="5"></td>
					</tr>	
					<tr>
						<td width="70">번호</td>
						<td width="380">제목</td>
						<td width="70">작성자</td>
						<td width="160">작성일</td>
						<td width="60">조회수</td>
					</tr>
					<%if(list != null && !list.isEmpty()){
						for (BoardVo b : list) {
					%>
					<tr>
						<td><%=b.getBdNo()%></td>
						<td><%=b.getBdName()%></td>
						<td><%=b.getBdWriter()%></td>
						<td><%=b.getBdWriteDate()%></td>
						<td><%=b.getBdViewCount()%></td>
					</tr>
					<%
						}
					%>
				</table>
				<label><%=getPageCount %></label>
				
				<form action="/search" method="get">
				<input type="text" name="search">
				<input type="submit" value="검색">
				</form>
				<% 
					}
					}else{ %>
				<h1>회원이 없습니다.</h1>
				<% } %>
			</div>
		</div>

	</div>
	<!-- 본문 끝 -->

	<!-- 푸터 시작  -->
	<jsp:include page="/View/main/layout/footer.jsp"></jsp:include>
	<!-- 푸터 끝 -->
</body>

<script>
	
</script>

</html>
