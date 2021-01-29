import json

with open('daily.json') as jsonFile: 
    covidData = json.load(jsonFile)
    newData = {}
    #initialize the dictionary with all states that have data
    for stateData in covidData:
        try:
            if stateData['positive'] and stateData['negative']:
                newData[stateData['state']] = {'date': 0, 'totalPositive':0, 'totalNegative': 0, 'ratio': 0}
        except KeyError:
            pass
    for stateData in covidData:
        state = stateData['state']
        hiLoRat = 0
        try:
            if state in newData:
                if (stateData['negative']) and (stateData['positive'] != None):
                    hiLoRat = (round((stateData['positive']/stateData['negative']) * 100, 2))
                if hiLoRat > newData[state]['ratio']:
                    newData[state]['ratio'] = hiLoRat
                    newData[state]['date'] = stateData['date']
                    newData[state]['totalPositive'] = stateData['positive']
                    newData[state]['totalNegative'] = stateData['negative']
        except KeyError:
            pass


def dict2csvPretty(data):
    pretty = "State, Date, Total Positive, Total Negative,  Mortality Rate\n"
    for state in data:
        pretty += (state + ", " + str(data[state]['date']) + ", " + str(data[state]['totalPositive']) + ", " + str(data[state]['totalNegative']) + ", " + str(data[state]['ratio']) + "\n")
    return pretty.strip()


print(dict2csvPretty(newData))
