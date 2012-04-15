# About
jTicTac is a small time management tool. With it you can easily record what you've been doing and for how long.

It was merely an excuse for playing with JavaFX 2.0 (ahhh sweet new toys ~~).

# How to set up dev env:
* Download/install Java Fx 2 SDK
* Maven settings.xml (adapt path):
    ...	 
    <profile>
      <id>javafx</id>
      <properties>
        <javafx.rt.jar>.../JavaFX 2.0 SDK/rt/lib/jfxrt.jar</javafx.rt.jar>
        <ant.javafx.jar>../JavaFX 2.0 SDK/tools/ant-javafx.jar</ant.javafx.jar>
      </properties>
    </profile>
    ...
	<activeProfiles>
		...
		<activeProfile>javafx</activeProfile>
	</activeProfiles>

* Install JavaFX 2 in your local Maven repo: http://stackoverflow.com/questions/7105660/javafx-2-0-netbeans-maven
* Create a jar containing (at the root) all the DLLs of JavaFX 2 (if you're on Windows) or .so files otherwise. That jar should be put in your maven repository as well. That artifact will be used in the build process to generate the build (via the maven assembly plugin). This artifact correspond to the following dependency in the pom.xml:
	<dependency>
		<groupId>com.oracle</groupId>
		<artifactId>javafx-bin-x64</artifactId>
		<version>2.0</version>
		<scope>compile</scope>
	</dependency>
* Copy Java Fx dlls from install\rt\bin to mvn repository (com > oracle > ...). This is required for some IDEs (i.e., IntelliJ)

* Download install the scene editor: http://www.oracle.com/technetwork/java/javafx/tools/index.html

# How to build:
	mvn package

Once done, you'll get the 'target/build' folder with all required files. Start the app using the script.

# Links
* FXML reference: http://fxexperience.com/wp-content/uploads/2011/08/Introducing-FXML.pdf
* JavaFX 2.0 CSS reference: http://docs.oracle.com/javafx/2.0/api/javafx/scene/doc-files/cssref.html