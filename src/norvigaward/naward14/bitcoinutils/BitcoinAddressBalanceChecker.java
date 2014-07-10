package norvigaward.naward14.bitcoinutils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

public class BitcoinAddressBalanceChecker {
	
	private static String API = "https://blockchain.info/q/addressbalance/"; //1N6HNyB28SnPmdeJg6qGsYsXHcfDi9Qweb
	private static String addr = "1N6HNyB28SnPmdeJg6qGsYsXHcfDi9Qweb";
	
	public static void main(String[] args) throws IOException {
		URL url = new URL(API + addr);
		System.out.println(url.toString());
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String body = IOUtils.toString(in);
		System.out.println(body);
	}

}
