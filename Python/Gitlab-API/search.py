#!/usr/bin/env python3
"""
Python script to search keywords in all project inside Gitlab
and print the files where the occurrences appear.

author:  Juan Lozano <lozanotux@gmail.com>
version: 1.0
date: 2024-06-10

pip dependencies:
    - requests
"""
# -----------------------------
# Import Section
# -----------------------------
import requests
import sys
import time
import urllib.parse


# -----------------------------
# Variables Section
# -----------------------------
GITLAB_DOMAIN = 'https://{YOUR_GITLAB_INSTANCE_URL}'
GITLAB_TOKEN = '{YOUR_GITLAB_PERSONAL_ACCESS_TOKEN}'
RATE_LIMIT = True
HITS_PER_MINUTE = 25  # Gitlab API Rate Limit
WAIT_TIME = 60 / HITS_PER_MINUTE
ITEMS_PERPAGE = 100


def print_help_message():
    print("")
    print("Gitlab Search Tool\n")
    print("Usage: search [QUERY]\n")


if len(sys.argv) < 2:
    print("\n[ERROR] insufficient arguments!")
    print_help_message()
    exit()

if sys.argv[1] == "--help":
    print_help_message()
    exit()


# -----------------------------
# Process Section
# -----------------------------
def find_hits():
    end = False
    page = 1
    print("Searching...")
    keyword = urllib.parse.quote(sys.argv[1], safe="")
    while not end:
        contents = requests.get(
            f"{GITLAB_DOMAIN}/api/v4/projects"
            f"?private_token={GITLAB_TOKEN}"
            f"&per_page={ITEMS_PERPAGE}&page={page}"
        )
        if contents.status_code == 200:
            if len(contents.json()) == 0:
                end = True
                break
            else:
                contents_data = contents.json()
                for i in range(len(contents.json())):
                    project_id = contents_data[i]['id']
                    project_name = contents_data[i]['name']
                    # Rate Limit Control
                    time.sleep(WAIT_TIME)
                    hits = requests.get(
                        f"{GITLAB_DOMAIN}/api/v4/projects/{project_id}/search"
                        f"?scope=blobs&search={keyword}"
                        f"&private_token={GITLAB_TOKEN}"
                    )
                    if hits.status_code == 200:
                        if len(hits.json()) > 0:
                            print("\n")
                            print('-'*50)
                            print(f' {project_name}')
                            print('-'*50)
                            for j in range(len(hits.json())):
                                hit_data = hits.json()
                                print(f"• {hit_data[j]['path']}")
                    else:
                        print(
                            f"[!] Failed to make the HTTP Request for project"
                            f"  {project_name} -"
                            f" Error Code: {hits.status_code}"
                        )
        else:
            print(f"[!] Failed to make the HTTP Request for page {page}")
            print(contents.text)
        page += 1


if __name__ == "__main__":
    try:
        find_hits()
    except KeyboardInterrupt:
        pass
