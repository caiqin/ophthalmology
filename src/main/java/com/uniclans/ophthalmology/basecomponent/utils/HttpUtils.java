package com.uniclans.ophthalmology.basecomponent.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP协议访问
 *
 */
public class HttpUtils {
	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	public static String getRealIp() throws SocketException {
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
		String netip = null;// 外网IP

		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		boolean finded = false;// 是否找到外网IP
		while (netInterfaces.hasMoreElements() && !finded) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
					netip = ip.getHostAddress();
					finded = true;
					break;
				} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
						&& ip.getHostAddress().indexOf(":") == -1) {// 内网IP
					localip = ip.getHostAddress();
				}
			}
		}

		if (netip != null && !"".equals(netip)) {
			return netip;
		} else {
			return localip;
		}
	}
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 微信接口基础post请求
     * @param args[接口path，xmlbody参数]
     * @return json字符串
     * @throws ClientProtocolException
     * @throws IOException
     */
     public static String sendGet(String path){
         try{
             HttpGet post = new HttpGet(path);
            // post.set
             return EntityUtils.toString(new DefaultHttpClient().execute(post).getEntity(),"utf-8");
          }catch(Exception e){
               return String.format("{\"exception\":\"%s\"}", e.getMessage());
         }
     }
	 /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) throws Exception{
        OutputStreamWriter  out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            // 设置通用的请求属性
          //  conn.setRequestProperty("accept", "*/*");
          //  conn.setRequestProperty("connection", "Keep-Alive");
           // conn.setRequestProperty("user-agent",
           //         "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=UTF-8"); 
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream());
            // 发送请求参数
            out.write(param);
           // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            throw e;
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        logger.info("请求发送消息成功，返回："+result);
        return result;
    }    
    
	/**
	 * GET方式访问网页，并返回内容
	 * @param getURL 网页链接
	 * @param encode 网页编码
	 * @return
	 */
	public static String getContent(String getURL, String encode){
        HttpURLConnection connection =null;
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        try {
        	URL getUrl = new URL(getURL);
        // 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
        connection = (HttpURLConnection) getUrl
                .openConnection();
        // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
        // 服务器
        connection.connect();
        // 取得输入流，并使用Reader读取
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),encode));//设置编码,否则中文乱码
        String lines;
        while ((lines = reader.readLine()) != null){
        	sb.append(lines);
        }
       
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 断开连接
			connection.disconnect();
		}
        return sb.toString();
	}
	
	/** 
     * 从网络Url中下载文件 
     * @param urlStr 
     * @param fileName 
     * @param savePath 
     * @throws IOException 
     */  
    public static void  downLoadFromUrl(String urlStr,String savePath) throws IOException{ 
    	//将接口返回的对账单下载地址传入urlStr
    	//String urlStr = "http://dwbillcenter.alipay.com/downloadBillFile.resource?bizType=X&userId=X&fileType=X&bizDates=X&downloadFileName=X&fileId=X";
    	//指定希望保存的文件路径
    	
    	URL url = null;
    	HttpURLConnection httpUrlConnection = null;
    	InputStream fis = null;
    	FileOutputStream fos = null;
    	try {
    	    url = new URL(urlStr);
    	    httpUrlConnection = (HttpURLConnection) url.openConnection();
    	    httpUrlConnection.setConnectTimeout(5 * 1000);
    	    httpUrlConnection.setDoInput(true);
    	    httpUrlConnection.setDoOutput(true);
    	    httpUrlConnection.setUseCaches(false);
    	    httpUrlConnection.setRequestMethod("GET");
    	    httpUrlConnection.setRequestProperty("Charsert", "UTF-8");
    	    httpUrlConnection.connect();
    	    fis = httpUrlConnection.getInputStream();
    	    byte[] temp = new byte[1024];
    	    int b;
    	    fos = new FileOutputStream(new File(savePath));
    	    while ((b = fis.read(temp)) != -1) {
    	        fos.write(temp, 0, b);
    	        fos.flush();
    	    }
    	} catch (MalformedURLException e) {
    	    e.printStackTrace();
    	} catch (IOException e) {
    	    e.printStackTrace();
    	} finally {
    	    try {
    	        if(fis!=null) fis.close();
    	        if(fos!=null) fos.close();
    	        if(httpUrlConnection!=null) httpUrlConnection.disconnect();
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    	}
    }
    
    public static void main(String[] args){
    	try {
			downLoadFromUrl("http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=zRdeAXtPk_pXaQzxjNYUDNyLJXwG0JB8l5lkZYbp61utiiLRoUzTXO6959SCL3KIG_uuwA-GPkdrpZ4C4MiUOvwbqL7Y_n-8QR6mbU6NQXhmb3zviL83g8I85P09XS9fCAIaABAYWE&media_id=u43Te0xoRDfxyEyZLDrKOBjVB8gMDOgzrzAapDS594t7XKLp827-SzOL8ZpDvEAG", "/Users/zhoufufa/Desktop/tc/zf/aa.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
