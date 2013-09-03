find /Users/tillmann/gwt/meva/issuemap.module/ios/ /Users/tillmann/gwt/meva/itemscript_ios/src -name "UtilIos.java" | \
 xargs /Users/tillmann/iOS/j2objc/0.8.5/j2objc \
 -d /Users/tillmann/gwt/meva/issuemap.module/ios_objc/gen \
 -sourcepath /Users/tillmann/gwt/meva/issuemap.module/ios/cpy:\
/Users/tillmann/gwt/meva/issuemap.module/ios/src:\
/Users/tillmann/gwt/meva/issuemap.module/ios/gen:\
/Users/tillmann/gwt/meva/itemscript_ios/src\
 -use-arc
