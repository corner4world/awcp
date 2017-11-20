package cn.org.awcp.wechat.domain.message;

/**
 * 
 * @author yqtao
 *
 */
public class Article {
	private String title;		// 图文消息名称
    private String description;	// 图文消息描述
    private String picUrl;		// 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
    private String url;			// 点击图文消息跳转链接
    
	public Article(String title, String description, String picUrl, String url) {
		super();
		this.title = title;
		this.description = description;
		this.picUrl = picUrl;
		this.url = url;
	}

	public Article() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
}
