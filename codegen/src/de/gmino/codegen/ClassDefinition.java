package de.gmino.codegen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

// Für später auf iOS: siehe Klasse http://redmine.h1898116.stratoserver.net/projects/ira/repository/revisions/master/entry/ios-reiseapp/ReiseMapViewController.m

public class ClassDefinition {
	String packageName;
	String className;
	boolean entity;
	boolean query;
	ArrayList<AttributeDefiniton> attributes;
	static Set<String> nativeTypes = new TreeSet<String>();
	static Map<String, String> sqlTypes = new TreeMap<String, String>();
	static Map<String, String> wrapperTypes = new TreeMap<String, String>();
	private Map<String, ClassDefinition> definitions;

	public ClassDefinition(Map<String, ClassDefinition> definitions) {
		this.definitions = definitions;
	}

	static void registerNativeType(String nativeType, String sqlType,
			String wrapperType) {
		nativeTypes.add(nativeType);
		sqlTypes.put(nativeType, sqlType);
		wrapperTypes.put(nativeType, wrapperType);
	}

	String target;
	private String moduleName;
	private String baseClassName;

	static {
		registerNativeType("int", "INT", "Integer");
		registerNativeType("byte", "TINYINT", "Byte");
		registerNativeType("boolean", "BOOLEAN", "Boolean");
		registerNativeType("char", "SMALLINT", "Character");
		registerNativeType("double", "DOUBLE", "Double");
		registerNativeType("float", "FLOAT", "Float");
		registerNativeType("long", "BIGINT", "Long");
		registerNativeType("short", "SMALLINT", "Short");
	}

	public void createJavaSourceFiles(File srcDir, File genDir, String target)
			throws IOException {
		String fullSrcDir = srcDir.getCanonicalPath() + "/"
				+ getFullPackage(target, false).replace(".", "/");
		String fullGenDir = genDir.getCanonicalPath() + "/"
				+ getFullPackage(target, true).replace(".", "/");
		new File(fullSrcDir).mkdirs();
		new File(fullGenDir).mkdirs();

		this.target = target;
		baseClassName = className;
		className += "Gen";

		if (target.equals("android"))
			createJavaAndroidGen(new File(fullGenDir + "/" + className
					+ ".java"));
		else if (target.equals("server"))
			createJavaServerGen(new File(fullGenDir + "/" + className + ".java"));
		else if (target.equals("client"))
			createJavaClientGen(new File(fullGenDir + "/" + className + ".java"));
		else
			createJavaSharedGen(new File(fullGenDir + "/" + className + ".java"));
		className = baseClassName;
		createJavaExtends(new File(fullSrcDir + "/" + className + ".java"));
	}

	private void createRegisterType(PrintWriter pw) {
		pw.println("	static {");
		pw.println("		EntityFactory.registerType(\"" + baseClassName + "\");");
		pw.println("	}");
		pw.println();
	}

	public void createJavaExtends(File file) throws IOException {
		if (file.exists())
			return;
		final FileOutputStream fos = new FileOutputStream(file);
		PrintWriter pw = new PrintWriter(fos);
		pw.println("// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.\n// To regenerate this file, delete it and run Meva again.");
		pw.println();
		pw.println("package " + getFullPackage(target, false) + ";");
		pw.println();
		generateImports(pw);
		pw.println();
		pw.println("import " + getFullyQualifiedName(target, true) + ";");

		
		pw.println("public class " + className + " extends " + className
				+ "Gen {");
		generateConstructors(pw, true);
		pw.println("}");
		pw.close();
		fos.close();

	}

	public void createJavaAndroidGen(File file) throws IOException {
		final FileOutputStream fos = new FileOutputStream(file);
		PrintWriter pw = new PrintWriter(fos);
		pw.println("// DONTEDIT This file has been generated. \n// You should not edit this file, because your edits will be lost later. \n// There is a derived class without 'Gen' in its name that you may edit.");
		pw.println();
		pw.println("package " + getFullPackage(target, true) + ";");
		pw.println();
		generateImports(pw);
		pw.println();

		String modifier = query ? "abstract " : " ";
		pw.println("public " + modifier + "class " + className + " extends "
				+ getFullPackage("shared", false) + "." + baseClassName
				+ (entity ? " implements EntityAndroid" : " implements ValueAndroid") + " {");

		generateConstructors(pw, true);
		if(entity)
			generateDeserialisers(pw);

		pw.println("	// Binary");
		generateBinarySerializer(pw);

		pw.println("}");
		pw.close();
		fos.close();

	}

	public void createJavaServerGen(File file) throws IOException {
		final FileOutputStream fos = new FileOutputStream(file);
		PrintWriter pw = new PrintWriter(fos);
		pw.println("// DONTEDIT This file has been generated. \n// You should not edit this file, because your edits will be lost later. \n// There is a derived class without 'Gen' in its name that you may edit.");
		pw.println();
		pw.println("package " + getFullPackage(target, true) + ";");
		pw.println();
		generateImports(pw);
		pw.println();

		String modifier = query ? "abstract " : " ";
		pw.println("public " + modifier + "class " + className + " extends "
				+ getFullPackage("shared", false) + "." + baseClassName + " {");

		generateConstructors(pw, true);

		if (entity) {
			generateDeserialisers(pw);

			pw.println("	// Sql");
			generateTableComment(pw);
			generateSqlSerializer(pw);
			pw.println();
		}

		pw.println("	// Binary");
		generateBinarySerializer(pw);

		pw.println("}");
		pw.close();
		fos.close();

	}

	public void createJavaClientGen(File file) throws IOException {
		final FileOutputStream fos = new FileOutputStream(file);
		PrintWriter pw = new PrintWriter(fos);
		pw.println("// DONTEDIT This file has been generated. \n// You should not edit this file, because your edits will be lost later. \n// There is a derived class without 'Gen' in its name that you may edit.");
		pw.println();
		pw.println("package " + getFullPackage(target, true) + ";");
		pw.println();
		generateImports(pw);
		pw.println();

		String modifier = query ? "abstract " : " ";
		pw.println("public " + modifier + "class " + className + " extends "
				+ getFullPackage("shared", false) + "." + baseClassName + " {");
		generateConstructors(pw, true);
		if(entity)
			generateDeserialisers(pw);

		pw.println("}");
		pw.close();
		fos.close();

	}

	public void createJavaSharedGen(File file) throws IOException {
		final FileOutputStream fos = new FileOutputStream(file);
		PrintWriter pw = new PrintWriter(fos);
		pw.println("// DONTEDIT This file has been generated. \n// You should not edit this file, because your edits will be lost later. \n// There is a derived class without 'Gen' in its name that you may edit.");
		pw.println();
		pw.println("package " + getFullPackage(target, true) + ";");
		pw.println();

		generateImports(pw);

		String modifier = query ? "abstract " : " ";
		pw.println("public " + modifier + "class " + className + " implements "
				+ (entity ? "Entity" : "Value") + (query ? ", Query" : "")
				+ " {");

		if (entity)
			createRegisterType(pw);

		pw.println("	// Attributes");
		generateAttributes(pw);
		pw.println();

		generateConstructors(pw, false);
		if(entity)
			generateDeserialisers(pw);

		if (entity) {
			pw.println("	// Factory- and entity-related");
			generateGetById(pw);
			generateGetNew(pw);
			pw.println();
		}

		pw.println("	// Json");
		generateJsonSerializer(pw);
		pw.println();

		pw.println("	// Getters");
		for (AttributeDefiniton attribute : attributes) {
			generateGetter(attribute, pw);
			pw.println();
		}

		if (entity) {
			pw.println("	// Setters");
			for (AttributeDefiniton attribute : attributes) {
				generateSetter(attribute, pw);
				pw.println();
			}
		}

		if (query) {
			pw.println("	// Query specific");
			pw.println("	public String getUrlPostfix()");
			pw.println("	{");
			pw.println("		return \"" + baseClassName + "\";");
			pw.println("	}");
		}

		pw.println("	// Stuff");
		generateToString(pw);
		if (entity)
			generateGetTypeName(pw);

		pw.println("}");
		pw.close();
		fos.close();
	}

	private void generateConstructors(PrintWriter pw, boolean superConstructor) {
		pw.println("	// Constructors");
		// pw.println("	protected " + className + "()");
		// pw.println("	{");
		// pw.println("	}");
		// pw.println();
		if (entity)
			generateIdConstructor(pw, superConstructor);
		else {
			if (target.equals("server"))
				generateSqlDeserializerConstructor(pw, superConstructor, false);
			if (target.equals("server") || target.equals("android"))
				generateBinaryDeserializerConstructor(pw, superConstructor);
			generateJsonDeserializerConstructor(pw, superConstructor);
		}

		generateAttributeConstructor(pw, superConstructor);

		pw.println();
	}

	/**
	 * For entities only
	 * 
	 * @param pw
	 */
	private void generateDeserialisers(PrintWriter pw) {
		if (target.equals("server"))
			generateSqlDeserializer(pw);
		if (target.equals("server") || target.equals("android"))
			generateBinaryDeserializer(pw);
		//if (target.equals("server") || target.equals("client")|| target.equals("android"))
		generateJsonDeserializer(pw);
	}

	private void generateGetNew(PrintWriter pw) {
		pw.println("	public static " + className + " getNew()");
		pw.println("	{");
		pw.println("		return (" + className + ")EntityFactory.getNewEntity(\""
				+ baseClassName + "\");");
		pw.println("	}");
		pw.println();

	}

	private void generateGetById(PrintWriter pw) {
		pw.println("	public static " + className
				+ " getById(long id, ReturnEntityPolicy policy)");
		pw.println("	{");
		pw.println("		return (" + className + ")EntityFactory.getEntityById(\""
				+ baseClassName + "\", id, policy);");
		pw.println("	}");
		pw.println();
	}

	private void generateTableComment(PrintWriter pw) {
		pw.println();
		pw.println("/*");
		pw.println("To generate a table, use the following SQL command:");
		pw.println();
		pw.println("CREATE TABLE IF NOT EXISTS `" + className + "` (");
		generateTableCommentAttributeList("", pw);
		pw.println("\tPRIMARY KEY (`id`)");
		pw.println(") ENGINE=MyISAM DEFAULT CHARSET=utf8;");
		pw.println();
		pw.println("*/");
		pw.println();
	}

	private void generateTableCommentAttributeList(String prefix, PrintWriter pw) {
		for (AttributeDefiniton attribute : attributes) {

			String typeName = attribute.typeName;
			String attributeName = attribute.attributeName;
			if (typeName.equals("String"))
				pw.println("\t`" + prefix + attributeName + "` varchar("
						+ attribute.maxLen + ") NOT NULL,");
			else if (nativeTypes.contains(typeName))
				pw.println("\t`" + prefix + attributeName + "` "
						+ sqlTypes.get(typeName) + " NOT NULL,");
			else {
				ClassDefinition classDef = getClassDefinition(typeName, true);
				if (classDef.entity)
					pw.println("\t`" + prefix + attributeName + "_id` BIGINT,");
				else
					getClassDefinition(typeName, true)
							.generateTableCommentAttributeList(
									prefix + attributeName + "_", pw);
			}
		}
	}

	private void generateAttributes(PrintWriter pw) {
		for (AttributeDefiniton attribute : attributes) {
			if (isAttributeEntity(attribute)) {
				pw.println("	protected " + attribute.typeName + " "
						+ attribute.attributeName + ";");
				pw.println("	protected long " + attribute.attributeName
						+ "_id;");
			} else {
				String modifier = "protected";
				// we can't use final here, because some subclasses may have
				// constructors that perform complex read operations
				// to get the initial values. But we would need to set them all
				// in a call to the super constructor.
				// if (!entity)
				// modifier += " final";
				pw.println("	" + modifier + " " + attribute.typeName + " "
						+ attribute.attributeName + ";");
			}
		}
	}

	private void generateImports(PrintWriter pw) {
		pw.println("// gmino stuff");
		if (entity)
			pw.println("import de.gmino.meva.shared.Entity;");
		else
			pw.println("import de.gmino.meva.shared.Value;");

		if (query)
			pw.println("import de.gmino.meva.shared.Query;");

		pw.println("import de.gmino.meva.shared.EntityFactory;");
		pw.println("import de.gmino.meva.shared.ReturnEntityPolicy;");
		pw.println();

		pw.println("// default imports");
		pw.println("import java.io.DataInputStream;");
		pw.println("import java.io.DataOutputStream;");
		pw.println("import java.io.IOException;");
		pw.println("import java.io.PrintWriter;");
		pw.println("import java.io.StringWriter;");

		pw.println();
		
		pw.println("// imports for JSON");
		pw.println("import org.itemscript.core.values.JsonObject;");
		pw.println("import org.itemscript.core.values.JsonValue;");
		pw.println();


		if (target.equals("android") || target.equals("server")) {
			pw.println("// imports for SQL stuff");
			pw.println("import java.sql.Connection;");
			pw.println("import java.sql.ResultSet;");
			pw.println("import java.sql.Statement;");
			pw.println("import java.sql.SQLException;");
			pw.println();
		}

		if (target.equals("android")) {
			pw.println("// android");
			pw.println("import de.gmino.meva.android.EntityAndroid;");
			pw.println("import de.gmino.meva.android.ValueAndroid;");
			pw.println();
		}
		TreeSet<String> importClasses = new TreeSet<String>();
		for (AttributeDefiniton attribute : attributes) {
			String typeName = attribute.typeName;
			if (!typeName.equals("String") && !nativeTypes.contains(typeName)) {
				ClassDefinition classDef = getClassDefinition(typeName, true);

				String targetForImport = target;
				// if (Meva.getModule(classDef.getModuleName(), target) == null)
				// targetForImport = "shared";

				importClasses.add(classDef.getFullyQualifiedName(
						targetForImport, false));
			}
		}
		if (importClasses.size() > 0) {
			pw.println("// imports for field types");

			for (String qualifiedClassName : importClasses) {
				pw.println("import " + qualifiedClassName + ";");
			}
			pw.println();
		}
	}

	private String getModuleName() {
		return moduleName;
	}

	private boolean isAttributeEntity(AttributeDefiniton attribute) {
		boolean attributeIsEntity = false;
		if (!attribute.typeName.equals("String")
				&& !nativeTypes.contains(attribute.typeName))
			attributeIsEntity = getClassDefinition(attribute.typeName, true).entity;
		return attributeIsEntity;
	}

	private void generateBinaryDeserializer(PrintWriter pw) {
		pw.println("	public void deserializeBinary(DataInputStream dis) throws IOException");
		pw.println("	{");
		for (AttributeDefiniton attribute : attributes) {
			String type = attribute.typeName;
			if (nativeTypes.contains(type))
				pw.println("		" + attribute.attributeName + " = dis.read"
						+ capitalizeFirst(type) + "();");
			else if (type.equals("String")) {
				String varName = "has"
						+ capitalizeFirst(attribute.attributeName);
				pw.println("		byte " + varName + " = dis.readByte();");
				pw.println("		if(" + varName + " != 0)");
				pw.println("			" + attribute.attributeName
						+ " = dis.readUTF();");
			} else {
				ClassDefinition typeDef = getClassDefinition(type, true);
				if (typeDef.entity) {
					pw.println("		" + attribute.attributeName
							+ "_id = dis.readLong();");
				} else {
					if (entity) {
						String varName = "has"
								+ capitalizeFirst(attribute.attributeName);
						pw.println("		byte " + varName + " = dis.readByte();");
						pw.println("		if(" + varName + " != 0)");
						pw.println("			" + attribute.attributeName + " = new "
								+ attribute.typeName + "(dis);");
					} else {
						pw.println("		" + attribute.attributeName + " = new "
								+ attribute.typeName + "(dis);");
					}
				}
			}
		}
		pw.println("	}");
	}

	private void generateBinaryDeserializerConstructor(PrintWriter pw, boolean superConstructor) {
		pw.println("	public " + className
				+ "(DataInputStream dis) throws IOException");
		pw.println("	{");
		pw.print("		this(");
		boolean first = true;
		for (AttributeDefiniton attribute : attributes) {
			pw.print(first ? "\n" : ",\n");
			String type = attribute.typeName;
			if (nativeTypes.contains(type))
				pw.print("			dis.read" + capitalizeFirst(type) + "()");
			else if (type.equals("String")) {
				pw.print("				dis.readUTF()");
			} else {
				ClassDefinition typeDef = getClassDefinition(type, true);
				if (typeDef.entity) {
					pw.print("			dis.readLong()");
				} else {
					if (entity) {
						pw.print("			new " + attribute.typeName + "(dis)");
					} else {
						pw.print("			new " + attribute.typeName + "(dis)");
					}
				}
			}
			first = false;
		}
		pw.println(");\n	}");
	}

	private void generateBinarySerializer(PrintWriter pw) {
		pw.println("	public void serializeBinary(DataOutputStream dos) throws IOException");
		pw.println("	{");
		for (AttributeDefiniton attribute : attributes) {
			String type = attribute.typeName;
			if (nativeTypes.contains(type))
				pw.println("		dos.write" + capitalizeFirst(type) + "("
						+ attribute.attributeName + ");");
			else if (type.equals("String")) {

				pw.println("		if(" + attribute.attributeName + " == null)");
				pw.println("			dos.writeByte(0);");
				pw.println("		else");
				pw.println("		{");
				pw.println("			dos.writeByte(1);");
				pw.println("			dos.writeUTF(" + attribute.attributeName + ");");
				pw.println("		}");
			} else {
				ClassDefinition typeDef = getClassDefinition(type, true);
				if (typeDef.entity) {
					pw.println("		if (" + attribute.attributeName + " == null)");
					pw.println("			dos.writeLong(0);");
					pw.println("		else");
					pw.println("			dos.writeLong(" + attribute.attributeName
							+ ".getId());");
				} else {
					if (entity) {
						pw.println("		if (" + attribute.attributeName
								+ " == null)");
						pw.println("			dos.writeByte(0);");
						pw.println("		else");
						pw.println("		{");
						pw.println("			dos.writeByte(1);");
						pw.println("			(("
								+ typeDef.getFullyQualifiedName(target, false)
								+ ")" + attribute.attributeName
								+ ").serializeBinary(dos);");
						pw.println("		}");
					} else
						pw.println("		((" + attribute.typeName + ")"
								+ attribute.attributeName
								+ ").serializeBinary(dos);");
				}

			}
		}
		pw.println("	}");
	}

	private ClassDefinition getClassDefinition(String type,
			boolean throwIfNotFound) {
		ClassDefinition typeDef = definitions.get(type);
		if (throwIfNotFound && typeDef == null)
			throw new RuntimeException("Type " + type + " in unknown.");
		return typeDef;
	}

	// for entities only
	private void generateJsonDeserializer(PrintWriter pw) {
		pw.println("	public void deserializeJson(JsonObject json) throws IOException");
		pw.println("	{");
		pw.println("		JsonValue val;");

		for (AttributeDefiniton attribute : attributes) {
			String type = attribute.typeName;
			pw.println("		val = json.get(\"" + attribute.attributeName + "\");");
			if (nativeTypes.contains(type)) {
				pw.println("		" + attribute.attributeName + " = "
						+ wrapperTypes.get(type) + ".parse"
						+ capitalizeFirst(type)
						+ "(val.asString().stringValue());");
				/*
				 * if (type.equals("boolean")) pw.println("		" +
				 * attribute.attributeName +
				 * " = Boolean.parseBool(val.isBoolean());"); else
				 * pw.println("		" + attribute.attributeName +
				 * " = ("+type+")val.isNumber().doubleValue();");
				 */
			} else if (type.equals("String")) {
				pw.println("		" + attribute.attributeName
						+ " = val.asString().stringValue();");
			} else {
				ClassDefinition typeDef = getClassDefinition(type, true);
				if (typeDef.entity) {
					pw.println("		long "
							+ attribute.attributeName
							+ "Id = Long.parseLong(val.asString().stringValue());");
				} else {
					pw.println("		" + attribute.attributeName + " = new "
							+ attribute.typeName + "(val.asObject());");

				}
			}
		}
		pw.println("	}");
		pw.println();
	}

	// only for values
	private void generateJsonDeserializerConstructor(PrintWriter pw, boolean superConstructor) {
		pw.println("	public " + className
				+ "(JsonObject json) throws IOException");
		pw.println("	{");
		pw.print("		this(");

		boolean first = true;
		for (AttributeDefiniton attribute : attributes) {
			pw.print(first ? "\n" : ",\n");
			String type = attribute.typeName;
			String val = "json.get(\"" + attribute.attributeName + "\")";
			if (nativeTypes.contains(type)) {
				pw.print("			" + wrapperTypes.get(type) + ".parse"
						+ capitalizeFirst(type) + "(" + val
						+ ".asString().stringValue())");
			} else if (type.equals("String")) {
				pw.print("			" + val + ".asString().stringValue()");
			} else {
				ClassDefinition typeDef = getClassDefinition(type, true);
				if (typeDef.entity) {
					pw.print("			Long.parseLong(" + val
							+ ".asString().stringValue())");
				} else {
					pw.print("			new " + attribute.typeName + "(" + val
							+ ".asObject())");

				}
			}
			first = false;
		}
		pw.println(");\n	}");
		pw.println();
	}

	private void generateJsonSerializer(PrintWriter pw) {
		pw.println("	public void serializeJson(StringBuilder sb) throws IOException");
		pw.println("	{");
		pw.println("		serializeJson(sb,\"\");");
		pw.println("	}");
		pw.println();

		pw.println("	public void serializeJson(StringBuilder sb, String indentation) throws IOException");
		pw.println("	{");
		pw.println("		sb.append(indentation + \"{\");");
		pw.println("		String moreIndentation = indentation + \"\\t\";");

		boolean first = true;
		for (AttributeDefiniton attribute : attributes) {
			if (first)
				pw.println("		sb.append(\"\\n\");");
			else
				pw.println("		sb.append(\",\\n\");");

			String name = attribute.attributeName;
			String type = attribute.typeName;
			pw.println("		sb.append(moreIndentation + \"\\\"" + name
					+ "\\\" : \");");
			if (nativeTypes.contains(type) || type.equals("String"))
				pw.println("		sb.append(\"\\\"\" + " + name + " + \"\\\"\");");
			else {
				ClassDefinition typeDef = getClassDefinition(type, true);
				if (typeDef.entity)
					pw.println("		sb.append(\"\\\"\" + " + name
							+ "_id  +\"\\\"\");");
				else {
					pw.println("		sb.append(\"\");");
					pw.println("		if(" + name + " != null)");
					pw.println("			" + name
							+ ".serializeJson(sb, moreIndentation);");
					pw.println("		else");
					pw.println("			sb.append(\"null\");");
				}
			}
			first = false;
		}
		pw.println("		sb.append(\"\\n\" + indentation + \"}\");");
		pw.println("	}");
	}

	private void generateSqlDeserializer(PrintWriter pw) {
		if (!entity)
			throw new RuntimeException(
					"Can't serialize value classes into SQL yet.");

		pw.println("	public void deserializeSql(Connection dbCon) throws SQLException");
		pw.println("	{");
		pw.println("		String prefix = \"\";");
		pw.println("		Statement stat = dbCon.createStatement();");
		pw.print("		String selectString = \"SELECT  ");
		printAttributeList("", pw);
		pw.println(" FROM `" + baseClassName + "` WHERE id = '\"+id+\"';\";");
		pw.println("		stat.executeQuery(selectString);");
		pw.println("		System.out.println(selectString);");
		pw.println("		ResultSet rs = stat.executeQuery(selectString);");
		pw.println("		rs.next();");
		// pw.println("		return new " + getFullyQualifiedName(target, false) +
		// "(\"\", rs);");
		printValueReadList("", pw);
		// printValueReadList("", "", pw);
		pw.println("	}");
	}

	/**
	 * For Values only
	 * 
	 * @param pw
	 * @param superConstructor
	 * @param superHasSqlDeserializerConstructor
	 */
	private void generateSqlDeserializerConstructor(PrintWriter pw,
			boolean superConstructor, boolean superHasSqlDeserializerConstructor) {
		pw.println("	// Constructor for SQL deseralizaiton");
		pw.println("	public " + className
				+ "(String prefix, ResultSet rs) throws SQLException");
		pw.println("	{");
		if (superConstructor) {
			if (superHasSqlDeserializerConstructor)
				pw.println("		super(prefix, rs);");
			else {
				pw.println("		super(");
				printValueReadListArguments("", pw);
				pw.println("		);");
			}
		} else
			printValueReadList("", pw);
		pw.println("	}");
	}

	private void generateSqlSerializer(PrintWriter pw) {
		if (!entity)
			throw new RuntimeException(
					"Can't serialize value classes into SQL yet.");

		pw.println("	public void serializeSql(Connection dbCon) throws SQLException");
		pw.println("	{");
		pw.println("		Statement stat = dbCon.createStatement();");
		pw.print("		String replaceString = \"REPLACE INTO `" + className
				+ "` (");
		printAttributeList("", pw);
		pw.print(") VALUES (");
		printValueList("", pw);
		pw.println(");\";");
		pw.println("		stat.executeUpdate(replaceString);");
		pw.println("	}");
	}

	private void printAttributeList(String prefix, PrintWriter pw) {
		final int size = attributes.size();
		for (int i = 0; i < size; i++) {
			AttributeDefiniton attribute = attributes.get(i);
			if (nativeTypes.contains(attribute.typeName)
					|| attribute.typeName.equals("String"))
				pw.print("`" + prefix + attribute.attributeName + "`");
			else {
				ClassDefinition classDef = getClassDefinition(
						attribute.typeName, true);
				if (classDef.entity)
					pw.print("`" + prefix + attribute.attributeName + "_id`");
				else
					classDef.printAttributeList(prefix
							+ attribute.attributeName + "_", pw);
			}
			pw.print((i != size - 1) ? "," : "");
		}
	}

	private void printValueList(String prefix, PrintWriter pw) {
		final int size = attributes.size();

		for (int i = 0; i < size; i++) {
			AttributeDefiniton attribute = attributes.get(i);
			final String getter = ((attribute.typeName.equals("boolean")) ? "is"
					: "get")
					+ capitalizeFirst(attribute.attributeName) + "()";
			if (nativeTypes.contains(attribute.typeName)
					|| attribute.typeName.equals("String"))
				pw.print("'\" + " + prefix + getter + " + \"'");
			else {
				ClassDefinition classDef = getClassDefinition(
						attribute.typeName, true);
				if (classDef.entity) {
					pw.print("'\" + get"
							+ capitalizeFirst(attribute.attributeName)
							+ "Id() + \"'");
				} else
					classDef.printValueList(prefix + getter + ".", pw);
			}
			pw.print((i != size - 1) ? "," : "");
		}
	}

	private void printValueReadList(String prefixDash, PrintWriter pw) {
		final int size = attributes.size();

		for (int i = 0; i < size; i++) {
			AttributeDefiniton attribute = attributes.get(i);
			if (nativeTypes.contains(attribute.typeName)
					|| attribute.typeName.equals("String"))
				pw.println("			" + attribute.attributeName + " = rs.get"
						+ capitalizeFirst(attribute.typeName) + "(prefix + \""
						+ prefixDash + attribute.attributeName + "\");");
			else {
				ClassDefinition classDef = getClassDefinition(
						attribute.typeName, true);
				if (classDef.entity)
					pw.println("			" + attribute.attributeName
							+ "_id = rs.getLong(\"" + attribute.attributeName
							+ "_id\");");
				else
					pw.println("			" + attribute.attributeName + " = new "
							+ classDef.className + "(prefix + \""
							+ attribute.attributeName + "_" + "\", rs);");
			}
		}
	}

	private void printValueReadListArguments(String prefixDash, PrintWriter pw) {
		final int size = attributes.size();

		for (int i = 0; i < size; i++) {
			AttributeDefiniton attribute = attributes.get(i);
			if (nativeTypes.contains(attribute.typeName)
					|| attribute.typeName.equals("String"))
				pw.print("			rs.get" + capitalizeFirst(attribute.typeName)
						+ "(prefix + \"" + prefixDash + attribute.attributeName
						+ "\")");
			else {
				ClassDefinition classDef = getClassDefinition(
						attribute.typeName, true);
				if (classDef.entity)
					pw.print("			(" + classDef.baseClassName
							+ ")EntityFactory.getEntityById(\""
							+ classDef.baseClassName + "\",rs.getLong(\""
							+ attribute.attributeName + "_id\"))");
				else
					pw.print("			new " + classDef.className + "(prefix + \""
							+ attribute.attributeName + "_" + "\", rs)");
			}
			pw.print((i < size - 1) ? ",\n" : "\n");
		}
	}

	private void generateAttributeConstructor(PrintWriter pw,
			boolean superConstructor) {
		pw.println("	public " + className + "(");
		final int size = attributes.size();
		for (int i = 0; i < size; i++) {
			AttributeDefiniton attribute = attributes.get(i);
			pw.println("			" + attribute.typeName + " "
					+ attribute.attributeName + ((i != size - 1) ? "," : ")"));
		}
		pw.println("	{");

		if (superConstructor) {
			pw.print("\t\tsuper(");
			boolean first = true;
			for (AttributeDefiniton attribute : attributes) {
				pw.print(first ? "" : ",");
				ClassDefinition attributeTypeDef = getClassDefinition(
						attribute.typeName, false);
				if (attributeTypeDef != null)
					pw.print("\n			("
							+ attributeTypeDef.getFullyQualifiedName(target,
									false) + ")" + attribute.attributeName);
				else
					pw.print("\n			" + attribute.attributeName);
				first = false;
			}
			pw.println("\n\t\t);");
		} else
			for (AttributeDefiniton attribute : attributes)
				pw.println("		this." + attribute.attributeName + " = "
						+ attribute.attributeName + ";");

		pw.println("	}");
		pw.println("	");
	}

	private void generateIdConstructor(PrintWriter pw, boolean superConstructor) {
		pw.println("	public " + className + "(long id)");
		pw.println("	{");
		if (superConstructor)
			pw.println("		super(id);");
		else
			pw.println("		this.id = id;");
		pw.println("	}");
		pw.println("	");
	}

	private void generateSetter(AttributeDefiniton attribute, PrintWriter pw) {
		pw.println("	public void set"
				+ capitalizeFirst(attribute.attributeName) + "("
				+ attribute.typeName + " " + attribute.attributeName + ")");
		pw.println("	{");
		pw.println("		this." + attribute.attributeName + " = "
				+ attribute.attributeName + ";");
		pw.println("	}");
		pw.println("	");
	}

	private void generateGetter(AttributeDefiniton attribute, PrintWriter pw) {
		if (isAttributeEntity(attribute)) {
			pw.println("	public " + attribute.typeName + " get"
					+ capitalizeFirst(attribute.attributeName)
					+ "(ReturnEntityPolicy policy)");
			pw.println("	{");
			pw.println("		return (" + attribute.typeName
					+ ")EntityFactory.getEntityById(\"" + attribute.typeName
					+ "\"," + attribute.attributeName + "_id, policy);");
		} else {
			pw.println("	public " + attribute.typeName
					+ ((attribute.typeName.equals("boolean")) ? " is" : " get")
					+ capitalizeFirst(attribute.attributeName) + "()");
			pw.println("	{");

			pw.println("		return " + attribute.attributeName + ";");
		}
		pw.println("	}");
		pw.println("	");

		if (isAttributeEntity(attribute)) {
			pw.println("	public long get"
					+ capitalizeFirst(attribute.attributeName) + "Id()");
			pw.println("	{");
			pw.println("		return " + attribute.attributeName + "_id;");
			pw.println("	}");
			pw.println("	");
		}
	}

	private String capitalizeFirst(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static ClassDefinition fromFile(File schemaFile,
			Map<String, ClassDefinition> definitions, String moduleName)
			throws IOException {
		JSONObject json = (JSONObject) JSONValue.parse(new FileReader(
				schemaFile));
		ClassDefinition ret = new ClassDefinition(definitions);
		ret.packageName = (String) json.get("package");
		ret.className = (String) json.get("name");
		ret.baseClassName = (String) json.get("name");
		ret.entity = json.get("concept").equals("entity");
		if (json.containsKey("query"))
			ret.query = json.get("query").equals("true");

		ret.attributes = new ArrayList<AttributeDefiniton>();

		if (ret.entity) {
			ret.attributes.add(new AttributeDefiniton("long", "id"));
			ret.attributes.add(new AttributeDefiniton("boolean", "ready"));
		}

		JSONArray attributes = (JSONArray) json.get("attributes");
		for (Object o : attributes) {
			JSONObject att = (JSONObject) o;
			ret.attributes.add(new AttributeDefiniton((String) att.get("type"),
					(String) att.get("name")));
		}

		ret.moduleName = moduleName;

		return ret;
	}

	private void generateToString(PrintWriter pw) {
		pw.println("	@Override");
		pw.println("	public String toString() {");
		pw.println("		StringBuilder sb = new StringBuilder();");
		pw.println("		try {");
		pw.println("			serializeJson(sb);");
		pw.println("		} catch (IOException e) {");
		pw.println("			e.printStackTrace();");
		pw.println("			return \"Error while building String: \" + e.getMessage();");
		pw.println("		}");
		pw.println("		return sb.toString();");
		pw.println("	}");
	}

	private void generateGetTypeName(PrintWriter pw) {
		pw.println("	@Override");
		pw.println("	public String getTypeName() {");
		pw.println("		return \"" + baseClassName + "\";");
		pw.println("	}");
	}

	public String getFullPackage(String target, boolean gen) {
		return packageName.replace("TARGET", target) 
				+ (gen ? ".gen" : "");
	}

	public String getFullyQualifiedName(String target, boolean gen) {
		// sometimes, "Gen" is added temporaty to the class name. This has its
		// use, but is useless when we need the FQN.
		String tmpClassName = className.replace("Gen", "");

		return getFullPackage(target, gen) + "." + tmpClassName
				+ (gen ? "Gen" : "");
	}

}
