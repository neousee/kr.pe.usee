package kr.pe.usee.io;

import java.io.File;
import java.util.ArrayList;

public class DirReader {
	
	public static File[] getFileList(String string) {
		File dir = new File(string);
		String[] list = dir.list();
		File t;
		ArrayList<File> al = new ArrayList<File>();
		for(String fName:list) {
			t = new File(dir,fName);
			if(t.isFile()) al.add(t);
		}
		File[] rt = new File[al.size()];
		al.toArray(rt);
		return rt;
	}
}
