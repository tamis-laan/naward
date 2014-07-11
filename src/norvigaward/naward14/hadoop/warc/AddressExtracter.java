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
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
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

	@Override
	public void map(LongWritable key, WarcRecord value, Context context) throws IOException, InterruptedException {
		context.setStatus(Counters.CURRENT_RECORD + ": " + key.get());
		if ("application/http; msgtype=response".equals(value.header.contentTypeStr)) {
			HttpHeader httpHeader = value.getHttpHeader();
			if (httpHeader == null) {
				// No header so we are unsure that the content is text/html: NOP
			} else {
				if (httpHeader.contentType != null && httpHeader.contentType.contains("text/html")) {
					context.getCounter(Counters.NUM_HTTP_RESPONSE_RECORDS).increment(1);
					Payload payload = value.getPayload();
					if (payload == null) {
					} else {
						String warcContent = IOUtils.toString(payload.getInputStreamComplete());
						if (warcContent == null && "".equals(warcContent)) {
							// NOP
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