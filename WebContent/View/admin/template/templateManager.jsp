<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/View/main/layout/cssjs.jsp"></jsp:include>
</head>
<body>

  <jsp:include page="/View/admin/layout/sideMenu.jsp"></jsp:include>
  <!-- 이 부분이 본문 -->
  <div class="ui pusher">
  <!-- 헤더 부분  -->
    <div class="ui segment">
      <h3 class="ui header">Home</h3>
    </div>
    <!-- 콘텐츠  -->
    
    <div class="ui container">
    
    <h2>Main 사진 변경</h2>
    <h5>(최소 8장 이상)</h5>
    <hr>
    <div class="ui grid">
    
<c:forEach begin="0" items="${requestScope.MainPicTb}" var="list">
<div class="four wide column">
  <img src="${list.mainPicPath }" style="width:100%;">
  </div>
</c:forEach>
</div><br>
<!-- Main 사진 추가 시작 -->
<form action="/adminMainInsert" method="post" enctype="Multipart/form-data" style="display:inline;">
<input type="hidden" name="img" id="upload" accept="image/*" multiple required/><br>
<div id='upload_image'>

</div>
<br><button type="button" id="upload_btn" onclick="insert('upload');" class="ui button">추가</button>
<input type="hidden" id="upload_submit" value="추가" class="ui button">
<button type="button" id="upload_reBtn" style="display:none;" onclick="imageCancle('upload');" class="ui button">취소</button>
</form>
<!-- Main 사진 추가 끝 -->

<!-- Main 사진 변경 시작 -->
<form action="/adminMainUpdateView" method="get" style="display:inline;">
<input type="submit" value="수정" class="ui button">
</form>
<!-- Main 사진 변경 끝 -->

<!-- Main 사진 삭제 시작 -->
<form action="/adminMainDeleteView" method="get" style="display:inline;">
<input type="submit" value="삭제" class="ui button">
</form>
<!-- Main 사진 삭제 끝 -->

<hr>
    <h2>Main 문구 변경</h2>
    <h5>(최대 1333글자)</h5>
<hr>
<!-- Main 문구 변경 시작 -->
<form action="/adminUpdateSelect" method="get" style="display:inline;">
<textarea id="mainView" name="update" readonly style="width:100%; resize:none;" rows="20">${requestScope.dTb.desMainView }</textarea>
<input type="hidden" value="1" name="value">
<br><button type="button" id="mainView_btn" onclick="modify('mainView');" class="ui button">수정</button>
<input type="hidden" id="mainView_submit" value="수정" class="ui button">
<button type="button" id="mainView_reBtn" style="display:none;" onclick="cancle('mainView');" class="ui button">취소</button>
</form>
<!-- Main 문구 변경 끝 -->
<hr>
<h2>고정 사진 변경</h2>
<hr>
<img src="${requestScope.dTb.desImagePath }" style="width:100%;">
<!-- 고정 사진 변경 시작 -->
<form action="/adminUpdateImage" method="post" enctype="Multipart/form-data" style="display:inline;">
<input type="hidden" name="desimage_path" value="${requestScope.dTb.desImagePath }">
<input type="hidden" name="img" id="desupload" accept="image/*" required/><br>
<br>
<div id="desupload_image" style='display:none;'> <img id="desimage" style='width:100px; height:100px;'> </div>

<br><button type="button" id="desupload_btn" onclick="insert('desupload');" class="ui button">변경</button>
<input type="hidden" id="desupload_submit" value="변경" class="ui button">
<button type="button" id="desupload_reBtn" style="display:none;" onclick="imageCancle('desupload');" class="ui button">취소</button>
</form>
<!-- 고정 사진 변경 끝 -->
<hr>
<h2>펜션 소개 문구 변경</h2>
    <h5>(최대 1333글자)</h5>
<hr>
<!--  펜션 소개 문구 변경 시작 -->
<form action="/adminUpdateSelect" method="get" style="display:inline;">
<textarea id="psAbout" name="update" readonly style="width:100%; resize:none;" rows="20">${requestScope.dTb.desPensionAbout }</textarea>
<input type="hidden" value="2" name="value">
<br><button type="button" id="psAbout_btn" onclick="modify('psAbout');" class="ui button">수정</button>
<input type="hidden" id="psAbout_submit" value="수정" class="ui button">
<button type="button" id="psAbout_reBtn" style="display:none;" onclick="cancle('psAbout');" class="ui button">취소</button>
</form>
<!--  펜션 소개 문구 변경 끝 -->
<hr>
<h2>객실 안내 문구 변경</h2>
    <h5>(최대 1333글자)</h5>
<hr>
<!--  객실 안내 문구 변경 시작 -->
<form action="/adminUpdateSelect" method="get" style="display:inline;">
<textarea id="room" name="update" readonly style="width:100%; resize:none;" rows="20">${requestScope.dTb.desRoom }</textarea>
<input type="hidden" value="3" name="value">
<br><button type="button" id="room_btn" onclick="modify('room');" class="ui button">수정</button>
<input type="hidden" id="room_submit" value="수정" class="ui button">
<button type="button" id="room_reBtn" style="display:none;" onclick="cancle('room');" class="ui button">취소</button>
</form>
<!--  객실 안내 문구 변경 끝 -->
<hr>
<h2>주의사항 변경</h2>
    <h5>(최대 1333글자)</h5>
<hr>
<!--  주의사항 변경 시작 -->
<form action="/adminUpdateSelect" method="get" style="display:inline;">
<textarea id="attention" name="update" readonly style="width:100%; resize:none;" rows="20">${requestScope.dTb.desAttention }</textarea>
<input type="hidden" value="4" name="value">
<br><button type="button" id="attention_btn" onclick="modify('attention');" class="ui button">수정</button>
<input type="hidden" id="attention_submit" value="수정" class="ui button">
<button type="button" id="attention_reBtn" style="display:none;" onclick="cancle('attention');" class="ui button">취소</button>
</form>
<!--  주의사항 변경 끝 -->
<hr>
<h2>환불안내 변경</h2>
    <h5>(최대 1333글자)</h5>
<hr>
<!--  환불안내 변경 시작 -->
<form action="/adminUpdateSelect" method="get" style="display:inline;">
<textarea id="refund" name="update" readonly style="width:100%; resize:none;" rows="20">${requestScope.dTb.desRefund }</textarea>
<input type="hidden" value="5" name="value">
<br><button type="button" id="refund_btn" onclick="modify('refund');" class="ui button">수정</button>
<input type="hidden" id="refund_submit" value="수정" class="ui button">
<button type="button" id="refund_reBtn" style="display:none;" onclick="cancle('refund');" class="ui button">취소</button>
</form>
<!--  환불안내 변경 끝 -->
<hr>
<h2>계좌안내 변경</h2>
    <h5>(최대 1333글자)</h5>
<hr>
<!--  계좌안내 변경 시작 -->
<form action="/adminUpdateSelect" method="get" style="display:inline;">
<textarea id="account" name="update" readonly style="width:100%; resize:none;" rows="20">${requestScope.dTb.desAccount }</textarea>
<input type="hidden" value="6" name="value">
<br><button type="button" id="account_btn" onclick="modify('account');" class="ui button">수정</button>
<input type="hidden" id="account_submit" value="수정" class="ui button">
<button type="button" id="account_reBtn" style="display:none;" onclick="cancle('account');" class="ui button">취소</button>
</form>
<!--  계좌안내 변경 끝 -->
<hr>




     </div>
     
     
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
function modify(id){
	document.getElementById(id).readOnly=false;
	text=document.getElementById(id).value;
	document.getElementById(id+"_btn").style="display:none";
	document.getElementById(id+"_submit").type="submit";
	document.getElementById(id+"_reBtn").style="display:inline";
}
function cancle(id){
	document.getElementById(id).value=text;
	document.getElementById(id).readOnly=true;
	document.getElementById(id+"_btn").style="display:inline";
	document.getElementById(id+"_submit").type="hidden";
	document.getElementById(id+"_reBtn").style="display:none";
}

function insert(id){
	document.getElementById(id).type="file";
	document.getElementById(id+"_btn").style="display:none";
	document.getElementById(id+"_submit").type="submit";
	document.getElementById(id+"_reBtn").style="display:inline";
}
function imageCancle(id) {
	document.getElementById(id).type="hidden";
	document.getElementById(id+'_image').innerHTML="";
	document.getElementById(id+"_btn").style="display:inline";
	document.getElementById(id+"_submit").type="hidden";
	document.getElementById(id+"_reBtn").style="display:none";
}
// 사진 여러장 미리보기 시작
var mainfile = document.getElementById('upload');
mainfile.onchange = function(e) {
    var files = e.target.files; // FileList 객체
for(var i=0; i<files.length; i++){
  if(i===0)
      {
          document.getElementById('upload_image').innerHTML = "";
      }
var fileReader = new FileReader();
fileReader.readAsDataURL(e.target.files[i]);
fileReader.onload = function(e) { 
var str = "<div style='display:inline;'> <img src='"+e.target.result+"'style='width:100px; height:100px;'> </div>";
    document.getElementById('upload_image').innerHTML += str;
  }
}
}
// 사진 여러장 미리보기 끝

// 사진 1장 미리보기
var desfile = document.getElementById('desupload');
desfile.onchange = function(e) {
	  var fileReader = new FileReader();
	  fileReader.readAsDataURL(e.target.files[0]);
	  fileReader.onload = function(e) { 
		  document.getElementById('desupload_image').style = "display:inline";
		  document.getElementById('desimage').src = e.target.result;
	 }
}
//사진 1장 끝
</script>
</body>
</html>
