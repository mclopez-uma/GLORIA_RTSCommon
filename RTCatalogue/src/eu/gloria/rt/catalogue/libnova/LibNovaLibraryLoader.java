package eu.gloria.rt.catalogue.libnova;

/**
 * Class to load Nova Library.
 * 
 * @author jcabello
 *
 */
public class LibNovaLibraryLoader {
	
	private static boolean loaded;
	
	static{
		loaded = false;
	}
	
	public static void loadLibrary(){
		try{
			if (loaded) {
				System.out.println("NovaJNI is already loaded.");
			}else{
				System.out.println("java.library.path=" + System.getProperty("java.library.path"));
				System.loadLibrary("NovaJNI"); //SIN "lib" ni ".so"
				System.out.println("NovaJNI loaded.");
				loaded = true;
			}
		}catch(Throwable ex){
			ex.printStackTrace();
		}
	}

}
