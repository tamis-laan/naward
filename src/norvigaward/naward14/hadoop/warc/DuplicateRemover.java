package norvigaward.naward14.hadoop.warc;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class DuplicateRemover extends Reducer<Text, Text, Text, Text> {

	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		Text out = new Text();
		
		// remove duplicates with hashset
		HashSet<String> addresses = new HashSet<String>();
		for(Text t : values) {
			addresses.add(t.toString());
		}
		
		// merge into a result string for this country
		String merge = "";
		for (String s : addresses) {
			merge += ", " + s;
		}
		
		out.set(merge);
		context.write(key, out);
	}
}
