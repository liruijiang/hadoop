import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author lrj on 2017-6-22.
 */
public class FlowSum {
    //在kv中传输我们的自定义的对象是可以的 ，不过必须要实现hadoop的序列化机制  也就是implement Writable
    public static class FlowSumMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
        Text k = new Text();
        FlowBean v = new FlowBean();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            //将获取到的每一行进行切割
            String line = value.toString();
            String[] fields = StringUtils.split("/t");

            //抽取我们所需要的字段信息
            String phoneNum = fields[1];
            long upFlow = Long.parseLong(fields[fields.length-3]);
            long downFlow = Long.parseLong(fields[fields.length-2]);

            k.set(phoneNum);
            v.set(upFlow,downFlow);

            context.write(k,v);
        }

        public static class FlowSumReducer extends Reducer<Text,FlowBean,Text,FlowBean>{

            FlowBean v = new FlowBean();

            //这里reduce方法接收到的key就是某一组<a手机号,bean> <a手机号,bean> <a手机号,bean> <b手机号,bean>
            //这里reduce方法接收到的values就是这一组kv对中的所有bean的一个迭代器
            @Override
            protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
                long upFlowCount = 0;
                long downFlowCount = 0;
                for (FlowBean bean : values){
                    upFlowCount += bean.getUpFlow();
                    downFlowCount += bean.getDownFlow();
                }
                v.set(upFlowCount,downFlowCount);
                context.write(key,v);
            }
        }

        public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
            Configuration conf = new Configuration();
            Job job = Job.getInstance(conf);

            job.setJarByClass(FlowBean.class);

            FileInputFormat.setInputPaths(job,new Path("/flowsum/input"));
            FileOutputFormat.setOutputPath(job,new Path("/flowsum/output"));
            boolean res = job.waitForCompletion(true);

            System.exit(res?0:1);

        }

    }
}
