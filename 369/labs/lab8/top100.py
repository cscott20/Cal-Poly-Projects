#Charlie Scott
#cscott20@calpoly.edu
import pyspark
import sys
import re

def getTop100(filename):
    sc = pyspark.SparkContext()
    stops = set(sc.textFile("/data/stopwords.txt").collect())
    document = (sc.textFile(filename)).map(breakIntoWords)
    document = document.flatMap(lambda x: stopWordFilter(stops, x))
    document = document.aggregateByKey(0, aggregatorSum, aggregatorSum)
    top100 = document.top(100, key=lambda x: x[1])
    print(top100)
    return sc.parallelize(top100)

def aggregatorSum(x, y):
    return x + y

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

getTop100(sys.argv[1])
