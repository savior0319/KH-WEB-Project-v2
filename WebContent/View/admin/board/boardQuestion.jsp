<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*,jsp.member.model.vo.*"%>
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
	ArrayList<QuestionVo> list = (ArrayList<QuestionVo>) request.getAttribute("qList");
	%>
    <!-- 본문 내용 시작-->
    <div class="ui container">
    	<!-- 테이블 시작 -->
    	<h1>1 : 1 질문 관리</h1>
    	
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
		  
  		  <tbody>
		    <%
						if (list != null && !list.isEmpty()) {
					%>
					<%
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
							<input type="submit" value="상세보기"/> 
						</form>
						
					</tr>
					<%
						}
					%>
					<%
						} else {
					%>
					<%
						}
					%>
				</tbody>
		</table>
		
		
		
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
