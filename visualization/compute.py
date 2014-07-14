import json
from collections import defaultdict

country_list = defaultdict(dict);
min_balance   =  9999999999999
max_balance   = -9999999999999
total_balance = 0;

file = open('./test_file.json')
bitcoins = json.load(file)

for bitcoin in bitcoins:
	for country in bitcoins[bitcoin]["countries"]:
		balance = float(bitcoins[bitcoin]["balance"])	
		if(max_balance<balance):
			max_balance = balance
		if(min_balance>balance):
			min_balance = balance
		total_balance+=balance
		try:
			country_list[country]["balance"] += bitcoins[bitcoin]["balance"]
			country_list[country]["lang"] += bitcoins[bitcoin]["langs"]
		except Exception, e:
			country_list[country]["balance"] = bitcoins[bitcoin]["balance"]
			country_list[country]["lang"] = bitcoins[bitcoin]["langs"]

stats = {"min balance":min_balance,"max balance":max_balance,"total balance":total_balance}
out = {"stats":stats,"country list":country_list}

print out

with open('country_index.json', 'wb') as fp:
	json.dump(out, fp)