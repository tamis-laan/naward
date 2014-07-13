/**
 * Copyright 2014 SURFsara
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package norvigaward.naward14.hadoop.warc;

import java.io.IOException;
import java.util.Collection;

import norvigaward.naward14.bitcoinutils.BitcoinAddressFinder;
import norvigaward.naward14.languagetools.LanguageDetecter;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jwat.common.HttpHeader;
import org.jwat.common.Payload;
import org.jwat.warc.WarcRecord;

import com.cybozu.labs.langdetect.LangDetectException;

/**
 * Map function that from a WarcRecord extracts all links. The resulting key,
 * values: page URL, link.
 * 
 * @author mathijs.kattenberg@surfsara.nl
 */
class AddressExtracter extends Mapper<LongWritable, WarcRecord, Text, Text> {
	private static enum Counters {
		CURRENT_RECORD, NUM_HTTP_RESPONSE_RECORDS
	}
	
	LanguageDetecter ld = new LanguageDetecter();
    Runtime runtime = Runtime.getRuntime();
    long tick = System.currentTimeMillis();
    int k = 0;
	
	private void printMem() {
        int mb = 1024*1024;
       
        System.out.println("##### Heap utilization statistics [MB] #####");
         
        //Print used memory
        System.out.println("Used Memory:"
            + (runtime.totalMemory() - runtime.freeMemory()) / mb);
 
        //Print free memory
        System.out.println("Free Memory:"
            + runtime.freeMemory() / mb);
         
        //Print total available memory
        System.out.println("Total Memory:" + runtime.totalMemory() / mb);
 
        //Print Maximum available memory
        System.out.println("Max Memory:" + runtime.maxMemory() / mb);
	}

	@Override
	public void map(LongWritable key, WarcRecord value, Context context) throws IOException, InterruptedException {
		String path = ((FileSplit)context.getInputSplit()).getPath() + " " + key.get();
		System.out.println("Processing: " + path);
		k++;
		if(k > 1000) {
			k = 0;
			printMem();
			System.out.println("last 1000 maps: " + (System.currentTimeMillis() - tick));
			tick = System.currentTimeMillis();
			System.out.println();
		}
		
		context.setStatus(Counters.CURRENT_RECORD + ": " + key.get());
		if ("application/http; msgtype=response".equals(value.header.contentTypeStr)) {
			HttpHeader httpHeader = value.getHttpHeader();
			if (httpHeader == null) {
			} else {
				if (httpHeader.contentType != null && httpHeader.contentType.contains("text/html")) {
					context.getCounter(Counters.NUM_HTTP_RESPONSE_RECORDS).increment(1);
					Payload payload = value.getPayload();
					if (payload == null) {
					} else {
						String warcContent = IOUtils.toString(payload.getInputStreamComplete());
						if (warcContent == null && "".equals(warcContent)) {
						} else {
							Document doc = Jsoup.parse(warcContent);
							Collection<String> addresses = BitcoinAddressFinder.findBitcoinAddresses(doc);
							if(!addresses.isEmpty()) {
								String lang = "?";
								try {
									lang = ld.getLang(doc);
								} catch (LangDetectException e) {
									e.printStackTrace();
								}

								String country = ld.getCountry(value);
								
								for(String a : addresses) {
									Text addr = new Text(a);
									if(!country.isEmpty())
										context.write(addr, new Text("country:"+country));
									if(!!lang.isEmpty())
										context.write(addr, new Text("language:"+lang));
								}
							}
						}
					}
				}
			}
		}
	}
}