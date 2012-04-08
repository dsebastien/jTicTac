# JNLP config:
password: fCVd4SHB3c!
name: Sebastien Dubois
organizational unit: dsebastien.net
organization: -
city: Jurbise
State: Hainaut
country: BE

# How to set up dev env:
* Download/install Java Fx 2 SDK
* Maven settings.xml (adapt path):

	 <profile>
      <id>javafx</id>
      <properties>
        <javafx.rt.jar>.../JavaFX 2.0 SDK/rt/lib/jfxrt.jar</javafx.rt.jar>
        <ant.javafx.jar>../JavaFX 2.0 SDK/tools/ant-javafx.jar</ant.javafx.jar>
      </properties>
    </profile>

	</profiles>
	<activeProfiles>
		...
		<activeProfile>javafx</activeProfile>
	</activeProfiles>

* Java Fx @ local Maven repo: http://stackoverflow.com/questions/7105660/javafx-2-0-netbeans-maven
* Copy Java Fx dlls from install\rt\bin to mvn repository (com > oracle > ...)

