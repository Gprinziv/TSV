#A digidem script to split a database up for import.

#argv[1] = The CA lobbyist registration CSV file to be split by form type.

import csv
import sys

files = []

with open(sys.argv[1], 'rb') as csvfile:
    formreader = csv.reader(csvfile, delimiter='\t')
    header = formreader.next()
    for row in formreader:
        with open("Form" + row[3] + ".csv", "ab") as wf:
            temp = csv.writer(wf)
            if (files.count(row[3]) == 0):
                files.append(row[3])
                temp.writerow(header)
            temp.writerow(row)
        #write row to file row[3]
    print files
