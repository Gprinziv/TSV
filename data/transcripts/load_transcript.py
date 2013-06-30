from lxml import etree
import sys
import string
import re
import mysql.connector
import traceback

legislators = {}
conn = mysql.connector.connect(user="root", database="opengov", buffered=True)
get = conn.cursor()

class Utterance:
   put = conn.cursor()

   def __init__(self, first, last, time, text):
      if first not in legislators or last not in legislators[first]:
         self.pid = None
      else:
         self.pid   = legislators[first][last]
      self.time  = int(time)
      self.text  = text
      self.first = str(first)
      self.last  = str(last)

   def save(self):
      Utterance.put.execute("INSERT INTO Utterance (date, bid, cid, pid, "
            "time, text, first, last) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)",
            (sys.argv[2], sys.argv[3], sys.argv[4],
               self.pid, self.time, self.text, self.first, self.last))

failures = set()

get.execute("SELECT first, last, pid FROM Legislator")
for (first, last, pid) in get:
   if first not in legislators:
      legislators[first] = {}

   legislators[first][last] = pid

transcript = etree.parse(sys.argv[1])
utterances = []

for utterance_element in transcript.xpath("BODY/SYNC"):
   speaker_first = utterance_element.xpath('@speaker_first')
   speaker_last  = utterance_element.xpath('@speaker_last')
   Start         = utterance_element.xpath('@Start')

   first = speaker_first[0] if len(speaker_first) > 0 else ""
   last  = speaker_last[0]  if len(speaker_last)  > 0 else ""
   time  = Start[0]         if len(Start)         > 0 else -1

   text = ' '.join(utterance_element.xpath('descendant::*[name() != "sic"]/text()'))
   text = ' '.join(text.split())
   text = re.sub(r' ([.,?!])', r'\1', text)
   text = text.encode('ascii', 'ignore')

   try:
      utterance = Utterance(first, last, time, text)
      utterance.save()
   except:
      traceback.print_exc()
      failures.add((first, last))

for (first, last) in failures:
   print("%s, %s" % (last, first))

conn.commit()
conn.close()
