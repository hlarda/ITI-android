#!/bin/bash

redLedPin=23
greenLedPin=24
targetHost="192.168.1.9"

initializeGpio() {
    echo "$redLedPin" > /sys/class/gpio/export
    echo "$greenLedPin" > /sys/class/gpio/export
    echo "out" > /sys/class/gpio/gpio"$redLedPin"/direction
    echo "out" > /sys/class/gpio/gpio"$greenLedPin"/direction
}

releaseGpio() {
    echo "$redLedPin" > /sys/class/gpio/unexport
    echo "$greenLedPin" > /sys/class/gpio/unexport
}

activateLed() {
    local pin=$1
    echo "1" > /sys/class/gpio/gpio"$pin"/value
}

deactivateLed() {
    local pin=$1
    echo "0" > /sys/class/gpio/gpio"$pin"/value
}

checkHostConnection() {
    ping -c 1 -w 5 "$targetHost" > /dev/null 2>&1
    return $?
}

executeMainLoop() {
    while true; do
        if checkHostConnection; then
            activateLed "$greenLedPin"
            deactivateLed "$redLedPin"
            echo "Host reachable, green LED activated."
            sleep 40
            deactivateLed "$greenLedPin"
        else
            activateLed "$redLedPin"
            deactivateLed "$greenLedPin"
            echo "Host unreachable, red LED activated."
            sleep 10
        fi
    done
}

trap releaseGpio EXIT
initializeGpio
executeMainLoop
