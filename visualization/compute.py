import json
from collections import defaultdict

country_list = {};
min_balance   =  9999999999999
max_balance   = -9999999999999
total_balance = 0;

file = open('./bitcoinjson129.json')
bitcoins = json.load(file)

for bitcoin in bitcoins:
	for country in bitcoins[bitcoin]["countries"]:
		total_balance+=float(bitcoins[bitcoin]["balance"])
		if country in country_list and not (country_list[country] is None):
			country_list[country]["balance"] += float(bitcoins[bitcoin]["balance"])
		else:
			country_list[country] = {};
			country_list[country]["balance"] = float(bitcoins[bitcoin]["balance"])
for country in country_list:
	balance  = country_list[country]["balance"]
	if(balance<min_balance):
		min_balance = balance;
	if(balance>max_balance):
		max_balance = balance;

stats = {"min balance":min_balance,"max balance":max_balance,"total balance":total_balance}
out = {"stats":stats,"country list":country_list}

print out

with open('country_index.json', 'wb') as fp:
	json.dump(out, fp)
