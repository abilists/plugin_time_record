<#import "/apps/layout/plugins/abilistsPluginsLayout.ftl" as layout>
<#import "/spring.ftl" as spring/>
<@layout.myLayout>

<link rel="stylesheet" href="${configBean.contextPath?if_exists}/static/apps/css/abilists/ad.css?2017102207">
<style>
	.plugins-box {
		list-style: none; 
		width: 220px;
    	margin: 5px;
		float: left;
		border-radius: 3px;
    	border: 1px solid #eee;
		padding: 10px;
	}
</style>

<div class="item-box">
	<nav class="breadcrumbs">
	<ul>
		<li><a class="menu-works-button" href="${configBean.contextPath?if_exists}/plugins"> <@spring.message "plugins.navi.title"/></a></li>
		<li class="active"><a href="#"><@spring.message "plugins.main.title"/></a></li>
	</ul>
	</nav>
</div>

<div id="divBodyId" class="row">
	<div class="col-md-2 right-col-cus sideImg">
		<div class="item-box" style="border: 0.5px solid #dadada;">
			<a href="${configBean.contextPath?if_exists}/account">
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
<div class="col-sm-12">
	<div class="caption mittle-size-title middle-works-bg">
		<h5><b><@spring.message "plugins.main.title"/></b></h5>
	</div>
  <div class="item-box" style="overflow: hidden;">
  <#if mPluginsList??>
  <#if mPluginsList?has_content>
	  <#list mPluginsList as mPlugins>
	  	<li class="plugins-box">
			<a href="${configBean.contextPath?if_exists}/plugins/${mPlugins.mpName?if_exists}/index">
			<div style="height: 220px;"><img src="${mPlugins.mpImgUrl?if_exists}" alt="icon"></div>
			<div style="box-sizing: border-box;">
				<div><span>Servey</span></div>
				<div class="title">
					${mPlugins.mpName?if_exists}
				</div>
				<p>${mPlugins.mpExplain?if_exists}</p>
			</div>
			</a>
		</li>
		</#list>
	<#else>
	<div id="restartId" style="margin-bottom: 1px;text-align: center; margin: 50px;">
		<a class="btn btn-info" href="${configBean.contextPath?if_exists}/admin/plugins/pluginList" style="width: 50%;height: 65px;font-size: 120%;padding: 20px;"> <@spring.message "plugins.main.link.button"/></a>
	</div>
  </#if>
  </#if>
  </div>

</div>
</div>

<#include "/apps/common/abilistsPluginsLoadJs.ftl"/>

</@layout.myLayout>