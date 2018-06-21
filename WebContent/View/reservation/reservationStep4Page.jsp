<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page errorPage="/View/error/error.jsp"%>

<%@page import="jsp.member.model.vo.*"%>
<% MemberVo m = (MemberVo)session.getAttribute("member"); %>

<!DOCTYPE html>
<html>

<head>

<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-120156974-1"></script>
<script>
	window.dataLayer = window.dataLayer || [];
	function gtag() {
		dataLayer.push(arguments);
	}
	gtag('js', new Date());
	gtag('config', 'UA-120156974-1');
</script>
<jsp:include page="/View/main/layout/cssjs.jsp"></jsp:include>
<title>step4. 결제 완료</title>


</head>

<style>
</style>
<body>
	<!-- 헤더 -->
	<jsp:include page="/View/main/layout/header.jsp"></jsp:include>
	<!-- 헤더 끝 -->

	<!-- 본문시작 -->
	<br>
	<div class="ui container">
		
		<div class="ui four top attached steps">
			<div class="disabled step">
				<i class="calendar alternate outline icon"></i>
				<div class="content">
					<div class="title">예약 날짜 선택</div>
					<div class="description"></div>
				</div>
			</div>
			<div class="disabled step">
				<i class="tasks icon"></i>
				<div class="content">
					<div class="title">예약 정보 선택</div>
					<div class="description"></div>
				</div>
			</div>
			<div class="disabled step">
				<i class="won sign icon"></i>
				<div class="content">
					<div class="title">예약 확인 및 결제</div>
					<div class="description"></div>
				</div>
			</div>
			<div class="active step">
				<i class="calendar check outline icon"></i>
				<div class="content">
					<div class="title">예약 완료</div>
					<div class="description"></div>
				</div>
			</div>
		</div>
		
		<div class="ui center aligned segment">
		
			<div class="ui center aligned segment">
				<br><br><br>
				<div class="ui icon message" style="width: 80%; margin: 0 auto;">
					<i class="bullhorn icon"></i>
					<div class="content">
						<div class="header">
							<h1>
								예약 완료
							</h1>
						</div>
					</div>
				</div>
				<br>
				<div class="ui large message" style="width: 80%; margin: 0 auto;">
					<br>
					<h2>예약이 완료되었습니다.</h2>
					<h4>예약 사항은 마이페이지에서 확인하실 수 있습니다.</h4>
					<br>
				</div>
				<button type="button" class="ui inverted red button" style="margin-top: 60px;" id="myPageGo">마이페이지로 이동</button>
				<br>
				<br><br><br><br>
			</div>
		
		</div>
		
	</div>
	<!-- 본문 끝 -->

	<!-- 푸터 시작 -->
	<jsp:include page="/View/main/layout/footer.jsp"></jsp:include>
	<!-- 푸터 끝 -->

</body>

<script type="text/javascript">
	$('#myPageGo').click(function(){
		location.href="/View/member/myPage.jsp";
	});
</script>

</html>