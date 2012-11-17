package de.gmino;

import org.apache.tools.ant.taskdefs.Javac;
import org.eclipse.jdt.core.JDTCompilerAdapter;

public class JDTCompiler15 extends JDTCompilerAdapter {
	@Override
	public void setJavac(Javac attributes) {
		System.out.println(attributes);
		if (attributes.getTarget() == null) {
			attributes.setTarget("1.6"); // make target level to 1.6
		}
		if (attributes.getSource() == null) {
			attributes.setSource("1.6"); // make target level to 1.6
		}
		super.setJavac(attributes);
	}
}
