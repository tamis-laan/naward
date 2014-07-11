package norvigaward.naward14.bitcoinutils;

import com.google.bitcoin.core.Address;
import com.google.bitcoin.core.AddressFormatException;
import com.google.bitcoin.core.NetworkParameters;

public class BitcoinAddressValidator {
	
	/**
	 * Uses the bitcoinj library to validate a Bitcoin address
	 * @param address
	 * @return true if the address is valid
	 */
	public static boolean validateAddress(String address) {
		boolean valid = true;
		try {
			new Address(NetworkParameters.prodNet(), address);
		} catch (AddressFormatException e) {
			valid = false;
		}
		return valid;
	}

}
