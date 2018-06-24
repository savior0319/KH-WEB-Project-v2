<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*, jsp.admin.model.vo.*, jsp.reservation.model.vo.*" %>
<jsp:include page="/View/main/layout/preventurl.jsp"></jsp:include>
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
			<h1>예약 취소 요청</h1>
			
			<form action="/adminReserveCancel" method="post" style="display: inline;">
				<select name="searchOption">
					<option value="RC_RES_ROOM_NAME">객실이름</option>
					<option value="RC_RES_ID">아이디</option>
				</select>
				<input type="text" name="searchData" >
				<input type="submit" value="검색">
			</form>

			<table class="ui celled table">
				<thead>
					<tr>
						<th>취소요청일</th>
						<th>아이디</th>
						<th>객실명</th>
						<th>입실일</th>
						<th>퇴실일</th>
						<th>요금</th>
						<th>결제일</th>
						<th>환불</th>
						<th>최종 취소</th>
					</tr>
				</thead>
				<!--  객실 관리   -->
				<!--  이 부분에 추가  -->
				
				<%
				ReserveCancelPageVo rcpv = (ReserveCancelPageVo)request.getAttribute("ReserveCancelPage");
				if(rcpv != null){
				ArrayList<ReservationCancelVo> rclist = rcpv.getList();
			 	String pageNavi = rcpv.getPageNavi();	// navi 리스트 
			 	String searchData = request.getParameter("searchData");
			 	%> 	
			 	
			 	<tbody>
			 		<%
			 		if(rclist != null && !rclist.isEmpty()){ 
			 		%>
			 		<%for(ReservationCancelVo rc : rclist){ %>
			 		<tr>
			 			<td><%=rc.getRcDate()%></td>
			 			<td><%=rc.getRcResId()%></td>
			 			<td><%=rc.getRcResRoomName()%></td>
			 			<td><%=rc.getRcResInDate()%></td>
			 			<td><%=rc.getRcResOutDate()%></td>
			 			<td><%=rc.getRcResPrice()%></td>
			 			<td><%=rc.getRcResPaymentDate()%></td>
			 			<td>
			 				<% if(rc.getRcRefundCheck().equals("N")) { %>
			 				<form action="/refundReservation" method="post">
			 					<input type="hidden" name="rc_no" value="<%=rc.getRcNo()%>" />
			 					<input type="submit" value="환불" onclick="refundCheck();">
			 				</form>
			 				<% } else { %>
			 				완료
			 				<% } %>
			 			</td>
			 			<td>
			 				<% if(rc.getRcCancelCheck().equals("N")&&rc.getRcRefundCheck().equals("Y")) { %>
			 				<form action="/cancelReservation" method="post">
			 					<input type="hidden" name="rc_no" value="<%=rc.getRcNo()%>" />
			 					<input type="submit" value="취소" onclick="return cancelCheck();">
			 				</form>
			 				<% } else if(rc.getRcRefundCheck().equals("N")) { %>
			 				환불 필요
			 				<% } else { %>
			 				완료
			 				<% } %>
			 			</td>
			 		</tr>
			 		<% } %>
			 		<%} %>
			 	</tbody>
			 	<!--  페이지 처리를 하는 부분. -->

			 	<tfoot>
			 		<tr>
			 			<th colspan="9">
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

			function refundCheck() {
				alert("결제 사이트에서 카드 결제 취소 확인 해주세요.");
			}
			
			function cancelCheck() {
				if(window.confirm("예약이 최종적으로 취소 됩니다.")) {
					return true;
				} else {
					return false;
				}
			}
		</script>
	</body>
	</html>
