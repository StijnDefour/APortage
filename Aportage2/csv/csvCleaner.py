import csv

with open('ellermansstraat-cleaned.csv','wb') as output:
	output.write('"campusafk","verdiepnr","lokaalnr"\n')
	with open('ellermansstraat.csv') as csvfile:
		reader = csv.DictReader(csvfile)
		for row in reader:
			campusafk = row["lokaal_ref"].split(' ')[0]
			verdiepnr = row["lokaal_ref"].split(' ')[1].split('.')[0]
			lokaalnr = row["lokaal_ref"].split(' ')[1].split('.')[1]
			if(len(lokaalnr) < 3):
				lokaalnr = "0" + lokaalnr
			output.write(campusafk + "," + verdiepnr + "," + lokaalnr + ",\n")

print("CSV Created")

csvfile.close()
output.close()