<#import "/apps/admin/layout/abilistsAdminLayout.ftl" as layout>
<#import "/spring.ftl" as spring/>
<@layout.myLayout>

<style>

</style>

<div class="item-box" style="margin-top: 15px;">
<div class="row">
	<div class="col-md-8">
		<h3>
			<ol class="breadcrumb-std">
			  <li><a href="/admin/"><@spring.message "admin.menu.plugins"/></a></li>
			  <li class="active">출퇴근 관리2.2</li>
			</ol>
		</h3>
	</div>
	<div class="col-md-4">

	</div>
</div>
</div>

<div class="row">
  <div class="col-md-12">
	<#include "/apps/common/errorMessage.ftl"/>
	<#include "/apps/common/abilistsSuccess.ftl"/>

<!-- Start to write code ---->
	<div id="userTimeRecordId" style="margin: 10px;">
		<div id="timeTableId" style="border: 1px solid #CDCDCD;">
		<div>
	    <ul class="table-ul table-ul-header">
	    	<li class="time-li6">아이디</li>
	    	<li class="time-li1">근무 종류</li>
	        <li class="time-li2">출근 날</li>
	        <li class="time-li3">출근 시간</li>
	        <li class="time-li4">퇴근 시간</li>
	        <li class="time-li5">근무한 시간</li>
	    </ul>
	    <#if plugins??>
	    <#if plugins.timeRecordList?has_content>
	    <#list plugins.timeRecordList as timeRecord>
		    <ul class="table-ul bg-color ul-hover"">
		    	<li class="time-li6">${timeRecord.userId?if_exists}</li>
			    <li class="time-li1">
			    <#if timeRecord.utrKind??>
				    <#if timeRecord.utrKind == "0">
				    		상근
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
				<#else>
			    </#if>
			    </li>
			    <li class="time-li2"><#if timeRecord.utrWorkDay??>${timeRecord.utrWorkDay?string('yyyy-MM-dd')?if_exists}</#if></li>
		        <li class="time-li3"><#if timeRecord.utrStartTime??>${timeRecord.utrStartTime?string('HH:mm:ss')?if_exists}</#if></li>
		        <li class="time-li4"><#if timeRecord.utrEndTime??>${timeRecord.utrEndTime?string('HH:mm:ss')?if_exists}</#if></li>
		        <li class="time-li5"><#if timeRecord.utrWorkHour??>${timeRecord.utrWorkHour?if_exists}</#if></li>
		    </ul>
		    <ul style="list-style: none; padding: 0px;">
		    	<li style="padding: 10px;">
		    		${timeRecord.utrComment?if_exists}
		    	</li>
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
			<li><a href="/admin/plugins/timerecord?nowPage=${model.paging.prevPage.nowPage}&allCount=${model.paging.allCount?c}" title="Prev" accesskey="*">Prev</span></a></li>
			</#if>
			<#if model.paging.pagingInfoList?has_content>
				<#list model.paging.pagingInfoList as pageList>
					<#if model.paging.nowPage?if_exists == pageList.pageNumber?if_exists>
					<li class="active"><a href="#">${pageList.pageNumber} <span class="sr-only">(current)</span></a></li>
					<#else>
					<li><a href="/admin/plugins/timerecord?nowPage=${pageList.pageNumber}&allCount=${model.paging.allCount?c}">${pageList.pageNumber}</a></li>
					</#if>
				</#list>
			</#if>
			<#if model.paging.nextPage?exists>
			<li><a href="/admin/plugins/timerecord?nowPage=${model.paging.nextPage.nowPage}&allCount=${model.paging.allCount?c}" accesskey="#" title="Next">Next</a></li>
			</#if>
		</#if>
		</#if>
	</ul>
	</nav><!-- end #nav -->

<!-- End to write code ---->

  </div><!-- #col-md-12 -->
</div><!-- #row -->

<#include "/apps/common/abilistsAdminLoadJs.ftl"/>

</@layout.myLayout>