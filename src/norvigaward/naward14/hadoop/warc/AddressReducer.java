package norvigaward.naward14.hadoop.warc;

import java.io.IOException;
import java.util.HashMap;

import norvigaward.naward14.bitcoinutils.BitcoinAddressBalanceChecker;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.json.JSONObject;

public class AddressReducer extends Reducer<Text, Text, Text, Text> {

	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		Text out = new Text();
		
		String address = key.toString();
		
		HashMap<String, Integer> langs = new HashMap<String, Integer>();
		HashMap<String, Integer> exts = new HashMap<String, Integer>();
		
		for(Text t : values) {
			String s = t.toString();
			// count domain extensions of pages where address was found
			if(s.contains("ext:")) {
				String ext = s.split("ext:")[1];
				Integer n = exts.get(ext);
				if(n == null) {
					n = 0;
				}
				n++;
				exts.put(ext, n);
			}
			// count detected languages of pages where address was found
			if(s.contains("lang:")) {
				String lang = s.split("lang:")[1];
				Integer n = exts.get(lang);
				if(n == null) {
					n = 0;
				}
				n++;
				langs.put(lang, n);
			}
		}
		
		JSONObject j = new JSONObject();
		j.put("address", address);
		j.put("balance", BitcoinAddressBalanceChecker.getBalance(address));
		j.put("langs", new JSONObject(langs));
		j.put("exts", new JSONObject(exts));
		
		out.set(j.toString());
		context.write(key, out);
	}
}
