package de.m_marvin.openui.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.m_marvin.archiveutility.ArchiveUtility;
import de.m_marvin.renderengine.resources.ISourceFolder;
import de.m_marvin.renderengine.resources.ResourceLoader;

public class UIResourceFolder implements ISourceFolder {

	public static final UIResourceFolder SHADERS = new UIResourceFolder("shaders");
	public static final UIResourceFolder TEXTURES = new UIResourceFolder("textures");

	public static final String ASSETS_PACKAGE = "/openui/assets/";
	
	public String getFolderName() {
		return folderName;
	}
	
	public static List<ArchiveUtility> archives = new ArrayList<>();
	
	static {
		archives.add(new ArchiveUtility(UIResourceFolder.class));
	}
	
	public static void addArchive(ArchiveUtility archive) {
		archives.add(archive);
	}

	private final String folderName;
	
	public UIResourceFolder(String folderName) {
		this.folderName = folderName;
	}
	
	@Override
	public String getPath(ResourceLoader<?, ?> loader, String namespace) {
		return ASSETS_PACKAGE + namespace + "/" + this.folderName;
	}

	@Override
	public InputStream getAsStream(String path) throws IOException {
		for (ArchiveUtility archive : archives) {
			InputStream is = archive.openFile(path);
			if (is == null) continue;
			return is;
		}
		throw new FileNotFoundException("Could not find resource " + path + "!");
	}

	@Override
	public String[] listFiles(String path) {
		return archives.stream().map(a -> a.listFiles(path)).reduce(this::concatResults).get();
	}

	@Override
	public String[] listFolders(String path) {
		return archives.stream().map(a -> a.listFolders(path)).reduce(this::concatResults).get();
	}

	@Override
	public String[] listNamespaces() {
		return archives.stream().map(a -> a.listFolders(ASSETS_PACKAGE)).reduce(this::concatResults).get();
	}
	
	private String[] concatResults(String[] a, String[] b) {
		String[] results = new String[a.length + b.length];
		for (int i = 0; i < results.length; i++) results[i] = i < a.length ? a[i] : b[i - a.length];
		return results;
	}
	
}
