import json
from collections import defaultdict

language_list  = {};
min_sent    =  9999999999999
max_sent    = -9999999999999
min_links    =  9999999999999
max_links    = -9999999999999
min_received    =  9999999999999
max_received    = -9999999999999
min_balance    =  9999999999999
max_balance    = -9999999999999
min_average_balance = 9999999999999
max_average_balance = -9999999999999
total_balance  = 0;
total_sent	   = 0;
total_received = 0;
total_links    = 0;

file = open('./results.json')
bitcoins = json.load(file)

for bitcoin in bitcoins:
	for language in bitcoins[bitcoin]["langs"]:
		total_balance+=float(bitcoins[bitcoin]["balance"])
		total_sent+=float(bitcoins[bitcoin]["sent"])
		total_received+=float(bitcoins[bitcoin]["received"])
		total_links+=1
		if language in language_list and not (language_list[language] is None):
			language_list[language]["balance"] += float(bitcoins[bitcoin]["balance"])
			language_list[language]["sent"] += float(bitcoins[bitcoin]["sent"])
			language_list[language]["received"] += float(bitcoins[bitcoin]["received"])
			language_list[language]["links"]   += 1
		else:
			language_list[language] = {};
			language_list[language]["balance"] = float(bitcoins[bitcoin]["balance"])
			language_list[language]["sent"] = float(bitcoins[bitcoin]["sent"])
			language_list[language]["received"] = float(bitcoins[bitcoin]["received"])
			language_list[language]["links"] = 1
for language in language_list:
	balance  = language_list[language]["balance"]
	if(balance<min_balance):
		min_balance = balance;
	if(balance>max_balance):
		max_balance = balance;
	average_balance = language_list[language]["balance"]/language_list[language]["links"]
	if(average_balance<min_average_balance):
		min_average_balance = average_balance;
	if(average_balance>max_average_balance):
		max_average_balance = average_balance;
	sent  = language_list[language]["sent"]
	if(sent<min_sent):
		min_sent = sent;
	if(sent>max_sent):
		max_sent = sent;
	received  = language_list[language]["received"]
	if(received<min_received):
		min_received = received;
	if(received>max_received):
		max_received = received;
	links  = language_list[language]["links"]
	if(links<min_links):
		min_links = links;
	if(links>max_links):
		max_links = links;

stats = {"min links":min_links,"max links":max_links,"min received":min_received,"max received":max_received,"min sent":min_sent,"max sent":max_sent,"min balance":min_balance,"max balance":max_balance,"min average balance":min_average_balance,"max average balance":max_average_balance,"total balance":total_balance,"total sent":total_sent,"total received":total_received,"total number of links":total_links}
out = {"stats":stats,"language list":language_list}

print out

with open('language_index.json', 'wb') as fp:
	json.dump(out, fp)
