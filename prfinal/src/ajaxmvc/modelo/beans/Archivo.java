/**
 * 
 */
package ajaxmvc.modelo.beans;

import java.io.File;
import java.net.URI;

import sun.security.krb5.internal.crypto.crc32;

/**
 * @author negrero
 *
 */
public class Archivo extends File implements InterpretaArchivo{
	
	

	/** 
	 * @author negrero
	 */
	public interface InterpretaArchivo {

	}

	private crc32 checksum=null;
	
	public Archivo(URI uri) {
		super(uri);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the checksum
	 */
	public crc32 getChecksum() {
		return checksum;
	}

	/**
	 * @param checksum the checksum to set
	 */
	public void setChecksum(crc32 checksum) {
		this.checksum=new crc32();
		
		this.checksum = checksum;
	}

	@Override
	public Object getInterpreta() {
		// TODO Auto-generated method stub
		return null;
	}

}
