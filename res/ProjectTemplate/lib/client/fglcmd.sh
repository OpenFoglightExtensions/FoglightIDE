#!/bin/sh
# Copyright 2012 Quest Software, Inc.
# ALL RIGHTS RESERVED.
#
# This software is the confidential and proprietary information of
# Quest Software Inc. ("Confidential Information").  You shall not
# disclose such Confidential Information and shall use it only in
# accordance with the terms of the license agreement you entered
# into with Quest Software Inc.
#
# QUEST SOFTWARE INC. MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT
# THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED,
# INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF
# MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
# NON-INFRINGEMENT. QUEST SOFTWARE SHALL NOT BE LIABLE FOR ANY
# DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
# OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.


#
# Some basic variables
#
PLATFORM=`uname`
# Copy the working directory
wd=`pwd`

instdir=`dirname "$0"`

#
# Now figure out the per-system options
#
case "$PLATFORM" in
    Solaris|SunOS)
        PLATFORM_JAVA_ARGS=
        ;;
    AIX)
        PLATFORM_JAVA_ARGS="-Djava.net.preferIPv4Stack=true"
		# VM bug requires JITC_COMPILEOPT set to turn off inlining.
		# need the "export", otherwise java doesn't pickup the environment variable.
		export JITC_COMPILEOPT=NINLINING
        ;;
    HP-UX)
        PLATFORM_JAVA_ARGS="-XdoCloseWithReadPending"
        ;;
    *)
        PLATFORM_JAVA_ARGS=
        ;;
esac

if [ -f "$instdir/foglight-cmdline-client.jar" ]; then
    CMDLINE_HOME=$instdir
else
  	FOGLIGHT_HOME=$instdir/..
  	CMDLINE_HOME=$FOGLIGHT_HOME/tools/lib
fi

# First try our local JRE
JRE=$FOGLIGHT_HOME/jre
if [ -d "$JRE" ]; then
    JAVA_OPTS=-server $JAVA_OPTS
else
	# Try to fall back on JAVA_HOME/jre
	JRE=$JAVA_HOME/jre
	if [ ! -d "$JRE" ]; then
		# Try to fall back on JAVA_HOME
		JRE=$JAVA_HOME
		if [ ! -d "$JRE" ]; then
			echo "The JDK wasn't found in directory $JRE."
			echo "Please edit the $0 script so that the JRE variable points to the"
			echo "root directory of your Java installation or set JAVA_HOME"
			exit 1
		fi
	fi
fi


##########################################################################
################   NO CHANGES SHOULD BE NECESSARY BELOW   ################
##########################################################################

"${JRE}/bin/java" $JAVA_OPTS $PLATFORM_JAVA_ARGS -jar "${CMDLINE_HOME}/foglight-cmdline-client.jar" "$@"

