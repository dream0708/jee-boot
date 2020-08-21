@echo off
echo 当前磁盘%~d0
echo 当前目录%~dp0
cd  %~dp0


call apidoc -i ./src -o ./apidoc 
call apidoc-markdown3 -p ./apidoc -o  ./apidoc/doc.md
echo '-----------------------'

cmd