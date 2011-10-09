package br.gov.frameworkdemoiselle.spatial.component.ogcws.sample;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.geotools.data.ows.CRSEnvelope;
import org.geotools.data.ows.Layer;
import org.geotools.data.ows.WFSCapabilities;
import org.geotools.data.ows.WMSCapabilities;
import org.geotools.data.wms.WMSUtils;
import org.geotools.data.wms.WebMapServer;
import org.geotools.data.wms.request.GetMapRequest;
import org.geotools.data.wms.response.GetMapResponse;
import org.geotools.ows.ServiceException;
import org.xml.sax.SAXException;

public class TestWMSCapabilities {

	public static void main(String[] args) {
		URL url = null;
		try {
			url = new URL(
					//"http://www2.dmsolutions.ca/cgi-bin/mswms_gmap?VERSION=1.1.0&REQUEST=GetCapabilities");
					"http://sigel.aneel.gov.br/arcgis/services/SIGEL/Geracao/MapServer/WMSServer?request=getcapabilities");
					//"http://www2.sipam.gov.br/geoserver/wms?service=WMS&request=GetCapabilities");
		} catch (MalformedURLException e) {
			// will not happen
		}

		WebMapServer wms = null;
		try {
			wms = new WebMapServer(url);
			
			
			WMSCapabilities capabilities = wms.getCapabilities();

			String serverName = capabilities.getService().getName();
			String serverTitle = capabilities.getService().getTitle();
			System.out.println("Capabilities retrieved from server: " + serverName + " (" + serverTitle + ")");
			
			System.out.println(capabilities.getService().getOnlineResource());
			
			//gets the top most layer, which will contain all the others
			Layer rootLayer = capabilities.getLayer();

			System.out.println(rootLayer.getName());
			
			
			System.out.println("===============");
			
			Layer[] layers2 = WMSUtils.getNamedLayers(capabilities);
			List<Layer> layers = capabilities.getLayerList();
			CRSEnvelope env = null;
			int i = 0;
			for (Layer layer : layers2) {
				  i++;
				  System.out.println("Layer: ("+i+")"+layer.getName());
				  System.out.println("       "+layer.getTitle());
				  System.out.println("       "+layer.getChildren().length);
				  System.out.println("       "+layer.getBoundingBoxes());
				  env = layer.getLatLonBoundingBox();
				  System.out.println("       "+env.getLowerCorner()+" x "+env.getUpperCorner());
				 
				  List<String> formats = wms.getCapabilities().getRequest().getGetMap().getFormats();
				  
				  System.out.println("formats: " + formats);
				  
				  
				  
			}
			System.out.println("===============");
			
			GetMapRequest request = wms.createGetMapRequest();
			
			request.setFormat("image/png");
			request.setDimensions("583", "420"); //sets the dimensions of the image to be returned from the server
			request.setTransparent(true);
			request.setSRS("EPSG:4326");
			request.setBBox(env);
			request.addLayer(layers.get(layers.size() - 1));
			
			System.out.println(request.getFinalURL());
			
			GetMapResponse response = (GetMapResponse) wms.issueRequest(request);
			
			ImageIO.write(ImageIO.read(response.getInputStream()), "png", new File("/Users/otos/development/projeto/demoiselle-spatial/tmp/file.png"));

			
			System.out.println(request.getFinalURL());
			
			
		} catch (IOException e) {
			// There was an error communicating with the server
			// For example, the server is down
		} catch (ServiceException e) {
			// The server returned a ServiceException (unusual in this case)
		} catch (SAXException e) {
			// Unable to parse the response from the server
			// For example, the capabilities it returned was not valid
		}
	}
}
