## Annotate.py
## Script to aid in the manual editing of MAVIS transcripts
## author: Bradley Zeller (brzeller@calpoly.edu)

import elementtree.ElementTree as ET

def annotate(path, skip=0, verbose=False):
	xml = ET.parse(path)
	out = open(path.split(".")[0]+"_annot.xml", "wb")

	if len(xml.getroot()) != 2:
		print ("Unknown transcript format")
		return

	body = xml.getroot()[1]
	if body.tag != 'BODY':
		print ("Unknown transcript format")
		return

	get_speaker_attrs.prev_last = ''
	get_speaker_attrs.prev_first = ''
	get_speaker_attrs.prev_conf = 0
	get_speaker_attrs.prev_cont = 0

	counter = 0
	for sync in body:
		counter += 1
		if counter <= skip:
			continue

		print ("SYNC %d" % (counter))
		print ("--------------------\n")
		try:
			get_corrections(sync, verbose)
			print ("\n--------------------\n\n")
		except EOFError:
			print ("\n--------------------\n\n")
			break

	# write corrections
	xml.write(out)

def get_corrections(sync, verbose):
	for para in sync:
		if "ID" not in para.attrib:
			if verbose or not has_corrections(para):
				orig = get_original(para)
				print ("Original: " + orig)
				corr = raw_input("Corrections: ")
				if corr != '':
					choice = new_choice(orig, corr)
					for child in para:
						para.remove(child)
					para.text = ''
					para.append(choice)
	
	get_speaker_attrs(sync)


def new_choice(orig, corr):
	sic_el = ET.Element("sic")
	sic_el.text = orig

	corr_el = ET.Element("corr")
	corr_el.text = corr

	choice_el = ET.Element("choice")
	choice_el.append(sic_el)
	choice_el.append(corr_el)

	return choice_el

def has_corrections(para):
	if len(para) > 0:
		return True
	else:
		return False

def get_original(para):
	if has_corrections(para):
		orig = ""
		if para.text is not None: orig += para.text.strip() + " "
		for choice in para:
			if choice[0].text is not None: orig += choice[0].text.strip() + " "
			if choice.tail is not None: orig += choice.tail.strip() + " "
		return orig
	else:
		return para.text.strip()

def get_speaker_attrs(sync):
	if "speaker_last" in sync.attrib:
		print ("Speaker Last Name: " + sync.attrib['speaker_last'])
		last = raw_input("Correction: ")
	else:
		last = raw_input("Speaker Last Name? ")
	# add attribute
	if last != '':
		sync.attrib['speaker_last'] = last
		get_speaker_attrs.prev_last = last
	else:
		sync.attrib['speaker_last'] = get_speaker_attrs.prev_last



	if "speaker_first" in sync.attrib:
		print ("Speaker First Name: " + sync.attrib['speaker_first'])
		first = raw_input("Correction: ")
	else:
		first = raw_input("Speaker First Name? ")
	# add attribute
	if first != '':
		sync.attrib['speaker_first'] = first
		get_speaker_attrs.prev_first = first
	else:
		sync.attrib['speaker_first'] = get_speaker_attrs.prev_first



	if "conf" in sync.attrib:
		print ("Confidence: " + sync.attrib['conf'])
		conf = raw_input("Correction: ")
	else:
		conf = raw_input("Confidence (1-5)? ")
	# add attribute
	if conf != '':
		sync.attrib['conf'] = str(conf)
		get_speaker_attrs.prev_conf = conf
	else:
		sync.attrib['conf'] = str(get_speaker_attrs.prev_conf)



	if "cont" in sync.attrib:
		print ("Same speaker as last utterance: " + sync.attrib['cont'])
		cont = raw_input("Correction: ")
	else:
		cont = raw_input("Same speaker as last utterance (0-1)? ")
	# add attribute
	if cont != '':
		sync.attrib['cont'] = str(cont)
		get_speaker_attrs.prev_cont = cont
	else:
		sync.attrib['cont'] = str(get_speaker_attrs.prev_cont)

	print ("new attrs: ")
	print (sync.attrib)

