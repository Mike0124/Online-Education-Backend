import sys
import json

if __name__ == '__main__':
    s = sys.argv[1]
    json.dumps(s)
    print(s["list"])