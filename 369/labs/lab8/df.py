#Charlie Scott
#cscott20@calpoly.edu
import pyspark
import sys
import re
import pickle

def df(directory):
    sc = pyspark.SparkContext()
    stops = set(sc.textFile("/data/stopwords.txt").collect())
    frequentWords = sc.pickleFile("hdfs://ambari-node1.csc.calpoly.edu:8020/user/cscott20/test/frequentWords.p").keys().collect()
    documents = sc.wholeTextFiles(directory)
    document = documents.mapValues(breakIntoWords)
    document = document.flatMapValues(lambda x: wordFilter(frequentWords, stops, x)).distinct().map(mapHelper)
    document = document.aggregateByKey(0, aggregatorSum, aggregatorSum)
    document.saveAsPickleFile("hdfs://ambari-node1.csc.calpoly.edu:8020/user/cscott20/test/documentFrequency.p")
    return document

def aggregatorSum(x, y):
    return x + y

def mapHelper(x):
    return (x[1],1)
    
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
