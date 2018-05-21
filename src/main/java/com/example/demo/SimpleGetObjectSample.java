/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.example.demo;

import java.io.*;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This sample demonstrates how to upload/download an object to/from 
 * Aliyun OSS using the OSS SDK for Java.
 */
public class SimpleGetObjectSample {
   static Logger LOGGER = LogManager.getLogger("123456");
    //oss key
    private static String accessKeyID = "LTAIJtKI2iJMbJzo";

    //oss keySecret
    private static String accessKeySecret = "RmdAAqrGnVev9ZkeVealcx7yBNXDC4";

    //上传地址
    private static String endpoint = "oss-cn-hangzhou.aliyuncs.com";

    //ossbucket
    private static String bucketName = "timetalk-dev";


    public static void main(String[] args) throws IOException {


        /*
         * Constructs a client instance with your account for accessing OSS
         */
         OSSClient  oss = new OSSClient(endpoint, accessKeyID, accessKeySecret);
       /*
        try {
            
            *//**
             * Note that there are two ways of uploading an object to your bucket, the one 
             * by specifying an input stream as content source, the other by specifying a file.
             *//*
            
            *//*
             * Upload an object to your bucket from an input stream
             *//*
            System.out.println("Uploading a new object to OSS from an input stream\n");
            String content = "Thank you for using Aliyun Object Storage Service";
            client.putObject(bucketName, accessKeyID, new ByteArrayInputStream(content.getBytes()));
            
            *//*
             * Upload an object to your bucket from a file
             *//*
            System.out.println("Uploading a new object to OSS from a file\n");
            client.putObject(new PutObjectRequest(bucketName, accessKeyID, createSampleFile()));
            
            *//*
             * Download an object from your bucket
             *//*
            System.out.println("Downloading an object");
            OSSObject object = client.getObject(new GetObjectRequest(bucketName, accessKeyID));
            System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
            displayTextInputStream(object.getObjectContent());
            
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } finally {
            *//*
             * Do not forget to shut down the client finally to release all allocated resources.
             *//*
            client.shutdown();
        }*/

        String ret = null;
        File file = new File("/Users/cuieneydemacbook/Downloads/123.log.gz");
        FileInputStream inputStream = new FileInputStream(file);
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            String fileName = "123.log";
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            PutObjectResult putResult = oss.putObject(bucketName, fileName, inputStream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            LOGGER.error("OSS上传文件出现异常", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                LOGGER.error("OSS上传文件關閉流出現異常", e);
            }
        }
        LOGGER.error("OSS上传文件出现异常", ret);
    }
    
    private static File createSampleFile() throws IOException {
        File file = new File("/Users/cuieneydemacbook/Downloads/123.log.gz");
//        file.deleteOnExit();

//        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
//        writer.write("abcdefghijklmnopqrstuvwxyz\n");
//        writer.write("0123456789011234567890\n");
//        writer.close();

        return file;
    }

    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("\t" + line);
        }
        System.out.println();

        reader.close();
    }
    
}
