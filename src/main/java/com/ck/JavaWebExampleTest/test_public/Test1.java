package com.ck.JavaWebExampleTest.test_public;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.*;

/**
 * Created by ron on 2017/11/7.
 */
public class Test1 {
    private static String matnrString = "1754M36P01_1.png,181893-2_1.png,181893-2_2.png,230-0138-000_1.png,230-0138-000_2.png,301-777-202-0_1.png,60493-101_1.png,66-15959-1_1.png,66-15959-1_2.png,67746-1_1.png,682-1001-001_1.png,69-23695-7_1.png,69-23695-7_2.png,69-73539-1_1.png,69-73539-1_2.png,830-8732-350_1.png,830-8732-350_2.png,9111M35P03_1.png,9111M35P03_2.png,BACB10HJ04_1.png,BACB10HJ04_2.png,BACB28W5B014_1.png,BACB28W5B014_2.png,BACB30LH4-3_1.png,BACB30NR4K6_1.png,BACB30NR4K6_2.png,BACC47DR1_1.png,BACC47DR1_2.png,BACS12FA3K12_1.png,BACS12FA3K12_2.png,BACS12N10-10_1.png,BACS12N10-10_2.png,BACS21AP120RP_1.png,BACS21AP120RP_2.png,BACS38K6_1.png,BACS38K6_2.png,BACT12AC54_1.png,BACT12AC54_2.png,D436-36.png,D436-36_2.png,J221P904_1.png,K20052_1.png,K20052_2.png,KRP502-4VT_1.png,KRP502-4VT_2.png,M83248-2-904_1.png,MS14101-7_2.png,MS24585C86_1.png,MS24665-359_1.png,MS24665-359_2.png,MS24693C296-1.png,MS29513-034_1.png,MS29513-034_2.png,MS29513-223_1.png,MS29513-223_2.png,NAS1149D0463J_1.png,NAS1149D0463J_2.png,NAS514P440-5P_1.png,NAS514P440-5P_2.png,P41780-2_1.png,P41780-2_2.png,RTV108_1.png,RTV108_2.png,S700B0116-4D012_1.png,";
    private static String nowUrl = "http://attachment.bireturn.com/2018-06/05ytjb/1754M36P01_1.png,http://attachment.bireturn.com/2018-06/05ytjb/181893-2_1.png,http://attachment.bireturn.com/2018-06/05ytjb/181893-2_2.png,http://attachment.bireturn.com/2018-06/05ytjb/230-0138-000_1.png,http://attachment.bireturn.com/2018-06/05ytjb/230-0138-000_2.png,http://attachment.bireturn.com/2018-06/05ytjb/301-777-202-0_1.png,http://attachment.bireturn.com/2018-06/05ytjb/60493-101_1.png,http://attachment.bireturn.com/2018-06/05ytjb/66-15959-1_1.png,http://attachment.bireturn.com/2018-06/05ytjb/66-15959-1_2.png,http://attachment.bireturn.com/2018-06/05ytjb/67746-1_1.png,http://attachment.bireturn.com/2018-06/05ytjb/682-1001-001_1.png,http://attachment.bireturn.com/2018-06/05ytjb/69-23695-7_1.png,http://attachment.bireturn.com/2018-06/05ytjb/69-23695-7_2.png,http://attachment.bireturn.com/2018-06/05ytjb/69-73539-1_1.png,http://attachment.bireturn.com/2018-06/05ytjb/69-73539-1_2.png,http://attachment.bireturn.com/2018-06/05ytjb/830-8732-350_1.png,http://attachment.bireturn.com/2018-06/05ytjb/830-8732-350_2.png,http://attachment.bireturn.com/2018-06/05ytjb/9111M35P03_1.png,http://attachment.bireturn.com/2018-06/05ytjb/9111M35P03_2.png,http://attachment.bireturn.com/2018-06/05ytjb/BACB10HJ04_1.png,http://attachment.bireturn.com/2018-06/05ytjb/BACB10HJ04_2.png,http://attachment.bireturn.com/2018-06/05ytjb/BACB28W5B014_1.png,http://attachment.bireturn.com/2018-06/05ytjb/BACB28W5B014_2.png,http://attachment.bireturn.com/2018-06/05ytjb/BACB30LH4-3_1.png,http://attachment.bireturn.com/2018-06/05ytjb/BACB30NR4K6_1.png,http://attachment.bireturn.com/2018-06/05ytjb/BACB30NR4K6_2.png,http://attachment.bireturn.com/2018-06/05ytjb/BACC47DR1_1.png,http://attachment.bireturn.com/2018-06/05ytjb/BACC47DR1_2.png,http://attachment.bireturn.com/2018-06/05ytjb/BACS12FA3K12_1.png,http://attachment.bireturn.com/2018-06/05ytjb/BACS12FA3K12_2.png,http://attachment.bireturn.com/2018-06/05ytjb/BACS12N10-10_1.png,http://attachment.bireturn.com/2018-06/05ytjb/BACS12N10-10_2.png,http://attachment.bireturn.com/2018-06/05ytjb/BACS21AP120RP_1.png,http://attachment.bireturn.com/2018-06/05ytjb/BACS21AP120RP_2.png,http://attachment.bireturn.com/2018-06/05ytjb/BACS38K6_1.png,http://attachment.bireturn.com/2018-06/05ytjb/BACS38K6_2.png,http://attachment.bireturn.com/2018-06/05ytjb/BACT12AC54_1.png,http://attachment.bireturn.com/2018-06/05ytjb/BACT12AC54_2.png,http://attachment.bireturn.com/2018-06/05ytjb/D436-36.png,http://attachment.bireturn.com/2018-06/05ytjb/D436-36_2.png,http://attachment.bireturn.com/2018-06/05ytjb/J221P904_1.png,http://attachment.bireturn.com/2018-06/05ytjb/K20052_1.png,http://attachment.bireturn.com/2018-06/05ytjb/K20052_2.png,http://attachment.bireturn.com/2018-06/05ytjb/KRP502-4VT_1.png,http://attachment.bireturn.com/2018-06/05ytjb/KRP502-4VT_2.png,http://attachment.bireturn.com/2018-06/05ytjb/M83248-2-904_1.png,http://attachment.bireturn.com/2018-06/05ytjb/MS14101-7_2.png,http://attachment.bireturn.com/2018-06/05ytjb/MS24585C86_1.png,http://attachment.bireturn.com/2018-06/05ytjb/MS24665-359_1.png,http://attachment.bireturn.com/2018-06/05ytjb/MS24665-359_2.png,http://attachment.bireturn.com/2018-06/05ytjb/MS24693C296-1.png,http://attachment.bireturn.com/2018-06/05ytjb/MS29513-034_1.png,http://attachment.bireturn.com/2018-06/05ytjb/MS29513-034_2.png,http://attachment.bireturn.com/2018-06/05ytjb/MS29513-223_1.png,http://attachment.bireturn.com/2018-06/05ytjb/MS29513-223_2.png,http://attachment.bireturn.com/2018-06/05ytjb/NAS1149D0463J_1.png,http://attachment.bireturn.com/2018-06/05ytjb/NAS1149D0463J_2.png,http://attachment.bireturn.com/2018-06/05ytjb/NAS514P440-5P_1.png,http://attachment.bireturn.com/2018-06/05ytjb/NAS514P440-5P_2.png,http://attachment.bireturn.com/2018-06/05ytjb/P41780-2_1.png,http://attachment.bireturn.com/2018-06/05ytjb/P41780-2_2.png,http://attachment.bireturn.com/2018-06/05ytjb/RTV108_1.png,http://attachment.bireturn.com/2018-06/05ytjb/RTV108_2.png,http://attachment.bireturn.com/2018-06/05ytjb/S700B0116-4D012_1.png,";

    public static void main(String[] args) {
        String[] value= {"123","asd"};
    }

    //超时处理测试
    private static void overTime(){
        //java超时处理测试
        final ExecutorService exec = Executors.newFixedThreadPool(1);
       /* FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "345";
            }
        });*/
        Callable<String> call = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5*1000);
                return "345";
            }
        };
        Future<String> future = exec.submit(call);
        try {
            String ss = future.get(3*1000,TimeUnit.MILLISECONDS);
            System.out.println(ss);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            System.out.println("超时");
        }finally {
            System.out.println("程序执行完毕");
            exec.shutdown();
        }
        System.out.println("fujia");
    }
    public static String dd(String a){
        a="10";
        return  a ;
    }
    //url下载方法测试
    public void urlDownload(){
        try {


            InputStream in = null;
            HttpURLConnection urlconnection = null;
            URL url = null;
            url = new URL("http://attachment.bireturn.com/2018-06/05ytjb/1754M36P01_1.png");
            urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.connect();
            in = urlconnection.getInputStream();
            System.out.println("file type:"+HttpURLConnection.guessContentTypeFromStream(in)+"||");
            OutputStream out = new FileOutputStream("H:\\123.png");
            byte [] bytes = new byte[2048];
            int count =0 ;
            while((count = in.read(bytes))>0){
                out.write(bytes,0,count);
            }
            out.close();
            in.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //测试读取某个文件夹下的文件
    public void testReadfile(){
        try {
            readfile("D:\\aaa\\工作文档接收\\圆通价拨image\\圆通价拨image");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 读取某个文件夹下的所有文件
     */
    public static boolean readfile(String filepath) throws FileNotFoundException, IOException {
        try {

            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println("文件");
                System.out.println("path=" + file.getPath());
                System.out.println("absolutepath=" + file.getAbsolutePath());
                System.out.println("name=" + file.getName());

            } else if (file.isDirectory()) {
                System.out.println("文件夹");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        System.out.println("path=" + readfile.getPath());
                        System.out.println("absolutepath="
                                + readfile.getAbsolutePath());
                        System.out.println("name=" + readfile.getName());

                    } else if (readfile.isDirectory()) {
                        readfile(filepath + "\\" + filelist[i]);
                    }
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return true;
    }

    /**
     * 删除某个文件夹下的所有文件夹和文件（包括当前文件夹）
     */
    public static boolean deletefile(String delpath) throws FileNotFoundException, IOException {
        try {

            File file = new File(delpath);
            if (!file.isDirectory()) {
                System.out.println("1");
                file.delete();
            } else if (file.isDirectory()) {
                System.out.println("2");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(delpath + "\\" + filelist[i]);
                    if (!delfile.isDirectory()) {
                        System.out.println("path=" + delfile.getPath());
                        System.out.println("absolutepath="
                                + delfile.getAbsolutePath());
                        System.out.println("name=" + delfile.getName());
                        delfile.delete();
                        System.out.println("删除文件成功");
                    } else if (delfile.isDirectory()) {
                        deletefile(delpath + "\\" + filelist[i]);
                    }
                }
                file.delete();

            }

        } catch (FileNotFoundException e) {
            System.out.println("deletefile()   Exception:" + e.getMessage());
        }
        return true;
    }

    //批量替换文件地址
    public void batchDeal() {
        String[] matnrs;
        String[] nowUrls;
        matnrs = matnrString.split(",");
        nowUrls = nowUrl.split(",");
        for (int i = 0; i < matnrs.length; i++) {
            int mindex1 = matnrs[i].lastIndexOf(".");
            int mindex2 = matnrs[i].lastIndexOf("_");
            //截取不到下划线“_”
            if (mindex2 == -1) {
                mindex2 = matnrs[i].lastIndexOf("-");
            }
            if (mindex2 == -1) {
                System.out.println("截取出错！----》" + matnrs[i]);
            }
            String count1 = matnrs[i].substring(mindex2 + 1, mindex1);
            String count2 = null;
            int mindex3 = matnrs[i].indexOf("_");
            //数目大于10,重新截取位置数
            if (Integer.parseInt(count1) > 10) {
                count2 = count1;
                count1 = matnrs[i].substring(mindex3 + 1, mindex2);
            }
            if (!isNumeric(count1)) {
                System.out.println("位置数出错！！！------》" + "点位置：" + mindex1 + ";横杠位置：" + mindex2 + ";位置：" + count1 + ";订单号：" + String.valueOf(count2) + ";||" + matnrs[i] + ":" + nowUrls[i]);
                continue;
            }
            if (count2 != null && !isNumeric(count2)) {
                System.out.println("订单号出错！！！------》" + "点位置：" + mindex1 + ";横杠位置：" + mindex2 + ";位置：" + count1 + ";订单号：" + String.valueOf(count2) + ";||" + matnrs[i] + ":" + nowUrls[i]);
                continue;
            }
            System.out.println("点位置：" + mindex1 + ";横杠位置：" + mindex2 + ";位置：" + count1 + ";订单号：" + String.valueOf(count2) + ";||" + matnrs[i] + ":" + nowUrls[i]);
        }
        //根据位置数，以及订单号，件号，航司名称，执行不同的sql
    }


    //判断是否是数字----方法一：用JAVA自带的函数
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    //string转date
    public void stringtodataMaiin() {
        String date = "20161010";
        Test1 test = new Test1();
        Date date1 = test.stringToDate(date);
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        System.out.println(sdf.format(date1));
    }

    public Date stringToDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6, date.length()));
        Date resultDate = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(Calendar.YEAR, year);
        gc.set(Calendar.MONTH, month - 1);
        gc.set(Calendar.DAY_OF_MONTH, day);
        resultDate = gc.getTime();
        return resultDate;
    }

    public void test1() {
        Long timeStart = System.currentTimeMillis();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Long timeEnd = System.currentTimeMillis();
        System.out.println("：" + new Date() + "，耗时：" + (((timeEnd - timeStart) / 1000) / 60) + "分" + (timeEnd - timeStart) / 1000 + "秒");

        File file = new File("D://1.text");
        try {
            if (file.createNewFile()) {
                System.out.println("创建文件成功");
            }
            method1("D://1.text", "123");
            method2("D://1.text", "345");
            method3("D://1.text", "567");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void method1(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 追加文件：使用FileWriter
     */
    public static void method2(String fileName, String content) {
        FileWriter writer = null;
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(fileName, true);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 追加文件：使用RandomAccessFile
     */
    public static void method3(String fileName, String content) {
        RandomAccessFile randomFile = null;
        try {
            // 打开一个随机访问文件流，按读写方式
            randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

