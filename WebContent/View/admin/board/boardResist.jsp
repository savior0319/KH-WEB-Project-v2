<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

<title>title</title>
</head>

<style>
</style>

<body>
	<!-- 헤더 -->
	<jsp:include page="/View/main/layout/header.jsp"></jsp:include>
	<!-- 헤더 끝 -->

	<!-- 본문 내용 -->
	<div class="ui container"></div>
	<!-- 본문 끝 -->

	<!-- 푸터 -->
	<jsp:include page="/View/main/layout/footer.jsp"></jsp:include>
	<!-- 푸터 끝 -->
</body>

<script>
</script>

</html>