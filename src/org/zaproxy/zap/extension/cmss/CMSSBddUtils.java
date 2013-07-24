/**
 * 
 */
package org.zaproxy.zap.extension.cmss;

import org.python.util.PythonInterpreter;

/**
 * @author abdelhadi
 *
 */
public class CMSSBddUtils {
      /* BlindElephant DB */
	
	/**
	 * calls a python script that convert BE db pkl files into xml files 
	 * @todo implemant a general pkl2xml convertor using python xml serializators
	 */
	public static void pkl2xml (){
		PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.execfile("test.py"); 
	}
}
