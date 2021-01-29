#Charlie Scott
#cscott20@calpoly.edu
import pyspark
import sys
import re
import pickle
import numpy as np
def df(directory):
    sc = pyspark.SparkContext()
    stops = set(sc.textFile("/data/stopwords.txt").collect())
    frequentWords = sc.pickleFile("hdfs://ambari-node1.csc.calpoly.edu:8020/user/cscott20/test/frequentWords.p").keys().collect()
    documents = sc.wholeTextFiles(directory)
    document = documents.mapValues(breakIntoWords)
    document = document.flatMapValues(lambda x: wordFilter(frequentWords, stops, x)).map(mapHelper).reduceByKey(lambda a, b: a + b).mapValues(docFreq)
    document = document.flatMap(flatten)
    document = document.map(keyValArrange)
    document = document.reduceByKey(lambda a, b : a + b).map(derearange).reduceByKey(lambda a,b : a + " " + b).mapValues(makeLis)
    print(document.collect())
    docText = document.collect()
    for x in docText:
        sc.parallelize(x[1]).saveAsPickleFile("hdfs://ambari-node1.csc.calpoly.edu:8020/user/cscott20/test/TermFrequency-" + x[0].split("/Guttenberg/")[1].split(".txt")[0] + ".p")
    return document

def makeLis(x):
    new = []
    for i in x.split(" "):
        new.append((i.split("--")[0], i.split("--")[1]))
    return new    


def derearange(x):
    return(x[0][0], (x[0][1] +"--"+ str(x[1])))


def keyValArrange(x):
    return ((x[0],x[1][0]), x[1][1])

def flatten(x):
    word = x[0]
    fDic = x[1]
    for f in fDic:
        yield (f, (word, fDic[f]))
    return

def aggregatorSum(x, y):
        y.append(x)
        return y

def finish(x,y):
    return (x,y)


def mapHelper(x):
    return (x[1], [x[0]])

def docFreq(x):
    files ={"hdfs://ambari-node1.csc.calpoly.edu:8020/data/Guttenberg/11-0.txt":0,
"hdfs://ambari-node1.csc.calpoly.edu:8020/data/Guttenberg/1342-0.txt":0,
"hdfs://ambari-node1.csc.calpoly.edu:8020/data/Guttenberg/1952-0.txt":0,
"hdfs://ambari-node1.csc.calpoly.edu:8020/data/Guttenberg/219-0.txt":0,
"hdfs://ambari-node1.csc.calpoly.edu:8020/data/Guttenberg/2701-0.txt":0,
"hdfs://ambari-node1.csc.calpoly.edu:8020/data/Guttenberg/76-0.txt":0,
"hdfs://ambari-node1.csc.calpoly.edu:8020/data/Guttenberg/84-0.txt":0,
"hdfs://ambari-node1.csc.calpoly.edu:8020/data/Guttenberg/98-0.txt":0,
"hdfs://ambari-node1.csc.calpoly.edu:8020/data/Guttenberg/pg1080.txt":0,
"hdfs://ambari-node1.csc.calpoly.edu:8020/data/Guttenberg/pg1661.txt":0,
"hdfs://ambari-node1.csc.calpoly.edu:8020/data/Guttenberg/pg844.txt":0}
    for fname in x:
        files[fname] = x.count(fname)
    return files 

    
def breakIntoWords(doc):
    rawOut = doc.strip().split(" ")
    out = [x.strip() for x in rawOut]
    return out

def wordFilter(frequentWords, stops, row):
    stops.add("guttenbergtm")
    stops.add("guttenberg")
    for word in row:
        newWord = re.sub('[^a-zA-Z0-9]','', word).lower()
        if (not newWord in stops) and (word in frequentWords):
            yield newWord
    return

df(sys.argv[1])
