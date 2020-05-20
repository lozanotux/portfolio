# Depending on your system you might need to use sudo
# For Red Hat, CentOS or Fedora you can use next commands:
# 01:   $ adduser jenkins
# 02:   $ yum update
# 03:   $ yum install java-1.8.0-openjdk wget git vim gnupg1
# 04:   $ mkdir /jenkins
# 05:   $ chwon -R jenkins:jenkins /jenkins
#
adduser jenkins
apt-get update
apt-get install openjdk-8-jdk wget gnupg git vim
mkdir /jenkins
chwon -R jenkins:jenkins /jenkins
# sudo su jenkins
# vim /home/jenkins/.ssh/authorized_keys
