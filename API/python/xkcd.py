import urllib.request
import json

def main():
    url = 'https://xkcd.com/info.0.json'

    # Method 1
    response = urllib.request.urlopen( url )
    data = json.load( response )
    print( json.dumps(data, sort_keys=True) )

    # Method 2
    """
    import requests
    r = requests.get(url)
    print(r.json())
    """


if __name__ == '__main__':
    main()