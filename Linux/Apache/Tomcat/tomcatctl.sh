#!/bin/bash
# Script: tomcatctl.sh
# Run this script without parameters show the possible options

# User that runs Tomcat
export USER_TOMCAT=tomcat
export INSTANCES_PATH=/home/tomcat/instances
TOMCAT_TMP_BASE=/tmp/tomcat
PRGDIR=/opt/apache-tomcat_v8/bin
EXECUTABLE=catalina.sh

# Prevents execution with root user
if [ "$EUID" -eq 0 ]
then
    echo "ERROR: Run with user \"${USER_TOMCAT}\""
    exit
fi

# Check subcommand that will run
case "$1" in
    1|start)
        # Test if the start subcommand has a parameter
        if [ `echo "$@" | wc -w` -eq 2 ]
        then
            # Search the instance with: USER_TOMCAT, INSTANCE, Exclude greps commands, and get the PID number 
            if [ `ps -fea | grep $USER_TOMCAT | grep /${2}/ | grep -v grep | awk '{print $2}' | wc -w` -eq "1" ]
            then
                # If the instance is already running, print the next message
                echo "Instance ${2} Already started"
            elif [ `ps -fea | grep $USER_TOMCAT | grep /${2}/ | grep -v grep | awk '{print $2}' | wc -w` -eq 0 ]
            then
                export JAVA_OPTS=" -Djava.security.egd=file:/dev/./urandom "
                export CATALINA_OPTS=" -Dinstance.name=${2} "
                export CATALINA_TMPDIR=$TOMCAT_TMP_BASE/$2/temp
                export CATALINA_BASE=/home/tomcat/instances/$2
                export CATALINA_OUT=/var/log/tomcat/instances/$2/catalina.out

                # Remember comment the environment variable CATALINA_BASE in catalina.sh
                /opt/apache-tomcat_v8/bin/catalina.sh start 
                echo "Instance ${2} Started"
            fi
        else
            echo "Number of parameters is incorrect. Enter the instance name after the subcommand start."
        fi
    ;;
    2|stop)
        # Test if the stop subcommand has a parameter
        if [ `echo "$@" | wc -w` -eq 2 ]
        then
            # Search the instance with: USER_TOMCAT, INSTANCE, Exclude greps commands, and get the PID number
            if [ `ps -fea | grep $USER_TOMCAT | grep /${2}/ | grep -v grep | awk '{print $2}' | wc -w` -eq "1" ]
            then
                kill -9 `ps -fea | grep $USER_TOMCAT | grep /${2}/ | grep -v grep | awk '{print $2}'`
                echo "Instance ${2} stopped"
            elif [ `ps -fea | grep $USER_TOMCAT | grep /${2}/ | grep -v grep | awk '{print $2}' | wc -w` -eq 0 ]
            then
                echo "Instance ${2} is not started"
            fi
        else
            echo "Number of parameters is incorrect. Enter the instance name after the subcommand stop."
        fi
    ;;
    3|restart)
        # Test if the restart subcommand has a parameter
        if [ `echo "$@" | wc -w` -eq 2 ]
        then
            # Search the instance with: USER_TOMCAT, INSTANCE, Exclude greps commands, and get the PID number
            kill -9 `ps -fea | grep $USER_TOMCAT | grep /${2}/ | grep -v grep | awk '{print $2}'`
            sleep 8

            export JAVA_OPTS=" -Djava.security.egd=file:/dev/./urandom "
            export CATALINA_OPTS=" -Dinstance.name=${2} "
            export CATALINA_TMPDIR=$TOMCAT_TMP_BASE/$2/temp
            export CATALINA_BASE=/home/tomcat/instances/$2
            export CATALINA_OUT=/var/log/tomcat/instances/$2/catalina.out

            /opt/apache-tomcat_v8/bin/catalina.sh start
        else
            echo "Number of parameters is incorrect. Enter the instance name after the subcommand restart."
        fi
    ;;
    4|status)
        # Test if the status subcommand has a parameter
        if [ `echo "$@" | wc -w` -eq 2 ]
        then
            # Search the instance with: USER_TOMCAT, INSTANCE, Exclude greps commands, and get the PID number
            if [ `ps -fea | grep $USER_TOMCAT | grep /${2}/ | grep -v grep | awk '{print $2}' | wc -w` -eq "1" ]
            then
                echo "Instance started."
                PID_APP=`ps -fea | grep $USER_TOMCAT | grep /${2}/ | grep -v grep | grep -v tomcatctl | awk '{print $2}'`
                PORT_HTTP=`grep "Connector port" $INSTANCES_PATH/${2}/conf/server.xml | grep HTTP | grep -v "!--" | grep -v "AJP" | grep -o '[0-9][0-9][0-9][0-9]'`
                PORT_SHUTDOWN=`grep "Server port" $INSTANCES_PATH/${2}/conf/server.xml | grep -v "!--"  | grep -o '[0-9][0-9][0-9][0-9]'`
                echo "PID: "$PID_APP
                echo "HTTP_PORT: "$PORT_HTTP
                echo "SHUTDOWN_PORT: "$PORT_SHUTDOWN
            elif [ `ps -fea | grep $USER_TOMCAT | grep /${2}/ | grep -v grep | awk '{print $2}' | wc -w` -eq 0 ]
            then
                echo "Instance not started."
                PORT_HTTP=`grep "Connector port" $INSTANCES_PATH/${2}/conf/server.xml | grep HTTP | grep -v "!--" | grep -v "AJP" | grep -o '[0-9][0-9][0-9][0-9]'`
                PORT_SHUTDOWN=`grep "Server port" $INSTANCES_PATH/${2}/conf/server.xml | grep -v "!--"  | grep -o '[0-9][0-9][0-9][0-9]'`
                echo "HTTP_PORT: "$PORT_HTTP
                echo "SHUTDOWN_PORT: "$PORT_SHUTDOWN
            fi
        elif [ `echo "$@" | wc -w` -eq 1 ]
        then
            # Print Header of status list
            echo -e "INSTANCES (\e[1;34mUP\e[0m|\e[1;31mDOWN\e[0m)\t|  PID   |   HTTP   |  SHUTDOWN  |   WEBAPPS"
            ls $INSTANCES_PATH | grep -v log | grep -v sample | while read T_INSTANCES
            do
                # Make different tabs to print better the instances names
                if [[ `echo "${T_INSTANCES}" | wc -c` -gt 16 ]]
                then
                    TABS='\t'
                elif [[ `echo "${T_INSTANCES}" | wc -c` -gt 8 ]]
                then
                    TABS='\t\t'
                else
                    TABS='\t\t\t'
                fi
                
                # Search the instance with: USER_TOMCAT, INSTANCE, Exclude greps commands, Exclude tomcatctl.sh command and get the PID number
                PID_APP=`ps -fea | grep $USER_TOMCAT | grep /${T_INSTANCES}/ | grep -v grep | grep -v tomcatctl | awk '{print $2}'`

                # Make different spans to print better the PID of instances
                if [[ `echo "${PID_APP}" | wc -c` -eq 6 ]]
                then
                    SP=' '
                elif [[ `echo "${PID_APP}" | wc -c` -eq 5 ]]
                then
                    SP='  '
                elif [[ `echo "${PID_APP}" | wc -c` -eq 4 ]]
                then
                    SP='   '
                else
                    SP='\t'
                fi

                PORT_HTTP=$(grep "Connector port" $INSTANCES_PATH/${T_INSTANCES}/conf/server.xml | grep HTTP | grep -v "!--" | grep -v "AJP" | grep -o '[0-9][0-9][0-9][0-9]')
                PORT_SHUTDOWN=$(grep "Server port" $INSTANCES_PATH/${T_INSTANCES}/conf/server.xml | grep -v "!--"  | grep -o '[0-9][0-9][0-9][0-9]')
                WEBDAV=$(ls $INSTANCES_PATH/${T_INSTANCES}/webapps/ | grep -v .war)

                # Put webapps one beside the other
                if [[ `echo ${WEBDAV} | wc -w` -gt 1 ]]; then WEBDAV=`echo ${WEBDAV}`; 
                fi
				
                # Test if the instance is up
                if [ `ps -fea | grep $USER_TOMCAT | grep /${T_INSTANCES}/ | grep -v grep | grep -v tomcatctl | awk '{print $2}'| wc -w` -eq "1" ]
                then
                    echo -e "\e[1;34m${T_INSTANCES}\e[0m${TABS}|  ${PID_APP}${SP}|   ${PORT_HTTP}   |   ${PORT_SHUTDOWN}   |   ${WEBDAV}"
                else
                    echo -e "\e[1;31m${T_INSTANCES}\e[0m${TABS}|        |   ${PORT_HTTP}   |   ${PORT_SHUTDOWN}   |   ${WEBDAV}"
                fi
            done
        else
            echo "Number of parameters is incorrect. Enter the instance name after the subcommand status or no parameter to show all."
        fi
    ;;
    5|stopall)
        ls $INSTANCES_PATH | grep -v log | grep -v client_ffdc | while read T_INSTANCES
        do
            # NOT IMPLEMENTED YET
        done
    ;;
    6|startall)
        ls $INSTANCES_PATH | grep -v log | grep -v client_ffdc | while read T_INSTANCES
        do
            # NOT IMPLEMENTED YET
        done
    ;;
    *)
        echo -e "\n\tUse: ./tomcatctl.sh subcommand parameter"
        echo -e "\n\tSubcommands available (You can use name or command number):"
        echo -e "\t\t1 | start     #Pass the name of the instance as parameter"
        echo -e "\t\t2 | stop      #Pass the name of the instance as parameter"
        echo -e "\t\t3 | restart   #Pass the name of the instance as parameter"
        echo -e "\t\t4 | status    #The name of the instance can be passed as a parameter. Without parameters show all instances."
        echo -e "\t\t5 | stopall   #Use without parameters"
        echo -e "\t\t6 | startall  #Use without parameters\n"

        exit 1
    ;;
esac
