package de.doridian.steammobile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	public static <T> List<Class<? extends T>> getSubClasses(Class<T> baseClass) {
		final List<Class<? extends T>> ret = new ArrayList<Class<? extends T>>();
		final File file;
		try {
			final ProtectionDomain protectionDomain = baseClass.getProtectionDomain();
			final CodeSource codeSource = protectionDomain.getCodeSource();
			final URL location = codeSource.getLocation();
			final URI uri = location.toURI();
			file = new File(uri);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return ret;
		}
		final String[] fileList;

		String packageName = baseClass.getPackage().getName();
		if (file.isDirectory() || (file.isFile() && !file.getName().endsWith(".jar"))) {
			String packageFolderName = "/" + packageName.replace('.', '/');

			URL url = baseClass.getResource(packageFolderName);
			if (url == null)
				return ret;

			File directory = new File(url.getFile());
			if (!directory.exists())
				return ret;

			// Get the list of the files contained in the package
			fileList = directory.list();
		} else if (file.isFile()) {
			final List<String> tmp = new ArrayList<String>();
			final JarFile jarFile;
			try {
				jarFile = new JarFile(file);
			} catch (IOException e) {
				e.printStackTrace();
				return ret;
			}

			Pattern pathPattern = Pattern.compile(packageName.replace('.', '/') + "/(.+\\.class)");
			final Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				Matcher matcher = pathPattern.matcher(entries.nextElement().getName());
				if (!matcher.matches())
					continue;

				tmp.add(matcher.group(1));
			}

			fileList = tmp.toArray(new String[tmp.size()]);
		} else {
			return ret;
		}

		Pattern classFilePattern = Pattern.compile("(.+)\\.class");
		for (String fileName : fileList) {
			// we are only interested in .class files
			Matcher matcher = classFilePattern.matcher(fileName);
			if (!matcher.matches())
				continue;

			// removes the .class extension
			String classname = matcher.group(1);
			try {
				final Class<?> classObject = Class.forName(packageName + "." + classname.replace('/', '.'));
				final Class<? extends T> classT = classObject.asSubclass(baseClass);

				// Try to create an instance of the object
				ret.add(classT);
			} catch (ClassNotFoundException e) {
				System.err.println(e);
			} catch (ClassCastException e) {
				continue;
			}
		}

		return ret;
	}
}
