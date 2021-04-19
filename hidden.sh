#!/bin/sh
CLASSPATH=.:../mdsj.jar:../gluegen-rt-natives-macosx-universal.jar:../gluegen-rt.jar:../jogl-all-natives-macosx-universal.jar:../jogl-all.jar
export CLASSPATH
cd src
javac ocha/itolab/hidden/applet/numdim/*.java
java ocha.itolab.hidden.applet.numdim.NumdimViewer
cd ..
