import json

with open('daily.json') as jsonFile: 
    covidData = json.load(jsonFile)
    dateData = {}
    for stateData in covidData:
        try:
            state = stateData['state']
            date = stateData['date']
            if (not date in dateData) or (stateData['positive'] and (stateData['positive'] > dateData[date][1])):
                dateData[date] = [state, stateData['positive']]
        except KeyError:
            pass
csvData = []
for date in dateData:
    csvData.append(str(date) + ", " + str(dateData[date][0]) + ", " +  str(dateData[date][1]))
csvData.append("Date, State, Positive Cases")
for line in reversed(csvData):
    print(line)
