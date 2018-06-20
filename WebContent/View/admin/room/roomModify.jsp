<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/View/main/layout/cssjs.jsp"></jsp:include>
</head>
<body>
<!-- 
	1. 객실의 정보 수정 
 -->
  <!-- 메뉴 바 -->
  <jsp:include page="/View/admin/layout/sideMenu.jsp"></jsp:include>
  <!-- 이 부분이 본문 -->
  <div class="ui pusher">
  <!-- 헤더 부분  -->
    <div class="ui segment">
      <h3 class="ui header">객실 관리</h3>
    </div>
   <!-- 헤더 끝  -->
    <!-- 본문 내용 시작-->
    <div class="ui container">
    	<!-- 테이블 시작 -->
    	<h1>객실 리스트</h1>
    	<table class="ui celled table">
    	
		  <thead>
		    <tr>
			    <th>객실번호</th>
			    <th>객실명</th>
			    <th>기준인원</th>
			    <th>최대인원</th>
			    <th>주말요금</th>
			    <th>주중요금</th>
			    <th>추가요금</th>
			    <th>상세보기</th>
		  	</tr>
		  </thead>
		  <!--  객실 관리   -->
		  <!--  이 부분에 추가  -->
		  
  		  <tbody>
		    <tr>
		      <td>Cell</td>
		      <td>Cell</td>
		      <td>Cell</td>
		      <td>Cell</td>
		      <td>Cell</td>
		      <td>Cell</td>
		      <td>Cell</td>
		      <td><button>상세보기</button></td>
		    </tr>
		  </tbody>
		  <!--  페이지 처리를 하는 부분. -->
		 
		  <!-- <tfoot>
		    <tr>
		    <th colspan="3">
		      <div class="ui right floated pagination menu">
		        <a class="icon item">
		          <i class="left chevron icon"></i>
		        </a>
		        <a class="item">1</a>
		        <a class="item">2</a>
		        <a class="item">3</a>
		        <a class="item">4</a>
		        <a class="icon item">
		          <i class="right chevron icon"></i>
		        </a>
		      </div>
		    </th>
		  </tr>
		  </tfoot> -->
		</table>
		
		<div class="ui button">
			<a href="/View/admin/room/roomResist.jsp">객실 추가</a>
		</div>
		
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
