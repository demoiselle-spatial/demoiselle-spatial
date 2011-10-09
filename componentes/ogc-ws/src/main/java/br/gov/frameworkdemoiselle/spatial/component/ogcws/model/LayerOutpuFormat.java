package br.gov.frameworkdemoiselle.spatial.component.ogcws.model;

import java.util.HashMap;
import java.util.Map;

public enum LayerOutpuFormat {

	
    BMP ("image/bmp"),
    JPEG ("image/jpeg"),
    TIFF ("image/tiff"),
    PNG ("image/png"),
    PNG8 ("image/png8"),
    PNG24 ("image/png24"),
    PNG32 ("image/png32"),
    GIF ("image/gif"),
    SVG ("image/svg+xml"),
    PNG242 ("image/png; mode=24bit"),
    WAPWBMP ("image/vnd.wap.wbmp"),
    WBMP ("image/wbmp"),
    ATOM ("application/atom+xml"),
    OPENLAYERS ("application/openlayers"),
    PDF ("application/pdf"),
    RSS ("application/rss+xml"),
    KML ("application/vnd.google-earth.kml+xml"),
    KMZ ("application/vnd.google-earth.kmz"),
    GEOTIFF ("image/geotiff"),
    GEOTIFF8 ("image/geotiff8"),
    TIFF8 ("image/tiff8");
    
    
	
	/** The Constant stringToEnum. */
	private static final Map<String, LayerOutpuFormat> stringToEnum = new HashMap<String, LayerOutpuFormat>();

	static { // Initialize map from constant name to enum constant
		for (LayerOutpuFormat op : values()) {
			stringToEnum.put(op.value(), op);
		}
	}

	/** The value. */
	private final String value;

	/**
	 * Instantiates a new image size.
	 * 
	 * @param value
	 *            the value
	 */
	LayerOutpuFormat(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	/**
	 * From value.
	 * 
	 * @param value
	 *            the value
	 * 
	 * @return the image size
	 */
	public static LayerOutpuFormat fromValue(String value) {
		return stringToEnum.get(value);
	}
}
