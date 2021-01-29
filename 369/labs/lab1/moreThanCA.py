#Charlie Scott
#Lab1
import json
import operator

with open('daily.json') as jsonFile: 
    covidData = json.load(jsonFile)
    newData = {}
    caData = {}
    dateData = {}
    for stateData in covidData:
        try:
            date = stateData['date']
            if stateData['state'] == 'CA' and stateData['positive']:
                caMort= round((stateData['death']/stateData['positive'] * 100), 2)
                caData[date] = caMort
        except KeyError:
            pass
    for stateData in covidData:
        try:
            state = stateData['state']
            date = stateData['date']
            if stateData['positive']:
                mortRate = round((stateData['death']/stateData['positive'] * 100), 2)
                if state != 'CA' and mortRate > caData[date]:
                    if date in dateData:
                        dateData[date].append([state, mortRate])
                    else:
                        dateData[date] = [[state, mortRate]]
        except KeyError:
            pass

for date in dateData:
    dateData[date].sort(key = lambda x : x[1])



mostDeathsDate = max(dateData, key = lambda x : len(dateData[x]))
print("Largest Number of States with Higher Mortality than California")
print("Total Number of States:", len(dateData[mostDeathsDate]))
print("Date:", mostDeathsDate)
for state in dateData[mostDeathsDate]:
    print(state[0], state[1])
print("CA", caData[mostDeathsDate])

