#!/bin/bash
####################################
# Autor : Lozano Juan Pablo        #
# Fecha : 19/02/2019               #
####################################

#Variables
DOMAIN_HOME=/u01/oracle/Middleware/Oracle_Home/user_projects/domains/domain_name
HOST="123.4.5.678"
PORT="7001"
OHS_PORT="7777"
WLS_PASSWORD="WLSp45w0rd"
MANAGED_FORMS="WLS_FORMS"
MANAGED_REPORTS="WLS_REPORTS"
OHS_INSTANCE_NAME="ohs_1"
REPORT_SERVER_INSTANCE="report_server"

#Funciones generales y de sistema
red_color() {
        printf '\E[31m'; echo "$@"; printf '\E[0m'
}

green_color() {
        printf '\E[32m'; echo "$@"; printf '\E[0m'
}

blue_color(){
        printf '\E[34m'; echo "$@"; printf '\E[0m'
}

pause(){
        read -p "$*"
}

appKilled(){
    clear
    echo ""
    echo ""
    red_color "             ,+*****+. "
    red_color "            /         \\"
    red_color "            {   /\/\  /"
    red_color "            |   \/\/ / "
    red_color "           __\      {  "
    red_color "          /  \| .___/  "
    red_color "       __/         /   "
    red_color "      <___/\______}    "
    echo  ""
    red_color "         I'm dying...                   "
    sleep 1
    red_color "            Bye!!!                      "
    sleep 1
    echo ""
    kill -15 $$
}


#Check root permissions
if [[ $EUID -eq 0 ]]; then
    error "Your user is $USER"
    error "The script should not run with root user"
    exit 1
fi


clear
echo ""
trap "appKilled" 2


while :
do
        red_color  "   +-----------------------------------------------------+"
        red_color  "   |  +-------------------+                              |"
        red_color  "   |  |                   |                              |"
        red_color  "   |  |     %%%%%%%%%     |   ORACLE FORMS & REPORTS     |"
        red_color  "   |  |  %###       %###  |        VERSION 12c           |"
        red_color  "   |  | %#%           ##  |                              |"
        red_color  "   |  | %#%           ##  |                              |"
        red_color  "   |  |  %##%       %##%  |       Control Script         |"
        red_color  "   |  |     %#######T     |       GNU GPLv3 2019         |"
        red_color  "   |  |                   |                              |"
        red_color  "   |  +-------------------+                              |"
        red_color  "   +-----------------------------------------------------+"
        blue_color "   +-----------------------------------------------------+"
        blue_color "   |                                                     |"
        blue_color "   |   Choose an option:                                 |"
        blue_color "   |                                                     |"
        blue_color "   |     1)  Startup Domain                              |"
        blue_color "   |     2)  Shutdown Domain                             |"
        blue_color "   |     3)  Exit                                        |"
        blue_color "   |     4)  Help                                        |"
        blue_color "   |_____________________________________________________|"
        echo ""

        read -p "formsctl~]# Choose your option: " option
        case "$option" in
        1)
                green_color "formsctl~]# Starting up Forms&Reports 12c, please wait this will take a while..."



                #Cleaning logs and temporary files
                rm -Rf $DOMAIN_HOME/bin/adminserver.out
                rm -Rf $DOMAIN_HOME/bin/nodemanager.out
                rm -Rf $DOMAIN_HOME/bin/wls_reports.out
                rm -Rf $DOMAIN_HOME/bin/wls_forms.out
                rm -Rf $DOMAIN_HOME/bin/exit_error.out


               
                #Starting up WebLogic 
                echo "formsctl~]# Starting up WebLogic..."
                nohup $DOMAIN_HOME/bin/./startWebLogic.sh > $DOMAIN_HOME/bin/adminserver.out & > /dev/null 2>&1
                sleep 5
                tail -f $DOMAIN_HOME/bin/adminserver.out | while read LOGLINE
                do
                    [[ "${LOGLINE}" == *"RUNNING"* ]] && pkill -P $$ tail
                done
                if [ `ps -fea | grep $USER | grep './startWebLogic.sh' | grep -v grep | awk '{print $2}' | wc -w` -eq "1" ]
                then
                    WLS_PID=`ps -fea | grep $USER | grep "./startWebLogic.sh" | grep -v grep | awk '{print $2}'`
                    green_color "formsctl~]# WebLogic Started [ PID: $WLS_PID ]"
                else
                    red_color "formsctl~]# Could not start WebLogic, check owner of files and verify that root user has not been used previously to start WebLogic without the script. You can also check the log file $DOMAIN_HOME/bin/exit_error.out"
                    exit 1
                fi



                #Starting up NodeManager
                echo "formsctl~]# Starting up NodeManager..."
                nohup $DOMAIN_HOME/bin/./startNodeManager.sh > $DOMAIN_HOME/bin/nodemanager.out & > /dev/null 2>&1
                sleep 15
                if [ `ps -fea | grep $USER | grep 'weblogic.NodeManager' | grep -v grep | awk '{print $2}' | wc -w` -eq "1" ]
                then
                    NM_PID=`ps -fea | grep $USER | grep "weblogic.NodeManager" | grep -v grep | awk '{print $2}'`
                    green_color "formsctl~]# NodeManager Started [ PID: $NM_PID ]"
                else
                    nohup $DOMAIN_HOME/bin/./stopWebLogic.sh > $DOMAIN_HOME/bin/exit_error.out & > /dev/null 2>&1
                    red_color "formsctl~]# Could not start NodeManager, check owner of files and verify that root user has not been used previously to start WebLogic without the script. You can also check the log file $DOMAIN_HOME/bin/exit_error.out"
                    exit 1
                fi



                #Starting up Managed REPORTS
                echo "formsctl~]# Starting up Managed $MANAGED_REPORTS..."
                nohup $DOMAIN_HOME/bin/./startManagedWebLogic.sh $MANAGED_REPORTS t3://$HOST:$PORT > $DOMAIN_HOME/bin/wls_reports.out & > /dev/null 2>&1
                sleep 5
                tail -f $DOMAIN_HOME/bin/wls_reports.out | while read LOGLINE
                do
                    [[ "${LOGLINE}" == *"RUNNING"* ]] && pkill -P $$ tail
                done
                if [ `ps -fea | grep $USER | grep 'servers/WLS_REPORTS' | grep -v grep | awk '{print $2}' | wc -w` -eq "1" ]
                then
                    RP_PID=`ps -fea | grep $USER | grep "servers/WLS_REPORTS" | grep -v grep | awk '{print $2}'`
                    green_color "formsctl~]# Managed $MANAGED_REPORTS Started [ PID: $RP_PID ]"
                else
                    nohup $DOMAIN_HOME/bin/./stopNodeManager.sh > $DOMAIN_HOME/bin/exit_error.out & > /dev/null 2>&1
                    sleep 30
                    nohup $DOMAIN_HOME/bin/./stopWebLogic.sh > $DOMAIN_HOME/bin/exit_error.out & > /dev/null 2>&1
                    red_color "formsctl~]# Could not start the Managed $MANAGED_REPORTS check the log file $DOMAIN_HOME/bin/exit_error.out"
                    exit 1
                fi



                #Starting up Managed FORMS
                echo "formsctl~]# Starting up Managed $MANAGED_FORMS..."
                nohup $DOMAIN_HOME/bin/./startManagedWebLogic.sh $MANAGED_FORMS t3://$HOST:$PORT > $DOMAIN_HOME/bin/wls_forms.out & > /dev/null 2>&1
                sleep 5
                tail -f $DOMAIN_HOME/bin/wls_forms.out | while read LOGLINE
                do
                    [[ "${LOGLINE}" == *"RUNNING"* ]] && pkill -P $$ tail
                done
                if [ `ps -fea | grep $USER | grep 'servers/WLS_FORMS' | grep -v grep | awk '{print $2}' | wc -w` -eq "1" ]
                then
                    FR_PID=`ps -fea | grep $USER | grep "servers/WLS_FORMS" | grep -v grep | awk '{print $2}'`
                    green_color "formsctl~]# Managed $MANAGED_FORMS Started [ PID: $FR_PID ]"
                else
                    nohup $DOMAIN_HOME/bin/./stopManagedWebLogic.sh WLS_REPORTS t3://$HOST:$PORT > $DOMAIN_HOME/bin/exit_error.out & > /dev/null 2>&1
                    sleep 30
                    nohup $DOMAIN_HOME/bin/./stopNodeManager.sh > $DOMAIN_HOME/bin/exit_error.out & > /dev/null 2>&1
                    sleep 30
                    nohup $DOMAIN_HOME/bin/./stopWebLogic.sh > $DOMAIN_HOME/bin/exit_error.out & > /dev/null 2>&1
                    red_color "formsctl~]# Could not start the Managed $MANAGED_FORMS check the log file $DOMAIN_HOME/bin/exit_error.out"
                    exit 1
                fi



                #Starting up OHS
                green_color "formsctl~]# Starting up OHS..."
                $DOMAIN_HOME/bin/./startComponent.sh $OHS_INSTANCE_NAME <<!
$WLS_PASSWORD
!

                #Starting up report_server
                green_color "formsctl~]# Starting up report server instance..."
                $DOMAIN_HOME/bin/./startComponent.sh $REPORT_SERVER_INSTANCE <<!
$WLS_PASSWORD
!
                ( curl http://$HOST:$OHS_PORT/reports/rwservlet/showjobs?server=$REPORT_SERVER_INSTANCE ) > /dev/null 2>&1


                green_color "Forms&Reports Started Correctly!"

                pause "formsctl~]# Press any key to continue"
                clear
        ;;

        2)
                #Shuting down OHS
                green_color "formsctl~]# Shuting down OHS..."
                $DOMAIN_HOME/bin/./stopComponent.sh $OHS_INSTANCE_NAME <<!
$WLS_PASSWORD
!

                #Shuting down report server instance
                green_color "formsctl~]# Shuting down $REPORT_SERVER_INSTANCE..."
                $DOMAIN_HOME/bin/./stopComponent.sh $REPORT_SERVER_INSTANCE <<!
$WLS_PASSWORD
!

                #Shuting down Managed FORMS
                green_color "formsctl~]# Shuting down Managed $MANAGED_FORMS..."
                $DOMAIN_HOME/bin/./stopManagedWebLogic.sh $MANAGED_FORMS t3://$HOST:$PORT



                #Shuting down Managed REPORTS
                green_color "formsctl~]# Shuting down Managed $MANAGED_REPORTS..."
                $DOMAIN_HOME/bin/./stopManagedWebLogic.sh $MANAGED_REPORTS t3://$HOST:$PORT



                #Shuting down NodeManager
                green_color "formsctl~]# Shuting down NodeManager..."
                $DOMAIN_HOME/bin/./stopNodeManager.sh



                #Shuting down WebLogic
                green_color "formsctl~]# Shuting down WebLogic..."
                $DOMAIN_HOME/bin/./stopWebLogic.sh

                sleep 10
                echo ""
                echo ""
                echo "" 

                if [ `ps -fea | grep $USER | grep Oracle | grep -v grep | wc -w` -eq "0" ]
                then
                    green_color "formsctl~]# Domain properly stopped!"
                else
                    red_color "formsctl~]# Errors stopping the Domain, check if there are processes running in background with the following command: \"ps -afe | grep Oracle\""
                fi

                pause "formsctl~]# Press any key to continue"
                clear

        ;;
        
        3)
                echo ""
                green_color "formsctl~]# Exiting....."
                sleep 1
                clear
                exit 1
        ;;

        *)
                clear
                echo ""
                green_color "---------------------------------"
                green_color "Script help for Forms&Reports 12c"
                green_color "---------------------------------"
                echo " Opcion 1: is used for start up the entire Domain Forms&Reports 12c"
                echo "           this includes AdminServer, NodeManager, the managed servers"
                echo "           WLS_FORMS, WLS_REPORTS, OHS, among others."
                echo ""
                echo " Opcion 2: is used for shuting down the entire Domain Forms&Reports 12c"
                echo "           shuting down the totality of previously named components."
                echo ""
                echo " Opcion 3: is used to exit the script without interrupting it with the"
                echo "           shortcut   Ctrl + C  abruptly."
                echo ""
                echo " Opcion 4: view the script help you are currently viewing."
                echo ""
                sleep 2
                pause "formsctl~]# Press any key to continue"
                clear

        ;;
        esac
done
