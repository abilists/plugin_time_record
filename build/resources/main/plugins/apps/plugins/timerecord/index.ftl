<#import "/apps/layout/plugins/abilistsPluginsLayout.ftl" as layout>
<#import "/spring.ftl" as spring/>
<@layout.myLayout>

<link rel="stylesheet" href="${configBean.contextPath?if_exists}/static/apps/css/abilists/ad.css?2017102207">
<div class="item-box">
<div class="row">
	<div class="col-md-6">
		<nav class="breadcrumbs">
		<ul>
		<li><a class="menu-works-button" href="${configBean.contextPath?if_exists}/plugins"> Plugins </a></li>
		<li class="active"><a href="#">Survey</a></li>
		</ul>
		</nav>
	</div>
	<div class="col-md-6">
	</div>
</div>
</div>

<div id="divBodyId" class="row">
	<div class="col-md-2 right-col-cus sideImg">
		<div class="item-box" style="border: 0.5px solid #dadada;">
			<a href="${configBean.baseURL?if_exists}/account">
				<img style="border-radius: 4px;" src="${myImgAvatar?if_exists}" id="showImg" width="125" alt="your picture" />
			</a>
	    </div>
	</div>
	<div class="col-sm-10 left-col-cus">
		<a href="http://www.abilists.com/" target="_blank">
			<img class="ad-img01" src="${configBean.contextPath?if_exists}/static/apps/img/abilists/adBanner01.png?2019010101" width="100%" alt="www.abilists.com">
		    <div class="ad-title01" style="color: #4363a2;">
				<h2>Platform for Experts</h2>
				<p style="color: #5a5a5a;">Abilists is a talent management system that grasps the current technology and growth trends from an objective point of view.</p>
			</div>
		</a>
	</div>
</div>


<div class="row">
  <div class="col-md-12">

	<div class="caption mittle-size-title middle-works-bg">
		<h5>
			<b> Survey </b>
			<span id="newToggleId" class="glyphicon glyphicon-chevron-down right-symbol-works-button" aria-hidden="true" onClick="newFormToggle();"></span>
		</h5>
	</div>
	<#include "/apps/common/errorMessage.ftl"/>
	<#include "/apps/common/abilistsSuccess.ftl"/>

	<div class="item-box" id="udtMdataFormId" style="background-color: #f7f5ff; display: none;">
		<form name="updateForm" class="form-horizontal" action="${configBean.baseURL?if_exists}/works/udtReports" method="post" id="updateFormId" onkeypress="return captureReturnKey(event);">
	  	  <div class="row">
		    <div class="col-sm-3 col-md-3" style="padding-right: 5px;">
		  	</div>
		  	<div class="col-sm-9 col-md-9">
	  			<label class="control-label"> report </label> <p class="string-size"><span id="idUdtReports">0</span>/2900</p>
	  			<textarea class="taForm" style="height: 155px;" id="udtUrReportsId" name="urReport" placeholder="Detail" rows="25" onkeyup="checkByteLength(this, 'idUdtReports', 2900)" onfocus="checkByteLength(this, 'idUdtReports', 2900)"></textarea>
		  	</div>
	  	  </div>
		  <input type="hidden" id="urNoId" name="urNo" />
		  <input type="hidden" id="tokenId" name="token" />
		  <br/>
			<p align="center">
		      <button type="button" onclick="return confirmData('updateFormId');" class="btn btn-primary" data-toggle="modal">
		      	confirm
		      </button>
		      <button class="btn btn-primary" type="button" onClick="updateFormCancel();">Cancel</button>
		      <button class="btn btn-danger" type="button" onclick="removeReports()">Delete</button>
			</p>
		</form>
	</div>

	<div class="item-box">
		This is a sample plugin.
	</div>

	<br/>
    <nav class="text-center">
	    <ul class="pagination">
	    <#if model?exists>
    	<#if model.paging?exists>
			<#if model.paging.prevPage?exists>
			<li><a href="sltSurveyList?nowPage=${model.paging.prevPage.nowPage}&allCount=${model.paging.allCount?c}" title="Prev" accesskey="*">Prev</span></a></li>
			</#if>
			<#if model.paging.pagingInfoList?has_content>
				<#list model.paging.pagingInfoList as pageList>
					<#if model.paging.nowPage?if_exists == pageList.pageNumber?if_exists>
					<li class="active"><a href="#">${pageList.pageNumber} <span class="sr-only">(current)</span></a></li>
					<#else>
					<li><a href="sltReportsList?nowPage=${pageList.pageNumber}&allCount=${model.paging.allCount?c}">${pageList.pageNumber}</a></li>
					</#if>
				</#list>
			</#if>
			<#if model.paging.nextPage?exists>
			<li><a href="sltSurveyList?nowPage=${model.paging.nextPage.nowPage}&allCount=${model.paging.allCount?c}" accesskey="#" title="Next">Next</a></li>
			</#if>
		</#if>
		</#if>
    	</ul>
    </nav><!-- end #nav -->

  </div><!-- col-md-12 -->
</div><!-- row -->

<#include "/apps/common/abilistsPluginsLoadJs.ftl"/>

</@layout.myLayout>