from lxml import etree
import sys

transcript = etree.parse(sys.argv[1])

for utterance in transcript.xpath("BODY/SYNC"):
   if len(utterance) != 2:
      continue

   text = ""
   line = utterance.xpath("P[not(@ID)]")

   if len(line) != 1:
      continue

   for child in line:
      if child.tag != "sic":
         print(child.text)
