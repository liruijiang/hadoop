package test1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Administrator on 2017-6-13.
 */
public class hdfsClient {
    FileSystem fs = null;123123

    @Before
    public void  init() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        fs = FileSystem.get(new URI("hdfs://hadoop2:9000"), conf, "root");
    }

    /**
     * 往hdfs上传文件
     * @throws IOException
     */
    @Test
    public void testAddFileToHdfs() throws IOException {
        //要上传的文件
        Path src = new Path("D:/bbb.txt");
        //要上传到hdfs的路径
        Path dst = new Path("/");
        fs.copyFromLocalFile(src,dst);
        fs.close();
    }

    /**
     * 新增修改重命名文件
     * @throws IOException
     */
    @Test
    public void testMkdirAndDeleteAndRename() throws IOException {
        //在hdfs中创建文件夹
        boolean mkdirs = fs.mkdirs(new Path("/testDir"));
        //删除文件夹,如果非空文件夹,参数2必须填写true
        boolean delete = fs.delete(new Path("/testDir"), true);
        //重命名文件夹或者文件
        boolean rename = fs.rename(new Path("/testDir"),new Path("/testDir2"));

    }


    /**
     * 查看目录信息，只显示文件
     * @throws IOException
     */
    @Test
    public void testListFiles() throws IOException {
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/testDir"),true);
        while(listFiles.hasNext()){
            LocatedFileStatus fileStatus = listFiles.next();
            System.out.println(fileStatus.getPath().getName());
            System.out.println(fileStatus.getBlockSize());
            System.out.println(fileStatus.getPermission());
            System.out.println(fileStatus.getLen());
            for (BlockLocation bl : fileStatus.getBlockLocations()) {
                System.out.println("block-length:" + bl.getLength() + "--" + "block-offset:" + bl.getOffset());
                String[] hosts = bl.getHosts();
                for (String host : hosts) {
                    System.out.println(host);
                }
            }
            System.out.println("--------------为allen打印的分割线--------------");
        }
    }

    /**
     * 查看文件及文件夹信息
     *
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws FileNotFoundException
     */
    @Test
    public void testListAll() throws FileNotFoundException, IllegalArgumentException, IOException {

        FileStatus[] listStatus = fs.listStatus(new Path("/"));

        String flag = "";
        for (FileStatus fstatus : listStatus) {

            if (fstatus.isFile()) {
                flag = "f-- ";
            } else {
                flag = "d-- ";
            }
            System.out.println(flag + fstatus.getPath().getName());
            System.out.println(fstatus.getPermission());

        }

    }

}
