package norvigaward.naward14.bitcoinutils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class BitcoinAddressBalanceChecker {
	
	private static String BalanceAPI = "https://blockchain.info/q/addressbalance/";
	private static String SentAPI = "https://blockchain.info/q/getsentbyaddress/";
	private static String ReceivedAPI = "https://blockchain.info/q/getreceivedbyaddress/";
	private static int timeout = 1000;
	
	public static void main(String[] args) {
		System.out.println(getBalance("19CWNopXCHSwWtWQ89NHC471LMn2Gy5j5G"));
	}
	
	public static double getBalance(String address) {
		double balance = 0;
		try {
			Document doc = Jsoup.connect(BalanceAPI + address)
					  .userAgent("Mozilla")
					  .timeout(timeout)
					  .get();
			balance = Double.parseDouble(doc.body().text()) / 100000000;
		} catch (Exception e) {
			System.out.println("Could not get address balance for account: " + address);
		}
		return balance;
	}
	
	public static double getSent(String address) {
		double balance = 0;
		try {
			Document doc = Jsoup.connect(SentAPI + address)
					  .userAgent("Mozilla")
					  .timeout(timeout)
					  .get();
			balance = Double.parseDouble(doc.body().text()) / 100000000;
		} catch (Exception e) {
			System.out.println("Could not get address sent for account: " + address);
		}
		return balance;
	}
	
	public static double getReceived(String address) {
		double balance = 0;
		try {
			Document doc = Jsoup.connect(ReceivedAPI + address)
					  .userAgent("Mozilla")
					  .timeout(timeout)
					  .get();
			balance = Double.parseDouble(doc.body().text()) / 100000000;
		} catch (Exception e) {
			System.out.println("Could not get address received for account: " + address);
		}
		return balance;
	}

}
