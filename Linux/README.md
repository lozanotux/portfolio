# Linux Things

## Common commands

**How to know which JAR has certain class:**
```
find . -type f -name '*.jar' -print0 |  xargs -0 -I '{}' sh -c 'jar tf {} | grep ClassName.class &&  echo {}'
```
<br />
<br />
<br />
**Know the __Filesystem__ distribution and usage of disk for each one:**
```
df -vh
```
<br />
<br />
<br />
**Know the disk usage of one __Folder__ and its subdirectories:**
```
du -h /folder -d 1
```
<br />
<br />
<br /> 
**Edit file with user who has no group:**
```
sudo -u <user> vi <file>
```
<br />
<br />
<br />
**Transfer file from one server to another:**
```
scp root@10.0.0.1:/path/to/file.tar.gz /path/to/copy
```
<br />
<br />
<br />
**know what files can be deleted to free up disk space:**
```
du -sch * --exclude=home
```
<br />
<br />
<br />
**know what files contain certain text inside it:**
```
find /in/path/ -type f -exec grep -H 'certain_text' {} \;
```
<br />
<br />
<br />
**List and view all files in a folder with full path:**
```
ls -R /opt | awk '/:$/&&f{s=$0;f=0} /:$/&&!f{sub(/:$/,"");s=$0;f=1;next} NF&&f{ print s"/"$0 }'
```
<br />
<br />
<br /> 
**Find and view files with full path:**
```
find /opt -iname "*lib*"
```
<br />
<br />
<br />
**Find files X old days and run some command:**
```
find /path -name '*some_word*' -mtime +15 -exec rm -rf {} \; 2> /dev/null
```
<br />
<br />
<br />
**Know wich ports are used and find one:**
```
netstat -puntl | grep ':9503'
```
<br />
<br />
<br /> 
**Find for files by a specific date and save the result in a log file (Recursive):**
```
find /path -type f -ls | grep 'Jan 10' > /tmp/result_file.log
```
