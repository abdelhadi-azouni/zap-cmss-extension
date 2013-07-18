#####################################################################
#     this code convert BlindElephant db pkl files into xml files   #
#####################################################################
__author__ = 'abdelhadi@ZAP_TEAM'

import pickle
import urllib
import os
import tarfile


#
# Checking BlindElephant database from BlinElephant svn on sourceforge repo
# the db pkl files are converted into xml ones
#

# Checking BlindElephant db

def db_check():
    file = open("file","ab")
    #  print >> wac.DEFAULT_LOGFILE, "Fetching latest DB files from", dbtar_url
    src = urllib.urlretrieve("http://blindelephant.svn.sourceforge.net/viewvc/blindelephant/trunk/src/blindelephant/dbs/?view=tar","file")
    f = tarfile.open("file")
    #  print >> wac.DEFAULT_LOGFILE, "Extracting to ", untar_dir
    f.extractall()
    print "done!"


#pkl2xml specific conversion implementation TODO generic conversion
def pkl2xml_S(filename):
    f = open(filename,"rb")
    print filename
    content = (pickle.load(f))[0]
    taille = len(content)

    f = open(filename+".xml","w")

    for i in range (0,taille):
        print >> f,  "<file path=\""+(content.keys())[i]+"\">"
        dict = (content.values())[i]
        taille2 = len(dict)
        for j in range (0,taille2):
            print >> f, "     <hash md5=\""+(dict.keys())[j]+"\">"
            values = (dict.values())[j]
            taille3 = len(values)
            for k in range(0,taille3):
                print >> f, "         <version>"+str(values[k])+"</version>"
            print >> f, "     </hash>"
        print >> f, "</file>"
    f.close()

def pklSearcher(path):
    #i=0
    pkllist = []
    for item in os.walk(path):
        path = item[0]
        for file in item[2]:
            if str(file).endswith(".pkl"):
                pkllist.append(""+path+"/"+file)
                #i = i+1
    # print i # i pkl files
    return pkllist


for file in pklSearcher("./"):
    pkl2xml_S(file)
