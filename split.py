#A digidem script to split a database up for import.

#argv[1] = The CA lobbyist registration CSV file to be split by form type.

import csv
import sys

files = []

with open(sys.argv[1], 'rb') as csvfile:
    formreader = csv.reader(csvfile, delimiter='\t')
    h = formreader.next()
    for r in formreader:
        with open("Form" + r[3] + ".csv", "ab") as wf:
            temp = csv.writer(wf)
            if (files.count(r[3]) == 0):
                files.append(r[3])
                temp.writerow((h[3], h[4], h[5], h[6], h[7], h[8], h[12], h[13], h[14], h[61], h[1], h[15], h[16]))
            temp.writerow((r[3], r[4], r[5], r[6], r[7], r[8], r[12], r[13], r[14], r[61], r[1], r[15], r[16]))
