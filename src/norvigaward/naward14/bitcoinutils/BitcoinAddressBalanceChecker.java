package norvigaward.naward14.bitcoinutils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

public class BitcoinAddressBalanceChecker {
	
	private static String BalanceAPI = "https://blockchain.info/q/addressbalance/";
	private static String SentAPI = "https://blockchain.info/q/getsentbyaddress/";
	private static String ReceivedAPI = "https://blockchain.info/q/getreceivedbyaddress";
	
	public static double getBalance(String address) {
		double balance = 0;
		try {
			URL url = new URL(BalanceAPI + address);
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			String body = IOUtils.toString(in);
			balance = Double.parseDouble(body) / 100000000;
		} catch (IOException io) {
			System.out.println("Could not get address balance for account: " + address);
		}
		return balance;
	}
	
	public static double getSent(String address) {
		double balance = 0;
		try {
			URL url = new URL(SentAPI + address);
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			String body = IOUtils.toString(in);
			balance = Double.parseDouble(body) / 100000000;
		} catch (IOException io) {
			System.out.println("Could not get address sent for account: " + address);
		}
		return balance;
	}
	
	public static double getReceived(String address) {
		double balance = 0;
		try {
			URL url = new URL(ReceivedAPI + address);
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			String body = IOUtils.toString(in);
			balance = Double.parseDouble(body) / 100000000;
		} catch (IOException io) {
			System.out.println("Could not get address received for account: " + address);
		}
		return balance;
	}

}
