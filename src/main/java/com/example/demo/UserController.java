package com.example.demo;


import com.example.demo.model.User;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;


@RestController
@RequestMapping("/hello")
public class UserController {

    @Autowired
    private UserRespository userRespository;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String hello() {
        return "Hello Spring-Boot";
    }

    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/findUserByName")
    public List<User> findUserByName(@RequestParam String name) {
        logger.info(name);

        List<User> byName = userRespository.findByName(name);
        return byName;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/saveUser")
    public ResponseObject saveUser(@RequestBody User user) {
        logger.info(user.toString());
        ResponseObject responseObject = new ResponseObject();
        responseObject.setCode("200");
        responseObject.setData(userRespository.save(user));
        return responseObject;

    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public long getCount() {
        logger.info("输出info");
        logger.error("输出error");

        return userRespository.count();
    }

    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public List<User> queryAll() {
        return userRespository.findAll();

    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseObject upload(@RequestParam("file") MultipartFile file) {
        ResponseObject responseObject = new ResponseObject();
        if (!file.isEmpty()) {
            try {
                // 这里只是简单例子，文件直接输出到项目路径下。
                // 实际项目中，文件需要输出到指定位置，需要在增加代码处理。
                // 还有关于文件格式限制、文件大小限制，详见：中配置。
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File("/usr/tmp/projects/files/" + file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                responseObject.setCode(500 + "");
                responseObject.setMessage("上传失败," + e.getMessage());

                return responseObject;
            } catch (IOException e) {
                e.printStackTrace();
                responseObject.setCode(500 + "");
                responseObject.setMessage("上传失败," + e.getMessage());
                return responseObject;
            }
            responseObject.setCode(200 + "");
            responseObject.setMessage("上传成功");
            return responseObject;
        } else {
            responseObject.setCode(500 + "");
            responseObject.setMessage("上传失败，因为文件是空的.");
            return responseObject;
        }
    }


    @RequestMapping(value = "/patch",method = RequestMethod.GET)
    public ResponseObject patchCheck(@RequestParam String version){
        ResponseObject responseObject = new ResponseObject();
        logger.info(version+"  version");
        String android_patch = System.getenv("ANDROID_PATCH");
        if (android_patch.equals("true")) {
            responseObject.setCode("0");
            responseObject.setMessage("有新的patch");

        }else{
            responseObject.setCode("1");
            responseObject.setMessage("无新的patch");
        }
        return responseObject;
    }

    @RequestMapping("/downloadFileAction")
    public void downloadFileAction(HttpServletRequest request, HttpServletResponse response) {


        // Get your file stream from wherever.
        String fullPath = "/usr/tmp/projects/files/patch_signed_7zip.apk";
        File downloadFile = new File(fullPath);

        ServletContext context = request.getServletContext();

        // get MIME type of the file
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
            System.out.println("context getMimeType is null");
        }
        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // Copy the stream to the response's output stream.
        try {
            InputStream myStream = new FileInputStream(fullPath);
            IOUtils.copy(myStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
