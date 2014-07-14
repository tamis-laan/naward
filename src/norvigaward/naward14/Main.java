package norvigaward.naward14;

import java.util.Arrays;

import norvigaward.naward14.hadoop.warc.BitcoinAddresses;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

public class Main 
{
    public static void main( String[] args )
    {
    	int retval = 0;
    	String[] toolArgs = Arrays.copyOfRange(args, 0, args.length);
    	try {
    		retval = ToolRunner.run(new Configuration(), new BitcoinAddresses(), toolArgs);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	System.exit(retval);
    }
}

