find ../ios/ ../../itemscript_ios/src -name "*.java" | \
 xargs j2objc \
 -d ../ios_objc/BasisApp/BasisApp/gen \
 -sourcepath ../ios/cpy:\
../ios/src:\
../ios/gen:\
../../itemscript_ios/src \
 --prefixes ../ios/prefixes \
 -use-arc
