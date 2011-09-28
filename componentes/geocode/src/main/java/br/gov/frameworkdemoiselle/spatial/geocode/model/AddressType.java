package br.gov.frameworkdemoiselle.spatial.geocode.model;

import java.util.HashMap;
import java.util.Map;

public enum AddressType  {
    
    STREET_ADDRESS ("street_address"),
    ROUTE ("route"),
    INTERSECTION ("intersection"),
    POLITICAL ("political"),
    COUNTRY ("country"),
    ADM_LEVEL1 ("administrative_area_level_1"),
    ADM_LEVEL2 ("administrative_area_level_2"),
    ADM_LEVEL3 ("administrative_area_level_3"),
    COLLOQUIAL ("colloquial_area"),
    LOCALITY ("locality"),
    SUBLOCALITY ("sublocality"),
    NEIGHBORHOOD ("neighborhood"),
    PREMISE ("premise"),
    SUBPREMISE ("subpremise"),
    POSTAL_CODE ("postal_code"),
    NATURAL_FEATURE ("natural_feature"),
    AIRPORT ("airport"),
    PARK ("park"),
    POI ("point_of_interest"),
    POST_BOX ("post_box"),
    STREET_NUMBER ("street_number"),
    FLOOR ("floor"),
    ROMM ("room");
    
	/** The Constant stringToEnum. */
	private static final Map<String, AddressType> stringToEnum = new HashMap<String, AddressType>();

	static { // Initialize map from constant name to enum constant
		for (AddressType op : values()) {
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
	AddressType(String value) {
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
	public static AddressType fromValue(String value) {
		return stringToEnum.get(value);
	}
}