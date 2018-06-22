# Linux Things

## Common commands

**__How to know which JAR has certain class:__**
```
find . -type f -name '*.jar' -print0 |  xargs -0 -I '{}' sh -c 'jar tf {} | grep ConfigServiceHelperImpl.class &&  echo {}'
```