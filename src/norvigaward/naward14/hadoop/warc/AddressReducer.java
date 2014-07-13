package norvigaward.naward14.hadoop.warc;

import java.io.IOException;
import java.math.BigDecimal;
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
		HashMap<String, Integer> countries = new HashMap<String, Integer>();
		
		for(Text t : values) {
			System.out.println(t.toString());
			String s = t.toString();
			// count domain extensions of pages where address was found
			if(s.contains("country:")) {
				String ext = s.split("country:")[1];
					if(ext.length() == 2) {
					Integer n = countries.get(ext);
					if(n == null) {
						n = 0;
					}
					n++;
					countries.put(ext, n);
				}
			}
			// count detected languages of pages where address was found
			if(s.contains("language:")) {
				String lang = s.split("language:")[1];
				if(lang.length() == 2) {
					Integer n = langs.get(lang);
					if(n == null) {
						n = 0;
					}
					n++;
					langs.put(lang, n);
				}
			}
		}
		
		BigDecimal balance = new BigDecimal(BitcoinAddressBalanceChecker.getBalance(address));
		BigDecimal sent = new BigDecimal(BitcoinAddressBalanceChecker.getSent(address));
		BigDecimal received = new BigDecimal(BitcoinAddressBalanceChecker.getReceived(address));
		
		JSONObject j = new JSONObject();
		j.put("balance", balance.toPlainString());
		j.put("sent", sent.toPlainString());
		j.put("received", received.toPlainString());
		j.put("langs", new JSONObject(langs));
		j.put("countries", new JSONObject(countries));
		
		out.set("\"" + address + "\":" + j.toString() + ",");
		context.write(new Text(""), out);
	}
}
