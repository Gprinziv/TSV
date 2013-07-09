import mysql.connector
from lxml import etree 
from sets import Set
import re

dups = Set()
matches = Set()

def test_name(cursor, name, expected):
   cursor.execute('SELECT count(*) FROM legislator_tbl WHERE INSTR("%s", author_name)' % name)
   (count,) = cursor.fetchone()
   if count != expected:
      dups.add(name)
   else:
      matches.add(name)
      #print("Error: found "+str(count)+" instances of "+name)

conn = mysql.connector.connect(user="root", database="capublic", password="FlyingP1llows")
capublic = conn.cursor(buffered=True)

conn2 = mysql.connector.connect(user="root", database="capublic", password="FlyingP1llows")
opengov = conn2.cursor(buffered=True)

capublic.execute("SELECT bill_version_id, name, committee_members FROM bill_version_authors_tbl")

for (vid, name, member_str) in capublic:
   if member_str is None:
      test_name(opengov, name, 1) 
   else:
#      members = member_str.split(',')
#      for member in members:
#         member = re.sub(r'\([^)]*\)| and,? |Senators? |Assembly Members? ', '', member)
#         member = member.strip()
      test_name(opengov, member_str, member_str.count(',') + 1) 

for name in dups:
   print(name)

print(len(dups))
print(len(matches))
