import json
from collections import defaultdict

country_list = defaultdict(dict);

file = open('./swag.json')
bitcoins = json.load(file)

for bitcoin in bitcoins:
	for country in bitcoins[bitcoin]["countries"]:
		try:
			country_list[country]["balance"] += bitcoins[bitcoin]["balance"]
			country_list[country]["lang"] += bitcoins[bitcoin]["langs"]
		except Exception, e:
			country_list[country]["balance"] = bitcoins[bitcoin]["balance"]
			country_list[country]["lang"] = bitcoins[bitcoin]["langs"]
		
for country in country_list:
	print country , country_list[country]


with open('country_index.json', 'wb') as fp:
	json.dump(country_list, fp)