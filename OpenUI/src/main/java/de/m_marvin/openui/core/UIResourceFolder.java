package de.m_marvin.openui.core;

import java.io.IOException;
import java.io.InputStream;

import de.m_marvin.archiveutility.ArchiveAccess;
import de.m_marvin.archiveutility.IArchiveAccess;
import de.m_marvin.archiveutility.access.MultiArchiveAccess;
import de.m_marvin.gframe.resources.ISourceFolder;
import de.m_marvin.gframe.resources.ResourceLoader;
import de.m_marvin.simplelogging.printing.Logger;

public class UIResourceFolder implements ISourceFolder {

	public static final UIResourceFolder SHADERS = new UIResourceFolder("shaders");
	public static final UIResourceFolder TEXTURES = new UIResourceFolder("textures");

	public static final String ASSETS_PACKAGE = "/openui/assets/";
	
	public String getFolderName() {
		return folderName;
	}
	
	private static IArchiveAccess archiveAccess;
	
	static {
		try {
			archiveAccess = ArchiveAccess.getClasspathAccess();
		} catch (IOException e) {
			Logger.defaultLogger().logError("failed to get jar access!");
			Logger.defaultLogger().printExceptionError(e);
		}
	}
	
	public static void setArchiveAccess(IArchiveAccess archive) {
		archiveAccess = archive;
	}
	
	public static void addArchiveAccess(IArchiveAccess archive) {
		archiveAccess = new MultiArchiveAccess(archiveAccess, archive);
	}
	
	public static IArchiveAccess getArchiveAccess() {
		return archiveAccess;
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
		return archiveAccess.open(path);
	}

	@Override
	public String[] listFiles(String path) {
		return archiveAccess.listFiles(path);
	}

	@Override
	public String[] listFolders(String path) {
		return archiveAccess.listFolders(path);
	}

	@Override
	public String[] listNamespaces() {
		return archiveAccess.listFolders(ASSETS_PACKAGE);
	}
	
}
