package cn.org.awcp.venson.dingding.helper;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpHelper {
	/**
	 * 日志对象
	 */
	protected static final Log logger = LogFactory.getLog(HttpHelper.class);

	public static JSONObject httpGet(String url) {
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpGet.setConfig(requestConfig);

		try {
			response = httpClient.execute(httpGet, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {

				logger.debug(
						"request url failed, http code=" + response.getStatusLine().getStatusCode() + ", url=" + url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");

				JSONObject result = JSON.parseObject(resultStr);
				if (result.getInteger("errcode") == 0) {
					result.remove("errcode");
					result.remove("errmsg");
					return result;
				} else {
					logger.debug("request url=" + url + ",return value=");
					logger.debug(resultStr);
					int errCode = result.getInteger("errcode");
					String errMsg = result.getString("errmsg");
					logger.debug("errCode：" + errCode + "，errMsg：" + errMsg);
				}
			}
		} catch (IOException e) {
			logger.debug("request url=" + url + ", exception, msg=" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return null;
	}

	public static JSONObject httpPost(String url, Object data) {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpPost.setConfig(requestConfig);
		httpPost.addHeader("Content-Type", "application/json");

		try {
			StringEntity requestEntity = new StringEntity(JSON.toJSONString(data), "utf-8");
			httpPost.setEntity(requestEntity);

			response = httpClient.execute(httpPost, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {

				logger.debug(
						"request url failed, http code=" + response.getStatusLine().getStatusCode() + ", url=" + url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");

				JSONObject result = JSON.parseObject(resultStr);
				if (result.getInteger("errcode") == 0) {
					result.remove("errcode");
					result.remove("errmsg");
					return result;
				} else {
					logger.debug("request url=" + url + ",return value=");
					logger.debug(resultStr);
					int errCode = result.getInteger("errcode");
					String errMsg = result.getString("errmsg");
					logger.debug("errCode：" + errCode + "，errMsg：" + errMsg);
				}
			}
		} catch (IOException e) {
			logger.debug("request url=" + url + ", exception, msg=" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return null;
	}

	public static JSONObject uploadMedia(String url, File file) {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpPost.setConfig(requestConfig);

		HttpEntity requestEntity = MultipartEntityBuilder.create()
				.addPart("media", new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, file.getName())).build();
		httpPost.setEntity(requestEntity);

		try {
			response = httpClient.execute(httpPost, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {

				logger.debug(
						"request url failed, http code=" + response.getStatusLine().getStatusCode() + ", url=" + url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");

				JSONObject result = JSON.parseObject(resultStr);
				if (result.getInteger("errcode") == 0) {
					// 成功
					result.remove("errcode");
					result.remove("errmsg");
					return result;
				} else {
					logger.debug("request url=" + url + ",return value=");
					logger.debug(resultStr);
					int errCode = result.getInteger("errcode");
					String errMsg = result.getString("errmsg");
					logger.debug("errCode：" + errCode + "，errMsg：" + errMsg);
				}
			}
		} catch (IOException e) {
			logger.debug("request url=" + url + ", exception, msg=" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return null;
	}

	public static JSONObject downloadMedia(String url, String fileDir) {
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpGet.setConfig(requestConfig);

		try {
			HttpContext localContext = new BasicHttpContext();

			response = httpClient.execute(httpGet, localContext);

			RedirectLocations locations = (RedirectLocations) localContext
					.getAttribute(HttpClientContext.REDIRECT_LOCATIONS);
			if (locations != null) {
				URI downloadUrl = locations.getAll().get(0);
				String filename = downloadUrl.toURL().getFile();
				logger.debug("downloadUrl=" + downloadUrl);
				File downloadFile = new File(fileDir + File.separator + filename);
				FileUtils.writeByteArrayToFile(downloadFile, EntityUtils.toByteArray(response.getEntity()));
				JSONObject obj = new JSONObject();
				obj.put("downloadFilePath", downloadFile.getAbsolutePath());
				obj.put("httpcode", response.getStatusLine().getStatusCode());

				return obj;
			} else {
				if (response.getStatusLine().getStatusCode() != 200) {

					logger.debug("request url failed, http code=" + response.getStatusLine().getStatusCode() + ", url="
							+ url);
					return null;
				}
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String resultStr = EntityUtils.toString(entity, "utf-8");

					JSONObject result = JSON.parseObject(resultStr);
					if (result.getInteger("errcode") == 0) {
						// 成功
						result.remove("errcode");
						result.remove("errmsg");
						return result;
					} else {
						logger.debug("request url=" + url + ",return value=");
						logger.debug(resultStr);
						int errCode = result.getInteger("errcode");
						String errMsg = result.getString("errmsg");
						logger.debug("errCode：" + errCode + "，errMsg：" + errMsg);
					}
				}
			}
		} catch (IOException e) {
			logger.debug("request url=" + url + ", exception, msg=" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return null;
	}
}