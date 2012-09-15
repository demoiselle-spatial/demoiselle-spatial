package sample.demoiselle_spatial_example.component.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;


@FacesConverter(forClass = Geometry.class)
public class GeometryConverter implements Converter {
	
   @Override
   public Object getAsObject(FacesContext context, UIComponent component, String value) {
       Geometry geom = null;
       if (value != null && !value.isEmpty()) {
           try {
               WKTReader reader = new WKTReader();
               geom = reader.read(value);
               geom.setSRID(4326);
           } catch (ParseException ex) {
               //logger.error("ParserErro on JSF Converter", ex);
           }

       }
       return geom;
   }

   @Override
   public String getAsString(FacesContext context, UIComponent component, Object value) {
       return value.toString();
   }
}
