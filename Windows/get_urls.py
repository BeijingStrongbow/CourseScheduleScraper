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
            print("https://courses.ksu.edu/" + semester + str(year) + "/" + str(link.get("href")))
            sys.stdout.flush()
			
__main__()