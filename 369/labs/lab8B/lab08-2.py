#Charlie Scott
#cscott20@calpoly.edu
import pyspark
from pyspark.sql import SparkSession
import pyspark.sql.types as tp
import pandas as pd
from pyspark.sql import functions as F


#Provided Starter Code
spark = SparkSession.builder.getOrCreate()

filenames = {"albums":"/data/katzenjammer/Albums.csv",
             "band": "/data/katzenjammer/Band.csv",
             "instruments": "/data/katzenjammer/Instruments.csv",
             "performance": "/data/katzenjammer/Performance.csv",
             "songs": "/data/katzenjammer/Songs.csv",
             "tracklists": "/data/katzenjammer/Tracklists.csv",
             "vocals": "/data/katzenjammer/Vocals.csv"}

attributeLists = {"albums": [tp.StructField("AId",tp.IntegerType()),
                             tp.StructField("Title", tp.StringType()),
                             tp.StructField("Year", tp.IntegerType()),
                             tp.StructField("Label",tp.StringType()),
                             tp.StructField("Type", tp.StringType())],
                  "band": [tp.StructField("BandmateID", tp.IntegerType()),
                           tp.StructField("Firstname", tp.StringType()),
                           tp.StructField("Lastname", tp.StringType())],
                  "instruments": [tp.StructField("SongId", tp.IntegerType()),
                                  tp.StructField("BandmateID", tp.IntegerType()),
                                  tp.StructField("Instrument", tp.StringType())],
                   "performance": [tp.StructField("SongId", tp.IntegerType()),
                                   tp.StructField("BandMateID", tp.IntegerType()),
                                   tp.StructField("StagePosition", tp.StringType())],
                    "songs": [tp.StructField("SongId", tp.IntegerType()),
                              tp.StructField("Title", tp.StringType())],
                     "tracklists": [tp.StructField("AlbumId", tp.IntegerType()),
                                    tp.StructField("Position", tp.IntegerType()),
                                    tp.StructField("SongId", tp.IntegerType())],
                     "vocals": [tp.StructField("SongId", tp.IntegerType()),
                                tp.StructField("BandmateID", tp.IntegerType()),
                                tp.StructField("Type", tp.StringType())]
                      }

schemas = {key: tp.StructType(alist) for key,alist in zip(attributeLists, 
                attributeLists.values())}
albumsDF = spark.read.format("csv").schema(schemas["albums"]).option("header",True).option("quote", "'").load(filenames["albums"])
bandDF = spark.read.format("csv").schema(schemas["band"]).option("header",True).option("quote","'").load(filenames["band"])
instrumentsDF = spark.read.format("csv").schema(schemas["instruments"]).option("header",True).option("quote","'").load(filenames["instruments"])
performanceDF = spark.read.format("csv").schema(schemas["performance"]).option("header",True).option("quote","'").load(filenames["performance"])
songsDF = spark.read.format("csv").schema(schemas["songs"]).option("header",True).option("quote","'").load(filenames["songs"])
tracklistsDF = spark.read.format("csv").schema(schemas["tracklists"]).option("header",True).option("quote",".").load(filenames["tracklists"])
vocalsDF = spark.read.format("csv").schema(schemas["vocals"]).option("header",True).option("quote","'").load(filenames["vocals"])

#My code below



#Step1
step1 = instrumentsDF.join(songsDF, on = "SongID", how = "left_outer").join(bandDF, on = "BandmateID", how = "left_outer").groupBy("FirstName", "Title").agg(F.collect_list("Instrument").alias("Instruments"))
step1 = step1.withColumn("Instruments",step1["Instruments"].cast('String')).sort("Title",ascending=False).withColumnRenamed("FirstName", "Name").withColumnRenamed("Title","Song").select("Song", "Name", "Instruments")


#Step2
name = "Solveig"
solvDF = step1.filter(step1.Name == name).withColumnRenamed("Instruments", name).select("Song", name)
name = "Marianne"
marDF = step1.filter(step1.Name == name).withColumnRenamed("Instruments", name).select("Song", name)
name = "Anne-Marit"
anneDF = step1.filter(step1.Name == name).withColumnRenamed("Instruments", name).select("Song", name)
name = "Turid"
turdDF = step1.filter(step1.Name == name).withColumnRenamed("Instruments", name).select("Song", name)

step2 = solvDF.join(marDF, on = "Song", how = "left_outer").join(anneDF, on = "Song", how = "left_outer").join(turdDF, on = "Song", how = "left_outer")



#Step 3
step3 = vocalsDF.filter(vocalsDF.Type == "lead").join(songsDF, on = "SongID", how = "left_outer").join(bandDF, on = "BandmateID", how = "left_outer").groupBy("Title").agg(F.collect_list("Firstname").alias("LeadVocals"))
step3 = step3.withColumn("LeadVocals",step3["LeadVocals"].cast('String')).withColumnRenamed("Title", "Song").join(step2, on = "Song", how = "outer").fillna({"LeadVocals" : "[No One]", "Solveig" : "[No Instrument Played]", "Marianne" : "[No Instrument Played]", "Anne-Marit": "[No Instrument Played]", "Turid": "[No Instrument Played]"})



#Step4
step4 = instrumentsDF.join(bandDF, on = "BandmateID", how = "left_outer").withColumn('Instrument', F.regexp_replace('Instrument', 'guitar|ukalele|banjo|mandolin|small guitar', 'strings'))
step4 = step4.filter((step4.Instrument == "strings")|(step4.Instrument == "keyboards")|(step4.Instrument == "drums")|(step4.Instrument == "bass balalaika"))

solvI = step4.filter(step4.Firstname == "Solveig").withColumnRenamed("Instrument", "Solveig")
marI = step4.filter(step4.Firstname == "Marianne").withColumnRenamed("Instrument", "Marianne")

step4 = solvI.join(marI, on = "SongID", how = "outer")
step4 = step4.filter((step4.Marianne.isNotNull())&(step4.Solveig.isNotNull())).stat.crosstab("Solveig", "Marianne")



#Print results
step1.show(165)
step2.show(42)
step3.show(43)
step4.show(4)












