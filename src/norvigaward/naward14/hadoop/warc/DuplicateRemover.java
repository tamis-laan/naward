package norvigaward.naward14.hadoop.warc;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class DuplicateRemover extends Reducer<Text, Text, Text, IntWritable> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		IntWritable out = new IntWritable();
		int sum = 0;
		for(Text t : values) {
			sum++;
		}
		out.set(sum);
		context.write(key, out);
	}
	
}
