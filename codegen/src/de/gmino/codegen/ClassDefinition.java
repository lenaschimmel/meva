package de.gmino.codegen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TreeSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

// Für später auf iOS: siehe Klasse http://redmine.h1898116.stratoserver.net/projects/ira/repository/revisions/master/entry/ios-reiseapp/ReiseMapViewController.m

public class ClassDefinition {
	private static final String END_OF_CONSTRUCTOR_BLOCK = "	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT";
	private static final String BEGINNING_OF_CONSTRUCTOR_BLOCK = "	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT";
	private static final String DONTEDIT = "// DONTEDIT This file has been generated. \n// You should not edit this file, because your edits will be lost later. \n// There is a derived class without 'Gen' in its name that you may edit.";
	String packageName;
	String className;
	boolean entity;
	String query;
	ArrayList<AttributeDefiniton> attributes;

	String target;
	private String moduleName;
	private String baseClassName;

	public void createJavaSourceFiles(File srcDir, File genDir, String target) throws IOException {
		String fullSrcDir = srcDir.getCanonicalPath() + "/" + getFullPackage(target, false).replace(".", "/");
		String fullGenDir = genDir.getCanonicalPath() + "/" + getFullPackage(target, true).replace(".", "/");
		new File(fullSrcDir).mkdirs();
		new File(fullGenDir).mkdirs();

		this.target = target;
		baseClassName = className;
		className += "Gen";

		if (target.equals("android"))
			createJavaAndroidGen(new File(fullGenDir + "/" + className + ".java"));
		else if (target.equals("server"))
			createJavaServerGen(new File(fullGenDir + "/" + className + ".java"));
		else if (target.equals("client"))
			createJavaClientGen(new File(fullGenDir + "/" + className + ".java"));
		else if (target.equals("ios"))
			createJavaClientGen(new File(fullGenDir + "/" + className + ".java"));
		else
			createJavaSharedGen(new File(fullGenDir + "/" + className + ".java"));
		className = baseClassName;
		createJavaExtends(new File(fullSrcDir + "/" + className + ".java"));
	}

	private void generateType(PrintWriter pw) {
		pw.println("	public static final EntityTypeName type = EntityTypeName.getByString(\"" + baseClassName + "\");");
		pw.println();
	}

	public void createJavaExtends(File file) throws IOException {
		
		if (!file.exists())	{
			final FileOutputStream fos = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fos);
			pw.println("// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.\n// To regenerate this file, delete it and run Meva again.");
			pw.println();
			pw.println("package " + getFullPackage(target, false) + ";");
			pw.println();
			generateImports(pw);
			pw.println();
			pw.println("import " + getFullyQualifiedName(target, true) + ";");
	
			pw.println("@SuppressWarnings(\"unused\")");
			pw.println("public class " + className + " extends " + className + "Gen {");
			generateConstructors(pw, true, target.equals("shared"));
	
			pw.println("}");
			pw.close();
			fos.close();
		}
		else
		{
			File tmpFile = new File(file.getAbsolutePath() + ".tmp");
			if(!file.renameTo(tmpFile))
				throw new RuntimeException("Error moving to " + tmpFile);
			final FileOutputStream fos = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fos);
			BufferedReader br = new BufferedReader(new FileReader(tmpFile));
			String line = br.readLine();
			boolean foundConstructorBlock = false;
			while(line != null)
			{
				if(line.trim().equals(BEGINNING_OF_CONSTRUCTOR_BLOCK.trim()))
				{
					foundConstructorBlock = true;
					while(!line.equals(END_OF_CONSTRUCTOR_BLOCK))
					{
						line = br.readLine();
						if(line == null)
						{
							br.close();
							throw new RuntimeException("Did not find end of constructor block in " + tmpFile);
						}
					}
					generateConstructors(pw, true, target.equals("shared"));
				}
				else
					pw.println(line);
				line = br.readLine();
			}
			br.close();
			
			pw.close();
			fos.close();
			if(!foundConstructorBlock)
			{
			//	file.delete();
			//	System.err.println("Deleted file without constructor block: " + file);
				throw new RuntimeException("Did not find start of constructor block in " + tmpFile);
			}
			tmpFile.delete();
		}
	
	}

	public void createJavaAndroidGen(File file) throws IOException {
		final FileOutputStream fos = new FileOutputStream(file);
		PrintWriter pw = new PrintWriter(fos);
		pw.println(DONTEDIT);
		pw.println();
		pw.println("package " + getFullPackage(target, true) + ";");
		pw.println();
		generateImports(pw);
		pw.println();

		String modifier = isQuery() ? "abstract " : " ";
		pw.println("@SuppressWarnings(\"unused\")");
		pw.println("public " + modifier + "class " + className + " extends " + getFullPackage("shared", false) + "." + baseClassName
				+ (entity ? " implements EntityBinary" : " implements ValueBinary") + " {");

		generateConstructors(pw, true, true);
		if (entity)
			generateDeserialisers(pw);

		pw.println("	// Binary");
		generateCheckClassHash(pw);
		generateBinarySerializer(pw);

		pw.println("}");
		pw.close();
		fos.close();

	}

	public boolean isQuery() {
		return query != null && query.length() > 0;
	}

	public void createJavaServerGen(File file) throws IOException {
		final FileOutputStream fos = new FileOutputStream(file);
		PrintWriter pw = new PrintWriter(fos);
		pw.println(DONTEDIT);
		pw.println();
		pw.println("package " + getFullPackage(target, true) + ";");
		pw.println();
		generateImports(pw);
		pw.println();

		String modifier = isQuery() ? "abstract " : " ";
		pw.println("@SuppressWarnings(\"unused\")");
		pw.println("public " + modifier + "class " + className + " extends " + getFullPackage("shared", false) + "." + baseClassName
				+ (entity ? " implements EntityBinary, EntitySql" : " implements ValueBinary") + " {");

		generateConstructors(pw, true, true);

		if (entity) {
			generateDeserialisers(pw);

			pw.println("	// Sql");
			generateTableComment(pw);
			generateSqlSerializer(pw);
			pw.println();
		}

		pw.println("	// Binary");
		generateCheckClassHash(pw);
		generateBinarySerializer(pw);

		pw.println("}");
		pw.close();
		fos.close();

	}

	public void createJavaClientGen(File file) throws IOException {
		final FileOutputStream fos = new FileOutputStream(file);
		PrintWriter pw = new PrintWriter(fos);
		pw.println(DONTEDIT);
		pw.println();
		pw.println("package " + getFullPackage(target, true) + ";");
		pw.println();
		generateImports(pw);
		pw.println();

		String modifier = isQuery() ? "abstract " : " ";
		pw.println("@SuppressWarnings(\"unused\")");
		pw.println("public " + modifier + "class " + className + " extends " + getFullPackage("shared", false) + "." + baseClassName + " {");
		generateConstructors(pw, true, true);
		if (entity)
			generateDeserialisers(pw);

		pw.println("}");
		pw.close();
		fos.close();

	}

	public void createJavaSharedGen(File file) throws IOException {
		final FileOutputStream fos = new FileOutputStream(file);
		PrintWriter pw = new PrintWriter(fos);
		pw.println(DONTEDIT);
		pw.println();
		pw.println("package " + getFullPackage(target, true) + ";");
		pw.println();

		generateImports(pw);

		String modifier = isQuery() ? "abstract " : " ";

		//																		  (entity ? ("Entity<" + className + ">") : "Value")
		pw.println("@SuppressWarnings(\"unused\")");
		pw.println("public " + modifier + "class " + className + " implements " + (entity ? ("Entity") : "Value") + (isEntityQuery() ? ", EntityQuery" : "")
				+ (isValueQuery() ? ", ValueQuery" : "") + " {");

		if (entity)
			generateType(pw);

		pw.println("	// Attributes");
		generateAttributes(pw);
		pw.println();

		generateConstructors(pw, false, false);
		if (entity)
			generateDeserialisers(pw);

		if (entity) {
			pw.println("	// Factory- and entity-related");
			generateGetById(pw);
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
				if (!attribute.isRelation())
					generateSetter(attribute, pw);
				pw.println();
			}
		}

		if (isQuery()) {
			pw.println("	// Query specific");
			pw.println("	public String getUrlPostfix()");
			pw.println("	{");
			pw.println("		return \"" + baseClassName + "\";");
			pw.println("	}");
			pw.println();
			pw.println("	@Override");
			if (isValueQuery())
				pw.println("	public Collection<? extends Value> evaluate() {");
			else if (isEntityQuery())
				pw.println("	public Collection<Long> evaluate() {");
			pw.println("		throw new RuntimeException(\"No, this method does not exist here.\");");
			pw.println("	}");
		}

		pw.println("	// Stuff");
		generateToString(pw);
		if (entity) {
			generateGetTypeName(pw);
			generateCompareTo(pw);
			generateReassignRelation(pw);
		}

		generateClassHash(pw);

		pw.println("}");
		pw.close();
		fos.close();
	}

	public boolean isEntityQuery() {
		return query != null && query.length() > 0 && Meva.getClassDefinition(query, true).entity;
	}

	public boolean isValueQuery() {
		return query != null && query.length() > 0 && !Meva.getClassDefinition(query, true).entity;
	}

	private void generateClassHash(PrintWriter pw) {
		pw.println("	public static long getClassHash() {");
		pw.println("		return " + hashCode() + ";");
		pw.println("	}");
	}

	private void generateReassignRelation(PrintWriter pw) {
		pw.println("	@Override");
		pw.println("	public void reassignRelation(String relname, Entity e) {");
		for (AttributeDefiniton def : attributes)
			if (def.isEntity()) {
				pw.println("		if(relname.equals(\"" + def.attributeName + "\"))");
				pw.println("		{");
				pw.println("			this.set" + capitalizeFirst(def.attributeName) + "((" + def.typeName + ")e);");
				pw.println("			return;");
				pw.println("		}");
			}
		pw.println("		throw new RuntimeException(\"Unknown relname: \" + relname);");
		pw.println("	}");
	}

	private void generateCompareTo(PrintWriter pw) {
		pw.println("	@Override");
		pw.println("	public int compareTo(Object that) {");
		pw.println("		return new Long(this.id).compareTo(((Entity)that).getId());");
		pw.println("	}");
	}

	private void generateConstructors(PrintWriter pw, boolean superConstructor, boolean extendsShared) {
		pw.println(BEGINNING_OF_CONSTRUCTOR_BLOCK);

		if (entity)
			generateIdConstructor(pw, superConstructor);
		else {
			generateDefaultConstructor(pw);
			if (target.equals("server"))
				generateSqlDeserializerConstructor(pw, superConstructor, !extendsShared && superConstructor);
			if (target.equals("server") || target.equals("android"))
				generateBinaryDeserializerConstructor(pw, !extendsShared && superConstructor);
			generateJsonDeserializerConstructor(pw, superConstructor);
		}

		if(!attributes.isEmpty())
			generateAttributeConstructor(pw, superConstructor);
		pw.println(END_OF_CONSTRUCTOR_BLOCK);
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
		// if (target.equals("server") || target.equals("client")||
		// target.equals("android"))
		if (target.equals("shared"))
			generateJsonDeserializer(pw);
	}

	private void generateGetById(PrintWriter pw) {
		pw.println("	public static " + className + " getById(long id)");
		pw.println("	{");
		pw.println("		return (" + className + ")EntityFactory.getUnloadedEntityById(type, id);");
		pw.println("	}");
		pw.println();
	}

	private void generateTableComment(PrintWriter pw) {
		pw.println();
		pw.println("/*");
		pw.println("To generate a table, use the following SQL command:");
		pw.println();
		pw.println("CREATE TABLE IF NOT EXISTS `" + baseClassName + "` (");
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

			if (attribute.isRelation())
				continue;

			if (attributeName.equals("ready"))
				continue;

			if (typeName.equals("String"))
				pw.println("\t`" + prefix + attributeName + "` varchar(" + attribute.maxLen + "),");

			else if (attribute.isNative())
				pw.println("\t`" + prefix + attributeName + "` " + attribute.getSqlType() + " NOT NULL,");
			else {
				ClassDefinition classDef = Meva.getClassDefinition(typeName, true);
				if (classDef.entity)
					pw.println("\t`" + prefix + attributeName + "_id` BIGINT,");
				else
					Meva.getClassDefinition(typeName, true).generateTableCommentAttributeList(prefix + attributeName + "_", pw);
			}
		}
	}

	private void generateAttributes(PrintWriter pw) {
		for (AttributeDefiniton attribute : attributes) {
			if (attribute.isEntity()) {
				pw.println("	protected " + attribute.typeName + " " + attribute.attributeName + ";");
				pw.println("	protected long " + attribute.attributeName + "_id;");
			} else {
				String modifier = "protected";
				// we can't use final here, because some subclasses may have
				// constructors that perform complex read operations
				// to get the initial values. But we would need to set them all
				// in a call to the super constructor.
				// if (!entity)
				// modifier += " final";
				pw.println("	" + modifier + " " + attribute.getUsableType() + " " + attribute.attributeName + ";");
			}
		}
	}

	private void generateImports(PrintWriter pw) {
		pw.println("// gmino stuff");
		if (entity)
			pw.println("import de.gmino.meva.shared.Entity;");
		else
			pw.println("import de.gmino.meva.shared.Value;");

		if (isQuery()) {
			pw.println("import de.gmino.meva.shared.EntityQuery;");
			pw.println("import de.gmino.meva.shared.ValueQuery;");
		}

		pw.println("import de.gmino.meva.shared.EntityFactory;");
		pw.println("import de.gmino.meva.shared.RelationCollection;");
		pw.println("import de.gmino.meva.shared.EntityTypeName;");
		pw.println("import de.gmino.meva.shared.Util;");
		pw.println();

		pw.println("// default imports");
		pw.println("import java.io.DataInputStream;");
		pw.println("import java.io.DataOutputStream;");
		pw.println("import java.io.IOException;");
		pw.println("import java.io.PrintWriter;");
		pw.println("import java.io.StringWriter;");
		pw.println("import java.util.Collection;");

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
			pw.println("import java.sql.PreparedStatement;");
			pw.println("import java.sql.SQLException;");
			pw.println("import java.util.TreeSet;");
			pw.println();
			pw.println("// imports for serialization interfaces");
			pw.println("import de.gmino.meva.shared.EntityBinary;");
			pw.println("import de.gmino.meva.shared.ValueBinary;");
			if (target.equals("server"))
				pw.println("import de.gmino.meva.server.EntitySql;");
			pw.println();
		}

		TreeSet<String> importClasses = new TreeSet<String>();
		for (AttributeDefiniton attribute : attributes) {
			ClassDefinition classDef = null;
			if (attribute.isDomainType()) {
				classDef = Meva.getClassDefinition(attribute.typeName, true);
			} else if (attribute.isRelation()) {
				classDef = Meva.getClassDefinition(attribute.reltype, true);
			}
			if (classDef != null)
				importClasses.add(classDef.getFullyQualifiedName(target, false));
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

	private void generateCheckClassHash(PrintWriter pw) {
		pw.println("	public static DataInputStream checkClassHash(DataInputStream dis) {");
		pw.println("		try {");
		pw.println("			if(dis.readLong() != " + hashCode() + ")");
		pw.println("				throw new RuntimeException(\"Invalid class hash read.\");");
		pw.println("		} catch (IOException e) {");
		pw.println("			throw new RuntimeException(\"Error reading class hash.\", e);");
		pw.println("		}");
		pw.println("		return dis;");
		pw.println("	}");
		pw.println();
	}

	private void generateBinaryDeserializer(PrintWriter pw) {
		pw.println("	public void deserializeBinary(DataInputStream dis) throws IOException");
		pw.println("	{");
		pw.println("		long readClassHash = dis.readLong();");
		pw.println("		if(readClassHash != getClassHash())");
		pw.println("			throw new RuntimeException(\"Invalid class hash: expected \" + getClassHash() + \" but got \" + readClassHash);");
		for (AttributeDefiniton attribute : attributes) {
			String type = attribute.typeName;
			if (attribute.isNative())
				pw.println("		" + attribute.attributeName + " = dis.read" + capitalizeFirst(type) + "();");
			else if (type.equals("String")) {
				String varName = "has" + capitalizeFirst(attribute.attributeName);
				pw.println("		byte " + varName + " = dis.readByte();");
				pw.println("		if(" + varName + " != 0)");
				pw.println("			" + attribute.attributeName + " = dis.readUTF();");
			} else if (type.equals("relation")) {
				pw.println("		long " + attribute.attributeName + "Id = dis.readLong();");
				pw.println("		while(" + attribute.attributeName + "Id != 0)");
				pw.println("		{");
				pw.println("			" + attribute.attributeName + ".add((" + attribute.reltype + ")EntityFactory.getUnloadedEntityById(" + attribute.reltype + ".type, " + attribute.attributeName + "Id));");
				pw.println("			" + attribute.attributeName + "Id = dis.readLong();");
				pw.println("		}");
			} else {
				ClassDefinition typeDef = attribute.getClassDefinition();
				if (typeDef.entity) {
					pw.println("		" + attribute.attributeName + "_id = dis.readLong();");
				} else {
					if (entity) {
						String varName = "has" + capitalizeFirst(attribute.attributeName);
						pw.println("		byte " + varName + " = dis.readByte();");
						pw.println("		if(" + varName + " != 0)");
						pw.println("			" + attribute.attributeName + " = new " + attribute.typeName + "(dis);");
					} else {
						pw.println("		" + attribute.attributeName + " = new " + attribute.typeName + "(dis);");
					}
				}
			}
		}
		if (entity)
			pw.println("		ready = true;");
		pw.println("	}");
	}

	private void generateBinaryDeserializerConstructor(PrintWriter pw, boolean superConstructor) {
		pw.println("	public " + className + "(DataInputStream dis) throws IOException");
		pw.println("	{");

		if (superConstructor) {
			pw.println("		super(dis);");
			pw.println("	}");
			return;
		}

		boolean hasStringAttribute = false;

		pw.print("		this(");
		boolean first = true;
		for (AttributeDefiniton attribute : attributes) {
			String dis = first ? "checkClassHash(dis)" : "dis";

			pw.print(first ? "\n" : ",\n");
			String type = attribute.typeName;
			if (attribute.isNative())
				pw.print("			" + dis + ".read" + capitalizeFirst(type) + "()");
			else if (type.equals("String")) {
				pw.print("			readStringOrNull(" + dis + ")");
				hasStringAttribute = true;
			} else {
				if (attribute.isEntity()) {
					pw.print("			(" + type + ")EntityFactory.getUnloadedEntityById(" + type + ".type, dis.readLong())");
				} else {
					if (entity) {
						pw.print("			new " + attribute.typeName + "(" + dis + ")");
					} else {
						pw.print("			new " + attribute.typeName + "(" + dis + ")");
					}
				}
			}
			first = false;
		}
		pw.println(");\n	}");

		if (hasStringAttribute) {

			pw.println();
			pw.println("	public static String readStringOrNull(DataInputStream dis) throws IOException");
			pw.println("	{");
			pw.println("		byte hasString = dis.readByte();");
			pw.println("		if(hasString != 0)");
			pw.println("			return dis.readUTF();");
			pw.println("		else");
			pw.println("			return null;");
			pw.println("	}");

		}
	}

	private void generateBinarySerializer(PrintWriter pw) {
		pw.println("	public void serializeBinary(DataOutputStream dos) throws IOException");
		pw.println("	{");
		pw.println("		dos.writeLong(getClassHash());");
		for (AttributeDefiniton attribute : attributes) {
			String type = attribute.typeName;
			if (attribute.isNative())
				pw.println("		dos.write" + capitalizeFirst(type) + "(" + attribute.attributeName + ");");
			else if (type.equals("relation")) {

				pw.println("		for(Object " + attribute.attributeName + "Object : " + attribute.attributeName + ")");
				pw.println("			dos.writeLong(((Entity)" + attribute.attributeName + "Object).getId());");
				pw.println("		dos.writeLong(0);");

			} else if (type.equals("String")) {

				pw.println("		if(" + attribute.attributeName + " == null)");
				pw.println("			dos.writeByte(0);");
				pw.println("		else");
				pw.println("		{");
				pw.println("			dos.writeByte(1);");
				pw.println("			dos.writeUTF(" + attribute.attributeName + ");");
				pw.println("		}");
			} else {
				ClassDefinition typeDef = attribute.getClassDefinition();
				if (typeDef.entity) {
					pw.println("		if (" + attribute.attributeName + " == null)");
					pw.println("			dos.writeLong(0);");
					pw.println("		else");
					pw.println("			dos.writeLong(" + attribute.attributeName + ".getId());");
				} else {
					if (entity) {
						pw.println("		if (" + attribute.attributeName + " == null)");
						pw.println("			dos.writeByte(0);");
						pw.println("		else");
						pw.println("		{");
						pw.println("			dos.writeByte(1);");
						pw.println("			((" + typeDef.getFullyQualifiedName(target, false) + ")" + attribute.attributeName + ").serializeBinary(dos);");
						pw.println("		}");
					} else
						pw.println("		((" + attribute.typeName + ")" + attribute.attributeName + ").serializeBinary(dos);");
				}

			}
		}
		pw.println("	}");
	}

	// for entities only
	private void generateJsonDeserializer(PrintWriter pw) {
		pw.println("	public void deserializeJson(JsonObject json) throws IOException");
		pw.println("	{");
		pw.println("		JsonValue val;");

		for (AttributeDefiniton attribute : attributes) {
			String type = attribute.typeName;
			pw.println("		val = json.get(\"" + attribute.attributeName + "\");");
			if (attribute.isNative()) {
				pw.println("		" + attribute.attributeName + " = " + attribute.getWrapperType() + ".parse" + capitalizeFirst(type) + "(val.asString().stringValue());");
				/*
				 * if (type.equals("boolean")) pw.println("		" +
				 * attribute.attributeName +
				 * " = Boolean.parseBool(val.isBoolean());"); else
				 * pw.println("		" + attribute.attributeName +
				 * " = ("+type+")val.isNumber().doubleValue();");
				 */
			} else if (type.equals("String")) {
				pw.println("		if(!val.isNull())");
				pw.println("			" + attribute.attributeName + " = val.asString().stringValue();");
			} else if (type.equals("relation")) {
				pw.println("		for(JsonValue subVal : val.asArray())");
				pw.println("		{");
				pw.println("			long " + attribute.attributeName + "Id = Long.parseLong(subVal.asString().stringValue());");
				pw.println("			" + attribute.attributeName + ".add((" + attribute.reltype + ")EntityFactory.getUnloadedEntityById(" + attribute.reltype + ".type, " + attribute.attributeName + "Id));");
				pw.println("		}");
			} else {
				if (attribute.isEntity()) {
					pw.println("		" + attribute.attributeName + "_id = Long.parseLong(val.asString().stringValue());");
				} else {
					pw.println("		if(val.asObject() == null)");
					pw.println("			" + attribute.attributeName + " = null;");
					pw.println("		else");
					pw.println("			" + attribute.attributeName + " = new " + attribute.typeName + "(val.asObject());");

				}
			}
		}
		pw.println("		ready = true;");
		pw.println("	}");
		pw.println();
	}

	// only for values
	private void generateJsonDeserializerConstructor(PrintWriter pw, boolean superConstructor) {
		pw.println("	public " + className + "(JsonObject json) throws IOException");
		pw.println("	{");

		if (superConstructor) {
			pw.println("		super(json);");
			pw.println("	}");
			return;
		}

		pw.print("		this(");
		boolean first = true;
		for (AttributeDefiniton attribute : attributes) {
			pw.print(first ? "\n" : ",\n");
			String type = attribute.typeName;
			String val = "json.get(\"" + attribute.attributeName + "\")";
			if (attribute.isNative()) {
				pw.print("			" + attribute.getWrapperType() + ".parse" + capitalizeFirst(type) + "(" + val + ".asString().stringValue())");
			} else if (type.equals("String")) {
				pw.print("			" + val + ".isNull() ? null : " + val + ".asString().stringValue()");
			} else {
				if (attribute.isEntity()) {
					pw.print("			(" + type + ")EntityFactory.getUnloadedEntityById(" + type + ".type, Long.parseLong(" + val + ".asString().stringValue()))");
				} else {
					pw.print("			new " + attribute.typeName + "(" + val + ".asObject())");
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
			pw.println("		sb.append(moreIndentation + \"\\\"" + name + "\\\" : \");");
			if (attribute.isNative())
				pw.println("		sb.append(\"\\\"\" + " + name + " + \"\\\"\");");
			else if (attribute.typeName.equals("String"))
			{
				pw.println("		sb.append(\"\");");
				pw.println("		if(" + name + " != null)");
				pw.println("			sb.append('\"' + Util.escapeForJson(" + name + ") + '\"');");
				pw.println("		else");
				pw.println("			sb.append(\"null\");");
			}
			else if (type.equals("relation")) {
				pw.println("		sb.append(\"\\n\" + moreIndentation + \"[\");");
				String firstName = "first" + capitalizeFirst(attribute.attributeName);
				pw.println("		boolean " + firstName + " = true;");
				pw.println("		for(Object " + attribute.attributeName + "Object : " + attribute.attributeName + ")");
				pw.println("		{");
				pw.println("			sb.append(" + firstName + " ? \"\\n\" : \",\\n\");");
				pw.println("			sb.append(moreIndentation + \"\\t\\\"\" + ((Entity)" + attribute.attributeName + "Object).getId() + \"\\\"\");");
				pw.println("			" + firstName + " = false;");
				pw.println("		}");
				pw.println("		sb.append(moreIndentation + \"]\");");
			} else {
				if (attribute.isEntity())
					pw.println("		sb.append(\"\\\"\" + " + name + "_id  +\"\\\"\");");
				else {
					pw.println("		sb.append(\"\");");
					pw.println("		if(" + name + " != null)");
					pw.println("			" + name + ".serializeJson(sb, moreIndentation);");
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
			throw new RuntimeException("Can't serialize value classes into SQL yet.");

		pw.println("	public void deserializeSql(Connection dbCon) throws SQLException");
		pw.println("	{");
		pw.println("		Statement stat = dbCon.createStatement();");
		pw.print("		String selectString = \"SELECT  ");
		printAttributeList("", pw);
		pw.println(" FROM `" + baseClassName + "` WHERE id = '\"+id+\"';\";");
		pw.println("		stat.executeQuery(selectString);");
		pw.println("		System.out.println(selectString);");
		pw.println("		ResultSet rs = stat.executeQuery(selectString);");
		pw.println("		rs.next();");
		pw.println("		deserializeSql(rs, dbCon);");
		pw.println("	}");
		pw.println();		
		
		// TODO When converting this query to a prepared statement, try http://stackoverflow.com/questions/178479/preparedstatement-in-clause-alternatives#comment15231131_178479
		pw.println("	public void deserializeSql(Connection dbCon, Collection<Entity> toLoad) throws SQLException");
		pw.println("	{");
		pw.println("		System.out.println(\"Loading \" + toLoad.size() + \" entities.\");");
		pw.println("		long then = System.currentTimeMillis();");
		pw.println("		String idList = \"\";");
		pw.println("		Collection<Long> idListCopy = new TreeSet<Long>();");
		pw.println("		for(Entity e : toLoad)");
		pw.println("			{");
		pw.println("			if(idList.length() > 0)");
		pw.println("				idList += ',';");
		pw.println("			idList += e.getId();");
		pw.println("			idListCopy.add(e.getId());");
		pw.println("			}");
		pw.println("		Statement stat = dbCon.createStatement();");
		pw.print(  "		String selectString = \"SELECT  ");
		printAttributeList("", pw);
		pw.println(" FROM `" + baseClassName + "` WHERE id IN (\"+idList+\");\";");
		pw.println("		System.out.println(selectString);");
		pw.println("		ResultSet rs = stat.executeQuery(selectString);");
		pw.println("		int loadCount = 0;");
		pw.println("		while(rs.next())");
		pw.println("		{");
		pw.println("			long id = rs.getLong(\"id\");");
		pw.println("			((EntitySql) EntityFactory.getUnloadedEntityById(type, id)).deserializeSql(rs, dbCon);");
		pw.println("			loadCount++;");
		pw.println("			idListCopy.remove(id);");
		pw.println("		}");
		pw.println("		if(loadCount < toLoad.size())");
		pw.println("		{");
		pw.println("			for(Long id: idListCopy)");
		pw.println("				System.out.println(\"Missing entity: \" + id" + ");");
		pw.println("			throw new RuntimeException(\"Could not load all entities. Possible some id's are just not in the DB.\");");
		pw.println("		}");
		for (AttributeDefiniton def : attributes)
			if (def.isRelation())
			{
				String defName = def.attributeName;
				String single = baseClassName;
				String multiple = def.reltype;
				pw.println();
				pw.println("		// Read the related " + defName);
				pw.println("		String "+defName+"SelectString = \"SELECT "+single+".id as "+single+"_id, "+multiple+".id as "+multiple+"_id FROM `"+single+"`, `"+multiple+"` WHERE "+single+".id = "+multiple+"." + def.relname + "_id AND "+single+".id IN(\"+idList+\");\";");
				pw.println("		System.out.println("+defName+"SelectString);");
				pw.println("		ResultSet "+defName+"Rs = stat.executeQuery("+defName+"SelectString);");
				pw.println("		while("+defName+"Rs.next())");
				pw.println("		{");
				pw.println("			long "+uncapitalizeFirst(single)+"Id = "+defName+"Rs.getLong(1);");
				pw.println("			long "+uncapitalizeFirst(multiple)+"Id = "+defName+"Rs.getLong(2);");
				pw.println("			((" + baseClassName + "Gen) EntityFactory.getUnloadedEntityById(type, "+uncapitalizeFirst(single)+"Id)).get"+capitalizeFirst(defName)+"().add(EntityFactory.getUnloadedEntityById("+def.reltype+".type, "+uncapitalizeFirst(multiple)+"Id));");
				pw.println("		}");
			}
		pw.println("		long now = System.currentTimeMillis();");
		pw.println("		System.out.println(\"Finished loading \" + toLoad.size() + \" entities in \" + (now - then) + \" ms.\");");
		pw.println("	}");
		pw.println();
		pw.println("	public void deserializeSql(ResultSet rs, Connection dbCon) throws SQLException");
		pw.println("	{");
		pw.println("		String prefix = \"\";");
		
		// pw.println("		return new " + getFullyQualifiedName(target, false) +
		// "(\"\", rs);");
		printValueReadList("", pw);
		// printValueReadList("", "", pw);
		pw.println();
		/*boolean alreadyHasDbStuff = false;
		for (AttributeDefiniton def : attributes)
			if (def.isRelation())
			{
				if(!alreadyHasDbStuff)
				{
					pw.println("		String selectString = \"\";");
					pw.println("		Statement stat = dbCon.createStatement();");
					alreadyHasDbStuff = true;
				}
				generateSqlRelationDeserializer(pw, def);
			}
			*/
		if (entity)
			pw.println("		ready = true;");
		pw.println("	}");
	}

	private void generateSqlRelationDeserializer(PrintWriter pw, AttributeDefiniton def) {
		pw.println("		// Read the related " + def.attributeName);
		pw.println("		selectString = \"SELECT id FROM `" + Meva.getClassDefinition(def.reltype, true).baseClassName + "` WHERE " + def.relname + "_id = '\"+id+\"';\";");
		pw.println("		stat.executeQuery(selectString);");
		pw.println("		System.out.println(selectString);");
		pw.println("		rs = stat.executeQuery(selectString);");
		pw.println("		while(rs.next())");
		pw.println("			" + def.attributeName + ".add((" + def.reltype + ")EntityFactory.getUnloadedEntityById(" + def.reltype + ".type, rs.getLong(1)));");
	}

	/**
	 * For Values only
	 * 
	 * @param pw
	 * @param superConstructor
	 * @param superHasSqlDeserializerConstructor
	 */
	private void generateSqlDeserializerConstructor(PrintWriter pw, boolean superConstructor, boolean superHasSqlDeserializerConstructor) {
		pw.println("	// Constructor for SQL deseralizaiton");
		pw.println("	public " + className + "(String prefix, ResultSet rs) throws SQLException");
		pw.println("	{");
		if (superConstructor) {
			if (superHasSqlDeserializerConstructor)
				pw.println("		super(prefix, rs);");
			else {
				pw.print("		super(");
				printValueReadListArguments("", pw);
				pw.println("		);");
			}
		} else
			printValueReadList("", pw);
		pw.println("	}");
	}

	private void generateSqlSerializer(PrintWriter pw) {
		if (!entity)
			throw new RuntimeException("Can't serialize value classes into SQL yet.");

		pw.println("	private static PreparedStatement replaceStatement;");
		pw.println("	");
		pw.println("	public void serializeSql(Connection dbCon) throws SQLException");
		pw.println("	{");
		//pw.println("		if(replaceStatement == null)");
		//pw.println("		{");
		pw.print  ("			String replaceString = \"REPLACE INTO `" + baseClassName + "` (");
		printAttributeList("", pw);
		pw.print(") VALUES (");
		printValueList(pw);
		pw.println(");\";");
		pw.println("			replaceStatement = dbCon.prepareStatement(replaceString);");
		//pw.println("		}");
		printValueSettings("", pw,1);
		pw.println("		replaceStatement.executeUpdate();");
		pw.println("	}");
	}

	private void printAttributeList(String prefix, PrintWriter pw) {
		final int size = attributes.size();
		boolean first = true;
		for (int i = 0; i < size; i++) {
			AttributeDefiniton attribute = attributes.get(i);
			if (attribute.typeName.equals("relation") || attribute.attributeName.equals("ready"))
				continue;
			if (!first)
				pw.print(",");
			if (attribute.isNativeOrString())
				pw.print("`" + prefix + attribute.attributeName + "`");
			else {
				ClassDefinition classDef = attribute.getClassDefinition();
				if (classDef.entity)
					pw.print("`" + prefix + attribute.attributeName + "_id`");
				else
					classDef.printAttributeList(prefix + attribute.attributeName + "_", pw);
			}
			first = false;
		}
	}

	// placeholders
	private void printValueList( PrintWriter pw) {
		final int size = attributes.size();

		boolean first = true;
		for (int i = 0; i < size; i++) {
			AttributeDefiniton attribute = attributes.get(i);
			if (attribute.typeName.equals("relation") || attribute.attributeName.equals("ready"))
				continue;

			if (!first)
				pw.print(",");
			
			
			if (attribute.isValue())
				attribute.getClassDefinition().printValueList(pw);
			else
				pw.print("?");
			first = false;
		}
	}

	// actual values
	private void printOldValueList(String prefix, PrintWriter pw) {
		final int size = attributes.size();

		boolean first = true;
		for (int i = 0; i < size; i++) {
			AttributeDefiniton attribute = attributes.get(i);
			if (attribute.typeName.equals("relation") || attribute.attributeName.equals("ready"))
				continue;

			if (!first)
				pw.print(",");
			final String getter = ((attribute.typeName.equals("boolean")) ? "is" : "get") + capitalizeFirst(attribute.attributeName) + "()";
			if (attribute.typeName.equals("boolean"))
				pw.print("'\" + (" + prefix + getter + " ? 1 : 0) + \"'");
			else if (attribute.isNativeOrString())
				pw.print("'\" + " + prefix + getter + " + \"'");
			else {
				ClassDefinition classDef = attribute.getClassDefinition();
				if (classDef.entity) {
					pw.print("'\" + get" + capitalizeFirst(attribute.attributeName) + "Id() + \"'");
				} else
					classDef.printOldValueList(prefix + getter + ".", pw);
			}
			first = false;

		}
	}

	private int printValueSettings(String prefix, PrintWriter pw, int n) {
		final int size = attributes.size();

		
		for (int i = 0; i < size; i++) {
			AttributeDefiniton attribute = attributes.get(i);
			if (attribute.typeName.equals("relation") || attribute.attributeName.equals("ready"))
				continue;

			final String getter = ((attribute.typeName.equals("boolean")) ? "is" : "get") + capitalizeFirst(attribute.attributeName) + "()";
			if (attribute.typeName.equals("boolean"))
				pw.println("		replaceStatement.setInt(" + n + ", " + prefix + getter + " ? 1 : 0);");
			else if (attribute.isNativeOrString())
			{
				pw.println("		replaceStatement.set"+capitalizeFirst(attribute.typeName)+"(" + n + ", " + prefix + getter + ");");
			}
			else {
				ClassDefinition classDef = attribute.getClassDefinition();
				if (classDef.entity) {
					pw.println("		replaceStatement.setLong(" + n + ", get" + capitalizeFirst(attribute.attributeName) + "Id());");
				} else
					n = classDef.printValueSettings(prefix + getter + ".", pw, n) - 1;
			}
			
			n++;
		}
		return n;
	}

	private void printValueReadList(String prefixDash, PrintWriter pw) {
		final int size = attributes.size();

		for (int i = 0; i < size; i++) {
			AttributeDefiniton attribute = attributes.get(i);
			if (attribute.typeName.equals("relation") || attribute.attributeName.equals("ready"))
				continue;
			if (attribute.isNativeOrString())
				pw.println("		" + attribute.attributeName + " = rs.get" + capitalizeFirst(attribute.typeName) + "(prefix + \"" + prefixDash + attribute.attributeName + "\");");
			else {
				ClassDefinition classDef = attribute.getClassDefinition();
				if (classDef.entity)
					pw.println("		" + attribute.attributeName + "_id = rs.getLong(\"" + attribute.attributeName + "_id\");");
				else
					pw.println("		" + attribute.attributeName + " = new " + classDef.className + "(prefix + \"" + attribute.attributeName + "_" + "\", rs);");
			}
		}
	}

	private void printValueReadListArguments(String prefixDash, PrintWriter pw) {
		final int size = attributes.size();

		boolean first = true;
		for (int i = 0; i < size; i++) {
			pw.print(first ? "\n" : ",\n");
			AttributeDefiniton attribute = attributes.get(i);
			if (attribute.isRelation() || attribute.attributeName.equals("ready"))
				continue;
			if (attribute.isNativeOrString())
				pw.print("			rs.get" + capitalizeFirst(attribute.typeName) + "(prefix + \"" + prefixDash + attribute.attributeName + "\")");
			else {
				ClassDefinition classDef = attribute.getClassDefinition();
				if (classDef.entity)
					pw.print("			(" + classDef.baseClassName + ")EntityFactory.getUnloadedEntityById(" + attribute.typeName + ".type, rs.getLong(\"" + attribute.attributeName + "_id\"))");
				else
					pw.print("			new " + classDef.className + "(prefix + \"" + attribute.attributeName + "_" + "\", rs)");
			}

			first = false;
		}
	}

	private void generateDefaultConstructor(PrintWriter pw) {
		pw.println("	public " + className + "()");
		pw.println("	{");
		for (AttributeDefiniton attribute : attributes) {
			if (attribute.isRelation())
				continue;

			if (attribute.isValue())
				pw.println("		this." + attribute.attributeName + " = new " + attribute.typeName + "();");
		}
		pw.println("	}");
		pw.println();
	}
	
	private void generateAttributeConstructor(PrintWriter pw, boolean superConstructor) {
		pw.print("	public " + className + "(");
		final int size = attributes.size();
		boolean first = true;
		for (int i = 0; i < size; i++) {
			AttributeDefiniton attribute = attributes.get(i);
			if (attribute.isRelation())
				continue;

			pw.print(first ? "\n" : ",\n");
			pw.print("			" + attribute.typeName + " " + attribute.attributeName);
			first = false;
		}
		pw.println(")\n	{");

		if (superConstructor) {
			pw.print("\t\tsuper(");
			first = true;
			for (AttributeDefiniton attribute : attributes) {
				if (attribute.isRelation())
					continue;

				pw.print(first ? "" : ",");

				if (attribute.isNativeOrString())
					pw.print("\n			" + attribute.attributeName);
				else {
					ClassDefinition attributeTypeDef = attribute.getClassDefinition();
					pw.print("\n			(" + attributeTypeDef.getFullyQualifiedName(target, false) + ")" + attribute.attributeName);
				}
				first = false;
			}
			pw.println("\n\t\t);");
		} else
			for (AttributeDefiniton attribute : attributes) {
				if (attribute.isRelation())
					continue;

				if (attribute.attributeName.equals("id"))
					pw.println("		this(id);");
				else
				{
					pw.println("		this." + attribute.attributeName + " = " + attribute.attributeName + ";");
					if(attribute.isEntity())
						pw.println("		this." + attribute.attributeName + "_id = " + attribute.attributeName + ".getId();");
				}
			}
		if (entity)
			pw.println("		this.ready = true;");
		pw.println("	}");
		pw.println("	");
	}

	private void generateIdConstructor(PrintWriter pw, boolean superConstructor) {
		pw.println("	public " + className + "(long id)");
		pw.println("	{");
		if (superConstructor)
			pw.println("		super(id);");
		else {
			pw.println("		this.id = id;");
			for (AttributeDefiniton attribute : attributes) {
				if (attribute.isRelation())
					pw.println("		this." + attribute.attributeName + " = new RelationCollection(this, \"" + attribute.relname + "\");"); // <" + def.reltype + ">
				
				if (attribute.isValue())
					pw.println("		this." + attribute.attributeName + " = new " + attribute.typeName + "();");
			}
			// did not work
		}
		pw.println("	}");
		pw.println("	");
	}

	private void generateSetter(AttributeDefiniton attribute, PrintWriter pw) {
		pw.println("	public void set" + capitalizeFirst(attribute.attributeName) + "(" + attribute.typeName + " " + attribute.attributeName + ")");
		pw.println("	{");
		pw.println("		this." + attribute.attributeName + " = " + attribute.attributeName + ";");
		if (attribute.isEntity())
		{
			pw.println("		if(" + attribute.attributeName + " != null)");
			pw.println("			this." + attribute.attributeName + "_id = " + attribute.attributeName + ".getId();");
			pw.println("		else");
			pw.println("			this." + attribute.attributeName + "_id = 0;");
		}
		pw.println("	}");
		pw.println("	");
	}

	private void generateGetter(AttributeDefiniton attribute, PrintWriter pw) {
		if (attribute.isEntity()) {
			pw.println("	public " + attribute.typeName + " get" + capitalizeFirst(attribute.attributeName) + "()");
			pw.println("	{");

			pw.println("		if (" + attribute.attributeName + " == null)");
			pw.println("			" + attribute.attributeName + " = (" + attribute.typeName + ")EntityFactory.getUnloadedEntityById(" + attribute.typeName + ".type," + attribute.attributeName + "_id);");
			pw.println("		return " + attribute.attributeName + ";");
		} else {
			pw.println("	public " + attribute.getUsableType() + ((attribute.typeName.equals("boolean")) ? " is" : " get") + capitalizeFirst(attribute.attributeName) + "()");
			pw.println("	{");

			pw.println("		return " + attribute.attributeName + ";");
		}
		pw.println("	}");
		pw.println("	");

		if (attribute.isEntity()) {
			pw.println("	public long get" + capitalizeFirst(attribute.attributeName) + "Id()");
			pw.println("	{");
			pw.println("		return " + attribute.attributeName + "_id;");
			pw.println("	}");
			pw.println("	");
		}
	}

	private static String capitalizeFirst(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	private static String uncapitalizeFirst(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	public static ClassDefinition fromFile(File schemaFile, String moduleName) throws IOException {
		JSONObject json = (JSONObject) JSONValue.parse(new FileReader(schemaFile));
		ClassDefinition ret = new ClassDefinition();
		ret.packageName = (String) json.get("package");
		ret.className = (String) json.get("name");
		ret.baseClassName = (String) json.get("name");
		ret.entity = json.get("concept").equals("entity");
		if (json.containsKey("query"))
			ret.query = json.get("query").toString();
		else
			ret.query = "";

		ret.attributes = new ArrayList<AttributeDefiniton>();

		if (ret.entity) {
			ret.attributes.add(new AttributeDefiniton("long", "id"));
			ret.attributes.add(new AttributeDefiniton("boolean", "ready"));
		}

		JSONArray attributes = (JSONArray) json.get("attributes");
		for (Object o : attributes) {
			JSONObject att = (JSONObject) o;
			String typeName = (String) att.get("type");
			String attributeName = (String) att.get("name");
			AttributeDefiniton attributeDefiniton = new AttributeDefiniton(typeName, attributeName);
			ret.attributes.add(attributeDefiniton);
			if (typeName.equals("relation")) {
				attributeDefiniton.relname = (String) att.get("relname");
				attributeDefiniton.reltype = (String) att.get("reltype");
			}
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
		if (entity) {
			pw.println();
			pw.println("	@Override");
			pw.println("	public String toShortString() {");
			pw.println("		return \"(" + baseClassName + ":\" + id + ')';");
			pw.println("	}");
		}
	}

	private void generateGetTypeName(PrintWriter pw) {
		pw.println("	@Override");
		pw.println("	public String getTypeName() {");
		pw.println("		return \"" + baseClassName + "\";");
		pw.println("	}");
		pw.println();
		pw.println("	@Override");
		pw.println("	public EntityTypeName getType() {");
		pw.println("		return type;");
		pw.println("	}");
		pw.println();
	}

	public String getFullPackage(String target, boolean gen) {
		return packageName.replace("TARGET", target) + (gen ? ".gen" : "");
	}

	public String getFullyQualifiedName(String target, boolean gen) {
		// sometimes, "Gen" is added temporaty to the class name. This has its
		// use, but is useless when we need the FQN.
		String tmpClassName = className.replace("Gen", "");

		return getFullPackage(target, gen) + "." + tmpClassName + (gen ? "Gen" : "");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((baseClassName == null) ? 0 : baseClassName.hashCode());
		result = prime * result + query.hashCode();
		return result;
	}

}
