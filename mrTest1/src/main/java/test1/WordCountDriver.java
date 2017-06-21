package test1;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

/**
 * @author lrj on 2017-6-20.
 *本类是客户端用来指定wordcount job程序运行时候所需要的很多参数
 *
 * 比如:指定哪个类作为map阶段的业务逻辑类,哪个类作为reduce阶段的业务逻辑类
 *
 * 	指定用那个组件作为数据的读取组件,数据结果输出组件,指定这个wordcount jar包所在的路径....以及其他各种所需要的参数
 */
public class WordCountDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://hadoop2:9090");
        Job job = Job.getInstance(conf);

        //告诉框架，我们的的程序所在jar包的位置
        //job.setJar("/root/wordcount.jar");
        job.setJarByClass(WordCountDriver.class);

        //告诉框架我们程序所有的mapper类和reducer类
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        //告诉框架我们输出的数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //告诉框架,我们程序使用的数据读取组件结果输出所用的组件是什么
        //TextInputFormat是mapreduce程序中内置的一种读取数据组件,准确的说叫做读取文本文件的输入组件
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        //告诉框架我们要处理的数据在那个目录下
        FileInputFormat.setInputPaths(job,"/wordcount/input");
        //告诉框架我们处理的结果要输出到什么地方
        FileOutputFormat.setOutputPath(job,new Path("/wordcount/output"));

        boolean res = job.waitForCompletion(true);
        System.exit(res?0:1);
    }
}
