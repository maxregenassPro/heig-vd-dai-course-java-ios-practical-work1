#!/usr/bin/env bash

SEQ_FILE="$HOME/school/repos/s3/dai/dai_pw01/demo/sequence.txt"
INDEX_FILE="$HOME/school/repos/s3/dai/dai_pw01/demo/ind.txt"

[ ! -f "$INDEX_FILE" ] && echo 0 > "$INDEX_FILE"

TOTAL=$(grep -cve '^\s*$' "$SEQ_FILE")
INDEX=$(cat "$INDEX_FILE")

CMD=$(sed -n "$((INDEX+1))p" "$SEQ_FILE")

echo -n "$CMD" | xclip -selection clipboard
echo -n "$CMD" | xclip -selection primary

xdotool type --delay 10 "$CMD"
#xdotool key --clearmodifiers ctrl+shift+v
#xdotool key Right

INDEX=$(( (INDEX + 1) % TOTAL ))
echo "$INDEX" > "$INDEX_FILE"
