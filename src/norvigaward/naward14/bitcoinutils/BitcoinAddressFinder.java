package norvigaward.naward14.bitcoinutils;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BitcoinAddressFinder {

	/**
	 * Retrieves valid Bitcoin address from a jsoup web document
	 * @param value Document (jsoup.nodes.Document)
	 * @return Collection of Strings that represent valid bitcoin addresses
	 * @throws IOException 
	 */
	public static Collection<String> findBitcoinAddresses(String content) throws IOException
	{
		HashSet<String> res = new HashSet<String>();
		Pattern bcpattern = Pattern.compile("(?i)bitcoin: *([a-z0-9]+)");
		Matcher m = bcpattern.matcher(content);
		while(m.find()) {
			if(BitcoinAddressValidator.validateAddress(m.group(1))) {
				res.add(m.group(1));
			}
		}
		
		/*
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
		}*/
		
		// Search all words in the body (separated by spaces) for valid bitcoin addresses
		String[] bodywords = content.split(" ");
		for (String w : bodywords) {
			if(BitcoinAddressValidator.validateAddress(w)) {
				res.add(w);
			}
		}
		
		return res;
	}
}
