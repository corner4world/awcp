<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="form-group">
	<label class="col-md-2"><a href="javascript:openCodeMirror('hiddenScript');">隐藏脚本</a></label>	
	<div class="col-md-4">
		<textarea name='hiddenScript' id='hiddenScript' rows='4' class='form-control'></textarea>
	</div>
</div>
<div class="form-group">
	<label class="col-md-2"><a href="javascript:openCodeMirror('disabledScript');">禁用脚本</a></label>	
	<div class="col-md-4">
		<textarea name='disabledScript' id='disabledScript' rows='4' class='form-control'></textarea>
	</div>
</div>
<div class="form-group">
	<label class="col-md-2"><a href="javascript:openCodeMirror('readonlyScript');">只读脚本</a></label>	
	<div class="col-md-4">
		<textarea name='readonlyScript' id='readonlyScript' rows='4' class='form-control'></textarea>
	</div>
</div>