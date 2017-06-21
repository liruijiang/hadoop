package test1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author lrj on 2017-6-20
 *
 * Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
 * KEYIN：是指框架读取到的数据的key类型
 * 		在默认的读取数据组件InputFormat下，读取的key是一行文本的偏移量，所以key的类型是long类型的
 *
 * VALUEIN指框架读取到的数据的value类型
 * 		在默认的读取数据组件InputFormat下，读到的value就是一行文本的内容，所以value的类型是String类型的
 *
 * keyout是指用户自定义逻辑方法返回的数据中key的类型 这个是由用户业务逻辑决定的。
 * 		在我们的单词统计当中，我们输出的是单词作为key，所以类型是String
 *
 * VALUEOUT是指用户自定义逻辑方法返回的数据中value的类型 这个是由用户业务逻辑决定的。
 * 		在我们的单词统计当中，我们输出的是单词数量作为value，所以类型是Integer
 *
 * 但是，String ,Long都是jdk中自带的数据类型，在序列化的时候，效率比较低。hadoop为了提高序列化的效率，他就自己自定义了一套数据结构。
 *
 * 所以说在我们的hadoop程序中，如果该数据需要进行序列化（写磁盘，或者网络传输），就一定要用实现了hadoop序列化框架的数据类型
 *
 *
 * Long------->LongWritable
 * String----->Text
 * Integer---->IntWritable
 * null------->nullWritable
 * .
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable>{

    /**
     * 这个map方法就是mapreduce程序中被主程序MapTask所调用的用户业务逻辑方法
     *
     * Maptask会驱动我们的读取数据组件InputFormat去读取(KEYIN,VALUEIN),每读取一个(k,v),他就会传入到这个用户写的map方法中去调用一次.
     *
     * 在默认的inputFormat实现中,此处的key就是一行的起始偏移量,value就是一行的内容.
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //读取每一行数据
        String lines = value.toString();
        String words[] = lines.split(" ");
        for (String word:words){
            context.write(new Text(word),new IntWritable(1));
        }
    }

}
