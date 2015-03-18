package com.qianfeng.gl4study.snssdk.utils;

import android.content.Context;
import android.os.Environment;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/10
 * Email:pdsfgl@live.com
 */
public class FileCache {
	private static FileCache ourInstance;

	private File fileCacheDir;
 static FileCache createInstance(Context context){
		if (ourInstance == null) {
			ourInstance = new FileCache(context);
		}
		return ourInstance;
	}
	public static FileCache getInstance() {
		if (ourInstance == null) {
			throw new IllegalStateException("FileCache must invoke createInstance before getInstance");

		}
		return ourInstance;
	}

	//Android上下文Context
	private Context context;

	/**
	 * 在私有构造方法中指定参数，那么该构造方法必须在类中初始化那么必须在静态方法中调用
	 */
	private FileCache(Context context) {
		this.context = context;
		if(context!=null){
			String state = Environment.getExternalStorageState();
			if(Environment.MEDIA_MOUNTED.equals(state)){
				//SD卡存在
				fileCacheDir = context.getExternalCacheDir();
			}else {
				fileCacheDir = context.getCacheDir();
			}
		}else {
			//上下文不存在
			String state = Environment.getExternalStorageState();
			if(Environment.MEDIA_MOUNTED.equals(state)){
				File directory = Environment.getExternalStorageDirectory();
				fileCacheDir = new File(directory,".snssdk");
			}else {
				throw new IllegalArgumentException("FileCache must set Context or devace has a SDCard");
			}

		}

		if(fileCacheDir!=null){
			if(!fileCacheDir.exists()){
				fileCacheDir.mkdirs();
			}
		}else {
			throw new IllegalArgumentException("FileCache can't get a directory");
		}

	}

	/**
	 * 将下载的数据保存到文件
	 * @param url
	 */
	public void putContent(String url,byte[] data){
		if(url!=null&&data!=null){
			String fileName = mapUrlToFile(url);
			File targetFile = new File(fileCacheDir, fileName);
			boolean bok = true;
			if(!targetFile.exists()){
				try {
					bok = targetFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bok){
				FileOutputStream fout = null;
				try {
					fout = new FileOutputStream(targetFile);
					fout.write(data);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 从本地存储获取url代表的数据
	 * @param url
	 * @return      若存在，返回数据，否则返回null
	 */
	public byte[] getContent(String url){
		byte[] ret = null;
		if(url!=null){
			String fileName = mapUrlToFile(url);
			if(fileName.length()>0){
				File targetFile = new File(fileCacheDir,fileName);
				if (targetFile.exists()){
					//读取文件到字节数组
					FileInputStream fin = null;
					try {
						fin = new FileInputStream(targetFile);
						ret = StreamUtil.readStream(fin);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}finally {
						StreamUtil.close(fin);
						fin = null;
					}
				}
			}
		}
		return ret;
	}

	/**
	 * 将Url转变为文件名
	 * @param url
	 * @return
	 */
	private static String mapUrlToFile(String url){
		String ret = null;

		if(url!=null){
			//MD5将内容转变为不可逆的，唯一的字节数组
			byte[] md5 = md5(url.getBytes());

			ret = hex(md5);
		}
		return ret;
	}

	/**
	 * MD5算法，将输入的字节数组转换成唯一的一个字节数组
	 * @param data
	 * @return
	 */
	private static byte[] md5(byte[] data){
		byte[] ret = null;
		if(data!=null&&data.length>0){
			try {
				MessageDigest digest = MessageDigest.getInstance("MD5");
				ret = digest.digest(data);//将data进行消息摘要，生成一个特定的字节数组，当中的数值不可逆转
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	/**
	 * 将字节数组中的每一个字节转换成0-9，A-F这些可以显示的十六进制字符
	 * @param data
	 * @return
	 */
	private static String hex(byte[] data){
		StringBuilder sb = new StringBuilder();
		if(data!=null&&data.length>0){
			for (byte b:data){
				int h,l;
				h = (b>>4)&0x0f;
				l = b & 0x0f;
				sb.append(Integer.toHexString(h));
				sb.append(Integer.toHexString(l));
			}
		}
		return sb.toString();
	}
}
