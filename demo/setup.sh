#!/bin/zsh
killall xbindkeys
xbindkeys -f ./demo/.xbindkeyrc &
echo 0 > ./demo/ind.txt
alacritty msg config font.size=20
#alacritty msg config font.size=32

rm -rf ./.todo.tlst
rm -rf ~/.todo.tlst

source /home/sowsanti/school/repos/s3/dai/dai_pw01/demo/prompt.zsh

xdotool key Return
tput clear
source /usr/share/zsh/plugins/zsh-syntax-highlighting/zsh-syntax-highlighting.zsh
