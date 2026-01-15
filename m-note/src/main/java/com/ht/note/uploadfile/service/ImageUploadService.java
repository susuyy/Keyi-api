package com.ht.note.uploadfile.service;


import com.ht.note.config.LinuxFileConfig;
import com.ht.note.exception.CheckException;
import com.ht.note.webauthconfig.result.ResultTypeEnum;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;

public class ImageUploadService {

    /**
     * 图片保存到服务器
     * @param imgFilePath
     * @param base64
     * @return
     * @throws Exception
     */
    public static Boolean saveImageToServer(String imgFilePath, String base64) throws Exception {
        String imgData = base64.split(",")[1];
        Decoder decoder = Base64.getDecoder();
        OutputStream out = null;
        try {
            out = new FileOutputStream(imgFilePath);
            byte[] b = decoder.decode(imgData);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            Runtime.getRuntime().exec("chmod 777 -R " + imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 接受base64码的图片数据,获取名字包含后缀
     */
    public static String getImageOrName(String base64){
        String orName = base64.split(",")[0].split("/")[1].split(";")[0];
        String name= System.currentTimeMillis()+"."+orName;
        return name;
    }


    /**
     * 文件保存服务器
     * @param imgFilePath
     * @param multipartFile
     * @return
     * @throws Exception
     */
    public static Boolean saveFileDataToServer(String imgFilePath, MultipartFile multipartFile) throws Exception {
        OutputStream out = null;
        try {
            out = new FileOutputStream(imgFilePath);
            byte[] b = multipartFile.getBytes();
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            Runtime.getRuntime().exec("chmod 777 -R " + imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static List<String> uploadFiles(MultipartFile[] files) throws Exception {
        for (MultipartFile multipartFile : files) {
            String fileName = multipartFile.getOriginalFilename();
            //KB单位
            long fileSize = multipartFile.getSize();
            if (fileSize >= 600000) {
                long sizeOfKb = 600000;
                throw new CheckException(ResultTypeEnum.BIND_EXCEPTION.getCode(),"上传的文件为【" + fileName + "】，大小为【" + fileSize + "】KB,不能超过【" + sizeOfKb + "】KB,请重新上传！");
            }
        }

        List<String> urlList= new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            String fileName = multipartFile.getOriginalFilename();
            //文件名称加随机数处理，避免重名
            String newFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + System.currentTimeMillis() % 10000 + fileName.substring(fileName.lastIndexOf("."));

            //获取Excel表单
            ImageUploadService.saveFileDataToServer(LinuxFileConfig.serverLocalPath+newFileName, multipartFile);

            urlList.add(LinuxFileConfig.fileResourceDomain + newFileName);
        }

        return urlList;
    }
}
