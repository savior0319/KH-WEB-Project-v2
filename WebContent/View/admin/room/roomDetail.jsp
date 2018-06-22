<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*,jsp.admin.model.vo.*,jsp.main.model.vo.*,jsp.reservation.model.vo.*" %>
<!-- roomInfo -->
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/View/main/layout/cssjs.jsp"></jsp:include>
</head>
<body>
<!-- 
	1. 객실의 디테일한 정보 
 -->
  <!-- 메뉴 바 -->
  <jsp:include page="/View/admin/layout/sideMenu.jsp"></jsp:include>
  <!-- 이 부분이 본문 -->
  
  <%  
  RoomTotalInfoVo rtiv = (RoomTotalInfoVo)request.getAttribute("roomInfo");
  if(rtiv != null){
	  PensionVo pv = rtiv.getPv(); 
	  ArrayList<PensionPicTb> list = rtiv.getList();

  %>
  <div class="ui pusher">
  <!-- 헤더 부분  -->
    <div class="ui segment">
      <h3 class="ui header">객실 관리</h3>
    </div>
   <!-- 헤더 끝  -->
     <!-- 본문 내용 시작-->
    <br>
    <br>
    <div class="ui container">
		<form class="ui form segment" action="/adminRoomInsert" method="post" enctype="multipart/form-data">
		  <h4 class="ui dividing header">객실 정보</h4>
		  <div class="field">
		    <label>객실명</label>
		    <div class="ui input">
			  <input type="text" name="PS_Name" maxlength="16" value="<%=pv.getPsName() %>" readonly="readonly">
			</div>
		  </div>
		  <div class="fields">
		    <div class="two wide field">
		      <label>기준 인원</label>
		      <input type="number" class="ableChange"name="PS_Person" placeholder="0" value="<%=pv.getPsPersonnel() %>" readonly="readonly" >
		    </div>
		    <div class="two wide field">
		      <label>최대인원</label>
		      <input type="number" class="ableChange" name="PS_MaxPerson" placeholder="0" value="<%=pv.getPsMaxPersonnel()%>" readonly="readonly">
		    </div>
		    
		  </div>
		  <!--  객실 요금  -->
		  <h4 class="ui dividing header">객실 요금 정보</h4>
		  <div class="fields">
		    <div class="three wide field">
		      <label> 주말 요금</label>
			  <input type="number" placeholder="0원" name="PS_Weekend" required readonly="readonly" class="ableChange" value="<%=pv.getPsWeekend()%>">
			</div>
			<div class="three wide field">
		      <label> 주중 요금</label>
			  <input type="number" placeholder="0원" name="PS_Weekday" required readonly="readonly" class="ableChange" value="<%=pv.getPsWeekday() %>">
			</div>
			<div class="three wide field">
		      <label> 추가 요금</label>
			  <input type="number" placeholder="0원" name="PS_Addtion_Price" required readonly="readonly" class="ableChange" value="<%=pv.getPsAddtionalPrice()%>">
			</div>
		  </div>
		  <div class="field">
		    <label>객실 소개</label>
		    <div class="ui input">
			  <textarea rows="10" name="PS_Contents" maxlength="1333" required readonly="readonly" class="ableChange"><%=pv.getPsContents() %></textarea>
			</div>
		  </div>
		  <h4>객실 사진 정보</h4>
		  <!--  메인 사진 설정 -->
		  <div class="field">
		  	<label>메인 사진 </label>
		  	<!--  -->
		  	<input type="hidden" name="input_main" onchange="mainImgPreview(this);" accept="image/*" required class="ableChange">
		  	<img class="ui image" id="main_img" src="<%=list.get(0).getPsPicPath()%>">
		  </div>
		  <!--  그 외 사진 설정 -->
		  	
		  <div class="field">
		  	<label>사진 추가</label>
		  	<!-- <input type="button" class="ui button" onclick="return addImg();" value="사진 추가" disabled="disabled" class="ableChange"/> -->
			<!--  사진 미리보기를 추가할 곳... -->
			<!--  이 곳은 개선이 필요하다... -->
				
			<input type="hidden" class="ableChange" name="etc_img" onchange="etcImgPreview(this);" multiple="multiple"/>
			<div id="etc_inputs" >
				
			</div>
			<div class="ui tiny images" id="etc_preview" >
				<% for(int i =1 ; i < list.size() ; i++) {%>
				  	<a href="javascript:void(0);" onclick="deleteImageAction(<%=i%>);" id="img_id_<%=i%>">
				  		<img src="<%=list.get(i).getPsPicPath()%>"  class='ui image' ></a>
				  	<% 
				  	
				  	} %>
			</div> 
			
					  
		  </div>
		  <!--  -->
		  <input   type="submit" class="ui button" value="등록" onclick="return Check();" style="display: none" >
		  <input  type="reset" class="ui button" value="리셋" style="display: none">
		</form>
		<button class="ui button" onclick="goBack();" >뒤로</button>
		<button class="ui button" onclick="ableChange();" >글 수정</button>
		<form action="/roomPicUpdateView" method="post" style="display:inline;">
			<input type="hidden" name="roonNameModi" value="<%=pv.getPsName()%>">
			<input type="submit" class="ui button" value="사진 수정"/>
		</form>
		<form action="/roomPicDeleteView" method="post" style="display:inline;">
			<input type="hidden" name="roonNameModi" value="<%=pv.getPsName()%>">
			<input type="submit" class="ui button" value="사진 삭제"/>
		</form>
		
    </div>
    <!-- 본문 내용 끝  -->
    <%} %>
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
// 수정 가능하게 바꿈..
function ableChange(){

	var $file = $('.ableChange[type=file]');
	var $noFile = $('.ableChange').not('[type=file]');
	var $submit = $('input.button').not('.ableChange');

	
	
	$noFile.each(function(){
		
		$(this).attr("readonly",false);
	});
	
	$file.each(function(){
		$(this).attr("disable",false);
	});
	
	$submit.each(function(){
		$(this).css('display','');
	});
	
}

function mainImgPreview(input){
	 if (input.files && input.files[0]) {           //파일존재할시 
		  var reader = new FileReader();          
		  reader.onload = function (e) {        
		   $('#main_img').attr('src', e.target.result);             
		  
		  }                    
		  reader.readAsDataURL(input.files[0]);
		 }
}
</script>
</body>
</html>
