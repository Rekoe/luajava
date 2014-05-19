/*
 * $Id: LuaJavaAPI.java,v 1.5 2007-04-17 23:47:50 thiago Exp $
 * Copyright (C) 2003-2007 Kepler Project.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.keplerproject.luajava;


/**
 * Class that contains functions accessed by lua.
 * 
 * @author Thiago Ponte
 */
public final class LuaJavaAPI {
	
	public static ThreadLocal<LuaJavaSandbox> sandbox = new ThreadLocal<LuaJavaSandbox>();

	private LuaJavaAPI() {
	}

	/**
	 * Java implementation of the metamethod __index for normal objects
	 * 
	 * @param luaState
	 *            int that indicates the state used
	 * @param obj
	 *            Object to be indexed
	 * @param methodName
	 *            the name of the method
	 * @return number of returned objects
	 */
	public static int objectIndex(int luaState, Object obj, String methodName)
			throws LuaException {
		return DefaultLuaJavaAPI.objectIndex(luaState, obj, methodName);
	}

	/**
	 * Java function that implements the __index for Java arrays
	 * 
	 * @param luaState
	 *            int that indicates the state used
	 * @param obj
	 *            Object to be indexed
	 * @param index
	 *            index number of array. Since Lua index starts from 1, the
	 *            number used will be (index - 1)
	 * @return number of returned objects
	 */
	public static int arrayIndex(int luaState, Object obj, int index)
			throws LuaException {
		return DefaultLuaJavaAPI.arrayIndex(luaState, obj, index);
	}

	/**
	 * Java function to be called when a java Class metamethod __index is
	 * called. This function returns 1 if there is a field with searchName and 2
	 * if there is a method if the searchName
	 * 
	 * @param luaState
	 *            int that represents the state to be used
	 * @param clazz
	 *            class to be indexed
	 * @param searchName
	 *            name of the field or method to be accessed
	 * @return number of returned objects
	 * @throws LuaException
	 */
	public static int classIndex(int luaState, Class clazz, String searchName)
			throws LuaException {
		return DefaultLuaJavaAPI.classIndex(luaState, clazz, searchName);
	}

	/**
	 * Java function to be called when a java object metamethod __newindex is
	 * called.
	 * 
	 * @param luaState
	 *            int that represents the state to be used
	 * @param object
	 *            to be used
	 * @param fieldName
	 *            name of the field to be set
	 * @return number of returned objects
	 * @throws LuaException
	 */
	public static int objectNewIndex(int luaState, Object obj, String fieldName)
			throws LuaException {
		return DefaultLuaJavaAPI.objectNewIndex(luaState, obj, fieldName);
	}

	/**
	 * Java function to be called when a java array metamethod __newindex is
	 * called.
	 * 
	 * @param luaState
	 *            int that represents the state to be used
	 * @param object
	 *            to be used
	 * @param index
	 *            index number of array. Since Lua index starts from 1, the
	 *            number used will be (index - 1)
	 * @return number of returned objects
	 * @throws LuaException
	 */
	public static int arrayNewIndex(int luaState, Object obj, int index)
			throws LuaException {
		return DefaultLuaJavaAPI.arrayNewIndex(luaState, obj, index);
	}

	/**
	 * Pushes a new instance of a java Object of the type className
	 * 
	 * @param luaState
	 *            int that represents the state to be used
	 * @param className
	 *            name of the class
	 * @return number of returned objects
	 * @throws LuaException
	 */
	public static int javaNewInstance(int luaState, String className)
			throws LuaException {
		LuaJavaSandbox box = sandbox.get();
		if (box != null)
			box.checkClassName(className);
		return DefaultLuaJavaAPI.javaNewInstance(luaState, className);
	}

	/**
	 * javaNew returns a new instance of a given clazz
	 * 
	 * @param luaState
	 *            int that represents the state to be used
	 * @param clazz
	 *            class to be instanciated
	 * @return number of returned objects
	 * @throws LuaException
	 */
	public static int javaNew(int luaState, Class clazz) throws LuaException {
		return DefaultLuaJavaAPI.javaNew(luaState, clazz);
	}

	/**
	 * Calls the static method <code>methodName</code> in class
	 * <code>className</code> that receives a LuaState as first parameter.
	 * 
	 * @param luaState
	 *            int that represents the state to be used
	 * @param className
	 *            name of the class that has the open library method
	 * @param methodName
	 *            method to open library
	 * @return number of returned objects
	 * @throws LuaException
	 */
	public static int javaLoadLib(int luaState, String className,
			String methodName) throws LuaException {
		LuaJavaSandbox box = sandbox.get();
		if (box != null)
			box.checkClassMethod(className, methodName);
		return DefaultLuaJavaAPI.javaLoadLib(luaState, className, methodName);
	}

	/**
	 * Checks if there is a field on the obj with the given name
	 * 
	 * @param luaState
	 *            int that represents the state to be used
	 * @param obj
	 *            object to be inspected
	 * @param fieldName
	 *            name of the field to be inpected
	 * @return number of returned objects
	 */
	public static int checkField(int luaState, Object obj, String fieldName)
			throws LuaException {
		return DefaultLuaJavaAPI.checkField(luaState, obj, fieldName);
	}

	/**
	 * Function that creates an object proxy and pushes it into the stack
	 * 
	 * @param luaState
	 *            int that represents the state to be used
	 * @param implem
	 *            interfaces implemented separated by comma (<code>,</code>)
	 * @return number of returned objects
	 * @throws LuaException
	 */
	public static int createProxyObject(int luaState, String implem)
			throws LuaException {
		LuaJavaSandbox box = sandbox.get();
		if (box != null) {
			for (String className:implem.split(","))
				box.checkClassName(className);
		}
		return DefaultLuaJavaAPI.createProxyObject(luaState, implem);
	}
}
