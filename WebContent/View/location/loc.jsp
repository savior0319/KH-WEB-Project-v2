<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page errorPage="/View/error/error.jsp"%>

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
	<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=PyoIesHAhe6a3F1GDj9P"></script>
	<title>오시는 길</title>
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
		<div class="ui icon message">
			<i class="map outline icon"></i>
			<div class="content">
				<div class="header" style="text-align: center;">
					<h1>
						오시는 길
					</h1>
				</div>
			</div>
		</div>

		<div class="ui center aligned segment">
			<div id="map" style="width: 100%; height: 500px;"></div>
		</div>


		<div class="ui center aligned segment">
			<div class="ui large header">주소 : 서울 영등포구 선유동2로 57 (이레빌딩)</div>
			<div class="ui medium header">교통안내</div>
			<div class="ui small header">
				뛰어오는 고객 : 당산역 12번 출구 뛰어서 5분
				<br>
				걸어오는 고객 : 당산역 12번 출구 걸어서 10분
				<br>
				기어오는 고객 : 당산역 12번 출구 기어서 70분
				<br>
			</div>
		</div>

	</div>
	<!-- 본문 끝 -->

	<!-- 푸터 시작  -->
	<jsp:include page="/View/main/layout/footer.jsp"></jsp:include>
	<!-- 푸터 끝 -->
</body>

<script>
	/* 지도 옵션 */
	var mapOptions = {
		scaleControl : false,
		logoControl : false,
		mapDataControl : false,
		zoomControl : true
	};

	/* 지도 위치 및 설정 */
	var HOME_PATH = window.HOME_PATH || '.';
	var thePension = new naver.maps.LatLng(37.5338316, 126.8969046), map = new naver.maps.Map(
		'map', {
			center : thePension,
			zoom : 12,
			mapTypeControl : true,
			zoomControl : true,
			aroundControl : true
		}), marker = new naver.maps.Marker({
			map : map,
			position : thePension
		});

		/* 마커 클릭 시 문구 */
		var contentString = [ '<div class="iw_inner">', '   <h3>더 펜션</h3>',
		'   <p>서울 영등포구 선유동2로 57 (이레빌딩)<br><br>', '</div>' ].join('');

		/* 마커 클릭 시 창 */
		var infowindow = new naver.maps.InfoWindow({
			content : contentString,
			maxWidth : 200,
			backgroundColor : "#ffcfa7",
			borderColor : "#6897f0",
			borderWidth : 5,
			anchorSize : new naver.maps.Size(30, 30),
			anchorSkew : true,
			anchorColor : "#ffcfa7",
			pixelOffset : new naver.maps.Point(20, -20)
		});

		$(document).ready(function() {
			infowindow.open(map, marker);
		})
	</script>
	</html>
