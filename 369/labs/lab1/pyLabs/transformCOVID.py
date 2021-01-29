import json

with open('daily.json') as jsonFile: 
    covidData = json.load(jsonFile)
    newData = {}
    #initialize the dictionary with all states that have data
    for stateData in covidData:
        newData[stateData['state']] = {'startDate': stateData['date'], 'newCases': [], 'newDeaths':[], 'mortalityRates': []}
    for stateData in covidData:
        state = stateData['state']
        if stateData['date'] < newData[state]['startDate']:
            newData[state]['date'] = stateData['date']
        if stateData['positiveIncrease']:
            newData[state]['newCases'].append(stateData['positiveIncrease'])
        if stateData['deathIncrease']:
            newData[state]['newDeaths'].append(stateData['positiveIncrease'])
        if stateData['positive']:
            try:
                newData[state]['mortalityRates'].append(round(stateData['death']/stateData['positive'] * 100, 2))
            except KeyError:
                pass


def dict2json(data):
    lis = []
    entry = {}
    for state in data:
        entry['state'] = state
        entry['startDate'] = data[state]['startDate']
        entry['newCases'] = data[state]['newCases']
        entry['newDeaths'] = data[state]['newDeaths']
        entry['mortalityRates'] = data[state]['mortalityRates']
        lis.append(entry)
        entry = {}
    return (json.dumps(lis))

print(dict2json(newData))
