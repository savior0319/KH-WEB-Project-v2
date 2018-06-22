<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>The Pension</title>
</head>

<style>

</style>

<body>

	<!-- 헤더 시작  -->
	<jsp:include page="/View/main/layout/header.jsp"></jsp:include>
	<!-- 헤더 끝 -->

	<!-- 본문 시작 -->
	<br>
	<div class="ui container">
		<!-- 여기에 본문 내용 작성하면 됨 -->
		
		<div class="ui icon message">
				<i class="clipboard outline icon"></i>
				<div class="content">
					<div class="ui center aligned header">
						<h1>
							자주 묻는 질문
						</h1>
					</div>
				</div>
			</div>
		
<div class="ui styled fluid accordion">
  <div class="active title">
    <i class="dropdown icon"></i>
    	추가 인원이 있는 경우 추가요금은 얼마인가요?
  </div>
  <div class="active content">
    <p>추가요금은 방마다 다르기 때문에 1대1문의에 댓글로 남겨주시거나  대표전화(010-3787-5606)로 전화 부탁드립니다.</p>
  </div>
  <div class="title">
    <i class="dropdown icon"></i>
    	문의사항은 어디에 문의하면 되나요?
  </div>
  <div class="content">
    <p>궁금하신 사항은 1대1문의에 댓글로 남겨주시거나 대표전화(010-3787-5606)로 전화 부탁드립니다. </p>
  </div>
  <div class="title">
    <i class="dropdown icon"></i>
    	와이파이 있나요?
  </div>
  <div class="content">
    <p>네 빵빵합니다.</p>
  </div>
  <div class="title">
    <i class="dropdown icon"></i>
    	입실시간 변경과 퇴실시간 연장 가능한가요?
  </div>
  <div class="content">
    <p>입실시간 퇴실시간은 정해져 있기 때문에 최대한 시간을 지켜주셔야 합니다. </p>
    
  </div>
  <div class="title">
    <i class="dropdown icon"></i>
     펜션 주변에 편의점이나 마트가 있나요?
  </div>
  <div class="content">
    <p>네 걸어서 2~3분 거리에 gs편의점이 있고 마트는 아쉽게도 없습니다.</p>
    
  </div>
  <div class="title">
    <i class="dropdown icon"></i>
   	최종 객실 예약후 객실 변경이 가능한가요?
  </div>
  <div class="content">
    <p>네 사전문의 및 확인이되면 객실 변경이 가능합니다.</p>
    
  </div>
</div>
						
		</div>
	<!-- 본문 끝 -->

	<!-- 푸터 시작  -->
	<jsp:include page="/View/main/layout/footer.jsp"></jsp:include>
	<!-- 푸터 끝 -->
</body>

<script>
$('.ui.accordion')
.accordion()
;
</script>

</html>
