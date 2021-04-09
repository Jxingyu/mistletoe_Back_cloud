package com.xy.common;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


public class MyImgLoad {
    private static String imgurl = "/images/";

    public static void load(HttpServletRequest request, MultipartFile multipartFile) {
        FileItemFactory factory = new DiskFileItemFactory();
        // 文件上传处理器
        ServletFileUpload upload = new ServletFileUpload(factory);

        // 解析请求信息
        List<FileItem> items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        // 对请求信息进行判断
        Iterator iter = items.iterator();
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            // 信息为普通的格式
            if (item.isFormField()) {
                String fieldName = item.getFieldName();//得到id值
                String value = null;//得到value值
                try {
                    value = new String(item.getString().getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                request.setAttribute(fieldName, value);
            }
            // 信息为文件格式
            else {
                //c:/caadfa/fsdad.jpg
                String fileName = item.getName();//获得上传图片的名称
                int index = fileName.lastIndexOf("\\");

                fileName = fileName.substring(index + 1);
//                fileName = UUID.randomUUID().toString() + "--" + fileName;
                /*Random r = new Random(1);
                int ran = r.nextInt(999);*/
//                fileName = new StringBuilder().append(ran).append("--").append(fileName).toString();
                fileName = new StringBuilder().append("--").append(fileName).toString();


                String basePath = request.getSession().getServletContext().getRealPath(imgurl);
                request.setAttribute("imgHref", imgurl + fileName);
                System.out.println("打印当前位置" + basePath);//打印当前位置
                request.setAttribute("wangEditorUrl", imgurl + fileName);
                File file = new File(basePath, fileName);
                try {
                    item.write(file);
                    saveFile(file, fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void saveFile(File file, String fileName) {
        try {
            InputStream inputStream = new FileInputStream(file);
//            String path = "D:\\Project\\fzdz-7.0-ui-Web\\web\\img\\images";
            String path = "D:\\JavaProject\\xingyu\\springcloud\\mistletoe_web_2.0\\static\\layui\\images";
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            System.out.println("path" + path);
            File createFile = new File(tempFile, fileName);
            if (!createFile.exists()) {
                createFile.createNewFile();
            }
            OutputStream outputStream = new FileOutputStream(createFile);
            int result;
            while ((result = inputStream.read()) != -1) {
                outputStream.write(result);
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String UUIDFileName() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 单文件上传
     *
     * @param request
     * @param file
     */
    public static CommonResult loadOne(HttpServletRequest request, MultipartFile file) {

        String fileName = null;
        String path = "D:\\JavaProject\\xingyu\\springcloud\\mistletoe_web_2.0\\static\\layui\\images";
        try {
            InputStream inputStream = file.getInputStream();
            String name = file.getOriginalFilename();
            String extendsName = name.substring(name.lastIndexOf(".") + 1);
            if (extendsName.equalsIgnoreCase("jpg") || extendsName.equalsIgnoreCase("png") || extendsName.equalsIgnoreCase("bmp")
            || extendsName.equalsIgnoreCase("jpeg")) {
                fileName = UUIDFileName() + "." + extendsName;
                request.setAttribute("imgHref", fileName);
                String storeFile = path + "/" + fileName;
                File filePath = new File(path);
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                //写文件
                OutputStream outputStream = new FileOutputStream(storeFile);
                byte[] b = new byte[1024];
                int len = -1;
                while ((len = inputStream.read(b)) != -1) {
                    outputStream.write(b, 0, len);
                }
                inputStream.close();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResult.success(fileName, "code:200");
    }
}