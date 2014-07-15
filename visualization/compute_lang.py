import json
from collections import defaultdict

language_list = {};
min_balance   =  9999999999999
max_balance   = -9999999999999
total_balance = 0;

file = open('./bitcoinjson129.json')
bitcoins = json.load(file)

for bitcoin in bitcoins:
	for language in bitcoins[bitcoin]["langs"]:
		total_balance+=float(bitcoins[bitcoin]["balance"])
		if language in language_list and not (language_list[language] is None):
			language_list[language]["balance"] += float(bitcoins[bitcoin]["balance"])
		else:
			language_list[language] = {};
			language_list[language]["balance"] = float(bitcoins[bitcoin]["balance"])
for language in language_list:
	balance  = language_list[language]["balance"]
	if(balance<min_balance):
		min_balance = balance;
	if(balance>max_balance):
		max_balance = balance;

stats = {"min balance":min_balance,"max balance":max_balance,"total balance":total_balance}
out = {"stats":stats,"language list":language_list}

print out

with open('language_index.json', 'wb') as fp:
	json.dump(out, fp)
