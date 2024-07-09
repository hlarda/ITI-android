#!/usr/bin/env bash

[[ -f ~/.bashrc ]] && {
  cat<<-EOF >> ~/.bashrc
	export PATH=$PATH:$HOME/.dotnet/tools
	export LOCAL="$(whoami)"
EOF
}

gnome-terminal
