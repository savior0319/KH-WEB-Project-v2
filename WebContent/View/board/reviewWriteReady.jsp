<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page errorPage="/View/error/error.jsp"%>

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

		<form action="/reViewWrite" method="post" enctype="Multipart/form-data" style="display:inline;">
			<br> 글제목:<input
				type="text" size=90 name="bd_Name" /><br>
			<textarea rows="20" cols="100" name="bd_Contents"
				style="resize: none;"></textarea>
			<br> <input type="file" name="img" id="img" accept="image/*" multiple><br><br>
			<div id="image">
			
			</div><br>
						<input type="submit" value="작성하기" class="ui button">
		</form>
		<button type="button" class="ui button" onclick="back();">목록</button>

	</div>
	<!-- 본문 끝 -->

	<!-- 푸터 시작  -->
	<jsp:include page="/View/main/layout/footer.jsp"></jsp:include>
	<!-- 푸터 끝 -->


<script>
	var uploadfile = document.getElementById('img');
	uploadfile.onchange = function(e){
	var files = e.target.files;
	for(var i=0; i<files.length; i++){
		if(i===0)
			{
				document.getElementById('image').innerHTML = "";
			}
		var fileReader = new FileReader();
		fileReader.readAsDataURL(e.target.files[i]);
		fileReader.onload = function(e){
			var str = "<div style='display:inline;'> <img src='"+e.target.result+"'style='width:100px; height:100px;'> </div>";
		    document.getElementById('image').innerHTML += str;
		}
	}
}
	function back() {
		history.back(-1);
	};

</script>
</body>



</html>
