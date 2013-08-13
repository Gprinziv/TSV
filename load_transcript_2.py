#Terminal arguments the for usage
#argv[1] = The file to be entered inte the database 
#argv[2] = date (used in Utterance class)
#argv[3] = bid (used in utterance class)
#argv[4] = cid (used in utterance class)


from lxml import etree
import sys
import string
import re
import mysql.connector
import traceback

#Dictionary of legislators. The key is the legislator's first name and item is
#another dictionary that contains the last name's of legislator's with the same
#first name. The item for the last name dictionary is the pid (process id?)
legislators = {}
#Connects to the mysql database
conn = mysql.connector.connect(user="root", database="opengov", buffered=True)
#Creates an object that can iterate over the mySQL database
get = conn.cursor()

class Utterance:
   put = conn.cursor()

   #Begin and end were added to the constructor and time was removed  
   def __init__(self, first, last, begin, end, text):
      if first not in legislators or last not in legislators[first]:
         self.pid = None
      else:
         self.pid   = legislators[first][last]
      #Begin and end were added
      self.begin = str(begin)
      self.end = str(end)
      self.text  = text
      self.first = str(first)
      self.last  = str(last)

   def save(self):
      #The following cols in the table could have changed and might have to be updated
      #Ask Jorge about this since he's in charge of the database
      Utterance.put.execute("REPLACE INTO Utterance (date, bid, cid, pid, "
            "time, text, first, last) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)",
            (sys.argv[2], sys.argv[3], sys.argv[4],
               self.pid, self.begin, self.end, self.text, self.first, self.last))

failures = set()

#Populates the legislators dictionary
#Could this be optimized in any way? It seems like a bit of a waste to pull all
#legislators into the dictionary.
get.execute("SELECT first, last, pid FROM Legislator")
for (first, last, pid) in get:
   if first not in legislators:
      legislators[first] = {}
   legislators[first][last] = pid

transcript = etree.parse(sys.argv[1])
utterances = []

for utterance_element in transcript.xpath("BODY/P"):
   speaker_first = utterance_element.xpath('@first')
   speaker_last  = utterance_element.xpath('@last')
   #Added xpaths for the begenning and end
   Begin         = utterance_element.xpath('@begin')
   End           = utterance_element.xpath('@end')
   cont          = utterance_element.xpath('@cont')

   first = speaker_first[0] if len(speaker_first) > 0 else ""
   last  = speaker_last[0]  if len(speaker_last)  > 0 else ""
   #Begin and end added
   begin = Begin[0]         if len(Begin)         > 0 else ""
   end   = End[0]           if len(End)           > 0 else ""
   cont  = cont[0]          if len(cont)          > 0 else '0'

   text = ' '.join(utterance_element.xpath('descendant::*[name() != "sic"]/text()'))
   text = ' '.join(text.split())
   #Removes repeated punctuation
   text = re.sub(r' ([.,?!])', r'\1', text)
   text = text.encode('ascii', 'ignore')

   #In the database, text of continuous utterances is concatinated
   if cont == '1':
      utterance.text += " " + text
   else:
      try:
         #Modifed the Utterance Constructer to follow the above
         utterance = Utterance(first, last, begin, end, text)
         utterances.append(utterance)
      except:
         traceback.print_exc()
         failures.add((first, last))

for utterance in utterances:
   utterance.save()

for (first, last) in failures:
   print("%s, %s" % (last, first))

conn.commit()
cnn.close()
