# comment
# (note: the <tab> in the command line is necessary for make to work) 

APP=graalvm
BINARY=graalvm.core

help:
		@echo "Ensure you have graalvm"
		@echo "JAVA_HOME: $(JAVA_HOME)"
		which native-image

		@echo ""
		@echo "> make help"
		@echo "  shows this screen"
		@echo "> make deps"
		@echo "  gets and unpacks all deps"
		@echo "> make native"
		@echo "  builds the native binary"

deps:  
		lein clean
		lein uberjar
		cd target && jar xf $(APP)-0.1.0-SNAPSHOT-standalone.jar && cd ..

native:	
		cd target && native-image -H:+ReportUnsupportedElementsAtRuntime -H:EnableURLProtocols=http $(BINARY) && cd ..
		@echo "built $(BINARY)"
		@echo "graalvm docs at https://www.graalvm.org/docs/getting-started/"
