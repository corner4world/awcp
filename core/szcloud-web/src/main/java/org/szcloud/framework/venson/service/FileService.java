package org.szcloud.framework.venson.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.szcloud.framework.formdesigner.core.domain.Attachment;
import org.szcloud.framework.venson.util.PlatfromProp;

/**
 * 文件上传通用接口（支持mongodb,folder）
 * 
 * @author Venson
 *
 */
public interface FileService {

	/** 文件存储类型为：Mongodb */
	int MONGODB = 0;
	/** 文件存储类型为：文件夹 */
	int FOLDER = 1;
	/** 文件存储类型为：FTP */
	int FTP = 2;
	/** 文件存储类型默认为：Mongodb */
	int DEFAULT = Integer.parseInt(PlatfromProp.getValue("default_upload_type"));

	String separator = "/";

	/**
	 * 
	 * @param input
	 * @param fileType
	 * @param fileName
	 * @param type
	 *            保存类型(1:mongodb2:文件夹3:ftp)
	 * @param isIndex
	 *            是否需要建立文件索引
	 * @return
	 * @throws IOException
	 */
	Serializable save(InputStream input, String fileType, String fileName, int type, boolean isIndex);

	/**
	 * 
	 * @param input
	 * @param fileType
	 * @param fileName
	 * @param type
	 *            保存类型(1:mongodb2:文件夹3:ftp)
	 * @return
	 * @throws IOException
	 */
	Serializable save(InputStream input, String fileType, String fileName, int type);

	/**
	 * 
	 * @param input
	 * @param fileType
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	Serializable save(InputStream input, String fileType, String fileName);

	/**
	 * 获取文件
	 * 
	 * @param id
	 * @return
	 */
	Attachment get(Serializable id);

	/**
	 * 获取文件
	 * 
	 * @param id
	 * @return
	 */
	InputStream getInputStream(Serializable id);

	/**
	 * 获取文件
	 * 
	 * @param att
	 *            附件实例
	 * @return
	 */
	InputStream getInputStream(Attachment att);

	/**
	 * 删除文件
	 * 
	 * @param isCheck
	 *            是否检查删除权限（上传人与删除人一致则可删）
	 * @param id
	 * @return
	 */
	boolean delete(boolean isCheck, Serializable... id);

	/**
	 * 删除文件
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Serializable... id);

	/**
	 * 下载文件
	 * 
	 * @param id
	 * @param out
	 * @return
	 */
	boolean download(Serializable id, OutputStream out);

	/**
	 * 下载文件
	 * 
	 * @param att
	 *            附件实例
	 * @param out
	 * @return
	 */
	boolean download(Attachment att, OutputStream out);

	/**
	 * 
	 * @param in
	 * @param out
	 * @return
	 */
	boolean copy(InputStream in, OutputStream out);

	/**
	 * 批量下载文件
	 * 
	 * @param ids
	 * @param out
	 * @return
	 */
	boolean batchDownload(Serializable[] ids, OutputStream out);

	/**
	 * 建立文档搜索索引
	 * 
	 * @param input
	 *            文件流
	 * @param fileName
	 *            文件名称
	 * @param fileType
	 *            文件类型
	 */
	public void indexFilesSolr(InputStream input, String fileName, String fileType);

}
