find ../ios/ ../../itemscript_ios/src -name "*.java" | xargs -L 10 -P 4 ./generate_single.sh
