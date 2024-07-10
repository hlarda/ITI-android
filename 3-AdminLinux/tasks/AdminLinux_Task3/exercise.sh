#!/usr/bin/env bash

[[ -f ~/.bashrc ]] && {
  cat<<-EOF >> ~/.bashrc
	export HELLO=$HOSTNAME
	export LOCAL="$(whoami)"
EOF
}

gnome-terminal
