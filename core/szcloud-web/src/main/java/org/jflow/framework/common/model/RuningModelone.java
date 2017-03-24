package org.jflow.framework.common.model;

import BP.DA.DataRow;
import BP.DA.DataTable;
import BP.Port.WebUser;
import BP.Tools.StringHelper;
import BP.WF.Dev2Interface;
import BP.WF.Glo;
import BP.WF.Entity.GenerWorkFlow;
import BP.WF.Entity.GenerWorkFlowAttr;
import BP.WF.Entity.GenerWorkFlows;
import BP.WF.Template.PubLib.WFState;

public class RuningModelone {
	private String PageID;
	private String PageSmall;
	private String GroupBy;
	private String basePath;
	private String FK_Flow;

	public RuningModelone(String basePath, String FK_Flow, String GroupBy, String PageID, String PageSmall){
		this.basePath = basePath;
		this.FK_Flow = FK_Flow;
		this.GroupBy = GroupBy;
		this.PageID = PageID;
		this.PageSmall = PageSmall;
	}
	
	public StringBuilder Pub1 = null;
	public void init() {
		this.Pub1 = new StringBuilder();
		
		if(WebUser.getIsWap()){
			this.BindWap();
			return;
		}
		
		int colspan = 6;
        this.Pub1.append(BaseModel.AddTable("class='am-table am-table-striped am-table-hover table-main'"));
        
//        if (WebUser.getIsWap())
//            this.Pub1.append(BaseModel.AddCaption("<img src='"+basePath+"WF/Img/Home.gif' >&nbsp;<a href='"+basePath+"WF/Home.jsp' >Home</a>-<img src='"+basePath+"WF/Img/EmpWorks.gif' >在途工作"));
//        else
//            this.Pub1.append(BaseModel.AddCaption("在途工作"));
        
        this.Pub1.append(BaseModel.AddTR());
        this.Pub1.append(BaseModel.AddTDTitle("nowarp=true", "序"));
        this.Pub1.append(BaseModel.AddTDTitle("nowarp=true width='40%'", "标题"));
        this.Pub1.append(BaseModel.AddTDTitle("nowarp=true", ""));
        this.Pub1.append(BaseModel.AddTDTitle("nowarp=true", "发起人"));
        this.Pub1.append(BaseModel.AddTREnd());
        
        String groupVals = "";
        DataTable dt = Dev2Interface.DB_GenerRuning();
        if(dt.Rows.size()<2)
        {
        	for(DataRow dr : dt.Rows){
         		if(groupVals.contains("@" + dr.getValue(GroupBy)))continue;
         		 groupVals += "@" + dr.getValue(GroupBy);
         	}
        }else
        {
        	for (int i = 0; i < 2; i++) {
        		DataRow dr=dt.Rows.get(i);
        		if(groupVals.contains("@" + dr.getValue(GroupBy)))continue;
        		 groupVals += "@" + dr.getValue(GroupBy);
			}
        }
        int i = 0;
        //boolean is1 = false;
        String title = null;
        String workid = null;
        String fk_flow = null;
        int gIdx = 0;
        String[] gVals = groupVals.split("@");
        for (String g : gVals){
        	 if (StringHelper.isNullOrEmpty(g))continue;
        	 gIdx++;
        	 
        	  this.Pub1.append(BaseModel.AddTR());
              this.Pub1.append(BaseModel.AddTD("colspan=" + colspan + " onclick=\"GroupBarClick('" + basePath + "','" + gIdx + "')\" ", "<div style='text-align:left; float:left' ><img src='"+basePath+"WF/Img/Min.gif' alert='Min' id='Img" + gIdx + "'   border=0 />&nbsp;<b>" + g + "</b>"));
              this.Pub1.append(BaseModel.AddTREnd());
              if(dt.Rows.size()<4)
              {
            	  for(DataRow dr : dt.Rows){
                	  if(!g.equals(dr.getValue(GroupBy).toString()))continue;
                	  i++;
                      this.Pub1.append(BaseModel.AddTR("ID='" + gIdx + "_" + i + "'"));
                      this.Pub1.append(BaseModel.AddTDIdx(i));
                      
                      WFState wfstate = WFState.forValue(Integer.parseInt(dr.getValue("WFState").toString()));
                      title = "<span class='am-icon-sign-out'></span>" + dr.getValue("Title").toString();
                      
                      workid = dr.getValue("WorkID").toString();
                      fk_flow = dr.getValue("FK_Flow").toString();
                      
                      this.Pub1.append("\n<TD nowrap=\"nowrap\"><a href=\"javascript:WinOpen('"+ basePath +"WF/WFRpt.jsp?WorkID=" + workid + "&FK_Flow=" + fk_flow + "&FID=" + dr.getValue("FID") + "')\" >" + title + "</a></TD>");
                      this.Pub1.append(BaseModel.AddTDBegin());
                      if (!GenerWorkFlowAttr.StarterName.equals(GroupBy))
                		  this.Pub1.append(BaseModel.AddTD(dr.getValue(GenerWorkFlowAttr.StarterName).toString()));

                      this.Pub1.append(BaseModel.AddTDEnd());
                      this.Pub1.append(BaseModel.AddTREnd());
                  }
              }else
              {
            	  for (int j = 0; j < 4; j++) {
            		  DataRow dr=dt.Rows.get(i);
            		  if(!g.equals(dr.getValue(GroupBy).toString()))continue;
                	  i++;
                      this.Pub1.append(BaseModel.AddTR("ID='" + gIdx + "_" + i + "'"));
                      this.Pub1.append(BaseModel.AddTDIdx(i));
                      
                      WFState wfstate = WFState.forValue(Integer.parseInt(dr.getValue("WFState").toString()));
                      title = "<span class='am-icon-sign-out'></span>" + dr.getValue("Title").toString();
                      
                      workid = dr.getValue("WorkID").toString();
                      fk_flow = dr.getValue("FK_Flow").toString();
                      
                      this.Pub1.append("\n<TD nowrap=\"nowrap\"><a href=\"javascript:WinOpen('"+ basePath +"WF/WFRpt.jsp?WorkID=" + workid + "&FK_Flow=" + fk_flow + "&FID=" + dr.getValue("FID") + "')\" >" + title + "</a></TD>");
                      this.Pub1.append(BaseModel.AddTDBegin());
                      if (!GenerWorkFlowAttr.StarterName.equals(GroupBy))
                		  this.Pub1.append(BaseModel.AddTD(dr.getValue(GenerWorkFlowAttr.StarterName).toString()));

                      this.Pub1.append(BaseModel.AddTDEnd());
                      this.Pub1.append(BaseModel.AddTREnd());
				}
              }
              
        }
        this.Pub1.append(BaseModel.AddTRSum());
        this.Pub1.append(BaseModel.AddTREnd());
        this.Pub1.append(BaseModel.AddTableEnd());
	}
	
	public void BindWap(){
		
		this.Pub1.append(BaseModel.AddFieldSet("<img src='"+basePath+"WF/Img/Home.gif' ><a href='"+basePath+"WF/Home.jsp' >Home</a>-<img src='"+basePath+"WF/Img/EmpWorks.gif' >" + "在途工作"));
		String sql = " SELECT a.WorkID FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B  WHERE A.WorkID=B.WorkID   AND B.FK_EMP='" + WebUser.getNo() + "' AND B.IsEnable=1";
        GenerWorkFlows gwfs = new GenerWorkFlows();
        gwfs.RetrieveInSQL(GenerWorkFlowAttr.WorkID, "(" + sql + ")");
       //int i = 0;
       //boolean is1 = true;
        this.Pub1.append(BaseModel.AddUL());
        for(GenerWorkFlow gwf : GenerWorkFlows.convertGenerWorkFlows(gwfs)){
        	 //i++;
             //this.Pub1.append(BaseModel.AddTR(is1));
        	 //this.Pub1.append(BaseModel.AddTDBegin("border=0"));
        	 this.Pub1.append(BaseModel.AddLi(gwf.getTitle() + gwf.getNodeName()));
             this.Pub1.append("<a href=\"javascript:Do('您确认吗？','"+basePath+"WF/MyFlowInfo" + Glo.getFromPageType() + ".jsp?DoType=UnSend&FID=" + gwf.getFID() + "&WorkID=" + gwf.getWorkID() + "&FK_Flow=" + gwf.getFK_Flow() + "');\" ><img src='"+basePath+"WF/Img/Btn/delete.gif' border=0 />撤消</a>");
             this.Pub1.append("<a href=\"javascript:WinOpen('"+basePath+"WF/WFRpt.jsp?WorkID=" + gwf.getWorkID() + "&FK_Flow=" + gwf.getFK_Flow() + "&FID=0')\" ><img src='"+basePath+"WF/Img/Btn/rpt.gif' border=0 />报告</a>");
        	
        }
        this.Pub1.append(BaseModel.AddULEnd());
        this.Pub1.append(BaseModel.AddFieldSetEnd());
	}
	
	
	public String getPageID() {
		return PageID;
	}
	public void setPageID(String pageID) {
		PageID = pageID;
	}
	public String getPageSmall() {
		return PageSmall;
	}
	public void setPageSmall(String pageSmall) {
		PageSmall = pageSmall;
	}
	public String getGroupBy() {
		return GroupBy;
	}
	public void setGroupBy(String groupBy) {
		GroupBy = groupBy;
	}
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public String getFK_Flow() {
		return FK_Flow;
	}
	public void setFK_Flow(String fK_Flow) {
		FK_Flow = fK_Flow;
	}
}
