#!/bin/sh

if command -v screen >/dev/null 2>&1; then
    exec screen -S minecraft-server ./start_without_screen.sh
else
    ./start_without_screen.sh
fi
