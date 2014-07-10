package norvigaward.naward14.languagetools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jwat.common.Payload;
import org.jwat.warc.WarcReader;
import org.jwat.warc.WarcReaderFactory;
import org.jwat.warc.WarcRecord;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;


public class LanguageDetecter 
{
	HashMap<String,String> domain_map;
	Detector detector; 
	private String[] langs = {"af", "ar", "bg", "bn", "cs", "da", "de", "el", "en", "es", "et", "fa", "fi", "fr", "gu", "he", "hi", "hr", "hu",
								"id", "it", "ja", "kn", "ko", "lt", "lv", "mk", "ml", "mr", "ne", "nl", "no", "pa", "pl", "pt", "ro", "sk", "sl",
								"so", "sq", "sv", "sw", "ta", "te", "th", "tl", "tr", "uk", "ur", "vi", "zh-cn", "zh-tw" };
	
	public LanguageDetecter()
	{
//		domain_map = loadDomainMap();
		try {
			ArrayList<String> res = new ArrayList<String>();
			for(String lang : langs) {
				InputStream is2 = getClass().getResourceAsStream("profiles/"+lang);
				res.add(IOUtils.toString(is2, "UTF-8"));
				is2.close();
			}
			DetectorFactory.loadProfile(res);
			detector = DetectorFactory.create();
		} catch (Exception e) {System.out.println("Language detection failed error: 01");e.printStackTrace();}
	}

	private HashMap<String,String>loadDomainMap()
	{
		HashMap<String,String> map = new HashMap<String,String>();
		
		map.put(".ac" , "Ascension Island");
		map.put(".ad" , "Andorra");
		map.put(".ae" , "United Arab Emirates");
		map.put(".aero" , "Reserved for members of the air-transport industry");
		map.put(".af" , "Afghanistan");
		map.put(".ag" , "Antigua and Barbuda");
		map.put(".ai" , "Anguilla");
		map.put(".al" , "Albania");
		map.put(".am" , "Armenia");
		map.put(".an" , "Netherlands Antilles");
		map.put(".ao" , "Angola");
		map.put(".aq" , "Antarctica");
		map.put(".ar" , "Argentina");
		map.put(".arpa" ,	"UNKNOWN");
		map.put(".as" , "American Samoa");
		map.put(".asia" , "Restricted to the Pan-Asia and Asia Pacific community");
		map.put(".at" , "Austria");
		map.put(".au" , "Australia");
		map.put(".aw" , "Aruba");
		map.put(".ax" , "Aland Islands");
		map.put(".az" , "Azerbaijan");
		map.put(".ba" , "Bosnia and Herzegovina");
		map.put(".bb" , "Barbados");
		map.put(".bd" , "Bangladesh");
		map.put(".be" , "Belgium");
		map.put(".bf" , "Burkina Faso");
		map.put(".bg" , "Bulgaria");
		map.put(".bh" , "Bahrain");
		map.put(".bi" , "Burundi");
		map.put(".biz" , "UNKNOWN");
		map.put(".bj" , "Benin");
		map.put(".bl" , "Saint Barthelemy");
		map.put(".bm" , "Bermuda");
		map.put(".bn" , "Brunei Darussalam");
		map.put(".bo" , "Bolivia");
		map.put(".br" , "Brazil");
		map.put(".bs" , "Bahamas");
		map.put(".bt" , "Bhutan");
		map.put(".bv" , "Bouvet Island");
		map.put(".bw" , "Botswana");
		map.put(".by" , "Belarus");
		map.put(".bz" , "Belize");
		map.put(".ca" , "Canada");
		map.put(".caT" , "UNKNOWN");
		map.put(".cc" , "Cocos (Keeling) Islands");
		map.put(".cd" , "Congo The Democratic Republic of the");
		map.put(".cf" , "Central African Republic");
		map.put(".cg" , "Congo");
		map.put(".ch" , "Switzerland");
		map.put(".ci" , "Cote d'Ivoire");
		map.put(".ck" , "Cook Islands");
		map.put(".cl" , "Chile");
		map.put(".cm" , "Cameroon");
		map.put(".cn" , "China");
		map.put(".co" , "Colombia");
		map.put(".coM" , "UNKNOWN");
		map.put(".coOP" , "UNKNOWN");
		map.put(".cr" , "Costa Rica");
		map.put(".cu" , "Cuba");
		map.put(".cv" , "Cape Verde");
		map.put(".cx" , "Christmas Island");
		map.put(".cy" , "Cyprus");
		map.put(".cz" , "Czech Republic");
		map.put(".de" , "Germany");
		map.put(".dj" , "Djibouti");
		map.put(".dk" , "Denmark");
		map.put(".dm" , "Dominica");
		map.put(".do" , "Dominican Republic");
		map.put(".dz" , "Algeria");
		map.put(".ec" , "Ecuador");
		map.put(".edU" , "Reserved for post-secondary US accredited institutions");
		map.put(".ee" , "Estonia");
		map.put(".eg" , "Egypt");
		map.put(".eh" , "Western Sahara");
		map.put(".er" , "Eritrea");
		map.put(".es" , "Spain");
		map.put(".et" , "Ethiopia");
		map.put(".eu" , "European Union");
		map.put(".fi" , "Finland");
		map.put(".fj" , "Fiji");
		map.put(".fk" , "Falkland Islands (Malvinas)");
		map.put(".fm" , "Micronesia Federated States of");
		map.put(".fo" , "Faroe Islands");
		map.put(".fr" , "France");
		map.put(".ga" , "Gabon");
		map.put(".gb" , "United Kingdom");
		map.put(".gd" , "Grenada");
		map.put(".ge" , "Georgia");
		map.put(".gf" , "French Guiana");
		map.put(".gg" , "Guernsey");
		map.put(".gh" , "Ghana");
		map.put(".gi" , "Gibraltar");
		map.put(".gl" , "Greenland");
		map.put(".gm" , "Gambia");
		map.put(".gn" , "Guinea");
		map.put(".goV" , "U");
		map.put(".gp" , "Guadeloupe");
		map.put(".gq" , "Equatorial Guinea");
		map.put(".gr" , "Greece");
		map.put(".gs" , "South Georgia and the South Sandwich Islands");
		map.put(".gt" , "Guatemala");
		map.put(".gu" , "Guam");
		map.put(".gw" , "Guinea-Bissau");
		map.put(".gy" , "Guyana");
		map.put(".hk" , "Hong Kong");
		map.put(".hm" , "Heard Island and McDonald Islands");
		map.put(".hn" , "Honduras");
		map.put(".hr" , "Croatia");
		map.put(".ht" , "Haiti");
		map.put(".hu" , "Hungary");
		map.put(".id" , "Indonesia");
		map.put(".ie" , "Ireland");
		map.put(".il" , "Israel");
		map.put(".im" , "Isle of Man");
		map.put(".in" , "India");
		map.put(".info" , "UNKNOWN");
		map.put(".int" , "UNKNOWN");
		map.put(".io" , "British Indian Ocean Territory");
		map.put(".iq" , "Iraq");
		map.put(".ir" , "Iran Islamic Republic of");
		map.put(".is" , "Iceland");
		map.put(".it" , "Italy");
		map.put(".je" , "Jersey");
		map.put(".jm" , "Jamaica");
		map.put(".jo" , "Jordan");
		map.put(".jobs" , "UNKNOWN");
		map.put(".jp" , "Japan");
		map.put(".ke" , "Kenya");
		map.put(".kg" , "Kyrgyzstan");
		map.put(".kh" , "Cambodia");
		map.put(".ki" , "Kiribati");
		map.put(".km" , "Comoros");
		map.put(".kn" , "Saint Kitts and Nevis");
		map.put(".kp" , "Korea Democratic People's Republic of");
		map.put(".kr" , "Korea Republic of");
		map.put(".kw" , "Kuwait");
		map.put(".ky" , "Cayman Islands");
		map.put(".kz" , "Kazakhstan");
		map.put(".la" , "Lao People's Democratic Republic");
		map.put(".lb" , "Lebanon");
		map.put(".lc" , "Saint Lucia");
		map.put(".li" , "Liechtenstein");
		map.put(".lk" , "Sri Lanka");
		map.put(".lr" , "Liberia");
		map.put(".ls" , "Lesotho");
		map.put(".lt" , "Lithuania");
		map.put(".lu" , "Luxembourg");
		map.put(".lv" , "Latvia");
		map.put(".ly" , "Libyan Arab Jamahiriya");
		map.put(".ma" , "Morocco");
		map.put(".mc" , "Monaco");
		map.put(".md" , "Moldova Republic of");
		map.put(".me" , "Montenegro");
		map.put(".mf" , "Saint Martin");
		map.put(".mg" , "Madagascar");
		map.put(".mh" , "Marshall Islands");
		map.put(".mil" , "United States Military");
		map.put(".mk" , "Macedonia The Former Yugoslav Republic of");
		map.put(".ml" , "Mali");
		map.put(".mm" , "Myanmar");
		map.put(".mn" , "Mongolia");
		map.put(".mo" , "Macao");
		map.put(".mobi" , "UNKNOWN");
		map.put(".mp" , "Northern Mariana Islands");
		map.put(".mq" , "Martinique");
		map.put(".mr" , "Mauritania");
		map.put(".ms" , "Montserrat");
		map.put(".mt" , "Malta");
		map.put(".mu" , "Mauritius");
		map.put(".museum" , "UNKNOWN");
		map.put(".mv" , "Maldives");
		map.put(".mw" , "Malawi");
		map.put(".mx" , "Mexico");
		map.put(".my" , "Malaysia");
		map.put(".mz" , "Mozambique");
		map.put(".na" , "Namibia");
		map.put(".name" ,	"UNKNOWN");
		map.put(".nc" , "New Caledonia");
		map.put(".ne" , "Niger");
		map.put(".net" , "UNKNOWN");
		map.put(".nf" , "Norfolk Island");
		map.put(".ng" , "Nigeria");
		map.put(".ni" , "Nicaragua");
		map.put(".nl" , "Netherlands");
		map.put(".no" , "Norway");
		map.put(".np" , "Nepal");
		map.put(".nr" , "Nauru");
		map.put(".nu" , "Niue");
		map.put(".nz" , "New Zealand");
		map.put(".om" , "Oman");
		map.put(".org" , "UNKNOWN");
		map.put(".pa" , "Panama");
		map.put(".pe" , "Peru");
		map.put(".pf" , "French Polynesia");
		map.put(".pg" , "Papua New Guinea");
		map.put(".ph" , "Philippines");
		map.put(".pk" , "Pakistan");
		map.put(".pl" , "Poland");
		map.put(".pm" , "Saint Pierre and Miquelon");
		map.put(".pn" , "Pitcairn");
		map.put(".pr" , "Puerto Rico");
		map.put(".pro" , "UNKNOWN");
		map.put(".ps" , "Palestinian Territory Occupied");
		map.put(".pt" , "Portugal");
		map.put(".pw" , "Palau");
		map.put(".py" , "Paraguay");
		map.put(".qa" , "Qatar");
		map.put(".re" , "Reunion");
		map.put(".ro" , "Romania");
		map.put(".rs" , "Serbia");
		map.put(".ru" , "Russian Federation");
		map.put(".rw" , "Rwanda");
		map.put(".sa" , "Saudi Arabia");
		map.put(".sb" , "Solomon Islands");
		map.put(".sc" , "Seychelles");
		map.put(".sd" , "Sudan");
		map.put(".se" , "Sweden");
		map.put(".sg" , "Singapore");
		map.put(".sh" , "Saint Helena");
		map.put(".si" , "Slovenia");
		map.put(".sj" , "Svalbard and Jan Mayen");
		map.put(".sk" , "Slovakia");
		map.put(".sl" , "Sierra Leone");
		map.put(".sm" , "San Marino");
		map.put(".sn" , "Senegal");
		map.put(".so" , "Somalia");
		map.put(".sr" , "Suriname");
		map.put(".st" , "Sao Tome and Principe");
		map.put(".su" , "Soviet Union (being phased out)");
		map.put(".sv" , "El Salvador");
		map.put(".sy" , "Syrian Arab Republic");
		map.put(".sz" , "Swaziland");
		map.put(".tc" , "Turks and Caicos Islands");
		map.put(".td" , "Chad");
		map.put(".tel" , "UNKNOWN");
		map.put(".tf" , "French Southern Territories");
		map.put(".tg" , "Togo");
		map.put(".th" , "Thailand");
		map.put(".tj" , "Tajikistan");
		map.put(".tk" , "Tokelau");
		map.put(".tl" , "Timor-Leste");
		map.put(".tm" , "Turkmenistan");
		map.put(".tn" , "Tunisia");
		map.put(".to" , "Tonga");
		map.put(".tp" , "Portugal");
		map.put(".tr" , "Turkey");
		map.put(".travel" , "UNKNOWN");
		map.put(".tt" , "Trinidad and Tobago");
		map.put(".tv" , "Tuvalu");
		map.put(".tw" , "Taiwan");
		map.put(".tz" , "Tanzania United Republic of");
		map.put(".ua" , "Ukraine");
		map.put(".ug" , "Uganda");
		map.put(".uk" , "United Kingdom");
		map.put(".um" , "United States Minor Outlying Islands");
		map.put(".us" , "United States");
		map.put(".uy" , "Uruguay");
		map.put(".uz" , "Uzbekistan");
		map.put(".va" , "Holy See (Vatican City State)");
		map.put(".vc" , "Saint Vincent and the Grenadines");
		map.put(".ve" , "Venezuela Bolivarian Republic of");
		map.put(".vg" , "Virgin Islands British");
		map.put(".vi" , "Virgin Islands U.S.");
		map.put(".vn" , "Vietnam");
		map.put(".vu" , "Vanuatu");
		map.put(".wf" , "Wallis and Futuna");
		map.put(".ws" , "Samoa");
		map.put(".ye" , "Yemen");
		map.put(".yt" , "Mayotte");
		map.put(".yu" , "Yugoslavia (being phased out)");
		map.put(".za" , "South Africa");
		map.put(".zm" , "Zambia");
		map.put(".zw" , "Zimbabwe ");
		return map;
	}
	
	public String getLang(WarcRecord record) throws IOException
	{
		String lang = "?";
		Payload payload = record.getPayload();
		if (payload == null) {
		} else {
			String warcContent = IOUtils.toString(payload.getInputStreamComplete());
			if (warcContent == null && "".equals(warcContent)) {
				// NOP
			} else {
				Document doc = Jsoup.parse(warcContent);
				try{
						detector.append(doc.body().text());
						lang =  detector.detect();
				} catch(Exception e){e.printStackTrace();}
			}
		}
		return lang;
		
//		try {
//			String lang = "UNKNOWN";
//			HeaderLine URI = record.getHeader("WARC-Target-URI");
//			if(URI!=null)
//				for(String key : domain_map.keySet())
//					if(URI.value.contains(key+"/"))
//						lang = domain_map.get(key);
//			if(lang.equals("UNKNOWN") && URI!=null)
//			{
//				System.out.println(URI.value);
//				detector.append(URI.value);
//				lang = detector.detect();
//			}
//			return lang;
//		} catch (LangDetectException e) {System.out.println("Language detection failed error: 02");e.printStackTrace();return "ERROR 02";}
	}
	
	public static void main(String[] args) {
		LanguageDetecter ld = new LanguageDetecter();
			try {
				File file = new File("./example.warc");
				InputStream in = new FileInputStream(file);
				WarcReader reader = WarcReaderFactory.getReader(in);
				WarcRecord record;
				while((record = reader.getNextRecord()) != null)
					System.out.println(ld.getLang(record));
			}
			catch(Exception e){e.printStackTrace();}
	}
}
