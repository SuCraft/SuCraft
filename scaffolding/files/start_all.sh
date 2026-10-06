#!/bin/sh

if command -v screen >/dev/null 2>&1; then
    screen -dmS minecraft-proxysh sh -c 'cd minecraft-proxy && ./start_without_screen.sh'
    screen -S minecraft-server sh -c 'cd minecraft-server && ./start_without_screen.sh'
else

    SCRIPT_DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"

    case "$(uname -s)" in
        Darwin)
            # macOS: open two separate Terminal windows.
            osascript -e "tell application \"Terminal\" to do script \"cd '$SCRIPT_DIR/minecraft-proxy' && ./start_without_screen.sh\""
            osascript -e "tell application \"Terminal\" to do script \"cd '$SCRIPT_DIR/minecraft-server' && ./start_without_screen.sh\""
            ;;

        Linux)
            # Linux: open two separate GNOME Terminal windows.
            echo "Please install screen"
            ;;

        MINGW*|MSYS*|CYGWIN*)
            # Windows from Git Bash: open two separate windows.
            cd "$SCRIPT_DIR" || exit 1
            cmd.exe /c start "" bash -lc "minecraft-proxy/start_without_screen.sh"
            cmd.exe /c start "" bash -lc "minecraft-server/start_without_screen.sh"
            ;;

        *)
            echo "Unsupported operating system: $(uname -s)" >&2
            exit 1
            ;;
    esac

fi
