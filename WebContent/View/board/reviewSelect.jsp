<%@page import="java.sql.Timestamp"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="jsp.board.model.vo.*" import="jsp.member.model.vo.*" import="java.util.*"%>
<%
MemberVo m = (MemberVo)session.getAttribute("member");
BoardVo board = (BoardVo) request.getAttribute("review");

@SuppressWarnings("all")
ArrayList<Comment> list = (ArrayList<Comment>) request.getAttribute("comment");
int index=0;
%>
<%
Timestamp writeDay = board.getBdWriteDate();
Timestamp stamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
Date date = new Date(writeDay.getTime());
%>
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
	<title>고객후기</title>
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

		<div class="ui center aligned segment">
			<!-- view에서 눌렀을때 나오는 내용들 -->
			<div class="ui left aligned basic segment" style="margin-bottom: 0px;">
				<span class="ui large header" style="display: inline-block; width: 70%; margin-bottom: 5px;"><i class="bullhorn icon"></i>[후기] <%=board.getBdName()%></span>
				<div class="ui divider"></div>
				<span class="ui small header" style="margin-right: 15px;"><i class="user icon"></i>작성자</span><span style="display: inline-block; width: 15%;"><%=board.getBdWriter()%></span>
				<span class="ui small header" style="margin-right: 15px;"><i class="calendar icon"></i>작성일</span><span style="display: inline-block; width: 15%;"><%=date%></span>
				<span class="ui small header" style="margin-right: 15px;"><i class="eye icon"></i>조회수</span><span style="display: inline-block; width: 15%;"><%=board.getBdViewCount() %></span>
				<span class="ui small header" style="margin-right: 15px;"><i class="thumbs up outline icon"></i>추천수</span><span id="spanRmBd"style="display: inline-block; width: 15%;"><%=board.getBdRecommendCount() %></span>
				<div class="ui divider"></div>
			</div>
			
			<%
			if(m != null) {
			%>
			<form id="rmBd" method="get" style="display:inline;">
						<!-- 추천수 중복되는 걸 막기위해서 아이디 당 한번씩밖에 안되도록 설정 -->
					<input type="hidden" name="recommendId" value="<%=m.getMbId() %>" />
					<input type="hidden" name="bdNo" value="<%=board.getBdNo() %>" />
					<input type="button" id="submitRmBd" class="ui red button" value="추천" />
			</form>
			<%
			}
			%>
			
			<div class="ui medium header">후기내용</div>
				<div class="ui form">
					<textarea rows="20" readonly style="resize: none;"><%=board.getBdContents()%></textarea>
				</div>
				<br>
				
				<h3 class="ui dividing header">댓글</h3>
				<%
				if(list.isEmpty()) {
				%>
					댓글이 없습니다.
				<%
				}
				%>
				
				<div class="ui comments">
  						
				<%
				for (Comment c : list) {
				%>
  					<div class="comment">
    					<a class="avatar">
      						<img src="/Image/user.JPG">
    					</a>
    					<div class="content">
      						<a class="author"><%=c.getCmWriter() %></a>
      						<div class="metadata">
       							<div class="date"><%=c.getCmWriteDate()%></div>
        						<div class="rating" >
        							<i class="star icon"></i>
        							<p id="cmRmCount<%=index%>" style="display:inline;"><%=c.getCmRecCount() %></p>
        						</div>
      						</div>
      						<div id="<%=c.getCmNo()%>_input_show" class="text"><%=c.getCmContents()%></div>
      						<br>
      						<%
								if (m != null && (m.getMbId().equals(c.getCmWriter()) || m.getMbId().equals("thepension"))) {
								%>
								<form action="/commentUpdate" method="post" style="display: inline;">
									<div class="ui input">
									<input type="hidden" name="CM_CONTENTS" id="<%=c.getCmNo()%>_input" value="<%=c.getCmContents()%>" />
									</div>
									<input type="hidden" name="CM_NO" value="<%=c.getCmNo()%>" />
									<input type="hidden" name="CM_BD_NO" value="<%=c.getCmBdNo()%>" />
									<button type="button" class="mini ui button" id="<%=c.getCmNo()%>_btn" onclick="modify(<%=c.getCmNo()%>);">수정</button>
									<input type="hidden" class="mini ui button" id="<%=c.getCmNo()%>_submit" value="수정">
									<button type="button" class="mini ui button" id="<%=c.getCmNo()%>_reBtn" style="display: none;" onClick="cancle(<%=c.getCmNo()%>);">취소</button>
								</form>
								<form action="/commentDelete" style="display: inline">
									<input type="hidden" name="CM_NO" value="<%=c.getCmNo()%>" />
									<input type="hidden" name="CM_BD_NO" value="<%=c.getCmBdNo()%>" />
									<input type="submit" class="mini ui button" value="삭제" />
								</form>
								
								<form action="/recommendComment" id="rmCm<%=index %>" method="get" style="display:inline;">
									<!-- 추천수 중복되는 걸 막기위해서 아이디 당 한번씩밖에 안되도록 설정 -->
									<input type="hidden" name="recommendId" value="<%=m.getMbId() %>" />
									<input type="hidden" name="cmNo" value="<%=c.getCmNo()%>" />
									<input type="button" id="submitCmBd<%=index %>" class="mini ui red button" value="추천" />
								</form>
								<br>
									<%
										index++;
									}
        							%>
      						
    					</div>
  					</div>
  					
  					
  					<% }%>
				</div>
				
				
				<%
					if (m != null && (m.getMbId().equals(board.getBdWriter()) || m.getMbId().equals("thepension"))) {
				%>
				<form action="/noticeComment" class="ui reply form" method="get">
				<div class="field">
  					<textarea name="CM_CONTENTS" id="cmContents"></textarea>
				</div>
				<input type="hidden" name="bdNo" value=<%=board.getBdNo()%> />
  				<input type="submit" value="댓글 작성" onclick="return commentCheck();" class="ui blue button" />
				</form>
				<br><br>
				
				<form action="/noticeUpdateReady" style="display:inline;">
					<input type="hidden" name="bdNo" value="<%=board.getBdNo()%>" />
					<input type="submit" class="ui orange button" value="수정">
				</form>
				
				<form action="/noticeDelete" method="post" style="display:inline;">
					<input type="hidden" name="bdNo" value="<%=board.getBdNo()%>" />
					<input type="submit" class="ui red button" value="삭제">
				</form>
				<%
					}
				%>
				
				<button class="ui black button" onclick="back()" style="display:inline;">목록</button>
				
				
				<br>
				
				<!-- 관리자 페이지 쪽으로 이동 & 공지에는 댓글 기능 필요없음 -->
			<!--<%if (session.getAttribute("member") != null) {%>
				<form action="/noticeUpdateReady" style="display:inline;">
					<input type="hidden" name="bdNo" value="<%=board.getBdNo()%>" />
					<input type="submit" value="수정">
				</form>
				<form action="/noticeDelete" method="post" style="display:inline;">
					<input type="hidden" name="bdNo" value="<%=board.getBdNo()%>" />
					<input type="submit" value="삭제">
				</form>
				<%}%>-->
			<!--<h1>댓글</h1>
				<form action="noticeComment" method="post" style="display: inline;" />
				<%if (((MemberVo) session.getAttribute("member")) == null) {%>
				<textarea rows="5" cols="100" name="CM_CONTENTS" readonly
				placeholder="로그인 한 사용자만 댓글 작성이 가능합니다." style="resize: none"
				onclick="login();"></textarea>
				<br>
				<%} else {%>
				<textarea rows="5" cols=100 " name="CM_CONTENTS" placeholder="댓글을 작성하세요" style="resize: none;"></textarea>
				<br> <input type="hidden" name="bdNo"
				value=<%=board.getBdNo()%> /> <input type="submit" value="댓글작성" />
				<%}%> -->
				<br>
				<br>
	</div>
</div>
<!-- 본문 끝 -->

<!-- 푸터 시작  -->
<jsp:include page="/View/main/layout/footer.jsp"></jsp:include>
<!-- 푸터 끝 -->
</body>
<script>
	function modify(id){
		document.getElementById(id+"_input_show").style="display:none";
		document.getElementById(id+"_input").type="text";
		document.getElementById(id+"_btn").style="display:none";
		document.getElementById(id+"_submit").type="submit";
		document.getElementById(id+"_reBtn").style="display:inline";
	}
	function cancle(id){
		document.getElementById(id+"_input_show").style="display:inline";
		document.getElementById(id+"_input").type="hidden";
		document.getElementById(id+"_btn").style="display:inline";
		document.getElementById(id+"_submit").type="hidden";
		document.getElementById(id+"_reBtn").style="display:none";
	}

	function back(){
		location.href="/review";
	}
	
	function commentCheck() {
		if(document.getElementById("cmContents").value == "") {
			alert("댓글 내용을 입력해주세요.");
			return false;
		}
		else return true;
			
	}
</script>

<script>
	// 게시글에 대한 추천버튼
	$('#submitRmBd').click(function() {
		var formData = $("#rmBd").serialize();
		
		$.ajax({
			type : "GET",
			url : "/recommendBoard",
			data : formData,
			success : function(data) {
				$('#spanRmBd').text(data);
			}
		});
	});
	
	
	
	// 댓글에 대한 추천 버튼
	var listSize = <%=list.size()%>;
	
	for(let i=0; i<listSize; i++) {
		
		$('#submitCmBd'+i).click(function() {
			
			var formData = $('#rmCm'+i).serialize();
			
			$.ajax({
				type : "GET",
				url : "/recommendComment",
				data : formData,
				success : function(data) {
					$('#cmRmCount'+i).text(data);
				}
			});
		});
	}

</script>

</html>
