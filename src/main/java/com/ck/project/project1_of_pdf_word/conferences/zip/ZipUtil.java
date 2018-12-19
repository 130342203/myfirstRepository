package com.ck.project.project1_of_pdf_word.conferences.zip;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by Administrator on 2018-5-23 0023.
 */
public class ZipUtil {

    private ZipUtil(){};
    private static Logger logger = LoggerFactory.getLogger(ZipUtil.class);
    //获取压缩包内文件,并解压zip压缩包到指定文件路径
    public static List<String> decompressionZip(String zipFilePath,String zipOutPath) throws IOException {
        //创建list 装压缩包内文件
        List<String> fileOfZip = new ArrayList();
        ZipFile zipFile = new ZipFile(zipFilePath, Charset.forName("GBK"));
        String name = zipFile.getName().substring(zipFile.getName().lastIndexOf(File.separator)+1, zipFile.getName().lastIndexOf('.'));
        logger.info("压缩包开始解压");
        //遍历压缩包内文件
        for (Enumeration entries = zipFile.entries(); entries.hasMoreElements();) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zipFile.getInputStream(entry);
            String outPath = (zipOutPath +File.separator+ name+File.separator+ zipEntryName);
            // 判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
            logger.info("解压路径:"+outPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }
            FileOutputStream out = new FileOutputStream(outPath);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            fileOfZip.add(outPath);
            in.close();
            out.close();
        }
        zipFile.close();
        logger.info("压缩包解压完成");
        return fileOfZip;
    }

    public static List<String> decompressionRar(String rarFilePath,String rarOutPath){
        List<String> fileOfRar = new ArrayList<>();

        File rarFile = new File(rarFilePath);
        if (!rarFile.exists()) {// 目标目录不存在时，创建该文件夹
            rarFile.mkdirs();
        }
        Archive rarArchive = null;
        String name = rarFile.getName().substring(rarFile.getName().lastIndexOf(File.separator)+1, rarFile.getName().lastIndexOf('.'));
        try {
            rarArchive = new Archive(rarFile);
            if (rarArchive != null) {
//                rarArchive.getMainHeader().print(); // 打印文件信息.
                FileHeader fileHeader = rarArchive.nextFileHeader();
                while (fileHeader != null) {
                    // 防止文件名中文乱码问题的处理

                    String fileName = fileHeader.getFileNameW().isEmpty() ? fileHeader
                            .getFileNameString() : fileHeader.getFileNameW();

                    if (fileHeader.isDirectory()) { // 文件夹
                        File fol = new File(rarOutPath +File.separator+ name+File.separator+ fileName);
                        fol.mkdirs();
                    } else { // 文件
                        String outPath = (rarOutPath +File.separator+ name+File.separator+ fileName);
                        File out = new File(outPath);
                        fileOfRar.add(outPath);
                        if (!out.exists()) {
                            if (!out.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录.
                                out.getParentFile().mkdirs();
                            }
                            out.createNewFile();
                        }
                        FileOutputStream os = new FileOutputStream(out);
                        rarArchive.extractFile(fileHeader, os);
                        os.close();
                    }
                    fileHeader = rarArchive.nextFileHeader();
                }
                rarArchive.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  fileOfRar;
    }

    //压缩到制定路径（zip格式）
    public static void doCompress(String srcFile, String zipFile) throws IOException {
        doCompress(new File(srcFile), new File(zipFile));
    }

    public static List<File> decompression7z(String filePath,String outPath){
        List<File> filesOf7z = new ArrayList<>();
        return filesOf7z;
    }

    public static void doCompress(File srcFile, File zipFile) throws IOException {
        ZipOutputStream out = null;
        try {

            out = new ZipOutputStream(new FileOutputStream(zipFile));
            doCompress(srcFile, out);
        } catch (Exception e) {
            throw e;
        } finally {
            out.close();
        }
    }
    public static void doCompress(String filelName, ZipOutputStream out) throws IOException{
        doCompress(new File(filelName), out);
    }
    public static void doCompress(File file, ZipOutputStream out) throws IOException{
        doCompress(file, out, "");
    }
    public static void doCompress(File inFile, ZipOutputStream out, String dir) throws IOException {
        if ( inFile.isDirectory() ) {
            File[] files = inFile.listFiles();
            if (files!=null && files.length>0) {
                for (File file : files) {
                    String name = inFile.getName();
                    if (!"".equals(dir)) {
                        name = dir + "/" + name;
                    }
                    ZipUtil.doCompress(file, out, name);
                }
            }
        } else {
            ZipUtil.doZip(inFile, out, dir);
        }
    }
    //实现zip压缩
    public static void doZip(File inFile, ZipOutputStream out, String dir) throws IOException {
        String entryName = null;
        if (!"".equals(dir)) {
            entryName = dir + "/" + inFile.getName();
        } else {
            entryName = inFile.getName();
        }
        ZipEntry entry = new ZipEntry(entryName);
        out.putNextEntry(entry);

        int len = 0 ;
        byte[] buffer = new byte[1024];
        FileInputStream fis = new FileInputStream(inFile);
        while ((len = fis.read(buffer)) > 0) {
            out.write(buffer, 0, len);
            out.flush();
        }
        out.closeEntry();
        fis.close();
    }
    // 删除某个文件夹下的所有文件夹和文件（包括当前文件夹）
    public static boolean deletefile(String delpath) throws FileNotFoundException, IOException {
        try {

            File file = new File(delpath);
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(delpath + File.separator + filelist[i]);
                    if (!delfile.isDirectory()) {
                        System.out.println("path=" + delfile.getPath());
                        System.out.println("absolutepath="
                                + delfile.getAbsolutePath());
                        System.out.println("name=" + delfile.getName());
                        delfile.delete();
                        System.out.println("删除文件成功");
                    } else if (delfile.isDirectory()) {
                        deletefile(delpath + file.separator + filelist[i]);
                    }
                }
                file.delete();

            }

        } catch (FileNotFoundException e) {
            System.out.println("deletefile()   Exception:" + e.getMessage());
        }
        return true;
    }
}
