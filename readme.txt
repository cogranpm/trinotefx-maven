how to use maven javafx plugin

mvn javafx:compile

mvn javafx:run

mvn javafx:jlink


jlink does not work because of sqlite
there are some weird workarounds doing the rounds
but for a regular maven package style build use:

mvn clean package

get the uberjars in the target directory
note: you have to get ride of the module-info.java file or it won't compile

then you can run:
java --module-path "C:\lib\java\javafx-sdk-11.0.2\lib" --add-modules=javafx.controls,javafx.fxml -jar .\target\hellofx-1.0-SNAPSHOT-jar-with-dependencies.jar

to run the packaged app, the big bugbear is that you need that hardcoded path to the javafx libs
I can't seem to make it a relative path

therefore going  back to jlink
