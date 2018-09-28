# Linux Things

## Common commands

**How to know which JAR has certain class:**
``` sh
find . -type f -name '*.jar' -print0 |  xargs -0 -I '{}' sh -c 'jar tf {} | grep ClassName.class &&  echo {}'
```
  
  
  
**Know the __Filesystem__ distribution and usage of disk for each one:**
``` sh
df -vh
```
  
  
  
**Know the disk usage of one __Folder__ and its subdirectories:**
``` sh
du -h /folder -d 1
```
  
  
  
**Edit file with user who has no group:**
``` sh
sudo -u <user> vi <file>
```
  
  
  
**Transfer file from one server to another:**
``` sh
scp root@10.0.0.1:/path/to/file.tar.gz /path/to/copy
```
  
  
  
**know what files can be deleted to free up disk space:**
``` sh
du -sch * --exclude=home
```
  
  
  
**know what files contain certain text inside it:**
``` sh
find /in/path/ -type f -exec grep -H 'certain_text' {} \;
```
  
  
  
**List and view all files in a folder with full path:**
``` sh
ls -R /opt | awk '/:$/&&f{s=$0;f=0} /:$/&&!f{sub(/:$/,"");s=$0;f=1;next} NF&&f{ print s"/"$0 }'
```
  
  
  
**Find and view files with full path:**
``` sh
find /opt -iname "*lib*"
```
  
  
  
**Find files X old days and run some command:**
``` sh
find /path -name '*some_word*' -mtime +15 -exec rm -rf {} \; 2> /dev/null
```
  
  
  
**Know wich ports are used and find one:**
``` sh
netstat -puntl | grep ':9503'
```
  
  
  
**Find for files by a specific date and save the result in a log file (Recursive):**
``` sh
find /path -type f -ls | grep 'Jan 10' > /tmp/result_file.log
```
