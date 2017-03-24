<%@page language="java" pageEncoding="UTF-8" %>
    <!-- bootstrap -->
    <link type="text/css" rel="stylesheet" href="${ctx}/workflow/s/bootstrap/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="${ctx}/workflow/s/bootstrap/css/bootstrap-responsive.min.css">

    <!-- html5 -->
    <!--[if lt IE 9]>
    <link rel="stylesheet" type="text/css" href="${ctx}/workflow/s/mossle/css/ie.css" media="screen" />
    <script type="text/javascript" src="${ctx}/workflow/s/html5/html5shiv.js"></script>
    <![endif]-->

    <!-- jquery -->
    <script type="text/javascript" src="${ctx}/workflow/s/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/workflow/s/jquery/jquery-migrate-1.2.1.min.js"></script>
	<!-- bootstrap -->
    <script type="text/javascript" src="${ctx}/workflow/s/bootstrap/js/bootstrap.min.js"></script>

    <!-- layout -->
    <script type="text/javascript" src="${ctx}/workflow/s/mossle/js/hideshow.js"></script>
    <script type="text/javascript" src="${ctx}/workflow/s/mossle/js/jquery.equalHeight.js"></script>
    <script type="text/javascript" src="${ctx}/workflow/s/mossle/js/table.js"></script>

    <!-- message -->
    <script type="text/javascript" src="${ctx}/workflow/s/jquery-sliding-message/jquery.slidingmessage.min.js"></script>

    <!-- uniform -->
    <link type="text/css" rel="stylesheet" href="${ctx}/workflow/s/uniform/css/uniform.default.css" media="screen" />
    <script type="text/javascript" src="${ctx}/workflow/s/uniform/js/jquery.uniform.min.js"></script>

    <!-- table and pager -->
    <script type="text/javascript" src="${ctx}/workflow/s/pagination/pagination.js"></script>
    <script type="text/javascript" src="${ctx}/workflow/s/table/table.js"></script>
    <script type="text/javascript" src="${ctx}/workflow/s/table/messages_${locale}.js"></script>

    <!-- validater -->
    <script type="text/javascript" src="${ctx}/workflow/s/jquery-validation/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/workflow/s/jquery-validation/additional-methods.min.js"></script>
    <script type="text/javascript" src="${ctx}/workflow/s/jquery-validation/localization/messages_${locale}.js"></script>
    <link type="text/css" rel="stylesheet" href="${ctx}/workflow/s/jquery-validation/jquery.validate.css" />

    <!-- datepicker -->
    <link type="text/css" rel="stylesheet" href="${ctx}/workflow/s/bootstrap-datepicker/datepicker.css">
    <script type="text/javascript" src="${ctx}/workflow/s/bootstrap-datepicker/bootstrap-datepicker.js"></script>
    <script type="text/javascript" src="${ctx}/workflow/s/bootstrap-datepicker/locales/bootstrap-datepicker.${locale}.js"></script>
    <link href="${ctx}/workflow/s/bootstrap-datetimepicker/css/datetimepicker.css" rel="stylesheet">
    <script type="text/javascript" src="${ctx}/workflow/s/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="${ctx}/workflow/s/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.${locale}.js"></script>

	<!-- tree -->
    <link rel="stylesheet" href="<%=basePath%>resources/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css" />
    <script type="text/javascript" src="<%=basePath%>resources/plugins/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>

	<!-- uniform -->
    <link rel="stylesheet" href="${ctx}/workflow/s/uniform/css/uniform.default.min.css" type="text/css" media="screen" />
    <script type="text/javascript" src="${ctx}/workflow/s/uniform/js/jquery.uniform.min.js"></script>

	<!-- chozen -->
    <link rel="stylesheet" href="${ctx}/workflow/s/chosen/chosen.css" type="text/css" media="screen" />
    <script type="text/javascript" src="${ctx}/workflow/s/chosen/chosen.jquery.min.js"></script>

	<!-- bootbox -->
    <script type="text/javascript" src="${ctx}/workflow/s/bootbox/bootbox.min.js"></script>

    <!-- ckeditor -->
    <script type="text/javascript" src="${ctx}/workflow/s/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="${ctx}/workflow/s/ckfinder/ckfinder.js"></script>

    <script type="text/javascript" src="${ctx}/workflow/s/jquery-tablednd/jquery.tablednd.min.js"></script>
	<!-- layout -->
    <link type="text/css" rel="stylesheet" href="${ctx}/workflow/s/mossle/css/layout3.css" media="screen" />
    
    <!-- createModelTemplate -->
    <script type="text/javascript" src="${ctx}/workflow/s/jquery-ui/ui/jquery-ui.js"></script>
    <link type="text/css" rel="stylesheet" href="${ctx}/workflow/s/jquery-ui/themes/base/jquery-ui.css" media="screen" />

    <script type="text/javascript">
$(function() {
    $.showMessage($('#m-success-message').html(), {
        position: 'top',
        size: '55',
        fontSize: '20px'
    });

    $("input:checkbox, input:radio, input:file").not('[data-no-uniform="true"],#uniform-is-ajax').uniform({
        fileDefaultHtml: '还未选择文件',
        fileButtonHtml: '选择文件'
    });

    $('.datepicker').datepicker({
		language: '${locale}',
		format: 'yyyy-mm-dd',
        autoclose: true
	});

    $('.datetimepicker').datetimepicker({
		language: '${locale}',
        format: "yyyy-mm-dd hh:ii",
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });

    $(".chzn-select").chosen({
        no_results_text: '找不到',
        width: '220px'
    });

    $(".chzn-select-deselect").chosen({
        allow_single_deselect:true,
        no_results_text: '找不到',
        width: '220px'
    });

    function widgetToggleContent() {
        var self = $(this);
        self.toggleClass('icon-chevron-up');
        self.toggleClass('icon-chevron-down');
        var widget = self.parents('.m-widget');
        var content = widget.find('.content');
        content.toggle(200);
    }

    $(document).delegate('.m-widget .header .ctrl .icon-chevron-up', 'click', widgetToggleContent);
    $(document).delegate('.m-widget .header .ctrl .icon-chevron-down', 'click', widgetToggleContent);
});
    </script>
