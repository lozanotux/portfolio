# Linux Things

## Common commands

**How to know which JAR has certain class:**
```
find . -type f -name '*.jar' -print0 |  xargs -0 -I '{}' sh -c 'jar tf {} | grep ConfigServiceHelperImpl.class &&  echo {}'
```

**Know the __Filesystem__ distribution and usage of disk for each one:**
```
df -vh
```

**Know the disk usage of one __Folder__ and its subdirectories:**
```
du -h /folder -d 1
```
