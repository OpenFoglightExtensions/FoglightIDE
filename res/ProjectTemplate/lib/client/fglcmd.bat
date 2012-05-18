@REM Copyright 2008 Quest Software, Inc.
@REM ALL RIGHTS RESERVED.
@REM 
@REM This software is the confidential and proprietary information of
@REM Quest Software Inc. ("Confidential Information").  You shall not
@REM disclose such Confidential Information and shall use it only in
@REM accordance with the terms of the license agreement you entered
@REM into with Quest Software Inc.
@REM 
@REM QUEST SOFTWARE INC. MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT
@REM THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED,
@REM INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF
@REM MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
@REM NON-INFRINGEMENT. QUEST SOFTWARE SHALL NOT BE LIABLE FOR ANY
@REM DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
@REM OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
@echo off

SETLOCAL

rem Try to fall back on JAVA_HOME\jre
set JRE=%JAVA_HOME%\jre
if exist "%JRE%\lib" goto runApp

rem Try to fall back on JAVA_HOME
set JRE=%JAVA_HOME%
if exist "%JRE%\lib" goto runApp
echo.
echo The JDK wasn't found in directory %JRE%.
echo Please edit the %0 script so that the JRE variable points to the
echo root directory of your Java installation.
goto finish

:runApp

rem ##########################################################################
rem ################   NO CHANGES SHOULD BE NECESSARY BELOW   ################
rem ##########################################################################

set PATH=%JAVA_HOME%\bin;%PATH%

"%JRE%\bin\java" -jar foglight-cmdline-client.jar %*
goto finish

:finish
popd
ENDLOCAL
