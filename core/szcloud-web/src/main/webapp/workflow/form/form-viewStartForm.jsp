<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<%pageContext.setAttribute("currentHeader", "bpm-workspace");%>
<%pageContext.setAttribute("currentMenu", "bpm-process");%>
<!doctype html>
<html lang="en">

  <head>
    <%@include file="../common/meta.jsp"%>
    <title><spring:message code="demo.demo.input.title" text="编辑"/></title>
    <%@include file="../common/s.jsp"%>
	<link href="${scopePrefix}/workflow/widgets/xform/styles/xform.css" rel="stylesheet">
    <script type="text/javascript" src="${scopePrefix}/workflow/widgets/xform/xform-packed.js"></script>

    <link type="text/css" rel="stylesheet" href="../widgets/userpicker/userpicker.css">
    <script type="text/javascript" src="../widgets/userpicker/userpicker.js"></script>

	<script type="text/javascript">
document.onmousedown = function(e) {};
document.onmousemove = function(e) {};
document.onmouseup = function(e) {};
document.ondblclick = function(e) {};

var xform;

$(function() {
	xform = new xf.Xform('xf-form-table');
	xform.render();

	if ($('#__gef_content__').val() != '') {
		xform.doImport($('#__gef_content__').val());
	}

	if ('${json}' != '') {
		xform.setValue(${json});
	}

	$("#demoForm").validate({
        submitHandler: function(form) {
			bootbox.animate(false);
			var box = bootbox.dialog('<div class="progress progress-striped active" style="margin:0px;"><div class="bar" style="width: 100%;"></div></div>');
            form.submit();
        },
        errorClass: 'validate-error'
    });

	$(document).delegate('#button0', 'click', function(e) {
		$('#xf-form').attr('action', 'form-saveDraft.do');
		$('#xf-form').submit();
	});

	$(document).delegate('#button1', 'click', function(e) {
		$('#xf-form').attr('action', 'form-${nextStep}.do');
		$('#xf-form').submit();
	});

	createUserPicker({
		multiple: true,
		url: '${scopePrefix}/workflow/rs/user/search'
	});

	setTimeout(function() {
		$('.datepicker').datepicker({
			autoclose: true,
			language: 'zh_CN',
			format: 'yyyy-mm-dd'
		})
	}, 500);
})
    </script>
  </head>

  <body>
    <section style="width: 98%;margin: 0 auto;">

      <form id="xf-form" method="post" action="${scopePrefix}/workflow/form/form-startProcessInstance.do" class="xf-form">
		<input id="processDefinitionId" type="hidden" name="processDefinitionId" value="${formInfo.processDefinitionId}">
		<input id="bpmProcessId" type="hidden" name="bpmProcessId" value="${bpmProcessId}">
		<input id="autoCompleteFirstTask" type="hidden" name="autoCompleteFirstTask" value="${formInfo.autoCompleteFirstTask}">
		<input id="businessKey" type="hidden" name="businessKey" value="${businessKey}">
<!--
<input id="taskId" type="hidden" name="taskId" value="${taskId}">
-->
		<div id="xf-form-table"></div>
		<br>
		<div id="nextStep">
		</div>
		  <script>
		  $.getJSON('${scopePrefix}/rs/workflow/bpm/next', {
			  processDefinitionId: '${formInfo.processDefinitionId}',
			  activityId: '${formInfo.activityId}'
		  }, function(data) {
			  $('#nextStep').append('下个环节：');
			  for (var i = 0; i < data.length; i++) {
				  $('#nextStep').append(data[i].name);
			  }
		  });
		  </script>
		<div style="text-align:center;">
		  <button id="button0" type="button">保存草稿</button>
		  <button id="button1" type="button">发起流程</button>
		</div>
	  </form>

    </section>
	<!-- end of main -->

    <form id="f" action="form-template-save.do" method="post" style="display:none;">
	  <textarea id="__gef_content__" name="content">${formTemplate.content}</textarea>
	</form>

  </body>

</html>
