package com.example.dczs.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;


import com.example.dczs.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;


public class ToastUtils {
    private static Random r = new Random();
    private static String ssource = "0123456789";
    private static char[] src = ssource.toCharArray();
    public static String menuRefresh = "MENUREFRESH";
    public static String isMenuRefresh = "ISMENUREFRESH";
    public static String menuRefreshTwo = "MENUREFRESHTWO";
    public static String isMenuRefreshTwo = "ISMENUREFRESHTWO";
    public static String menuRefreshThree = "MENUREFRESHTHREE";
    public static String isMenuRefreshThree = "ISMENUREFRESHTHREE";
    public static String menuRefreshFour = "MENUREFRESHFOUR";
    public static String isMenuRefreshFour = "ISMENUREFRESHFOUR";
    public static String menuRefreshFive = "MENUREFRESHFIVE";
    public static String isMenuRefreshFive = "ISMENUREFRESHFIVE";

    //全局吐司提示框
    public static void showShort(String str){
        try{
            Toast.makeText(MyApplication.mContext,str, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    //保存登录信息
    public static void setAccountInformation(String name ,String paswd, boolean issave){
        // 创建SharedPreferences对象用于储存帐号和密码,并将其私有化
        SharedPreferences share = MyApplication.mContext.getSharedPreferences("Login",
                Context.MODE_PRIVATE);
        // 获取编辑器来存储数据到sharedpreferences中
        SharedPreferences.Editor editor = share.edit();
        editor.putString("Account", name);
        editor.putString("Password", paswd);
        editor.putBoolean("LoginBool", issave);
        // 将数据提交到sharedpreferences中
        editor.apply();
    }

    public static String getUserName(){
        // 获取sharedpreferences对象
        SharedPreferences share = MyApplication.mContext.getSharedPreferences("Login",
                Context.MODE_PRIVATE);
        String strAccount = share.getString("Account", "");
        return strAccount;
    }

    public static String getUserPassword(){
        // 获取sharedpreferences对象
        SharedPreferences share = MyApplication.mContext.getSharedPreferences("Login",
                Context.MODE_PRIVATE);
        String strPassword = share.getString("Password", "");
        return strPassword;
    }

    public static Boolean getWhetherCancellation(){
        // 获取sharedpreferences对象
        SharedPreferences share = MyApplication.mContext.getSharedPreferences("Login",
                Context.MODE_PRIVATE);
        boolean cancellation = share.getBoolean("LoginBool", false);
        return cancellation;
    }

    public static void getLogout(){
        // 获取sharedpreferences对象
        SharedPreferences share = Objects.requireNonNull(MyApplication.mContext).getSharedPreferences("Login",
                Context.MODE_PRIVATE);
        share.edit().putBoolean("LoginBool", false).apply();

    }

    //获取系统时间
    public static String getTime(String time){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(time);
        String sim = dateFormat.format(date);
        return sim;
    }



    //产生随机字符串
    private static String randString (int length)
    {
        char[] buf = new char[length];
        int rnd;
        for(int i = 0; i < length; i++) {
            rnd = Math.abs(r.nextInt()) % src.length;
            buf[i] = src[rnd];
        }
        return new String(buf);
    }
     //调用该方法，产生随机字符串,
     //参数i: 为字符串的长度
    public static String runVerifyCode(int i)
    {
        String VerifyCode = randString(i);
        return VerifyCode;
    }


    /**
     * @将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     * @param path 图片路径
     * @return
     */
    public static String imageToBase64(String path) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {

            InputStream in = new FileInputStream(path);

            data = new byte[in.available()];

            in.read(data);

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        Base64Encoder encoder = new Base64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data);

    }

    // base64字符串转化成图片
    public static boolean GenerateImage(String imgStr, String imgFilePath) throws Exception {
        if (imgStr == null) // 图像数据为空
            return false;
        Base64Encoder decoder = new Base64Encoder();

        // Base64解码,对字节数组字符串进行Base64解码并生成图片
        byte[] b = decoder.decode(imgStr);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {// 调整异常数据
                b[i] += 256;
            }
        }
        // 生成jpeg图片
        // String imgFilePath = "c://temp_kjbl_001_ab_010.jpg";//新生成的图片
        OutputStream out = new FileOutputStream(imgFilePath);
        out.write(b);
        out.flush();
        out.close();
        return true;
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     * @param imgStr 图片字符串
     * @return byte[]
     */
    public static byte[] getStrToBytes(String imgStr) {
        if (imgStr == null) // 图像数据为空
            return null;
        Base64Encoder decoder = new Base64Encoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decode(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            return bytes;
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    //将字符串转换成Bitmap类型
    public static Bitmap stringtoBitmap(String string){
        Bitmap bitmap=null;
        try {
            byte[]bitmapArray;
            bitmapArray= Base64.decode(string, Base64.DEFAULT);
            bitmap=BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    //将Bitmap转换成字符串
    public static String bitmaptoString(Bitmap bitmap){
        String string=null;
        ByteArrayOutputStream bStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bStream);
        byte[]bytes=bStream.toByteArray();
        string=Base64.encodeToString(bytes,Base64.DEFAULT);
        return string;
    }
}
