j2objc \
 -d ../ios_objc/BasisApp/BasisApp/gen \
 -sourcepath ../ios/cpy:\
../ios/src:\
../ios/gen:\
../../itemscript_ios/src \
 --prefixes ../ios/prefixes \
 -use-arc $1
