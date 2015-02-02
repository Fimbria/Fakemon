javac -d Fakemon/bin/ -cp Fakemon/libs/slick-util.jar:Fakemon/libs/lwjgl-2.8.4/jar/lwjgl.jar:Fakemon/libs/lwjgl-2.8.4/jar/lwjgl_util.jar:Fakemon/libs/gson-2.3.1.jar:Fakemon/src/fakemon/ Fakemon/src/fakemon/*.java Fakemon/src/effects/*.java Fakemon/src/tiles/*.java Fakemon/Moves/*.java
mv Fakemon/bin/*.class Fakemon/res/Moves/
cd Fakemon/bin
java -cp .:../libs/gson-2.3.1.jar:../libs/lwjgl-2.8.4/jar/lwjgl.jar:../libs/lwjgl-2.8.4/jar/lwjgl_util.jar:../libs/slick-util.jar fakemon.Start
cd ../..
