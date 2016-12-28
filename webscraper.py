from bs4 import BeautifulSoup
import requests
import regex
import unicodedata
import sys

import urllib

def __main__():
    year = sys.argv[1];
    semester = sys.argv[2];

    schedulePull = semester + str(year) + "/schedule.html"

    data = requests.get("https://courses.k-state.edu/" + schedulePull)

    scheduleURLS = data.text

    soup = BeautifulSoup(scheduleURLS, "html.parser")

    urlList = []
    for link in soup.find_all('a'):
        if( len(str(link.get("href"))) < 7 and (str(link.get("href")) != "/" and str(link.get("href")) != "None") ):
            urlList.append(str(link.get("href")))
    print(len(urlList))
    sys.stdout.flush()
    courseHeadWrite = open('courseNum.txt', 'w')

    courseContentWrite = open('courseCont.txt', 'w')

    urlCounter = 0
    for url in urlList:
        fullURL = "https://courses.ksu.edu/" + semester + str(year) + "/" + url
        print(fullURL)
        sys.stdout.flush()
        r = urllib.request.urlopen(fullURL).read()
        urlSoup = BeautifulSoup(r, "html.parser")
        coursesHeader = urlSoup.find_all('tr', class_='course')

        headerCount = 0
        for c in coursesHeader:
            for cont in c.contents:
                if (headerCount >= len(coursesHeader) - 1 and urlCounter >= len(urlList) - 1):
                    modString = (regex.split('(\d+)', cont.get_text()))
                    courseHeadWrite.write(
                        "(\"" + modString[0] + "\"" + "," + "\"" + modString[1] + "\"" + "," + "\"" + modString[
                            2].replace("\"", "") + "\");" + "\n")
                elif (headerCount >= len(coursesHeader) - 1 and urlCounter < len(urlList) - 1):
                    modString = (regex.split('(\d+)', cont.get_text()))
                    courseHeadWrite.write(
                        "(\"" + modString[0] + "\"" + "," + "\"" + modString[1] + "\"" + "," + "\"" + modString[
                            2].replace("\"", "") + "\")," + "\n")
                else:
                    modString = (regex.split('(\d+)', cont.get_text()))
                    courseHeadWrite.write(
                        "(\"" + modString[0] + "\"" + "," + "\"" + modString[1] + "\"" + "," + "\"" + modString[
                            2].replace("\"", "") + "\")," + "\n")
                headerCount += 1


        closed = urlSoup.find_all('tbody', class_="closed")
        cancelled = urlSoup.find_all('tbody', class_="cancelled")
        coursesContent = urlSoup.find_all('tbody', class_="section")
        lineCounter = 0
        section = ""
        number = ""
        for data in coursesContent:
            if(closed.count(data) == 0 and cancelled.count(data) == 0):
                for sectionRow in data.find_all('tr', class_='st'):
                    counter = 0
                    data = sectionRow.find_all('td', {'class': lambda x : x != "session-label"})
                    if len(data) == 10 and (data[5].get_text() != "" or data[6].get_text() != ""):
                        courseContentWrite.write("(")
                        courseContentWrite.write("\"" + sanitize(data[0].get_text(), "something") + "\"" + ",")
                        courseContentWrite.write("\"" + sanitize(data[2].get_text(), "something") + "\"" + ",")
                        courseContentWrite.write("\"" + sanitize(data[5].get_text(), "day") + "\"" + ",")
                        courseContentWrite.write("\"" + sanitize(data[6].get_text(), "something") + "\"" + ",")
                        courseContentWrite.write("\"" + sanitize(data[8].get_text(), "something") + "\"" + ")\n")  
                    elif len(data) == 11 and (data[5].get_text() != "" or data[6].get_text() != ""):
                        courseContentWrite.write("(")
                        courseContentWrite.write("\"" + sanitize(data[0].get_text(), "something") + "\"" + ",")
                        courseContentWrite.write("\"" + sanitize(data[2].get_text(), "something") + "\"" + ",")
                        courseContentWrite.write("\"" + sanitize(data[5].get_text(), "day") + "\"" + ",")
                        courseContentWrite.write("\"" + sanitize(data[6].get_text(), "something") + "\"" + ",")
                        courseContentWrite.write("\"" + sanitize(data[9].get_text(), "something") + "\"" + ")\n")
                    elif len(data) == 9:
                        section = sanitize(data[0].get_text(), "something")
                        number = sanitize(data[2].get_text(), "something")
                    elif len(data) == 6 and (data[0].get_text() != "" or data[1].get_text() != ""):
                        courseContentWrite.write("(")
                        courseContentWrite.write("\"" + sanitize(section, "something") + "\"" + ",")
                        courseContentWrite.write("\"" + sanitize(number, "something") + "\"" + ",")
                        courseContentWrite.write("\"" + sanitize(data[0].get_text(), "day") + "\"" + ",")
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
