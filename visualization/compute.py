import json
from collections import defaultdict

country_list   = {};
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

file = open('./bitcoinjson129.json')
bitcoins = json.load(file)

for bitcoin in bitcoins:
	for country in bitcoins[bitcoin]["countries"]:
		if country != "UNKNOWN":
			total_balance+=float(bitcoins[bitcoin]["balance"])
			total_sent+=float(bitcoins[bitcoin]["sent"])
			total_received+=float(bitcoins[bitcoin]["received"])
			total_links+=1
			if country in country_list and not (country_list[country] is None):
				country_list[country]["balance"] += float(bitcoins[bitcoin]["balance"])
				country_list[country]["sent"] += float(bitcoins[bitcoin]["sent"])
				country_list[country]["received"] += float(bitcoins[bitcoin]["received"])
				country_list[country]["links"]   += 1
			else:
				country_list[country] = {};
				country_list[country]["balance"] = float(bitcoins[bitcoin]["balance"])
				country_list[country]["sent"] = float(bitcoins[bitcoin]["sent"])
				country_list[country]["received"] = float(bitcoins[bitcoin]["received"])
				country_list[country]["links"] = 1
for country in country_list:
	balance  = country_list[country]["balance"]
	if(balance<min_balance):
		min_balance = balance;
	if(balance>max_balance):
		max_balance = balance;
	average_balance = country_list[country]["balance"]/country_list[country]["links"]
	if(average_balance<min_average_balance):
		min_average_balance = average_balance;
	if(average_balance>max_average_balance):
		max_average_balance = average_balance;
	sent  = country_list[country]["sent"]
	if(sent<min_sent):
		min_sent = sent;
	if(sent>max_sent):
		max_sent = sent;
	received  = country_list[country]["received"]
	if(received<min_received):
		min_received = received;
	if(received>max_received):
		max_received = received;
	links  = country_list[country]["links"]
	if(links<min_links):
		min_links = links;
	if(links>max_links):
		max_links = links;

stats = {"min links":min_links,"max links":max_links,"min received":min_received,"max received":max_received,"min sent":min_sent,"max sent":max_sent,"min balance":min_balance,"max balance":max_balance,"min average balance":min_average_balance,"max average balance":max_average_balance,"total balance":total_balance,"total sent":total_sent,"total received":total_received,"total number of links":total_links}
out = {"stats":stats,"country list":country_list}

print out

with open('country_index.json', 'wb') as fp:
	json.dump(out, fp)
