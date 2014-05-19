package org.keplerproject.luajava;

import java.util.List;

public class LuaJavaSandbox {
	
	protected List<String> classNameWhiteList;
	protected List<String> classNameBlackList;
	
	public LuaJavaSandbox(List<String> classNameWhiteList,
			List<String> classNameBlackList) {
		this.classNameWhiteList = classNameWhiteList;
		this.classNameBlackList = classNameBlackList;
	}

	public void checkClassName(String className) {
		boolean flag = true;
		if (classNameWhiteList != null) {
			for (String klass : classNameWhiteList) {
				if (className.startsWith(klass)) {
					flag = false;
					break;
				}
			}
		}
		if (flag) {
			throw new SecurityException("luajava forbidden > " + className);
		}
		if (classNameBlackList != null) {
			for (String klass : classNameBlackList) {
				if (className.startsWith(klass)) {
					throw new SecurityException("luajava forbidden > " + className);
				}
			}
		}
	}
	
	public void checkClassMethod(String className, String methodName) {
		checkClassName(className);
	}
}
