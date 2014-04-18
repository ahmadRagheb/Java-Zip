Java-Zip
========

Java IO
java.util.zip package

Zip.java to unzip a Zip archive into a given directory, For example:
>java Zip a.zip destDir
which is unzip all contents of a.zip into the destDir directory

Unzip.java which, when given directory <dir>, can zip the whole directory (including all files and subdirectories) into a Zip archive.
>java Unzip srcDir b.zip
which is Zip the whole srcDir (together with all its contents) into a new zip file b.zip
