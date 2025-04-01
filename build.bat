@echo off
set JAVA_HOME=C:\Program Files\Android\Android Studio\jbr
set ANDROID_HOME=C:\Program Files\Android\Android Studio
set PATH=%JAVA_HOME%\bin;%ANDROID_HOME%\platform-tools;%PATH%

echo Очистка проекта...
gradlew clean --info

echo Сборка debug APK...
gradlew assembleDebug --scan > build_log.txt 2>&1

echo Сборка завершена. Проверьте build_log.txt для деталей.
pause 