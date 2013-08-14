package de.gmino.codegen;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Meva {

	public static Map<String, ClassDefinition> definitions;
	private static Map<String, Meva> projects = new TreeMap<String, Meva>();

	private File projectRootDir;
	private ArrayList<File> callStack;
	private File moduleRootDir;
	private File modulesDir;
	private File moduleConf;
	private String platform;
	private String moduleName;
	private File srcDir;
	private File genDir;
	private File cpyDir;

	private File schemaDir;
	public static TreeMap<String, TreeMap<String, ClassDefinition>> definitionsPerMoudle;

	public static Meva getModule(String moduleName, String platform) {
		return projects.get(moduleName + "/" + platform);
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		definitions = new TreeMap<String, ClassDefinition>();
		definitionsPerMoudle = new TreeMap<String, TreeMap<String, ClassDefinition>>();
		for (String arg : args) {
			File rootDir = new File(arg).getCanonicalFile();
			new Meva(rootDir, new ArrayList<File>()).buildProject();
		}
		UmlDiagram.tryWriteDotFile(new File("meva.dot"));
	}

	public Meva(File projectRootDir, List<File> callStack) {
		this.projectRootDir = projectRootDir;
		this.callStack = new ArrayList<File>(callStack);

	}

	public static void addDefinitionToModule(String moduleName, ClassDefinition def) {
		TreeMap<String, ClassDefinition> defs = definitionsPerMoudle.get(moduleName);
		if (defs == null) {
			defs = new TreeMap<String, ClassDefinition>();
			definitionsPerMoudle.put(moduleName, defs);
		}
		defs.put(def.className, def);
	}

	public void buildProject() {
		try {
			JSONObject json;
			moduleRootDir = projectRootDir.getParentFile();
			modulesDir = moduleRootDir.getParentFile();
			moduleConf = new File(moduleRootDir.getCanonicalPath() + "/mevaconf.json");
			platform = projectRootDir.getName();

			// Lese Konfiguration für das ganze Modul
			json = (JSONObject) JSONValue.parse(new FileReader(moduleConf));
			moduleName = (String) json.get("name");

			// Füge die aktuelle Modul/Platform-Kombo zu den insgesamt bekannten
			// Projekten hinzu
			projects.put(moduleName + "/ " + platform, this);

			srcDir = new File(projectRootDir.getCanonicalPath() + "/src");
			if (platform.equals("meva"))
				schemaDir = srcDir;
			else
				schemaDir = new File(moduleRootDir + "/meva/src");

			genDir = new File(projectRootDir.getCanonicalPath() + (platform.equals("gwt") ? "/src" : (platform.equals("android") ? "/mevagen" : "/gen")));

			// Workaround for broken GWT dependency management, see
			// http://code.google.com/p/google-web-toolkit/issues/detail?id=3928
			// cpyDir = new File(projectRootDir.getCanonicalPath() +
			// (platform.equals("gwt") ? "/src" : "/cpy"));
			
			cpyDir = new File(projectRootDir.getCanonicalPath()+ "/cpy");
			
			if (!platform.equals("gwt")) { 
				emptyDir(genDir);
				emptyDir(cpyDir); 
			}
			

			TreeMap<String, ClassDefinition> definitionsToBuild = new TreeMap<String, ClassDefinition>();

			if (!platform.equals("meva"))
				buildIfExistsAndCopy(callStack, moduleRootDir.getCanonicalPath(), "meva");

			JSONArray depModules = (JSONArray) json.get("depends");
			if (depModules.size() > 0) {
				System.out.println("Building " + moduleName + ".module/" + platform + ", beginning with its dependencies:");

				buildProjectDependenciesAndGetDefinitions(callStack, depModules, moduleRootDir, modulesDir, platform, moduleName);
			} else
				System.out.println("Building " + moduleName + ".module/" + platform + ", which has no dependencies.");

			collectDefinitions(schemaDir, definitionsToBuild);
			definitions.putAll(definitionsToBuild);

			for (ClassDefinition def : definitionsToBuild.values()) {

				System.out.print("Creating source for " + def.className);
				if (platform.equals("gwt")) {
					def.createJavaSourceFiles(srcDir, genDir, "server");
					def.createJavaSourceFiles(srcDir, genDir, "client");
				} else if (platform.equals("meva"))
					def.createJavaSourceFiles(srcDir, genDir, "shared");
				else
					def.createJavaSourceFiles(srcDir, genDir, platform);
				System.out.println(" (Hash: " + def.hashCode() + ")");
			}

			System.out.println("Finished Building: " + moduleName + ".module/" + platform);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void emptyDir(File dir) {
		if (!dir.exists())
			return;
		if (dir.isDirectory())
			for (File c : dir.listFiles())
				delete(c);
		else
			System.err.println(dir + " is not a dir.");
	}

	void delete(File f) {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				delete(c);
		}
		f.delete();
	}

	private void collectDefinitions(File dir, TreeMap<String, ClassDefinition> collection) {
		if (!dir.exists())
			return;
		for (File file : dir.listFiles()) {
			if (file.getName().endsWith(".schema")) {
				try {
					System.out.println("Reading schema " + file);
					ClassDefinition def = ClassDefinition.fromFile(file, moduleName);
					addDefinitionToModule(moduleName, def);
					collection.put(file.getName().replace(".schema", ""), def);
				} catch (Exception e) {
					throw new RuntimeException("Error while reading schema.", e);
				}
			}
			if (file.isDirectory() && !file.getName().startsWith("."))
				collectDefinitions(file, collection);
		}
	}

	private void buildProjectDependenciesAndGetDefinitions(List<File> callStack, JSONArray modules, File moduleRootDir, File modulesDir, String platform, String moduleName) throws IOException {
		for (Object module : modules) {
			for (File callStackElement : callStack)
				if (callStackElement.equals(moduleRootDir)) {
					System.err.println("Circular Dependency for " + moduleRootDir + ":");
					for (File callStackElement2 : callStack)
						System.err.println(callStackElement2);
					System.err.println(moduleRootDir);
					throw new RuntimeException("Circular dependency, see above.");
				}

			List<File> newCallStack = new ArrayList<File>(callStack);
			newCallStack.add(moduleRootDir);

			System.out.println(moduleName + " depends on " + module);

			String depModuleDirPath = modulesDir.getCanonicalPath() + "/" + module.toString() + ".module";

			if (!new File(depModuleDirPath).exists()) {
				throw new RuntimeException("Module not found: " + depModuleDirPath);
			}

			if (!platform.equals("meva"))
				buildIfExistsAndCopy(newCallStack, depModuleDirPath, "meva");
			buildIfExistsAndCopy(newCallStack, depModuleDirPath, platform);
		}

	}

	private void buildIfExistsAndCopy(List<File> newCallStack, String depModuleDirPath, String platformToBuild) {
		File depMevaProjectPath = new File(depModuleDirPath + "/" + platformToBuild);
		System.out.println("Checking " + depMevaProjectPath);
		if (depMevaProjectPath.exists()) {
			Meva depProject = new Meva(depMevaProjectPath, newCallStack);
			depProject.buildProject();
			definitions.putAll(depProject.definitions);
			
			try {
				copyFiles(depProject.srcDir, cpyDir);
				copyFiles(depProject.genDir, cpyDir);
				copyFiles(depProject.cpyDir, cpyDir);
			} catch (IOException e) {
				throw new RuntimeException("Error copying files.", e);
			}

		}
	}

	private void copyFiles(File source, File dest) throws IOException {
		if (!source.exists())
			return;
		for (File inSource : source.listFiles()) {
			if (inSource.getName().endsWith(".java") || inSource.getName().endsWith(".gwt.xml")) {
				dest.mkdirs();
				copyFile(inSource, new File(dest.getAbsolutePath() + "/" + inSource.getName()));
			} else if (inSource.getName().equals(".gitignore") || inSource.getName().endsWith(".schema")) {
				// "Nothing!" - "Shut up!"
			} else if (!inSource.isDirectory())
				System.err.println("File with strange extension found: " + inSource.getAbsolutePath());
			if (inSource.isDirectory() && !inSource.getName().startsWith("."))
				copyFiles(inSource, new File(dest.getAbsolutePath() + "/" + inSource.getName()));
		}
	}

	public static void copyFile(File sourceFile, File destFile) throws IOException {
		if (!destFile.exists()) {
			destFile.createNewFile();
		}

		BufferedReader input = null;
		BufferedWriter output = null;
		
		try {
			input = new BufferedReader(new FileReader(sourceFile));
			output = new BufferedWriter(new FileWriter(destFile));
			
			if (destFile.getName().endsWith(".xml")) {
				String line = input.readLine();
				while(line != null)
				{
					output.write(line);
					output.write("\n");
					line = input.readLine();
				}
			}
			else
			{
				String warning = "\n\n// DONTEDIT This file has been copied from " + sourceFile.getPath()
						+ ".\n\n// This warning may apply even when the original file contained a message that explicitly allows editing.\n\n";
				output.write(warning);
				output.flush();
				String line = input.readLine();
				boolean disableComments = false;
				while(line != null)
				{
					if(line.contains("/*"))
						disableComments = true;
					
					if(!disableComments && !line.startsWith("/* copy */") && !line.trim().startsWith("*"))
						output.write("/* copy */\t");
					output.write(line);
					output.write("\n");
	
					if(line.contains("*/"))
						disableComments = false;
					
					line = input.readLine();
				}
			}		
		} finally {
			if (input != null) {
				input.close();
			}
			if (output != null) {
				output.close();
			}
		}
	}

	public static ClassDefinition getClassDefinition(String type, boolean throwIfNotFound) {
		ClassDefinition typeDef = Meva.definitions.get(type);
		if (throwIfNotFound && typeDef == null)
			throw new RuntimeException("Type " + type + " is unknown.");
		return typeDef;
	}
}
