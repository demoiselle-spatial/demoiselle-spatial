package br.gov.frameworkdemoiselle.spatial.component.shapefile.sample;



public enum GeometryType {

	LINESTRING
	{				
		@Override
		public String toString() {
			
			return "LineString";
		}
		
	},
	MULTILINESTRING
	{				
		@Override
		public String toString() {
			
			return "MultiLineString";
		}
		
	},
	MULTIPOLYGON
	{				
		@Override
		public String toString() {
			
			return "MultiPolygon";
		}
		
	},
	POLYGON
	{				
		@Override
		public String toString() {
			
			return "Polygon";
		}
		
	};
	
	
}
