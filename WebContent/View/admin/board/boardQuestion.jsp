<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*,jsp.member.model.vo.*,jsp.admin.model.vo.*"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/View/main/layout/cssjs.jsp"></jsp:include>
</head>
<body>
	<!--  a 태그들은 나중에 서블릿을 만들어야 한다. -->
	<!-- 
	1. 1:1 문의 
	2. 답변을 어떻게 할지 정해야 한다.
 -->
	<!-- 메뉴 바 -->
	<jsp:include page="/View/admin/layout/sideMenu.jsp"></jsp:include>
	<!-- 이 부분이 본문 -->
	<div class="ui pusher">
		<!-- 헤더 부분  -->
		<div class="ui segment">
			<h3 class="ui header">1 : 1 질문 관리</h3>

		</div>
		<%
			@SuppressWarnings("all")
			    QuestionPageVo qpv = (QuestionPageVo)request.getAttribute("qList");
			    String searchData = request.getParameter("searchData");
				String searchOption = request.getParameter("searchOption");
		%>
		<!-- 본문 내용 시작-->
		<div class="ui container">
			<!-- 테이블 시작 -->
			<h1>1 : 1 질문 관리</h1>
			<form class="ui segment " action="/adminQuestionList" method="post"
				style="display: inline;">
				<select name="searchOption">
					<% if(searchOption==null||searchOption.equals("Q_NAME")){ %>
					<option value="Q_NAME" selected="selected">제목</option>
					<option value="Q_WRITER">작성자</option>
					<%}else{%>
					<option value="Q_NAME">제목</option>
					<option value="Q_WRITER" selected="selected">작성자</option>
					<% } %>
				</select> <input type="text" name="searchData" value=<%=searchData %>>
				<input type="submit" value="검색">
			</form>

			<table class="ui celled table">

				<thead>
					<tr>
						<th>문의 번호</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성날짜</th>
						<th>답변여부</th>
						<th>상세보기</th>
					</tr>
				</thead>
				<!--  객실 관리   -->
				<!--  이 부분에 추가  -->
					<% 
					  if(qpv != null){
						 	ArrayList<QuestionVo> list = qpv.getList();
						 	String pageNavi = qpv.getPageNavi();	// navi 리스트  	
					 %>
				<tbody>
					
					<%
						if (list != null && !list.isEmpty()) {
						for (QuestionVo q : list) {
					%>
					<tr>
						<!-- 객실 정보 -->

						<td><%=q.getqNo()%></td>
						<td><%=q.getqName()%></td>
						<td><%=q.getqWriter()%></td>
						<!-- 요금 정보 -->
						<td><%=q.getqWriteDate()%></td>
						<td><%=q.getqAnswerCheck()%></td>
						<td>
							<form action="/adminQuestionDetail" method="post">
								<input type="hidden" name="qNo" value="<%=q.getqNo()%>">
								<input type="submit" value="상세보기" />
							</form>
					</tr>
					<%}%>
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
			<% }%>



			<!--  테이블 끝 -->
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
