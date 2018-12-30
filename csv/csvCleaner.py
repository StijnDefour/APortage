import csv

with open('ellermansstraat-lokaal.csv','wb') as output:
	with open('ellermansstraat-verdiepingen.csv','wb') as output2:
		output.write('"campusafk","verdiepnr","lokaalnr"\n')
		output2.write('"campusafk","verdiepnr"\n')
		with open('ellermansstraat.csv') as csvfile:
			reader = csv.DictReader(csvfile)
			verdiepingen = set()
			for row in reader:
				campusafk = row["lokaal_ref"].split(' ')[0]
				verdiepnr = row["lokaal_ref"].split(' ')[1].split('.')[0]
				lokaalnr = row["lokaal_ref"].split(' ')[1].split('.')[1]
				if(len(lokaalnr) < 3):
					lokaalnr = "0" + lokaalnr
				output.write(campusafk + "," + verdiepnr + "," + lokaalnr + "\n")
				if(verdiepnr not in verdiepingen):
					verdiepingen.add(verdiepnr)
					output2.write(campusafk + "," + verdiepnr + "\n");

print("CSV Created")

csvfile.close()
output2.close()
output.close()