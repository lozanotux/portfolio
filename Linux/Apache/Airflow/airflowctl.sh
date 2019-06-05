#!/bin/bash
# Developer by Juan Lozano <lozanotux@gmail.com>
# For Airflow 1.10.3

# Script Settings (You can change the values if you wish)
APACHE_AIRFLOW_PORT=8080
APACHE_AIRFLOW_LOG_FILE=airflow.out

# Functions to print in color
# Green Color = OK
ok() {
    printf '\E[32m'; echo "$@"; printf '\E[0m'
}

# Red color = WARN
warn() {
    printf '\E[31m'; echo "$@"; printf '\E[0m'
}

# Main Apache Ariflow PID if Running
PIDCORE=$(ps -ef | grep "bin/airflow" | grep -v grep | awk '{print $2}')

# Function to stop Apache Ariflow Processes
stop_airflow() {
    PIDG1=$(ps -ef | grep airflow-webserver | grep -v grep | awk '{print $2}')
    PIDG2=$(ps -ef | grep gunicorn | grep -v grep | awk '{print $2}')

    if [ $PIDCORE ] || [ $PIDG1 ] || [ $PIDG2 ]
    then
        for PID in $PIDG1;
        do
            kill -9 $PID;
        done;
        for PID in $PIDG2;
        do
            kill -9 $PID;
        done;
        kill -9 $PIDCORE;
        ok "Apache Airflow Stopped!"
    else
        warn "Apache Airflow is not running!"
    fi
}

# Function to Start Apache Airflow WebServer
start_airflow() {
    if [ $PIDCORE ]
    then
        warn "Apache Airflow is already running!"
    else
        (nohup airflow webserver -p $APACHE_AIRFLOW_PORT > $APACHE_AIRFLOW_LOG_FILE &) 2>/dev/null
        AIRFLOW_PID=$(ps -ef | grep "bin/airflow" | grep -v grep | awk '{print $2}')
        ok "Apache Airflow Started :)   PID: [$AIRFLOW_PID]"
    fi
}


case "$1" in
    1|start)
        start_airflow
        ;;
    2|stop)
        stop_airflow
        ;;
    3|restart)
        stop_airflow
        sleep 2
        PIDCORE=$(ps -ef | grep "local/bin/airflow" | grep -v grep | awk '{print $2}')
        start_airflow
        ;;
    *)
        echo -e "\n\tUsage: airflowctl [command]"
        echo -e "\n\tAvailable commands (You can use name or command number):"
        echo -e "\t\t1 | start     # Start Apache Airflow WebServer"
        echo -e "\t\t2 | stop      # Stops the Apache Airflow processes"
        echo -e "\t\t3 | restart   # Restart Apache Airflow WebServer"
        exit 1
        ;;
esac
