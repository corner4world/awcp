/**
 * 搜索
 * 
 * 使用方法：
 *  new addSearch(option)
 * 
 * @author venson
 */
var addSearch = function(option){
	this.option = $.extend(addSearch.DEFAULTS,option);
	this.init();
}
addSearch.DEFAULTS={
	selectLabel:$("#selectLabel").val(),//下拉框标签
	selectName:$("#selectName").val(),//下拉框name值
	selectOption:$("#selectOption").val(),//下拉框数据（sql/Key=value）
	textLabel:$("#textLabel").val(), //文本标签
	textName:$("#textName").val(), //文本Name值
	textPrefix:'search_text',  //样式前缀
	selectPrefix:'search_select',  //样式前缀
	url:'api/execute',  //后端接口
	container:'.search_main'	//容器
}

addSearch.prototype={
	init:function(){
		//判断是否有下拉框 
		if($.trim(selectLabel)){
			this.initSelect();
		}
		//判断是否有文本框
		if($.trim(textLabel)){
			this.initText();
		}
	},
	
	initText:function(){
		var textLabels=this.option.textLabel.split("@");
		var textNames=this.option.textName.split("@");
		var textPrefix=this.option.textPrefix;
		var that=this;
		$.each(textLabels,function(i,e){
			if(!$.trim(e)){
				return;
			}
			var name = textNames[i];
			var html='<div class="col-xs-6 col-sm-2 col-md-2 col-lg-2" style="margin-bottom:10px"><div class="input-group"><span class="input-group-addon">'+e+'</span><input name="'+name+'" class="'+textPrefix+' form-control"/></div></div>';
			var $warp=$(html);
			$(that.option.container).append($warp);
			var $tag=$warp.find("."+textPrefix);
			that.setDefaultValue($tag);
		})
	},
	
	initSelect:function(){
		var selectLabels=this.option.selectLabel.split("@");
		var selectNames=this.option.selectName.split("@");
		var selectOptions=this.option.selectOption.split("@");
		var selectPrefix=this.option.textPrefix;
		var container=this;
		var that=this;
		$.each(selectLabels,function(i,e){
			if(!$.trim(e)){
				return;
			}
			var option =selectOptions[i];
			var name =selectNames[i];
			var html='<div class="col-xs-6 col-sm-2 col-md-2 col-lg-2"  style="margin-bottom:10px"><div class="input-group"><span class="input-group-addon">'+e+'</span><select name="'+name+'" class="'+selectPrefix+' form-control"></select></div></div>';
			var data;
			//查找是否是动态语句查找
			if(option.indexOf("=")==-1){
				data=Comm.getData(that.option.url+"/"+option,{"_method":"get"});
			}else{
				var options=option.split(";");
				data=[];
				$.each(options,function(i1,o){
					if(!$.trim(o)){
						return;
					}
					var arr=o.split("=");
					data.push({id:arr[0],text:arr[1]});
				})
			}
			$warp=$(html);
			$(that.option.container).append($warp);
			var $tag=$warp.find("."+selectPrefix);
			Comm.setSelectData($tag,data);
			that.setDefaultValue($tag);
		})
	},
	
	setDefaultValue:function($tag){
		var name = $tag.attr("name");
		var that = this;
		if($tag.is("select")){
			if(location.href==document.referrer||location.href+"?dynamicPageId="+$("#dynamicPageId").val()==document.referrer){
				$tag.val(Comm.get(that.option.selectPrefix+name));
			}
			$tag.bind("change",function(){
				Comm.set(that.option.selectPrefix+name,this.value);
			});
		}else{
			if(location.href==document.referrer||location.href+"?dynamicPageId="+$("#dynamicPageId").val()==document.referrer){
				$tag.val(Comm.get(that.option.textPrefix+name));
			}
			$tag.bind("blur",function(){
				Comm.set(that.option.textPrefix+name,this.value);
			});
		}
	}	
}
