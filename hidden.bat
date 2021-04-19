set JAVA_HOME="C:\Program Files\Java\jdk1.8.0_45\bin\"
set LIBPATH=..\jogl-winlib
set JARPATH=..\jogl-jar

set CLASSPATH=%CLASSPATH%;%JARPATH%\gluegen-rt.jar;%JARPATH%\gluegen-rt-natives-windows-i586.jar;%JARPATH%\jogl-all.jar;%JARPATH%\jogl-all-natives-windows-i586.jar;..\mdsj.jar;

cd src
javac -encoding UTF8 ocha/itolab/hidden/applet/numdim/*.java
java -Djava.library.path=%LIBPATH% ocha.itolab.hidden.applet.numdim.NumdimViewer
cd ..
