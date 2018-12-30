import csv

with open('ellermansstraat-lokaal.json','wb') as output:
	with open('ellermansstraat-verdiepingen.json','wb') as output2:
		output.write('[')
		output2.write('[')
		with open('ellermansstraat.csv') as csvfile:
			reader = csv.DictReader(csvfile)
			verdiepingen = set()
			i = 0

			for row in reader:
				campusafk = row["lokaal_ref"].split(' ')[0]
				verdiepnr = row["lokaal_ref"].split(' ')[1].split('.')[0]
				lokaalnr = row["lokaal_ref"].split(' ')[1].split('.')[1]
				if(len(lokaalnr) < 3):
					lokaalnr = "0" + lokaalnr
				if (i!=0):
					output.write(',')
				output.write(' { "campusafk" : "' + campusafk + '" , "verdiepnr" : "' + verdiepnr + '" , "lokaalnr" : "' + lokaalnr + '" , "volgorde" : ' + int(lokaalnr) + '} ')
				if(verdiepnr not in verdiepingen):
					verdiepingen.add(verdiepnr)
					if (i!=0):
						output2.write(',')
					output2.write(' { "campusafk" : "' + campusafk + '" , "verdiepnr" : "' + verdiepnr + '"} ')
				i += 1

			output.write(']')
			output2.write(']')

print("CSV Created")

csvfile.close()
output2.close()
output.close()