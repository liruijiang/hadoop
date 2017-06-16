package test1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Administrator on 2017-6-13.
 */
public class HdfsClient {
    FileSystem fs = null;

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


    @Test
    public void testListFiles() throws IOException {

        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/testDir"),true);
    }

}
