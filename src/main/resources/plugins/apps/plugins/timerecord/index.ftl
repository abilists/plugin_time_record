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
			<form name="reachForm" action="${configBean.contextPath?if_exists}/plugins/timerecord/istTimeRecord" method="post">
				<button type="submit" class="btn btn-success btn-lg large-button active">출근하기</button>
			</form>
		</div>
		<div class="col-sm-6">
			<form name="reachForm" action="${configBean.contextPath?if_exists}/plugins/timerecord/udtTimeRecord" method="post">
				<button type="submit" class="btn btn-warning btn-lg large-button">퇴근하기</button>
			</form>		
		</div>
	</div>

	<div id="udtMdataFormId" class="item-box" style="background-color: #efebe7;">
		<form name="updateForm" class="form-horizontal" action="${configBean.contextPath?if_exists}/plugins/timerecord/udtTimeRecord" method="post" id="updateFormId" onkeypress="return captureReturnKey(event);">

		
	  	  <div class="row">
	  	  	<div class="col-sm-3 col-md-3">
	  	  		<label class="control-label">근무 종류</label>
				<select class="form-control">
					<option value="3">외근</option>
					<option value="4">파견</option>
					<option value="5">자택근무</option>
			    </select>
	  	  	</div>
	  	  	<div class="col-sm-3 col-md-3">	
	  			<label class="control-label">출근 날짜</label>
			  	<div class="input-group" style="float:right; width: 100%;">
			  		<span class="input-group-addon"><span id="calendarId" class="glyphicon glyphicon-calendar" aria-hidden="true"></span></span>
			  		<input class="form-control" type="text" id="udtUrWorkHourId" name="urWorkHour" placeholder="01:44:16" />
			  	</div>
	  	  	</div>
	  	  	<div class="col-sm-3 col-md-3">	
	  			<label class="control-label">퇴근 시간</label>
			  	<div class="input-group" style="float:right; width: 100%;">
			  		<span class="input-group-addon"><span id="calendarId" class="glyphicon glyphicon-time" aria-hidden="true"></span></span>
			  		<input class="form-control" type="text" id="udtUrWorkHourId" name="urWorkHour" placeholder="02:09:38" />
			  	</div>
		  	</div>
	  	  	<div class="col-sm-3 col-md-3">	
	  			<label class="control-label">근무한 시간</label>
			  	<div class="input-group" style="float:right; width: 100%;">
			  		<span class="input-group-addon"><span id="calendarId" class="glyphicon glyphicon-time" aria-hidden="true"></span></span>
			  		<input class="form-control" type="text" id="udtUrWorkHourId" name="urWorkHour" placeholder="8" />
			  	</div>
		  	</div>
	  	  </div>

		  <div class="row">
		  	<div class="col-sm-12 col-md-12">

	  			<label class="control-label">코멘트</label> <span id="idUdtReports">0</span>/2900
	  			<textarea class="taForm" style="height: 50px;" id="udtUrReportsId" name="urReport" placeholder="Add the detail information" rows="3" ></textarea>

		  	</div>
	  	  </div>
		  <input type="hidden" id="urNoId" name="urNo" />
		  <input type="hidden" id="tokenId" name="token" />
		  <br/>
			<p align="center">
		      <button type="button" onclick="return confirmData('updateFormId');" class="btn btn-primary" data-toggle="modal">
		      	aaabcde
		      </button>
		      <button class="btn btn-primary" type="button" onClick="updateFormCancel();">cancel</button>
		      <button class="btn btn-danger" type="button" onclick="removeReports()">delete</button>
			</p>
		</form>
	</div>

	<div id="userTimeRecordId">
		<div class="item-box">
			<table id="tableSkillsListId" class="table table-striped">
				<tr style="background-color: #eeeeec;">
					<th class="my_profile">번호</th>
					<th class="my_profile">출근 날</th>
					<th class="my_profile">출근 시간</th>
					<th class="my_profile">퇴근 시간</th>
					<th class="my_profile">근무한 시간</th>
				</tr>
		    <#if plugins??>
		    <#if plugins.timeRecordList?has_content>
		    <#list plugins.timeRecordList as timeRecord>
				<tr onclick="selectTimeRecord(this, '${timeRecord.utrWorkDay?string('yyyy-MM-dd')?if_exists}');">
					<td>${timeRecord.utrNo?if_exists}</td>
					<td>${timeRecord.utrWorkDay?string('yyyy-MM-dd')?if_exists}</td>
					<td>${timeRecord.utrStartTime?string('hh:mm:ss')?if_exists}</td>
					<td><#if timeRecord.utrEndTime??>${timeRecord.utrEndTime?string('hh:mm:ss')?if_exists}</#if></td>
					<td>${timeRecord.utrWorkHour?c}</td>
				</tr>
			</#list>
			</#if>
			</#if>
			</table>
		</div>
	</div>

    <ul class="pagination">
	    <#if model?exists>
	  	<#if model.paging?exists>
			<#if model.paging.prevPage?exists>
			<li><a href="sltTimeRecordList?nowPage=${model.paging.prevPage.nowPage}&allCount=${model.paging.allCount?c}" title="Prev" accesskey="*">Prev</span></a></li>
			</#if>
			<#if model.paging.pagingInfoList?has_content>
				<#list model.paging.pagingInfoList as pageList>
					<#if model.paging.nowPage?if_exists == pageList.pageNumber?if_exists>
					<li class="active"><a href="#">${pageList.pageNumber} <span class="sr-only">(current)</span></a></li>
					<#else>
					<li><a href="sltTimeRecordList?nowPage=${pageList.pageNumber}&allCount=${model.paging.allCount?c}">${pageList.pageNumber}</a></li>
					</#if>
				</#list>
			</#if>
			<#if model.paging.nextPage?exists>
			<li><a href="sltTimeRecordList?nowPage=${model.paging.nextPage.nowPage}&allCount=${model.paging.allCount?c}" accesskey="#" title="Next">Next</a></li>
			</#if>
		</#if>
		</#if>
	  	</ul>
	  </nav><!-- end #nav -->

</div>
</div>

</@layout.myLayout>