
JAVA_OPTS="$JAVA_OPTS -Xms64m -Xmx512m -Djava.net.preferIPv4Stack=true"
JAVA_OPTS="$JAVA_OPTS -Djava.security.egd=file:/dev/./urandom"

java $JAVA_OPTS -jar /java-services.jar