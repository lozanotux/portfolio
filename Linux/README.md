# Linux Things

## Common commands

**How to know which JAR has certain class:**
```Shell
find . -type f -name '*.jar' -print0 |  xargs -0 -I '{}' sh -c 'jar tf {} | grep ClassName.class &&  echo {}'
```



**Know the __Filesystem__ distribution and usage of disk for each one:**
```Bash
df -vh
```



**Know the disk usage of one __Folder__ and its subdirectories:**
```sh
du -h /folder -d 1
```



**Edit file with user who has no group:**
```SH
sudo -u <user> vi <file>
```



**Transfer file from one server to another:**
```
scp root@10.0.0.1:/path/to/file.tar.gz /path/to/copy
```
|__
|__
|__
**know what files can be deleted to free up disk space:**
```
du -sch * --exclude=home
```
|__
|__
|__
**know what files contain certain text inside it:**
```
find /in/path/ -type f -exec grep -H 'certain_text' {} \;
```
|__
|__
|__
**List and view all files in a folder with full path:**
```
ls -R /opt | awk '/:$/&&f{s=$0;f=0} /:$/&&!f{sub(/:$/,"");s=$0;f=1;next} NF&&f{ print s"/"$0 }'
```
|__
|__
|__ 
**Find and view files with full path:**
```
find /opt -iname "*lib*"
```
|__
|__
|__
**Find files X old days and run some command:**
```
find /path -name '*some_word*' -mtime +15 -exec rm -rf {} \; 2> /dev/null
```
|__
|__
|__
**Know wich ports are used and find one:**
```
netstat -puntl | grep ':9503'
```
|__
|__
|__
**Find for files by a specific date and save the result in a log file (Recursive):**
```
find /path -type f -ls | grep 'Jan 10' > /tmp/result_file.log
```
