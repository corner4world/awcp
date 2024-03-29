﻿
/*
目的是为了手机也可通用此脚本.
注意不要在手机端修改.
*/
        var init;
        var wcDesc;
        var tks;
        var aths;
        var SignType; //签名的人员.有两个列  No, SignType,
        var nodeid = GetQueryString("FK_Node");
        var fk_flow = GetQueryString("FK_Flow");
        var workid = GetQueryString("WorkID");
        var fid = GetQueryString("FID");

        //是否是手机端.
        var isMobile = GetQueryString("IsMobile");

        //是否只读？
        var isReadonly = GetQueryString("isReadonly");
        if (isReadonly != "1")
            isReadonly = "0";
            
        var enName = GetQueryString("EnName");
        var isChange = false;

        $(function () {
            if (!workid || workid == "0") {
                workid = GetQueryString("OID");
            }
            InitPage();
        });

        function InitPage() {
            var param = {
                DoType: "WorkCheck_Init",
                FK_Flow: fk_flow,
                FK_Node: nodeid,
                WorkID: workid,
                FID: fid,
                IsReadonly: isReadonly
            };

            Handler_AjaxQueryData(param, function (data) {

                if (data.indexOf('err@') != -1) {
                    alert(data);
                    return;
                }

                //  alert(data);
                init = eval('(' + data + ')');
                wcDesc = init.wcDesc[0];
                tks = init.Tracks;
                aths = init.Aths;
                SignType = init.SignType; //签名的人员 No,SignType 列, SignType=0 不签名, 1=图片签名, 2=电子签名。

                var html = '';

                if (tks.length == 0) {
                    html += '<tr style="background-color: #E2F6FB">';
                    html += '<td>&nbsp;</td>';
                    html += '</tr>';
                    html += '<tr>';
                    html += '<td style="word-wrap: break-word;line-height:30px;min-height:100px;">&nbsp;</td>';
                    html += '</tr>';
                }

                $.each(tks, function () {
                    var subaths = GetSubAths(this.NodeID);

                    //                    if (wcDesc.FWCShowModel == 0) {
                    //                        //表格模式
                    //                        html += '<tr style="background-color: #E2F6FB">';
                    //                        html += '<td>' + this.NodeName + '</td>';
                    //                        html += '</tr>';
                    //                        html += '<tr>';
                    //                    }

                    //自由模式
                    html += "<tr>";

                    var tdWidth = "120px";
                    if (isMobile == "1")
                        tdWidth = "20%;";

                    html += "<td " + (this.IsDoc ? ("id='tdnode_" + this.NodeID + "'") : "") + " rowspan='" + (subaths.length > 0 ? 3 : 2) + "' style='width:" + tdWidth + ";border:1px solid #D6DDE6;'>";

                    var nodeName = this.NodeName;
                    nodeName = nodeName.replace('(会签)', '<br>(<font color=Gray>会签</font>)');
                    html += nodeName;
                    html += "</td>";

                    //审核意见
                    if (this.IsDoc && isReadonly == false) {

                        html += "<td>";

                        //html += "<div style='float:left'>" + wcDesc.FWCOpLabel + "</div>";
                        //html += "<div style='float:left'><a href=\"javascript:TBHelp('ND" + nodeid + "')\"><img src='../Img/Emps.gif' width='23px' align='middle' border=0 />选择词汇</a></div>";
                        //html += "<div style='float:left'></div>";

                        if (wcDesc.FWCAth == 1) {
                            html += "<div style='float:right' id='uploaddiv' onmouseover='UploadFileChange(this)'></div>";
                        }

                        html += "<div style='float:left;width:100%;'>";
                        var msg = this.Msg;

                        while (msg.indexOf('<BR>') >= 0) {
                            msg = msg.replace('<BR>', '\t\n');
                        }

                        html += "<textarea id='WorkCheck_Doc' rows='3' style='width:98%;border-style:solid;margin:5px; padding:5px;' onblur='SaveWorkCheck()' onkeydown='this.style.height='60px';this.style.height=this.scrollHeight+\"px\";setIframeHeight();'>";
                        html += msg;
                        html += "</textarea>";

                     //   var ss = "CheckItems";

                        //加入常用短语.
                        html += "<br>";
                        html += "<select id='DuanYu' onchange='SetDocVal();SaveWorkCheck();' >";
                        html += "<option value=''>常用短语</option>";
                        html += "<option value='同意'>同意</option>";
                        html += "<option value='同意办理'>同意办理</option>";
                        html += "<option value='同意,请领导批示.'>同意,请领导批示.</option>";
                        html += "<option value='情况属实报领导批准.'>情况属实报领导批准.</option>";
                        html += "<option value='不同意'>不同意</option>";
                        html += "</select>";

                        html += "</div>";



                        html += "</td>";
                    }
                    else {

                        html += '<td style="word-wrap: break-word;line-height:30px;margin:5px; padding:5px;font-color:green;" >';
                        html += '<font color=green>' + this.Msg + '</font>';
                        html += '</td>';
                    }

                    html += '</tr>';

                    //附件
                    if (subaths.length > 0) {
                        var tdid = this.IsDoc ? ("id='aths_" + this.NodeID + "'") : "";

                        html += "<tr style='" + (subaths.length > 0 ? "" : "display:none;") + "'>";
                        html += "<td " + tdid + " style='word-wrap: break-word;' colspan=2>";
                        html += "<b>附件：</b>&nbsp;" + subaths;
                        html += "</td>";
                        html += "</tr>";
                    }

                    //输出签名.
                    if (SignType == null || SignType == undefined) {

                        var rdt = this.RDT.substring(0, 16);

                        //签名，日期.
                        html += "<tr>";
                        html += "<td style='text-align:left;height:35px;line-height:35px;'><div style='float:left'><font color='Gray' >签名:</font>";

                        if (wcDesc.SigantureEnabel == "0")
                            html += GetUserSmallIcon(this.EmpFrom, this.EmpFromT);
                        else
                            html += GetUserSiganture(this.EmpFrom, this.EmpFromT);

                        html += "</div>";


                        html += "<div style='float:right'> ";
                        html += "<font color='Gray'>日期:</font>" + rdt;
                        html += "</div>";
                        html += "</td>";

                        html += "</tr>";

                    } else {

                        for (var idx = 0; idx < SignType.length; idx++) {

                            var st = SignType[idx];
                            if (st.No != this.EmpFrom)
                                continue;

                            var rdt = this.RDT.substring(0, 16);


                            if (st.SignType == 0 || st.SignType == 2 || st.SignType == null) {


                                html += "<tr>";
                                html += "<td style='text-align:left;height:35px;line-height:35px;'><div style='float:left'><font color='Gray' >签名:</font>"
                                    + GetUserSmallIcon(this.EmpFrom, this.EmpFromT) + '</div>'
                                    + "<div style='float:right' ><font color='Gray' >日期:</font>" + (this.IsDoc ? "<span id='rdt'>" : "") + rdt + (this.IsDoc ? "</span>" : "") + "</div></td>";
                                html += "</tr>";
                                break;
                            }

                            if (st.SignType == 1) {
                                html += "<tr>";
                                html += "<td style='text-align:left;height:35px;line-height:35px;'><div style='float:left'><font color='Gray' >签名:</font>"
                                    + GetUserSiganture(this.EmpFrom, this.EmpFromT) + '</div>'
                                    + " <div style='float:right' ><font color='Gray' >日期:</font>" + (this.IsDoc ? "<span id='rdt'>" : "") + rdt + (this.IsDoc ? "</span>" : "") + "</div></td>";
                                html += "</tr>";
                                break;
                            }

                            if (st.SignType == 2) {

                                html += "<tr>";
                                html += "<td style='text-align:left;height:35px;line-height:35px;'><div style='float:left'><font color='Gray' >签名:</font>"
                                    + GetUserSiganture(this.EmpFrom, this.EmpFromT) + '</div>'
                                    + " <div style='float:right' ><font color='Gray' >日期:</font>" + (this.IsDoc ? "<span id='rdt'>" : "") + rdt + (this.IsDoc ? "</span>" : "") + "</div></td>";
                                html += "</tr>";

                                //  alert('电子签名的逻辑尚未编写.');
                                break;
                            }
                        }
                    }

                    // GenerSiganture(SignType);

                });

                $("#tbTracks").append(html);

                if ($("#WorkCheck_Doc").length > 0) {
                    if (wcDesc.FWCDefInfo && wcDesc.FWCDefInfo.length > 0) {
                        SaveWorkCheck();
                    }
                }

                $("textarea").trigger("keydown");

                if ($("#uploaddiv").length > 0) {
                    AddUploadify("uploaddiv");
                }
               
            }, this);
        }

        function SetDocVal() {

            var objS = document.getElementById("DuanYu");
            var val = objS.options[objS.selectedIndex].value;

            if (val == "")
                return;

            document.getElementById("WorkCheck_Doc").value = val;

        }
		function setIframeHeight() {
			$("#" + window.frameElement.getAttribute("id"), parent.document).height($("body").height());
		}

        function SaveWorkCheck() {

            var doc = $("#WorkCheck_Doc").val();

            if (isReadonly==true)
                return;

//            if (doc.length == 0) {
//                if (wcDesc.FWCDefInfo && wcDesc.FWCDefInfo.length > 0) {
//                    doc = wcDesc.FWCDefInfo;
//                    $("#WorkCheck_Doc").val(doc);
//                }
//            }

            var param = {
                DoType: "WorkCheck_Save",
                FK_Flow: fk_flow,
                FK_Node: nodeid,
                WorkID: workid,
                FID: fid,
                Doc: doc,
                IsCC: GetQueryString("IsCC")
            };

            Handler_AjaxQueryData(param, function (data) {
                if (data.indexOf('err@') != -1) {
                    alert(data);
                    return;
                }

                if (data.length > 0) {
                    $("#rdt").text(data);
                }
            }, this);
        }

        function DelWorkCheckAth(athPK) {
            isChange = false;
            if (confirm("确定要删除所选文件吗？")) {
                $.ajax({
                    type: "GET", //使用GET或POST方法访问后台
                    dataType: "text", //返回json格式的数据
                    contentType: "application/json; charset=utf-8",
                    url: "../CCForm/Handler.ashx?DoType=DelWorkCheckAttach&MyPK=" + athPK, //要访问的后台地址
                    async: false,
                    cache: false,
                    success: function (msg) {//msg为返回的数据，在这里做数据绑定
                        if (msg == "true") {
                            isChange = true;

                            $("#Ath_" + athPK).remove();
                        }
                        if (msg == "false") {
                            alert("删除失败。");
                        }
                    }
                });
            }
        }

        function TBHelp(enName) {

            var explorer = window.navigator.userAgent;
            var str = "";
            var url = "../Comm/HelperOfTBEUI.aspx?EnsName=" + enName + "&AttrKey=WorkCheck_Doc&WordsSort=0" + "&FK_MapData=" + enName + "&id=WorkCheck_Doc";
            if (explorer.indexOf("Chrome") >= 0) {
                window.open(url, "sd", "left=200,height=500,top=150,width=600,location=yes,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no");
            }
            else {
                str = window.showModalDialog(url, 'sd', 'dialogHeight: 500px; dialogWidth:600px; dialogTop: 150px; dialogLeft: 200px; center: no; help: no');
                if (str == undefined)
                    return;

                $("#WorkCheck_Doc").val(str);
                isChange = true;
            }
        }

        function AthDown(fk_ath, pkVal, delPKVal, fk_node, fk_flow, ath) {
            window.location.href = '../CCForm/AttachmentUpload.aspx?DoType=Down&DelPKVal=' + delPKVal + '&FK_FrmAttachment=' + fk_ath + '&PKVal=' + pkVal + '&FK_Node=' + fk_node + '&FK_Flow=' + fk_flow + '&FK_MapData=ND' + fk_node + '&Ath=' + ath;
        }

        function AthOpenOfiice(fk_ath, pkVal, delPKVal, FK_MapData, NoOfObj, FK_Node) {
            var date = new Date();
            var t = date.getFullYear() + "" + date.getMonth() + "" + date.getDay() + "" + date.getHours() + "" + date.getMinutes() + "" + date.getSeconds();
            var url = '../WebOffice/AttachOffice.aspx?DoType=EditOffice&DelPKVal=' + delPKVal + '&FK_FrmAttachment=' + fk_ath + '&PKVal=' + pkVal + "&FK_MapData=" + FK_MapData + "&NoOfObj=" + NoOfObj + "&FK_Node=" + FK_Node + "&T=" + t;
            window.open(url, '_blank', 'height=600,width=850,top=50,left=50,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no');
        }

        function AthOpenFileView(pkVal, delPKVal, FK_FrmAttachment, FK_FrmAttachmentExt, fk_flow, fk_node, workID, isCC) {
            var url = '../CCForm/FilesView.aspx?showType=view&DelPKVal=' + delPKVal + '&PKVal=' + pkVal + '&FK_FrmAttachment=' + FK_FrmAttachment + '&FK_FrmAttachmentExt=' + FK_FrmAttachmentExt + '&FK_Flow=' + fk_flow + '&FK_Node=' + fk_node + '&WorkID=' + workID + '&IsCC=' + isCC;
            window.open(url, '_blank', 'height=600,width=850,top=50,left=50,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no');
        }

        function AthOpenView(pkVal, delPKVal, FK_FrmAttachment, FK_FrmAttachmentExt, FK_Flow, FK_Node, WorkID, IsCC) {
            var url = '../CCForm/FilesView.aspx?showType=view&DelPKVal=' + delPKVal + '&PKVal=' + pkVal + '&FK_FrmAttachment=' + FK_FrmAttachment + '&FK_FrmAttachmentExt=' + FK_FrmAttachmentExt + '&FK_Flow=' + FK_Flow + '&FK_Node=' + FK_Node + '&WorkID=' + WorkID + '&IsCC=' + IsCC;
            window.open(url, '_blank', 'height=600,width=850,top=50,left=50,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no');
        }

        function UploadFileChange(ctrl) {
            //ctrl.detachEvent('onblur', '');
            $(ctrl).unbind("onblur");
            isChange = false;
        }

        function GetUserSiganture(userNo, userName) {
            var func = " oncontextmenu='return false;' ondragstart='return false;'  onselectstart='return false;' onselect='document.selection.empty();'";
            return "<img src='../../DataUser/Siganture/" + userNo + ".jpg?m="+Math.random()+"' title='" + userName + "' " + func + " style='height:40px;' border=0 onerror=\"src='../../DataUser/Siganture/UnName.JPG'\" />";
        }

        function GetUserSmallIcon(userNo, userName) {
            return userName;

            //return "<img src='../../DataUser/UserIcon/" + userNo + "Smaller.png' border=0  style='height:15px;width:15px;padding-right:5px;vertical-align:middle;'  onerror=\"src='../../DataUser/UserIcon/DefaultSmaller.png'\" />" + userName;
        }

        function FindSubAths(nd) {
            var subAths = [];

            $.each(aths, function () {
                if (this.NodeID == nd) {
                    subAths.push(this);
                }
            });

            return subAths;
        }

        function GetSubAths(nd) {
            var subAths = FindSubAths(nd);
            var html = '';

            $.each(subAths, function () {
                html += GetAthHtml(this);
            });

            return html;
        }

        function GetAthHtml(ath) {
            var html = "<div id='Ath_" + ath.MyPK + "' style='margin:5px; display:inline-block;'>";

            if (ath.CanDelete) {
                html += "<img alt='删除' align='middle' src='../Img/Btn/Delete.gif' onclick=\"DelWorkCheckAth('" + ath.MyPK + "')\" />&nbsp;&nbsp;";
            }

            html += "<a style='color:Blue; font-size:14;' href=\"" + ath.Href + "\">" + ath.FileName;
            html += "&nbsp;&nbsp;<img src='../Img/FileType/" + ath.FileExts + ".gif' border=0 onerror=\"src='../Img/FileType/Undefined.gif'\" /></a>";
            html += "&nbsp;&nbsp;</div>";

            return html;
        }

        function AddUploadify(divid) {
            if ($("#file_upload").length == 0) {
                var html = "<div id='file_upload-queue' class='uploadify-queue'></div>"
                             + "<div id='s' style='float:right;margin-right:10px;' >"
                             + "<input type='file' name='file_upload' id='file_upload' width='60' height='30' />"
                             + "</div>";

                $("#" + divid).append(html);

                $('#file_upload').uploadify({
                    'swf': '../Scripts/Jquery-plug/fileupload/uploadify.swf',
                    'uploader': '../CCForm/Handler.ashx?AttachPK=' + nodeid + '_FrmWorkCheck&WorkID=' + workid + '&showType=MoreAttach&FK_Node=' + nodeid + '&EnsName=' + (enName ? enName : '') + '&FK_Flow=' + fk_flow + '&PKVal=' + workid,
                    'auto': true,
                    'fileTypeDesc': '请选择上传文件',
                    'buttonText': '上传附件',
                    //                    'hideButton': true,
                    'width': 60,
                    'fileTypeExts': '*.*',
                    'height': 18,
                    'multi': true,
                    'queueSizeLimit': 999,
                    'onDialogOpen': function (a, b) {
                    },
                    'onQueueComplete': function (queueData) {
                        isChange = true;

                        GetNewUploadedAths(queueData.files);
                    },
                    'removeCompleted': true
                });
            }
        }

        function GetNewUploadedAths(files) {
            var param = {
                showType: "WorkCheck_GetNewUploadedAths",
                Names: "|",
                AttachPK: nodeid + "_FrmWorkCheck",
                FK_Flow: fk_flow,
                FK_Node: nodeid,
                WorkID: workid
            };

            for (var field in files) {
                param.Names += files[field].name + "|";
            }

            Handler_AjaxQueryData(param, function (data) {
                if (data.indexOf('err@') != -1) {
                    alert(data);
                    return;
                }

                var naths = eval('(' + data + ')');
                aths = aths.concat(naths);

                if ($("#aths_" + nodeid).length == 0) {
                    if ($("#WorkCheck_Doc").length > 0) {
                        var tdid = "id='aths_" + nodeid + "'";
                        var html = "<tr><td " + tdid + " style='word-wrap: break-word;'>";
                        html += "<b>附件：</b>&nbsp;";
                        html += "</td></tr>";

                        $("#WorkCheck_Doc").parent("tr").after(html);
                    }
                }

                if (wcDesc.FWCShowModel != 0) {
                    $("#tdnode_" + nodeid).attr("rowspan", "3");
                }

                $("#aths_" + nodeid).parent().removeAttr("style");

                $.each(naths, function () {
                    $("#aths_" + nodeid).append(GetAthHtml(this));
                });
            }, this);
        }

        //执行保存
        function SaveDtlData() {
            if (isChange == true) {
                SaveWorkCheck();
                isChange = false;
            }
        }

        function Load() {
            var url = window.location.href;
            if (plant == "CCFlow") {
                url = url.replace('.htm', '.aspx');
                window.location.href = url;
                return;
            } else {
                url = url.replace('.htm', '.jsp');
                window.location.href = url;
                return;
            }
        }

        //为判断是否增加电子签章所用.
        function IsCanSendWork() {
            return true;
        }