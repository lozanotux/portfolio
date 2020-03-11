# Linux Things

## PS1 config for a generic look good prompt

**For normal user (green & dollar):**
``` sh
export PS1="\[\033[01;32m\]\u@\h\[\033[00m\]:\[\033[01;34m\]\w\[\033[00m\]\$ "
```

**For root user (red & hashtag):**
``` sh
export PS1='\[\033[01;31m\]\u@\h\[\033[00m\]:\[\033[01;34m\]\W\[\033[00m\]\$ '
```


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
  
  
  
**Give a list of the first depth directories and their sizes, limits the analysis to one file system:**
``` sh
du --max-depth=1 -x -h
```
  
  
  
**Read a text file and run certain command for each line:**
``` sh
cat text_file.txt | while read VARIABLE; do echo "$VARIABLE"; done
```
  
  
  
**Batch process that read each file and remove return carriage '\r':**
``` sh
for i in *;do tr -d '\r' < $i > out/$i; chmod +x out/$i;done
```
  
  
  
**View level traffic for Apache access log, count by day:**
``` sh
awk '{print $4}' file_access_log.txt | cut -d: -f1 | uniq -c
```
