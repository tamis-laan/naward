package norvigaward.naward14.bitcoinutils;

import com.google.bitcoin.core.Address;
import com.google.bitcoin.core.AddressFormatException;
import com.google.bitcoin.core.NetworkParameters;

public class BitcoinAddressValidator {
	
	public static boolean validateAddress(String address) {
		boolean valid = true;
		try {
			Address a = new Address(NetworkParameters.prodNet(), address);
		} catch (AddressFormatException e) {
			valid = false;
		}
		return valid;
	}

}
