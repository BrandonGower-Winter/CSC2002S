#!/bin/bash
read numLines
echo $numLines
for((i=1;i<=$numLines;i++));
do
  echo $i $((RANDOM % 10000))
done
