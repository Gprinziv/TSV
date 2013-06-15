import mysql.connector
from lxml import etree 
import re

def traverse(root):
   for node in root:
      traverse(node)
      if "caml" in node.tag:
         node.attrib['class'] = node.tag.split('}')[1]
         node.tag = 'span'

def billparse():
   conn = mysql.connector.connect(user="root", database="opengov", password="FlyingP1llows", buffered=True)
   get = conn.cursor()
   put = conn.cursor()

   get.execute("SELECT bill_version_id, bill_xml FROM capublic.bill_version_tbl")
   for (vid, xml) in get:
      try:
         xml = re.sub(r'<\?xm-(insertion|deletion)_mark\?>', r'', xml, flags=re.DOTALL)
         xml = re.sub(r'<\?xm-(insertion|deletion)_mark (?:data="(.*?)")\?>', r'<span class="\1">\2</span>', xml, flags=re.DOTALL)

         xml = re.sub(r'<\?xm-(insertion|deletion)_mark_start\?>', r'<span class="\1">', xml, flags=re.DOTALL)
         xml = re.sub(r'<\?xm-(insertion|deletion)_mark_end\?>', r'</span>', xml, flags=re.DOTALL)

         root = etree.fromstring(xml)
         namespace = {'caml': 'http://lc.ca.gov/legalservices/schemas/caml.1#'}

         title = root.xpath('//caml:Title', namespaces=namespace)[0]

         digest = root.xpath('//caml:DigestText', method="text", namespaces=namespace)[0]

         body = root.xpath('//caml:Bill', namespaces=namespace)[0]

         traverse(body)

         put.execute("UPDATE BillVersion SET title = %s, digest= %s, text = %s WHERE vid = %s", (title.text, etree.tostring(digest), etree.tostring(body), vid))
      except Exception as e:
         print(e)
         err = open("problems/" + vid + ".err", "w")
         err.write(xml)
         err.close()

   err.close()
   conn.commit()
   get.close()
   return

if __name__ == "__main__":
   billparse()
