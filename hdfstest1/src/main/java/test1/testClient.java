package test1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import java.io.IOException;

/**
 * Created by Administrator on 2017-6-9.
 */
public class testClient {
    public static void main(String[] args) throws IOException {
        Configuration configuration =  new Configuration();
        //获取hdfs客户端对象的时候，需要指定文件系统的类型为hdfs
        configuration.set("fs.defaultFS","hdfs://101.39.43.122:11:9000");
        //或许FileSystem实例对象
        FileSystem fileSystem = FileSystem.get(configuration);

        RemoteIterator<LocatedFileStatus> listFiles = fileSystem.listFiles(new Path("/"),false);

        //通过这个迭代器就可以遍历出我们hdfs文件系统根目录下的  文件
        while (listFiles.hasNext()){
            LocatedFileStatus next = listFiles.next();
            Path path = next.getPath();
            System.out.println(path.getName());
        }
    }
}
