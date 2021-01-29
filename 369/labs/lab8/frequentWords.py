#Charlie Scott
#cscott20@calpoly.edu
import pyspark
import sys
import re
import pickle

def frequentWords(directory):
    sc = pyspark.SparkContext()
    stops = set(sc.textFile("/data/stopwords.txt").collect())
    document = sc.wholeTextFiles(directory)
    document = document.values().map(breakIntoWords)
    document = document.flatMap(lambda x: stopWordFilter(stops, x))
    document = document.aggregateByKey(0, aggregatorSum, aggregatorSum)
    top100 = document.top(100, key=lambda x: x[1])
    print(top100)
    rdd = sc.parallelize(top100).saveAsPickleFile("hdfs://ambari-node1.csc.calpoly.edu:8020/user/cscott20/test/frequentWords.p")
    return rdd 

def aggregatorSum(x, y):
    return x + y

    print(document.collect()) 
def breakIntoWords(row):
    rawOut = row.strip().split(" ")
    out = [x.strip() for x in rawOut]
    return out

def stopWordFilter(stops, row):
    stops.add("guttenbergtm")
    stops.add("guttenberg")
    for word in row:
        newWord = re.sub('[^a-zA-Z0-9]','', word).lower()
        if not newWord in stops:
            yield [newWord, 1]
    return

frequentWords(sys.argv[1])
