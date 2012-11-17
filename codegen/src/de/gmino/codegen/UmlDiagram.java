package de.gmino.codegen;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map.Entry;
import java.util.TreeMap;

public class UmlDiagram {
	public static void tryWriteDotFile(File fileToWrite) {
		try {
			writeDotFile(fileToWrite);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeDotFile(File fileToWrite) throws Exception {
		PrintWriter pw = new PrintWriter(fileToWrite);
		pw.println("digraph G {");
		pw.println("fontname = \"Bitstream Vera Sans\"");
		pw.println("fontsize = 8");
		pw.println();
		pw.println("node [");
		pw.println("        fontname = \"Bitstream Vera Sans\"");
		pw.println("        fontsize = 8");
		pw.println("        shape = \"record\"");
		pw.println("]");
		pw.println();
		pw.println("edge [");
		pw.println("        fontname = \"Bitstream Vera Sans\"");
		pw.println("        fontsize = 8");
		pw.println("		arrowhead = \"none\"");
		pw.println("]");

		pw.println("subgraph clustermeva {  label = \"Module: meva\"");
		pw.println("		Entity [");
		pw.println("			label = \"{Entity|+ id : long\\l|+ serializeJson(StringBuilder sb) : void\\l+ serializeJson(StringBuilder sb, String indentation) : void\\l+ deserializeJson(JsonObject json) : void\\l+ getId() : long\\l+ setId(long id) : void\\l+ toString() : String\\l+ getTypeName() : String\\l+ isReady() : boolean\\l+ reassignRelation(String relname, Entity e) : void\\l+ toShortString() : String\\l+ getType() : EntityTypeName\\l}\"");
		pw.println("		]");
		pw.println("");
		pw.println("		Value [");
		pw.println("			label = \"{Value|+ serializeJson(StringBuilder sb) : void\\l+ serializeJson(StringBuilder sb, String indentation) : void\\l+ toString() : String\\l}\"");
		pw.println("		]");
		pw.println("		Query [");
		pw.println("			label = \"{Query|+ evaluate() : Collection(Long)\\l+ getUrlPostfix() : String\\l}\"");
		pw.println("		]");
		pw.println("		}");

		for (Entry<String, TreeMap<String, ClassDefinition>> entry : Meva.definitionsPerMoudle.entrySet()) {
			String moduleName = entry.getKey();
			pw.println("subgraph cluster" + moduleName + " {  label = \"Module: " + moduleName + "\"");
			for (ClassDefinition def : entry.getValue().values())
				printClass(pw, def);
			pw.println("}");
		}
		for (ClassDefinition def : Meva.definitions.values())
			printClassRelations(pw, def);

		pw.println("Query -> Value" + " [arrowhead=\"empty\"]");

		pw.println("}");

		pw.close();
	}

	private static void printClass(PrintWriter pw, ClassDefinition def) {
		pw.println(def.className + " [");
		pw.print("	label = \"{" + def.className + " (" + getType(def) + ")|");
		for (AttributeDefiniton att : def.attributes) {
			if ((att.isNativeOrString() || att.isValue()) && !att.attributeName.equals("id") && !att.attributeName.equals("ready"))
				pw.print("+ " + att.attributeName + " : " + att.typeName + "\\l");
		}
		// pw.println("|+ serializeJson(PrintWriter pw) : void\\l}\"");
		pw.println("}\"");
		pw.println("]");
		pw.println();
	}

	private static void printClassRelations(PrintWriter pw, ClassDefinition def) {
		/*
		 * if(def.entity) pw.println(def.className + " -> Entity" +
		 * " [style=dashed,color=gray63, arrowhead=\"empty\"]"); else
		 * pw.println(def.className + " -> Value" +
		 * " [style=dashed,color=gray63, arrowhead=\"empty\"]"); if(def.query)
		 * pw.println(def.className + " -> Query" +
		 * " [style=dashed,color=gray63, arrowhead=\"empty\"]");
		 */

		for (AttributeDefiniton att : def.attributes) {
			if (att.isRelation()) {
				pw.println(def.className + " -> " + att.reltype + " [headlabel=\"0..* " + att.attributeName + "\",taillabel=\"1 " + att.relname + "\"]");
			}
			if (att.isEntity()) {
				ClassDefinition other = Meva.getClassDefinition(att.typeName, false);
				boolean reverse = false;
				for (AttributeDefiniton otherAtt : other.attributes) {
					if (otherAtt.isRelation() && otherAtt.reltype.equals(def.className) && otherAtt.relname.equals(att.attributeName)) {
						reverse = true;
						continue;
					}
				}
				if (!reverse)
					pw.println(def.className + " -> " + att.typeName + " [headlabel=\"0..1\", arrowhead=\"open\"]");
			}
			if (att.isValue()) {
				pw.println(def.className + " -> " + att.typeName + " [arrowhead=\"open\", style=dashed,color=gray63]");
			}
		}

	}

	public static String getType(ClassDefinition def) {
		if (def.isEntityQuery())
			return "EntityQuery/Value";
		if (def.isValueQuery())
			return "ValueQuery/Value";
		if (def.entity)
			return "Entity";
		else
			return "Value";
	}
}
