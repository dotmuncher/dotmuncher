#!/bin/bash
cd orig
FILES=*.png
for f in $FILES
do
    identify $f
    convert $f -resize 50x50 $f
done

