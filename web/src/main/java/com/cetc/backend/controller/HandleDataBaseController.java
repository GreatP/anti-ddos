package com.cetc.backend.controller;

import com.cetc.backend.web.model.FileMeta;
import com.cetc.backend.web.model.UploadFiles;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import com.cetc.security.ddos.common.utils.AntiLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Administrator on 2015/8/27.
 */
@Controller
@RequestMapping("/sys")
public class HandleDataBaseController extends BaseController {

    private static Logger logger = AntiLogger.getLogger(HandleDataBaseController.class);
    private static final String FILE_PATH = "data";

    @ResponseBody
    @RequestMapping(value = "backUp", method = RequestMethod.GET)
    public String backUp(HttpServletRequest request/*@RequestBody String pwd*/) throws IOException{
        Runtime runtime = Runtime.getRuntime();
        /* 使用mysqldump时需要先将mysqldump.exe加入到系统环境变量中，否则mysqldump无法正常使用
         * -u后面是用户名，-p是密码-p后面最好不要有空格，-family是数据库的名字,-h是数据库所在主机的ip地址
         */
        //Process process = runtime.exec("mysqldump -u root -pcetc adconfig");
       Process process = runtime.exec("mysqldump -h 10.111.121.15 -u root -pcetc adconfig");
        /* 通过mysqldump获取到输入流，写成.sql文件 */
        InputStream inputStream = process.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(reader);
        String s = null;
        StringBuffer sb = new StringBuffer();
        while((s = br.readLine()) != null){
           // sb.append(s+"\\r\\n");
            sb.append(s+"\r\n");
        }
        s = sb.toString();

        /* the result of sha1 is 40 bytes */
        String sha1 = SHA1(s);

        /* ../路径指示的是jdk安装路径 */
        //String path="../anti-hasen.sql";

        //取得存放数据库备份文件的路径
        String filePath = request.getSession().getServletContext().getRealPath(FILE_PATH);
        String Path = filePath + "/anti-ddos.sql";

        File file = new File(Path);
        file.getParentFile().mkdirs();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        /* write the sha1 of file to new file */
        fileOutputStream.write(sha1.getBytes());
        fileOutputStream.write(s.getBytes());
        fileOutputStream.close();
        br.close();
        reader.close();
        inputStream.close();
        //file.delete();


        /* It's just for testing code,read file content from the path */

        /*
        File Inputfile = new File(path);
        FileInputStream inputstream = new FileInputStream(Inputfile);

        BufferedReader in = new BufferedReader(new FileReader(Inputfile));
        InputStreamReader reader1 = new InputStreamReader(inputstream);
        BufferedReader br1 = new BufferedReader(reader1);
        StringBuffer sb1 = new StringBuffer();
        String str = null;
        while((str = br1.readLine()) != null){
            sb1.append(str+"\r\n");
        }
        str = sb1.toString();

        String str1= str.substring(0,40);
        String str2= str.substring(40,str.length());

        FileOutputStream out=new FileOutputStream("D://out.sql");
        byte[] buff=new byte[]{};
        buff=str2.getBytes();
        out.write(buff,0,buff.length);
        */

        logger.info(this.getCurrentUser().getUsername() + " backup database configuration information and download the file!");

        return returnSuccess();
    }

    @ResponseBody
    @RequestMapping(value = "restore", method = RequestMethod.POST)
    public String restore(@RequestBody String pwd/*, @RequestParam("file") MultipartFile file*/) throws IOException{

        String str = null;
        String sha1vauleOld = null;
        String sha1vauleNew = null;
        String fileContent = null;
        //MultipartFile file = null;
        /*
        if (!file.isEmpty()) {
            try {
                    String filePath="/tmpdb.sql";
                File Inputfile = new File(filePath);
                FileInputStream fis = new FileInputStream(Inputfile);

                int sha1Length = 40;//指定位置

                byte[] bs = new byte[sha1Length]; // 指定长度
                fis.read(bs);// 得到内容
                byte[] bytes = file.getBytes();
                fis.skip(sha1Length);
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(filePath)));

                stream.write(bytes, 40, bytes.length - 40);
                stream.close();

            } catch (Exception e) {
                return "Failed to get " + file.getName() + " => " + e.getMessage();
            }
        } else {

        }
*/
        /*
        if (file.isEmpty()) {
            throw new IOException("File is empty!");
        }*/

        Runtime runtime = Runtime.getRuntime();

        //-u后面是用户名，-p是密码-p后面最好不要有空格，-family是数据库的名字，--default-character-set=utf8，这句话一定的加
        //mysql: Character set 'utf-8' is not a compiled character set and is not specified in the '
        //C:\\Program Files\\MySQL\\MySQL Server 5.5\\share\\charsets\\Index.xml' file ERROR 2019 (HY000): Can't
        // initialize character set utf-8 (path: C:\\Program Files\\MySQL\\MySQL Server 5.5\\share\\charsets\\)，
        Process process = runtime.exec("mysql -u root -pcetc --default-character-set=utf8 adconfig");
        OutputStream outputStream = process.getOutputStream();

       // InputStream inputStream = new FileInputStream(filePath);
        /* get the input file content */
        /*
        InputStream inputStream = file.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(reader);

        StringBuffer sb = new StringBuffer();
        while((str = br.readLine()) != null){
            sb.append(str+"\r\n");
        }
        str = sb.toString();*/

        // for test,read the config content from the backup file to recover the database config
        /******************************************************************************************/
        String path="../anti-hasen.sql";
        File Inputfile = new File(path);
        FileInputStream inputstream = new FileInputStream(Inputfile);

        BufferedReader in = new BufferedReader(new FileReader(Inputfile));
        InputStreamReader reader1 = new InputStreamReader(inputstream);
        BufferedReader br = new BufferedReader(reader1);
        StringBuffer sb = new StringBuffer();
        while((str = br.readLine()) != null){
            sb.append(str+"\r\n");
        }
        str = sb.toString();

        /******************************************************************************************/

        /* Get the sha1 from the string and compare with the new sha1 */
        sha1vauleOld = str.substring(0,40);
        fileContent = str.substring(40, str.length());
        sha1vauleNew = SHA1(fileContent);

        if (!sha1vauleOld.equals(sha1vauleNew)) {
            //return "Failed to restore " + file.getName() + ", because check sha1 failed.";
            return returnError();
        }

        /* write the input file content to the database file */
        OutputStreamWriter writer = new OutputStreamWriter(outputStream,"utf-8");
        /* skip the sha1 in the front of the file*/
        writer.write(fileContent, 0, fileContent.length());
        writer.flush();
        outputStream.close();
        br.close();
        writer.close();

        return returnSuccess();
    }

    public static String SHA1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * URL: /rest/sys/upload
     * upload(): receives files
     * @param request: MultipartHttpServletRequest auto passed
     * @param response: HttpServletResponse auto passed
     * @return LinkedList<FileMeta> as json format
     */
    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public @ResponseBody UploadFiles upload(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String str = null;
        String sha1vauleOld = null;
        String sha1vauleNew = null;
        String fileContent = null;

        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException(
                    "Request is not multipart, please 'multipart/form-data' enctype for your form.");
        }
        Runtime runtime = Runtime.getRuntime();

        //-u后面是用户名，-p是密码-p后面最好不要有空格，-family是数据库的名字，--default-character-set=utf8，这句话一定的加
        //mysql: Character set 'utf-8' is not a compiled character set and is not specified in the '
        //C:\\Program Files\\MySQL\\MySQL Server 5.5\\share\\charsets\\Index.xml' file ERROR 2019 (HY000): Can't
        // initialize character set utf-8 (path: C:\\Program Files\\MySQL\\MySQL Server 5.5\\share\\charsets\\)，
        Process process = runtime.exec("mysql -u root -pcetc --default-character-set=utf8 adconfig");
        OutputStream outputStream = process.getOutputStream();

        UploadFiles files = new UploadFiles();
        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
        response.setContentType("text/plain;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            List<FileItem> items = uploadHandler.parseRequest(request);
            String dir = request.getSession().getServletContext().getRealPath(FILE_PATH);
            File filePath = new File(dir);
            if (!filePath.exists()) {
                filePath.mkdir();
            }
            for (FileItem item : items) {
                if (!item.isFormField() && item.getSize() > 0) {
                    logger.debug(item.getName());
                    File file = new File(dir, item.getName());
                    item.write(file);

                    File Inputfile = new File(file.getPath());
                    FileInputStream inputstream = new FileInputStream(Inputfile);

                    BufferedReader in = new BufferedReader(new FileReader(Inputfile));
                    InputStreamReader reader1 = new InputStreamReader(inputstream);
                    BufferedReader br = new BufferedReader(reader1);
                    StringBuffer sb = new StringBuffer();
                    while((str = br.readLine()) != null){
                        sb.append(str+"\r\n");
                    }
                    str = sb.toString();

                    /******************************************************************************************/

                    /* Get the sha1 from the string and compare with the new sha1 */
                    sha1vauleOld = str.substring(0,40);
                    fileContent = str.substring(40, str.length());
                    sha1vauleNew = SHA1(fileContent);

                    if (!sha1vauleOld.equals(sha1vauleNew)) {
                        //return "Failed to restore " + file.getName() + ", because check sha1 failed.";
                        return null;
                    }

                     /* write the input file content to the database file */
                    OutputStreamWriter writer = new OutputStreamWriter(outputStream,"utf-8");
                    /* skip the sha1 in the front of the file*/
                    writer.write(fileContent, 0, fileContent.length());
                    writer.flush();
                    outputStream.close();
                    br.close();
                    writer.close();

                    FileMeta fileMeta = new FileMeta();
                    fileMeta.setName(item.getName());
                    fileMeta.setType(item.getContentType());
                    fileMeta.setSize(item.getSize());
                    fileMeta.setUrl(file.getPath());
                    fileMeta.setDeleteUrl(file.getPath());
                    fileMeta.setDeleteType("DELETE");
                    files.addFile(fileMeta);
                    break;
                }
            }
        } catch (FileUploadException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        // result will be like this
        // [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
        /*  can't get the current user through  this.getCurrentUser().getUsername() */
        logger.info("Upload the backup file: " + files.getFiles().get(0).getName() +
                ", and restore the configuration successfully!");
        return files;
    }

    @ResponseBody
    @RequestMapping(value="/upload", method = RequestMethod.GET)
    public String getRestUpload() {
        return returnSuccess("OK");
    }
}
