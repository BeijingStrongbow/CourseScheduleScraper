from bs4 import BeautifulSoup
import requests
import regex
import unicodedata
import sys

import urllib

def __main__():
	year = sys.argv[4]
	semester = sys.argv[5]
	if sys.argv[3] == "True":
		courseContentWrite = open(year + semester, 'a')
	else:
		courseContentWrite = open(year + semester, 'w')

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
	headerCount = 0
	for data in coursesContent:
		if(headers.count(data) == 1):
			for cont in data.find('tr', class_='course').contents:
				if (headerCount >= len(headers) - 1 and urlCounter >= totalURLs - 1)2:
					modString = (regex.split('(\d+)', cont.get_text()))
					courseContentWrite.write(
						"(\"" + modString[0] + "\"" + "," + "\"" + modString[1] + "\"" + "," + "\"" + modString[
							2].replace("\"", "") + "\");" + "\n")
				elif (headerCount >= len(headers) - 1 and urlCounter < totalURLs - 1):
					modString = (regex.split('(\d+)', cont.get_text()))
					courseContentWrite.write(
						"(\"" + modString[0] + "\"" + "," + "\"" + modString[1] + "\"" + "," + "\"" + modString[
							2].replace("\"", "") + "\")," + "\n")
				else:
					modString = (regex.split('(\d+)', cont.get_text()))
					courseContentWrite.write(
						"(\"" + modString[0] + "\"" + "," + "\"" + modString[1] + "\"" + "," + "\"" + modString[
							2].replace("\"", "") + "\")," + "\n")
			headerCount += 1
			
		elif(closed.count(data) == 0 and cancelled.count(data) == 0):
			for sectionRow in data.find_all('tr', class_='st'):
				counter = 0
				data = sectionRow.find_all('td', {'class': lambda x : x != "session-label"})
				if len(data) == 10 and (data[5].get_text() != "" or data[6].get_text() != ""):
					courseContentWrite.write("\t(")
					courseContentWrite.write("\"" + sanitize(data[0].get_text(), "something") + "\"" + ",")
					courseContentWrite.write("\"" + sanitize(data[2].get_text(), "something") + "\"" + ",")
					courseContentWrite.write("\"" + sanitize(data[5].get_text(), "day") + "\"" + ",")
					courseContentWrite.write("\"" + sanitize(data[6].get_text(), "something") + "\"" + ",")
					courseContentWrite.write("\"" + sanitize(data[8].get_text(), "something") + "\"" + ")\n")  
				elif len(data) == 11 and (data[5].get_text() != "" or data[6].get_text() != ""):
					courseContentWrite.write("\t(")
					courseContentWrite.write("\"" + sanitize(data[0].get_text(), "something") + "\"" + ",")
					courseContentWrite.write("\"" + sanitize(data[2].get_text(), "something") + "\"" + ",")
					courseContentWrite.write("\"" + sanitize(data[5].get_text(), "day") + "\"" + ",")
					courseContentWrite.write("\"" + sanitize(data[6].get_text(), "something") + "\"" + ",")
					courseContentWrite.write("\"" + sanitize(data[9].get_text(), "something") + "\"" + ")\n")
				elif len(data) == 9:
					section = sanitize(data[0].get_text(), "something")
					number = sanitize(data[2].get_text(), "something")
				elif len(data) == 6 and (data[0].get_text() != "" or data[1].get_text() != ""):
					print("here")
					courseContentWrite.write("\t(")
					courseContentWrite.write("\"" + sanitize(section, "something") + "\"" + ",")
					courseContentWrite.write("\"" + sanitize(number, "something") + "\"" + ",")
					courseContentWrite.write("\"" + sanitize(data[0].get_text(), "day") + " " + sanitize(sectionRow.find('td', class_='session-label').get_text(), "something") + "\"" + ",")
					courseContentWrite.write("\"" + sanitize(data[1].get_text(), "something") + "\"" + ",")
					courseContentWrite.write("\"" + sanitize(data[4].get_text(), "something") + "\"" + ")\n")
					
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