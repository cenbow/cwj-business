package com.mm.dev.fastdfs;
import java.io.File;

import org.apache.commons.io.FileUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

/**
 * <strong>类概要： FastDFS Java客户端工具类</strong> <br>
 * <strong>创建时间： 2016-9-26 上午10:26:48</strong> <br>
 */
public class FileManager implements FileManagerConfig {

    private static final long serialVersionUID = 1L;
    private static TrackerClient trackerClient;
    private static TrackerServer trackerServer;
    private static StorageServer storageServer;
    private static StorageClient storageClient;
    static {
        try {
	        	ClassPathResource cpr = new ClassPathResource("client.conf");  
	            ClientGlobal.init(cpr.getClassLoader().getResource("client.conf").getPath());  
        		trackerClient = new TrackerClient();
        		trackerServer = trackerClient.getConnection();
        		storageClient = new StorageClient(trackerServer, storageServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <strong>方法概要： 文件上传</strong> <br>
     * <strong>创建时间： 2016-9-26 上午10:26:11</strong> <br>
     * 
     * @param FastDFSFile
     *            file
     * @return fileAbsolutePath
     * @author Wang Liang
     */
    public static String upload(MultipartFile file,String fileSuffix) {
        String[] uploadResults = null;
        try {
        	FastDFSFile dfsFile = null;
    		dfsFile = new FastDFSFile(file.getBytes(),fileSuffix);
    		NameValuePair[] meta_list = new NameValuePair[4];
    		meta_list[0] = new NameValuePair("fileName", file.getOriginalFilename());
    		meta_list[1] = new NameValuePair("fileLength", String.valueOf(file.getSize()));
    		meta_list[2] = new NameValuePair("fileExt", fileSuffix);
    		meta_list[3] = new NameValuePair("fileAuthor", "ivplay");
            uploadResults = storageClient.upload_file(dfsFile.getContent(),dfsFile.getExt(), meta_list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];
        String fileAbsolutePath = PROTOCOL
                + TRACKER_NGNIX_ADDR
                //+ trackerServer.getInetSocketAddress().getHostName()
                + TRACKER_NGNIX_PORT 
                + SEPARATOR + groupName
                + SEPARATOR + remoteFileName;
        return fileAbsolutePath;
    }
    
    public static String upload(File file,String fileSuffix) {
        String[] uploadResults = null;
        try {
        	FastDFSFile dfsFile = null;
    		dfsFile = new FastDFSFile(FileUtils.readFileToByteArray(file),fileSuffix);
     	    NameValuePair[] meta_list = new NameValuePair[4];
     	    meta_list[0] = new NameValuePair("fileName", file.getName());
     	    meta_list[1] = new NameValuePair("fileLength", String.valueOf(file.length()));
     	    meta_list[2] = new NameValuePair("fileExt", fileSuffix);
     	    meta_list[3] = new NameValuePair("fileAuthor", "ivplay");
            uploadResults = storageClient.upload_file(dfsFile.getContent(),dfsFile.getExt(), meta_list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];
        String fileAbsolutePath = PROTOCOL
                + TRACKER_NGNIX_ADDR
                //+ trackerServer.getInetSocketAddress().getHostName()
                + TRACKER_NGNIX_PORT 
                + SEPARATOR + groupName
                + SEPARATOR + remoteFileName;
        return fileAbsolutePath;
    }
}