<#import "/apps/layout/plugins/abilistsPluginsLayout.ftl" as layout>
<#import "/spring.ftl" as spring/>
<@layout.myLayout>

<div class="row">
<div class="col-sm-12">
	<div class="caption mittle-size-title middle-works-bg">
		<h5><b>출퇴근 관리</b></h5>
	</div>
	<div class="item-box" style="overflow: hidden;">

	<p>
	<form name="reachForm" class="form-horizontal" action="${configBean.contextPath?if_exists}/plugins/timerecord/istTimeRecord" method="post">
		<button type="submit" class="btn btn-primary" >출근하기</button>
	</form>
	<form name="reachForm" class="form-horizontal" action="${configBean.contextPath?if_exists}/plugins/timerecord/istTimeRecord" method="post">
		<button class="btn btn-primary" type="button" onClick="leaveOffice();">퇴근하기</button>
	</form>
	</p>

	<div class="item-box">
		<table id="tableSkillsListId" class="table table-striped">
			<tr style="background-color: #eeeeec;">
				<th class="my_profile">aa</th>
				<th class="my_profile">bb</th>
				<th class="my_profile">cc</th>
				<th class="my_profile">dd</th>
			</tr>
	    <#if plugins??>
	    <#if plugins.timeRecordList?has_content>
	    <#list plugins.timeRecordList as timeRecord>
			<tr onmouseover="overChangeColor(this);"  onmouseout="outChangeColor(this);">
				<td>${timeRecord.ntrNo?if_exists}</td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</#list>
		</#if>
		</#if>
		</table>
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