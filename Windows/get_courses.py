from bs4 import BeautifulSoup
import requests
import regex
import unicodedata
import sys
import os

import urllib

def __main__():
	year = sys.argv[4]
	semester = sys.argv[5]
	if sys.argv[3] == "True":
		file1 = os.path.normpath('database/' + year + semester + '.txt')
		courseContentWrite = open(file1, 'a')
	else:
		file2 = os.path.normpath('database/' + year + semester + '.txt')
		courseContentWrite = open(file2, 'w')

	urlCounter = 0
	
	fullURL = sys.argv[1]
	totalURLs = int(sys.argv[2])
	r = urllib.request.urlopen(fullURL).read()
	urlSoup = BeautifulSoup(r, "html.parser")
	
	headers = urlSoup.find_all('tbody', class_='course-header')
	closed = urlSoup.find_all('tbody', class_="closed")
	cancelled = urlSoup.find_all('tbody', class_="cancelled")
	coursesContent = urlSoup.find_all('tbody')
	lineCounter = 0
	section = ""
	number = ""
	for data in coursesContent:
		if(headers.count(data) == 1):
			number = data.find('span', class_="number")
			name = data.find('span', class_="name")
			courseContentWrite.write("(\"" + sanitize(number.get_text(), "something") + "\"|\"" + sanitize(name.get_text(), "something") + "\")\n")
			
		elif(closed.count(data) == 0 and cancelled.count(data) == 0):
			for sectionRow in data.find_all('tr', class_='st'):
				counter = 0
				data = sectionRow.find_all('td', {'class': lambda x : x != "session-label"})
				if len(data) == 10 and (data[5].get_text() != "" or data[6].get_text() != ""):
					courseContentWrite.write("\t(\"" + sanitize(data[0].get_text(), "something") + "\"" + "|" +
						"\"" + sanitize(data[1].get_text(), "something") + "\"" + "|" +
						"\"" + sanitize(data[2].get_text(), "something") + "\"" + "|" +
						"\"" + sanitize(data[5].get_text(), "day") + "\"" + "|" +
						"\"" + sanitize(data[6].get_text(), "something") + "\"" + "|" +
						"\"" + sanitize(data[8].get_text(), "something") + "\"" + ")\n")
				elif len(data) == 11 and (data[5].get_text() != "" or data[6].get_text() != ""):
					courseContentWrite.write("\t(\"" + sanitize(data[0].get_text(), "something") + "\"" + "|" + 
						"\"" + sanitize(data[1].get_text(), "something") + "\"" + "|" +
						"\"" + sanitize(data[2].get_text(), "something") + "\"" + "|" +
						"\"" + sanitize(data[5].get_text(), "day") + "\"" + "|" +
						"\"" + sanitize(data[6].get_text(), "something") + "\"" + "," + 
						"\"" + sanitize(data[9].get_text(), "something") + "\"" + ")\n")
				elif len(data) == 9:
					section = sanitize(data[0].get_text(), "something")
					type = sanitize(data[1].get_text(), "something")
					number = sanitize(data[2].get_text(), "something")
				elif len(data) == 6 and (data[0].get_text() != "" or data[1].get_text() != ""):
					courseContentWrite.write("\t(\"" + section + "\"" + "|" +
						"\"" + type + "\"" + "|" +
						"\"" + number + "\"" + "|" +
						"\"" + sanitize(data[0].get_text(), "day") + " " + sanitize(sectionRow.find('td', class_='session-label').get_text(), "something") + "\"" + "|" +
						"\"" + sanitize(data[1].get_text(), "something") + "\"" + "|" +
						"\"" + sanitize(data[4].get_text(), "something") + "\"" + ")\n")
			lineCounter += 1
	urlCounter+=1


def sanitize(sanText, unit):
	cleanText = ""
	if unit == "day":
		if sanText == "Appointment":
			cleanText = unicodedata.normalize('NFKD', sanText)
		for char in sanText:
			if char == 'M':
				cleanText += "M"
			elif char == 'T':
				cleanText += "T"
			elif char == 'W':
				cleanText += "W"
			elif char == 'U':
				cleanText += "U"
			elif char == 'F':
				cleanText += "F"
	else:
		cleanText = unicodedata.normalize('NFKD', sanText)
	return cleanText

def shouldLoad(css_class):
	print(css_class)
	return css_class == "section" and css_class != "section closed" and css_class != "section cancelled" and css_class != "section even cancelled" and css_class != "section even closed"
__main__()