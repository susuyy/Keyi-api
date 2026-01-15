package com.ht.note.uploadfile.controller;


import com.ht.note.config.LinuxFileConfig;
import com.ht.note.exception.CheckException;
import com.ht.note.uploadfile.entity.FileData;
import com.ht.note.uploadfile.entity.RetFileUrl;
import com.ht.note.uploadfile.service.ImageUploadService;
import com.ht.note.webauthconfig.result.ResultTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 记录手机号验证码 前端控制器
 * </p>
 *
 * @author suyangyu
 * @since 2020-06-16
 */
@Api(value = "文件相关接口", tags = {"文件相关接口"})
@RestController
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/file")
public class FileController {



    /**
     * base64上传文件
     * @param fileData
     * @return
     */
    @ApiOperation("base64上传文件")
    @PostMapping("/uploadFileBaseCode")
    public RetFileUrl uploadImage(@RequestBody FileData fileData){
        String allName = ImageUploadService.getImageOrName(fileData.getBase64FileStr());
        try {
            ImageUploadService.saveImageToServer(LinuxFileConfig.serverLocalPath+allName, fileData.getBase64FileStr());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CheckException(ResultTypeEnum.UPLOAD_FILE_ERROR);
        }
        RetFileUrl retFileUrl = new RetFileUrl();
        retFileUrl.setUrl(LinuxFileConfig.fileResourceDomain + allName);
        return retFileUrl;
    }



    @PostMapping(value = "/uploadFileFormData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "文件上传(支持批量)", notes = "文件上传(支持批量)")
    public List<String> uploadFiles(@RequestParam("files") MultipartFile[] files) throws Exception {
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

