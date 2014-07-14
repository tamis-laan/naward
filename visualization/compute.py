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
		total_balance += bitcoins[bitcoin]["balance"]
		if bitcoins[bitcoin]["balance"]<min_balance:
			min_balance = bitcoins[bitcoin]["balance"]
		if bitcoins[bitcoin]["balance"]>max_balance:
			max_balance = bitcoins[bitcoin]["balance"]
		try:
			country_list[country]["balance"] += bitcoins[bitcoin]["balance"]
			country_list[country]["lang"] += bitcoins[bitcoin]["langs"]
		except Exception, e:
			country_list[country]["balance"] = bitcoins[bitcoin]["balance"]
			country_list[country]["lang"] = bitcoins[bitcoin]["langs"]
		
stats = {"min_balance":min_balance,"max_balance":0,"total_balance":total_balance}
out = {"stats":stats,"country list":country_list}

print out

with open('country_index.json', 'wb') as fp:
	json.dump(out, fp)