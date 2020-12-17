package com.wjh.manga_crawler.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.*;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

public class s3connector {
    private static EnvironmentVariableCredentialsProvider credentialsProvider;
    private static String AWS_ACCESS_KEY;
    private static String AWS_SECRET_KEY;
    private static final String bucket_name;

    private static AmazonS3 s3;
    private static TransferManager tx;

    public static void init_with_key() throws Exception {
        AWSCredentials credentials = null;
        //credentialsProvider = new EnvironmentVariableCredentialsProvider();
        //credentials = credentialsProvider.getCredentials();
        credentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        s3 = new AmazonS3Client(credentials);
        Region usEast2 = Region.getRegion(Regions.US_EAST_2);
        s3.setRegion(usEast2);
        tx = TransferManagerBuilder.standard().withS3Client(s3).build();
    }

    public static void listObjects() {
        System.out.format("Objects in S3 bucket %s:\n", bucket_name);
        ListObjectsV2Result result = s3.listObjectsV2(bucket_name);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        int objectNum = 0;
        for (S3ObjectSummary os : objects) {
            System.out.println("* " + os.getKey());
            objectNum++;
        }
        System.out.println("total " + objectNum + " object(s).");
    }

    public static boolean doesObjectExist(String key) {
        int len = key.length();
        ListObjectsV2Result result = s3.listObjectsV2(bucket_name);
        String s = new String();
        for(S3ObjectSummary os : result.getObjectSummaries()) {
            s = os.getKey();
            int slen = s.length();
            if(len == slen) {
                int i;
                for(i=0;i<len;i++) {
                    if(s.charAt(i) != key.charAt(i)) {
                        break;
                    }
                }
                if(i == len) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void deleteObject(String key) {
        if(s3.doesBucketExist(bucket_name) == false) {
            System.out.println(bucket_name + " does not exists!");
            return;
        }
        System.out.println("deleting " + key +" in " + bucket_name + " ...");
        int pre_len = key.length();
        ListObjectsV2Result result = s3.listObjectsV2(bucket_name);
        for(S3ObjectSummary os : result.getObjectSummaries()) {
            String skey = os.getKey();
            int len = skey.length();
            if(len != pre_len) {
                continue;
            }
            int i;
            for(i=0;i<pre_len;i++) {
                if(skey.charAt(i) != key.charAt(i)) {
                    break;
                }
            }
            if(i < pre_len) {
                continue;
            }
            s3.deleteObject(bucket_name, key);
        }
        System.out.println(key + " deleted!");
    }

    public static void uploadFile(String path, String key_prefix) {
        File fileToUpload = new File(path);
        if(fileToUpload.exists() == false) {
            System.out.println(path + " not exists!");
            return;
        }
        PutObjectRequest request = new PutObjectRequest(bucket_name, key_prefix + fileToUpload.getName(), fileToUpload);
        Upload upload = tx.upload(request);
        while((int)upload.getProgress().getPercentTransferred() < 100) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println(path + " upload succeed!");
    }

    public static void createFolder(String folder_name) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket_name, folder_name+"/",
                emptyContent, metadata);
        s3.putObject(putObjectRequest);
    }

    public static void uploadFolder(String folder_name, String key_prefix, boolean recursive) {
        File file = new File(folder_name);
        MultipleFileUpload multipleFileUpload = tx.uploadDirectory(bucket_name, key_prefix, file, recursive);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(folder_name + " upload succeed!");
    }


    public static void downloadFile(String key, String folder_path) {
        String file_name = StringUtils.substringAfterLast(key, "/");
        File file = new File(folder_path + file_name);
        Download download = tx.download(bucket_name, key, file);
        //TransferProgress tp = download.getProgress();
        while((int)download.getProgress().getPercentTransferred() < 100) {
            try {
                /*System.out.println(Double.parseDouble(String.format("%.4f", tp.getPercentTransferred())) +
                        " % has been completed");*/
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println(key + " download succeed!");
    }

    public static void main(String[] args) throws Exception {

        init_with_key();

        System.out.println(System.getenv("JAVA_HOME"));

        System.out.println(System.getProperty("user.dir").replace('\\','/') +
                "src/manga/");

        //listObjects();

        //uploadFile("E://manga/hw924739/chapter_1/1.jpg", "hw924739/chapter_1/");

        //downloadFile("black_kanojo/chapter_9/9.jpg","E://");

        //uploadFolder("E://manga/hw924739/chapter_1", "hw924739/", true);

    }
}
