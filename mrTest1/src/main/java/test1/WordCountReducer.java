package test1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import javax.xml.soap.Text;
import java.io.IOException;

/**
 * @author lrj on 2017-6-20.
 *
 * reducetask在调用我们的reduce方法
 *
 * reducetask应该接收到map阶段中所有maptask输出的数据中的一部分;
 *
 *(key.HashCode%numReduceTask=本ReduceTask编号)
 *
 *reductask将接收到的kv数据拿来做处理时,是这样调用我们的reduce方法的:
 *
 *先将自己接收到的所有的kv按照k分组(根据k是否相同)
 *
 *然后将一组kv中的k传给我们的reduce方法的key变量,把这一组kv中的所有的v用一个迭代器传给reduce方法的变量values
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        for (IntWritable v : values){
            count += v.get();
        }
        context.write(key,new IntWritable(count));
    }
}
