#!/usr/bin/env bash
echo "==================================================="
echo "    Compiling Hospital Management System..."
echo "==================================================="
mkdir -p bin
javac -d bin $(find src -name "*.java")
if [ $? -ne 0 ]; then
    echo "[ERROR] Compilation failed!"
    exit 1
fi
echo "[SUCCESS] Compilation complete. Launching application..."
echo ""
java -cp bin com.hospital.Main
