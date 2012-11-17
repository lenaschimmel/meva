#! /bin/bash
echo off
echo ; set +v # > NUL
echo ; function GOTO { true; } # > NUL
 
GOTO WIN
echo "Note: Just ignore the word 'off' on the previous line :)"
# #####################################################################################################
# This file does nothing but calling the single line of code below. It's so complex because
# it works on Windows, OS X and Linux.
# This is a trick from http://blog.bigsmoke.us/2007/06/11/microsoft-batch-file-meets-bash-shellscript
# #####################################################################################################
java -cp "./bin:lib/*" de.gmino.codegen.Meva ../checkin.module/gwt ../checkin.module/android ../meva.module/android ../cycleway.module/gwt
exit 0
 
:WIN
java -cp "./bin;lib/*" de.gmino.codegen.Meva ../checkin.module/gwt ../checkin.module/android ../meva.module/android ../cycleway.module/gwt

