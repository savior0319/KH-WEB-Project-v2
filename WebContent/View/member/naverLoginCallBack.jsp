<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page errorPage="/View/error/error.jsp" %>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.net.HttpURLConnection"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.InputStreamReader"%>
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
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="/SementicUI/semantic.min.css">
<script src="/SementicUI/semantic.min.js"></script>
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<title>네이버 아이디로 로그인</title>
</head>

<style>
</style>

<body>
	<script type="text/javascript">
		var naver_id_login = new naver_id_login("PyoIesHAhe6a3F1GDj9P", "http://localhost/View/member/naverLoginCallBack.jsp");
		
		// 네이버 로그인 완료 메세지 출력
		alert('로그인완료');
		
		// 접근 토큰 값 출력
		// alert(naver_id_login.oauthParams.access_token); 
	
		// 네이버 사용자 프로필 조회
		naver_id_login.get_naver_userprofile("naverSignInCallback()");
	
		// 네이버 사용자 프로필 조회 이후 프로필 정보를 처리할 callback function
		function naverSignInCallback() {
			alert('아이디(ID 고유 식별 값) ' + naver_id_login.getProfileData('id'));
			alert('닉네임 ' + naver_id_login.getProfileData('nickname'));
			alert('이메일 ' + naver_id_login.getProfileData('email'));
			alert('나이(연령대) ' + naver_id_login.getProfileData('age'));
			alert('이름 ' + naver_id_login.getProfileData('name'));
			alert('성별 ' + naver_id_login.getProfileData('gender'));
			alert('생일 ' + naver_id_login.getProfileData('birthday'));
		}
	</script>
</body>

<script>
</script>

</html>