# extracted code from BlindElephant code for BE db file update

import tempfile
import urllib
import os
import tarfile

dbtar_url = "http://blindelephant.svn.sourceforge.net/viewvc/blindelephant/trunk/src/blindelephant/dbs/?view=tar"
#untar_dir = os.path.join(wac.getDbDir(), os.path.pardir); #so that dbs/ in tar overlays existing dbs/

tmp = tempfile.NamedTemporaryFile();
#  print >> wac.DEFAULT_LOGFILE, "Fetching latest DB files from", dbtar_url
urllib.urlretrieve("http://blindelephant.svn.sourceforge.net/viewvc/blindelephant/trunk/src/blindelephant/dbs/?view=tar", tmp.name)
f = tarfile.open(tmp.name)
#  print >> wac.DEFAULT_LOGFILE, "Extracting to ", untar_dir
f.extractall()
print "done!"
tmp.close()
