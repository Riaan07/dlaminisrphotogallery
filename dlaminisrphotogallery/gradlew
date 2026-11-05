#!/usr/bin/env sh
app_path=$0
[ -n "$app_path" ] && app_path=$(dirname "$(readlink -f "$app_path" 2>/dev/null || echo "$app_path")")
app_home=$(cd "$app_path" && pwd)
CLASSPATH=$app_home/gradle/wrapper/gradle-wrapper.jar
exec java -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"