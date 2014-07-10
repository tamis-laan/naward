package norvigaward.naward14.bitcoinutils;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jwat.common.Payload;
import org.jwat.warc.WarcRecord;

public class BitcoinAddressFinder {

	/**
	 * Finds for a WarcRecord valid Bitcoin addresses
	 * @param value WardRecord
	 * @return Collection of Strings that represent valid bitcoin addresses
	 * @throws IOException 
	 */
	public static Collection<String> findBitcoinAddresses(WarcRecord value) throws IOException
	{
		HashSet<String> res = new HashSet<String>();
		Payload payload = value.getPayload();
		if (payload == null) {
		} else {
			String warcContent = IOUtils.toString(payload.getInputStreamComplete());
			if (warcContent == null && "".equals(warcContent)) {
				// NOP
			} else {
				// Pattern for matching addresses in bitcoin URLs
				Pattern bcpattern = Pattern.compile("(?i)bitcoin: *([a-z0-9]+)");
				Document doc = Jsoup.parse(warcContent);
				Elements links = doc.select("a");
				for (Element link : links) {
					// Find addresses in bitcoin URLs
					Matcher m = bcpattern.matcher(link.attr("href"));
					if(m.find()) {
						if(BitcoinAddressValidator.validateAddress(m.group(1))) {
							res.add(m.group(1));
						}
					}
					// Find addresses in text of bitcoin URLs
					m = bcpattern.matcher(link.text());
					if (m.find()) {
						if(BitcoinAddressValidator.validateAddress(m.group(1))) {
							res.add(m.group(1));
						}
					}
					if (BitcoinAddressValidator.validateAddress(link.text())) {
						res.add(link.text());
					}
				}
				// Search all words in the body (separated by spaces) for valid bitcoin addresses
				String[] bodywords = doc.body().text().split(" ");
				for (String w : bodywords) {
					if(BitcoinAddressValidator.validateAddress(w)) {
						res.add(w);
					}
				}
			}
		}
		return res;
	}
	
	

}
