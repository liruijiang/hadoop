import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;

/**
 * @author lrj on 2017-6-28.
 */
public class ProvivcePartitioner extends Partitioner<Text, FlowBean> {
    private static HashMap<String, Integer> provinceMap = new HashMap<String, Integer>();

    static{
        provinceMap.put("135", 0);
        provinceMap.put("136", 1);
        provinceMap.put("137", 2);
        provinceMap.put("138", 3);
        provinceMap.put("139", 4);

    }

    @Override
    public int getPartition(Text key, FlowBean value, int numPartitions) {
        Integer code = provinceMap.get(key.toString().substring(0, 3));
        if(code != null){
            return code;
        }
        return 5;
    }

}
