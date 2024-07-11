#!/bin/env bash

DIR_PATH=$1

for file in "$DIR_PATH"/*; do
    if [ -f "$file" ]; then
        typeset -l extension
        extension="${file##*.}"
        if [ "$extension" == "jpg" ] || [ "$extension" == "png" ] || [ "$extension" == "gif" ]; then
            if [ ! -d "$DIR_PATH/images" ]; then
                mkdir "$DIR_PATH/images"
            fi
            mv "$file" "$DIR_PATH/images"
        elif [ "$extension" == "txt" ] || [ "$extension" == "doc" ] || [ "$extension" == "pdf" ]; then
            if [ ! -d "$DIR_PATH/documents" ]; then
                mkdir "$DIR_PATH/documents"
            fi
            mv "$file" "$DIR_PATH/documents"
        else
            if [ ! -d "$DIR_PATH/others" ]; then
                mkdir "$DIR_PATH/others"
            fi
            mv "$file" "$DIR_PATH/others"
        fi
            
    fi
done
