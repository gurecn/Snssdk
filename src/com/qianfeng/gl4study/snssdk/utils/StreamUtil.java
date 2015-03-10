package com.qianfeng.gl4study.snssdk.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/10
 * Email:pdsfgl@live.com
 */
public class StreamUtil {
	/*
	工具类不能被继承和实例化
	 */
	private StreamUtil(){}

	/**
	 * 关闭输入流，输出流
	 * @param stream
	 */
	public static void close(Object stream) {
		if(stream!=null){
			if(stream instanceof InputStream){
				try {
					((InputStream) stream).close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(stream instanceof OutputStream){
				try {
					((OutputStream) stream).close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	public static  long readStream(InputStream in,OutputStream out){
		long ret = 0;
		if(in!=null&&out!=null){
			byte[] buf = new byte[128];
			int len;
			try {
				while (true){
						len = in.read(buf);

					if(len==-1){
						break;
					}
					ret +=len;
					out.write(buf,0,len);
				}
			}catch (IOException e) {
			e.printStackTrace();
		}
			buf = null;
		}
		return ret;
	}

	/**
	 *
	 * @param inputStream
	 * @return
	 */
	public  static  byte[] readStream(InputStream inputStream){
		byte[] ret = null;
		if(inputStream!=null){
			ByteArrayOutputStream bout = null;
			bout = new ByteArrayOutputStream();
			readStream(inputStream,bout);
			ret = bout.toByteArray();
			close(bout);
			bout = null;
		}
		return ret;
	}
}
