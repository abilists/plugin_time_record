<#import "/apps/layout/plugins/abilistsPluginsLayout.ftl" as layout>
<#import "/spring.ftl" as spring/>
<@layout.myLayout>

<style>
.large-button {
    margin: 10px;
	padding: 7%;
	width: 100%;
    font-size: x-large;
}

</style>

<div class="row">
<div class="col-sm-12">
	<div class="caption mittle-size-title middle-works-bg">
		<h5><b>출퇴근 관리</b></h5>
	</div>
	<div class="item-box" style="overflow: hidden;">

	<div class="row" style="margin-right: 0px;">
		<div class="col-sm-6">
			<form name="reachForm" action="${configBean.contextPath?if_exists}/plugins/timerecord/startTime" method="post">
				<button type="submit" class="btn btn-success btn-lg large-button <#if plugins.timeRecord??><#if plugins.timeRecord.utrStartTime?has_content>active</#if></#if>">
				출근하기 <#if plugins.timeRecord??><#if plugins.timeRecord.utrStartTime?has_content><small>( ${plugins.timeRecord.utrStartTime?string('HH:mm:ss')?if_exists} )</small></#if></#if>
				</button>
			</form>
		</div>
		<div class="col-sm-6">
			<form name="leaveForm" action="${configBean.contextPath?if_exists}/plugins/timerecord/endTime" method="post">
				<button type="submit" class="btn btn-warning btn-lg large-button <#if plugins.timeRecord??><#if plugins.timeRecord.utrEndTime?has_content>active</#if></#if>" <#if plugins.timeRecord??><#else>style="background-color: gray;border-color: gray;" disabled</#if> >
				퇴근하기 <#if plugins.timeRecord??><#if plugins.timeRecord.utrEndTime?has_content><small>( ${plugins.timeRecord.utrEndTime?string('HH:mm:ss')?if_exists} )</small></#if></#if>
				</button>
			</form>
		</div>
	</div>

	<div id="udtMdataFormId" class="item-box" style="background-color: #efebe7;margin: 10px; display: none;">
		<form id="updateFormId" name="updateForm" class="form-horizontal" action="${configBean.contextPath?if_exists}/plugins/timerecord/udtTimeRecord" method="post" onkeypress="return captureReturnKey(event);">
	  	  <div class="row">
	  	  	<div class="col-sm-3 col-md-3">
	  	  		<label class="control-label">근무 종류</label>
				<select id="utrKindId" class="form-control" name="utrKind" >
					<option value="0">상근</option>
					<option value="2">휴일</option>
					<option value="3">외근</option>
					<option value="4">출장</option>
					<option value="5">연수</option>
					<option value="6">파견</option>
					<option value="7">자택근무</option>
					<option value="8">결근</option>
					<option value="9">휴무</option>
					<option value="10">휴가</option>
					<option value="11">생리휴가</option>
					<option value="12">대기</option>
					<option value="20">기타</option>
			    </select>
	  	  	</div>
	  	  	<div class="col-sm-3 col-md-3">	
	  			<label class="control-label">출근 날짜</label>
			  	<div class="input-group" style="float:right; width: 100%;">
			  		<span class="input-group-addon"><span id="calendarId" class="glyphicon glyphicon-calendar" aria-hidden="true"></span></span>
			  		<input class="form-control" type="text" id="utrWorkDayId" name="utrWorkDay" placeholder="2020-02-22" autocomplete="off" />
			  	</div>
	  	  	</div>
	  	  	<div class="col-sm-3 col-md-3">	
	  			<label class="control-label">출근 시간</label>
			  	<div class="input-group" style="float:right; width: 100%;">
			  		<span class="input-group-addon"><span id="calendarId" class="glyphicon glyphicon-time" aria-hidden="true"></span></span>
			  		<input class="form-control" type="text" id="utrStartTimeId" name="utrStartTime" maxlength="8" size="8" placeholder="09:00:00" />
			  	</div>
		  	</div>
	  	  	<div class="col-sm-3 col-md-3">	
	  			<label class="control-label">퇴근 시간</label>
			  	<div class="input-group" style="float:right; width: 100%;">
			  		<span class="input-group-addon"><span id="calendarId" class="glyphicon glyphicon-time" aria-hidden="true"></span></span>
			  		<input class="form-control" type="text" id="utrEndTimeId" name="utrEndTime" maxlength="8" size="8" placeholder="18:00:00" />
			  	</div>
		  	</div>
	  	  </div>
		  <div class="row">
		  	<div class="col-sm-12 col-md-12">
	  			<label class="control-label">코멘트</label> <span id="idUtrComment">0</span>/200
	  			<textarea class="taForm" style="height: 50px;" id="utrCommentId" name="utrComment" placeholder="Add the detail information" rows="3" onkeyup="checkByteLength(this, 'idUtrComment', 200)" onfocus="checkByteLength(this, 'idUtrComment', 200)"></textarea>
		  	</div>
	  	  </div>
		  <input type="hidden" id="utrNoId" name="utrNo" />
		  <input type="hidden" id="tokenId" name="token" />
		  <br/>
			<p align="center">
		      <button type="button" class="btn btn-primary" onclick="return confirmData('updateFormId');">저장</button>
		      <button type="button" class="btn btn-primary" onClick="updateFormCancel();">취소</button>
			</p>
		</form>
	</div>

	<div id="userTimeRecordId" style="margin: 10px;">
		<div id="timeTableId" style="border: 1px solid #CDCDCD;">
		<div>
	    <ul class="table-ul table-ul-header">
	    	<li class="time-li1">근무 종류</li>
	        <li class="time-li2">출근 날</li>
	        <li class="time-li3">출근 시간</li>
	        <li class="time-li4">퇴근 시간</li>
	        <li class="time-li5">근무한 시간</li>
	        <li class="time-li6">코멘트(*)</li>
	    </ul>
	    <#if plugins??>
	    <#if plugins.timeRecordList?has_content>
	    <#list plugins.timeRecordList as timeRecord>
		    <ul class="table-ul bg-color ul-hover" onmouseover="overChangeColor(this);" onmouseout="outChangeColor(this);" onclick="selectTimeRecord(this, '${timeRecord.utrNo?if_exists}', '${timeRecord.utrWorkDay?string('yyyy-MM-dd')?if_exists}');">
			    <li class="time-li1">
			    <#if timeRecord.utrKind == "0">
			    		상근
			    <#elseif timeRecord.utrKind == "2">
			    		휴일
				<#elseif timeRecord.utrKind == "3">
			    		외근
			    <#elseif timeRecord.utrKind == "4">
			    		출장
			    <#elseif timeRecord.utrKind == "5">
			    		연수
			    <#elseif timeRecord.utrKind == "6">
			    		파견
			    <#elseif timeRecord.utrKind == "7">
			    		자택근무
			    <#elseif timeRecord.utrKind == "8">
			    		결근
			    <#elseif timeRecord.utrKind == "9">
			    		휴무
			    <#elseif timeRecord.utrKind == "10">
			    		휴가
			    <#elseif timeRecord.utrKind == "11">
			    		생리휴가
			    <#elseif timeRecord.utrKind == "12">
			    		대기
			    <#else>
			    		기타
			    </#if>
			    </li>
			    <li class="time-li2">${timeRecord.utrWorkDay?string('yyyy-MM-dd')?if_exists}</li>
		        <li class="time-li3"><#if timeRecord.utrStartTime??>${timeRecord.utrStartTime?string('HH:mm:ss')?if_exists}</#if></li>
		        <li class="time-li4"><#if timeRecord.utrEndTime??>${timeRecord.utrEndTime?string('HH:mm:ss')?if_exists}</#if></li>
		        <li class="time-li5">${timeRecord.utrWorkHour?if_exists}</li>
		        <li class="time-li6"><#if timeRecord.utrComment?has_content>*</#if></li>
		    </ul>
		</#list>
		</#if>
		</#if>
		</div>
		</div>
	</div>

	<nav class="text-center">
    <ul class="pagination">
	    <#if model?exists>
	  	<#if model.paging?exists>
			<#if model.paging.prevPage?exists>
			<li><a href="/plugins/timerecord?nowPage=${model.paging.prevPage.nowPage}&allCount=${model.paging.allCount?c}" title="Prev" accesskey="*">Prev</span></a></li>
			</#if>
			<#if model.paging.pagingInfoList?has_content>
				<#list model.paging.pagingInfoList as pageList>
					<#if model.paging.nowPage?if_exists == pageList.pageNumber?if_exists>
					<li class="active"><a href="#">${pageList.pageNumber} <span class="sr-only">(current)</span></a></li>
					<#else>
					<li><a href="/plugins/timerecord?nowPage=${pageList.pageNumber}&allCount=${model.paging.allCount?c}">${pageList.pageNumber}</a></li>
					</#if>
				</#list>
			</#if>
			<#if model.paging.nextPage?exists>
			<li><a href="/plugins/timerecord?nowPage=${model.paging.nextPage.nowPage}&allCount=${model.paging.allCount?c}" accesskey="#" title="Next">Next</a></li>
			</#if>
		</#if>
		</#if>
	</ul>
	</nav><!-- end #nav -->

</div>
</div>

<#include "/apps/common/abilistsPluginsLoadJs.ftl"/>
<#include "/apps/timerecord/js/indexJs.ftl"/>

</@layout.myLayout>