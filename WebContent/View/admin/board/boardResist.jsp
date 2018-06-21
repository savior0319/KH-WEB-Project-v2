<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="jsp.board.model.vo.*" %>
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

<title>공지사항 등록</title>
</head>

<style>
</style>

<body>

  <jsp:include page="/View/admin/layout/sideMenu.jsp"></jsp:include>
  <!-- 이 부분이 본문 -->
  <div class="ui pusher">
  <!-- 헤더 부분  -->
    <div class="ui segment">
      <h3 class="ui header">공지사항 등록</h3>
    </div>
    <!-- 본문 내용 시작-->
    <div class="ui container">
    	<!-- 테이블 시작 -->
    	<h1>공지사항</h1>
		<div class="ui segment">
		
			<form class="ui form" method="post" action="/adminBoardResist" enctype="multipart/form-data">
				<label>제목</label>
	    		<input type="text" name="bdName" required>
	    		<label>내용</label>	
	    		<textarea style="resizw:none" name="bdcontents" ></textarea>
	    		<label>첨부 사진</label>
	    		<input type="file" class="ui button" id="imglist" name="imglist" multiple="multiple" />
	    		<label>미리보기</label>
	    		<div id="preview"class="ui tiny images">
	    		
	    		</div>
	    		
	    		<input type="submit" class="ui button" value="공지사항 등록" >
	    		<input type="reset" class="ui button" value="취소">
	    		
    		</form>
    		
    		
		</div>
    	<!--  테이블 끝 -->
    	
    	<div class="ui button">
			<a href="/adminBoardList">목록으로</a>
		</div>
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
<script type="text/javascript">
var $imgList = $('#imglist');

$imgList.on("change",function(e) {
	//console.log(e);
    var files = e.target.files; // FileList 객체
	for(var i=0; i<files.length; i++){
 	 if(i===0)
      {
          $('#preview').html("");
      }
var fileReader = new FileReader();
fileReader.readAsDataURL(e.target.files[i]);
fileReader.onload = function(e) { 
var str = "<img src='"+e.target.result+"' class='ui image'> ";
    $('#preview').append(str);
  }
		}
	});
</script>
</body>

</html>