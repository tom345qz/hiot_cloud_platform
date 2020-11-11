package com.huatec.hiot_cloud.core.utils;

import com.huatec.hiot_cloud.core.config.Constants;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

//import org.junit.Test;

/**
 * @ Created by liwenqiang  on 2017/6/16 0016 at 下午 2:15  for hiot
 * @ Description:  文件上传下载的工具类
 */
public class FileUtil {
    //工程路径
    private static final String CONTEXTPATH = "/hiot";
    //默认图片存放目录
    private static final String DEFAULT = "default";
    //无图片或图片获取失败的默认图片
    private static final String NO_PHOTO = "nophoto.jpg";
    //图片上传失败时的默认图片
    private static final String ERR_PHOTO = "nophoto.jpg";
    //用户头像的默认图片
    private static final String USER_PHOTO = "default_user.jpg";

    /**
     * @param ImgData   //图片文件
     * @param directory //存图片的目录，如user,template,device等等
     * @return java.lang.String  //存放到数据库的url
     * @Author: lwq
     * @Date: 2017/6/16 0016 下午 2:42
     * @Description: 方法“uploadImg”用于: 图片的上传
     */
    public static String uploadImg(MultipartFile ImgData, String directory) {
        if (ImgData != null && !ImgData.isEmpty()) {
            // 获取图片的文件名
            String fileName = ImgData.getOriginalFilename();
            /** 重新定义图片名称*/
            // 获取图片的扩展名
            String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
            //当前时间
            long currentTimeMillis = System.currentTimeMillis();
            // 新的图片文件名 = 获取时间戳+"."图片扩展名
            String newFileName = currentTimeMillis + "." + extensionName;
            //保存路径
            String imgPath = Constants.UPLOAD_PATH + File.separator + Constants.UPLOAD_PATH_IMG + File.separator + directory + File.separator;
            //保存
            try {
                //创建文件
                File file = new File(imgPath, newFileName);
                //file.setWritable(true,false);
                file.mkdirs();
                //保存文件
                ImgData.transferTo(file);
                //保存url存入数据库
                //String url = CONTEXTPATH + "/" + Constants.DOWNLOAD_PATH_IMG + "/" + directory + "/" + fileName;
                String url = "/" + Constants.DOWNLOAD_PATH_IMG + "/" + directory + "/" + newFileName;
                return url;
            } catch (IOException e) {
                e.printStackTrace();
                /** 上传失败时的默认图片*/
                //return null;
            }
        }
        /** 默认图片*/
        if (directory.equalsIgnoreCase(Constants.UPLOAD_PATH_USER)) {
            //用户头像默认图片
            return "/" + Constants.DOWNLOAD_PATH_IMG + "/" + DEFAULT + "/" + USER_PHOTO;
        } else {
            return "/" + Constants.DOWNLOAD_PATH_IMG + "/" + DEFAULT + "/" + NO_PHOTO;
        }
    }

    //    @Test
    public void test() {
        System.out.println(Constants.DOWNLOAD_PATH_IMG + "/" + DEFAULT + "/" + NO_PHOTO);
    }
}

