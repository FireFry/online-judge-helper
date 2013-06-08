cd %1
%JAVA_HOME%\bin\javac.exe %2.java
del output_yours.txt
java -Xmx544m -Xss64m -DONLINE_JUDGE %2 > output_yours.txt < input.txt
exit
