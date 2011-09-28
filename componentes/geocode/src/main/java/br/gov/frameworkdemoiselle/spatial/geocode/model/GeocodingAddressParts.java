package br.gov.frameworkdemoiselle.spatial.geocode.model;

import java.util.List;

public class GeocodingAddressParts {

	    private String longAddressName;
	    private String shortAddressName;
	    private List<AddressType> types;
	    
		public String getLongAddressName() {
			return longAddressName;
		}
		public void setLongAddressName(String longAddressName) {
			this.longAddressName = longAddressName;
		}
		public String getShortAddressName() {
			return shortAddressName;
		}
		public void setShortAddressName(String shortAddressName) {
			this.shortAddressName = shortAddressName;
		}
		public List<AddressType> getTypes() {
			return types;
		}
		public void setTypes(List<AddressType> types) {
			this.types = types;
		}
	    
	    
}
