itemscript-standard-x.x.jar contains the version of the Itemscript
library for the standard Java environment. Unless you are using GWT,
use this JAR.

itemscript-gwt-x.x.jar contains the version of the Itemscript library
for GWT. It contains everything in the standard JAR, plus some classes
for GWT. It depends on the GWT libraries. If you are using GWT, use this
JAR.

You should only need to use one of the JARs, not both. The non-GWT
classes in both are identical.

---------------------------------------------------------------------------------------

To get started in the standard Java environment, just do:

  JsonSystem system = StandardConfig.createSystem();
  
You may want to store the JsonSystem in a static member or in a ThreadLocal,
depending on your application's architecture.

---------------------------------------------------------------------------------------  

In the GWT Java environment, import the Itemscript module like this:

  <inherits name="org.itemscript.Itemscript" /> 

and then access the static object GwtSystem.SYSTEM for an instance of JsonSystem.

---------------------------------------------------------------------------------------

For some examples, go to:

  http://code.google.com/p/itemscript/source/browse/trunk/itemscript/src/org/itemscript/examples/Examples.java

For Javadoc, go to:

  http://itemscript.googlecode.com/svn/trunk/itemscript/doc/index.html