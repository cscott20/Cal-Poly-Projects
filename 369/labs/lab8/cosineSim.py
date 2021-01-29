#Charlie Scott
#cscott20@calpoly.edu
import pyspark
import sys
import re
import pickle
import itertools
import math
import numpy as np
import pandas

def cosSim():
    sc = pyspark.SparkContext()
    stops = set(sc.textFile("/data/stopwords.txt").collect())
    doc1 =  sc.pickleFile("hdfs://ambari-node1.csc.calpoly.edu:8020/user/cscott20/test/TermFrequency-11-0.p")
    doc2 = sc.pickleFile("hdfs://ambari-node1.csc.calpoly.edu:8020/user/cscott20/test/TermFrequency-1342-0.p")
    doc3 = sc.pickleFile("hdfs://ambari-node1.csc.calpoly.edu:8020/user/cscott20/test/TermFrequency-1952-0.p")
    doc4 = sc.pickleFile("hdfs://ambari-node1.csc.calpoly.edu:8020/user/cscott20/test/TermFrequency-219-0.p")
    doc5 = sc.pickleFile("hdfs://ambari-node1.csc.calpoly.edu:8020/user/cscott20/test/TermFrequency-2701-0.p")
    doc6 = sc.pickleFile("hdfs://ambari-node1.csc.calpoly.edu:8020/user/cscott20/test/TermFrequency-76-0.p") 
    doc7 = sc.pickleFile("hdfs://ambari-node1.csc.calpoly.edu:8020/user/cscott20/test/TermFrequency-84-0.p")  
    doc8 = sc.pickleFile("hdfs://ambari-node1.csc.calpoly.edu:8020/user/cscott20/test/TermFrequency-98-0.p") 
    doc9 = sc.pickleFile("hdfs://ambari-node1.csc.calpoly.edu:8020/user/cscott20/test/TermFrequency-pg1080.p")  
    doc10 = sc.pickleFile("hdfs://ambari-node1.csc.calpoly.edu:8020/user/cscott20/test/TermFrequency-pg1661.p")  
    doc11 = sc.pickleFile("hdfs://ambari-node1.csc.calpoly.edu:8020/user/cscott20/test/TermFrequency-pg844.p") 
    documents = {doc1: "11-0", doc2 : "1342-0", doc3 : "1952-0", doc4 : "219-0",  doc5: "2701-01", doc6: "76-0", doc7 : "84-0", doc8 : "98-0", doc9 : "pg1080" , doc10 : "pg1661", doc11 : "pg844"}
    docFreq = sc.pickleFile("hdfs://ambari-node1.csc.calpoly.edu:8020/user/cscott20/test/documentFrequency.p").collectAsMap()
    results = []
    for doc1 in documents:
        row = []
        for doc2 in documents:
            num1 = doc1.map(lambda pair : (pair[0], float(pair[1]) * math.log(12 /docFreq[pair[0]], 2)))
            num2 = doc2.map(lambda pair : (pair[0], float(pair[1]) * math.log(12 /docFreq[pair[0]], 2)))
            numerator = num1.leftOuterJoin(num2).mapValues(lambda x: x[0] * x[1]).map(lambda x : x[1]).sum()
            denom1 = (num1.map(lambda x : (x[1] ** 2)).sum()) ** .5
            denom2 = (num2.map(lambda x : (x[1] ** 2)).sum()) ** .5
            denominator = denom1 * denom2
            row.append(numerator / denominator)
        results.append(row) 
    output = np.array(results)
    df = pandas.DataFrame(output, columns= list(documents.values()), index=list(documents.values()))
    print(df)
    sc.parallelize(df).saveAsPickleFile("hdfs://ambari-node1.csc.calpoly.edu:8020/user/cscott20/similarities.pickle")
    return df
    
cosSim()
