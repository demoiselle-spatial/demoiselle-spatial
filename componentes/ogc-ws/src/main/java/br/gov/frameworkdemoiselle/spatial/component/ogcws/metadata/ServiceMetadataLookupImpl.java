package br.gov.frameworkdemoiselle.spatial.component.ogcws.metadata;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.data.ServiceInfo;
import org.geotools.data.ows.CRSEnvelope;
import org.geotools.data.ows.WMSCapabilities;
import org.geotools.data.wfs.WFSDataStore;
import org.geotools.data.wms.WMSUtils;
import org.geotools.data.wms.WebMapServer;
import org.geotools.ows.ServiceException;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.metadata.citation.ResponsibleParty;
import org.opengis.referencing.ReferenceIdentifier;

import br.gov.frameworkdemoiselle.spatial.component.ogcws.exception.ServiceMetadataLookupException;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.model.Capability;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.model.Layer;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.model.LayerOutpuFormat;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.model.LayerWFS;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.model.Responsible;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.model.Service;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.model.WFS;
import br.gov.frameworkdemoiselle.spatial.component.ogcws.model.WMS;

public class ServiceMetadataLookupImpl implements ServiceMetadataLookup {

	@Override
	public Service lookup(URL wmsCapabilities, URL wfsCapabilities) throws ServiceMetadataLookupException {
		
		Service retorno = new Service();
		Capability capability = new Capability();
		
		if(wmsCapabilities == null && wfsCapabilities == null)
			throw new ServiceMetadataLookupException("Service metadada lookup needs at least one URL");
		
		capability.setWmsURL(wmsCapabilities);
		capability.setWfsURL(wfsCapabilities);
		
		retorno.setCapability(capability);
		capability.setService(retorno);
		
		if(wmsCapabilities != null)
			this.lookupWMS(retorno);
		
		if(wfsCapabilities != null)
			this.lookupWFS(retorno);

		return retorno;
	}
	
	private void lookupWFS(Service service) throws ServiceMetadataLookupException {
		
		Capability capability = service.getCapability();
		WFS wfs = new WFS();
		LayerWFS layer = null;
		CRSEnvelope envelop = null;
		
		Map<String,Object> connectionParameters = new HashMap<String,Object>();
		connectionParameters.put("WFSDataStoreFactory:GET_CAPABILITIES_URL", capability.getWfsURL().toString());
		DataStore data;
		SimpleFeatureType owsSchema = null;
		FeatureSource<SimpleFeatureType, SimpleFeature> source;
		
		try {
		
		data = DataStoreFinder.getDataStore( connectionParameters );
		this.lookupServiceWFSInfo(service, data.getInfo());

		String typeNames[] = data.getTypeNames();
		
			if (typeNames != null && typeNames.length > 0) {
	
			wfs.setLayers(new ArrayList<LayerWFS>());
			
				for (String type : typeNames) {
					
					try {
						owsSchema = data.getSchema(type);
					
					
					layer = new LayerWFS();
					layer.set_abstract(owsSchema.getDescription() == null?null:owsSchema.getDescription().toString());
					layer.setName(type);
					layer.setTitle(owsSchema.getName().toString());
					if(owsSchema.getCoordinateReferenceSystem() != null)
					{
						layer.setListSRID(new HashSet<String>());
						for (ReferenceIdentifier crs : owsSchema.getCoordinateReferenceSystem().getIdentifiers()) {
							layer.getListSRID().add(crs.toString());
						}
						
						
						
					}
					source = data.getFeatureSource(type);
					if(source != null && source.getBounds()!= null)
					{
						layer.setBoundingBoxes(new HashMap<String, CRSEnvelope>());
//						for (ReferenceIdentifier reference : source.getBounds().getCoordinateReferenceSystem().getIdentifiers()) {
//							srid = reference.getCode();
//						}
						envelop = new CRSEnvelope(source.getBounds());
						layer.getBoundingBoxes().put(envelop.getEPSGCode(),envelop);
					}
					

					
					layer.setType(owsSchema);
					
					wfs.getLayers().add(layer);
					
					} catch (Exception e) {
						//TODO Put log info here. Surrounded because xml schema may be mallformed
					}
				}
				
				WFSDataStore wfsStore = (WFSDataStore) data;
				if(wfsStore.getCapabilitiesURL() != null){
					wfs.setGetURL(wfsStore.getCapabilitiesURL().toString().replace("?", ""));
					wfs.setPostURL(wfsStore.getCapabilitiesURL().toString().replace("?", ""));
				}
				
			}
		} catch (IOException e) {
			throw new ServiceMetadataLookupException("Error while lookuping WFS",e);
		}
		
		capability.setWfs(wfs);
	}

	private void lookupServiceWFSInfo(Service service, ServiceInfo info) {
		
		if(service.getTitle() == null)
			service.setTitle(info.getTitle());
		
		if(service.get_abstract() == null)
			service.set_abstract(info.getDescription());
		
	}
	
	

	private void lookupWMS(Service service) throws ServiceMetadataLookupException
	{
		Capability capability = service.getCapability();
		WMS wms = new WMS();
		Layer layer = null;
		
		WebMapServer owsWMS = null;
		org.geotools.data.ows.Service owsService;
		
		try {
			owsWMS = new WebMapServer(capability.getWmsURL());
			WMSCapabilities owsCapabilities = owsWMS.getCapabilities();
			
			if(service.getResponsable() == null)
				service.setResponsable(new Responsible());
			
			if(owsCapabilities.getService() != null && owsCapabilities.getService().getContactInformation() != null)
				this.lookupWMSResponsable(service, owsCapabilities);
			
			if(owsCapabilities.getService() == null)
				throw new ServiceMetadataLookupException("Service metadada lookup needs at least one Service Information");
				
				owsService = owsCapabilities.getService();
				this.lookupServiceWMSInfo(service, owsService);
				
				wms.setMaxHeight(owsService.getMaxHeight());
				wms.setMaxWidht(owsService.getMaxWidth());
				
				org.geotools.data.ows.Layer[] owsLayerList = WMSUtils.getNamedLayers(owsCapabilities);
				
			if (owsLayerList != null && owsLayerList.length > 0) {

				wms.setLayers(new ArrayList<Layer>());
				
				for (org.geotools.data.ows.Layer owsLayer : WMSUtils.getNamedLayers(owsCapabilities)) {
					
					layer = new Layer();
					layer.set_abstract(owsLayer.get_abstract());
					layer.setName(owsLayer.getName());
					layer.setTitle(owsLayer.getTitle());
					
					layer.setKeyWords(this.lookupKeyWords(owsLayer.getKeywords()));
					layer.setBoundingBoxes(this.lookupBoundBoxes(owsLayer));
					layer.setListSRID(owsLayer.getSrs());
					
					wms.getLayers().add(layer);

				}

			}
			
			if(owsCapabilities.getRequest().getGetMap().getFormats() != null)
				capability.setFormats(new ArrayList<LayerOutpuFormat>());
			
			for (String format : owsCapabilities.getRequest().getGetMap().getFormats()) {
				
				try {
					capability.getFormats().add(LayerOutpuFormat.fromValue(format));
				} catch (Exception e) {
					//TODO Format unexpected
				}
				
			}
			
			if(owsCapabilities.getRequest().getGetMap().getGet() != null)
				wms.setGetURL(owsCapabilities.getRequest().getGetMap().getGet().toString().replace("?", ""));
			
			if(owsCapabilities.getRequest().getGetMap().getPost() != null)
				wms.setPostURL(owsCapabilities.getRequest().getGetMap().getPost().toString().replace("?", ""));
			
			capability.setWms(wms);	
			
		} catch (ServiceException e) {
			throw new ServiceMetadataLookupException("Error on lookup WMS", e);
		} catch (IOException e) {
			throw new ServiceMetadataLookupException("Error on lookup WMS", e);
		}
	}

	private Map<String,CRSEnvelope> lookupBoundBoxes(org.geotools.data.ows.Layer owsLayer) {
	
		Map<String,CRSEnvelope> retorno = new HashMap<String, CRSEnvelope>();
		
		if(owsLayer.getBoundingBoxes() != null)
			retorno = owsLayer.getBoundingBoxes();

		if(owsLayer.getLatLonBoundingBox() != null)
		{
			if (owsLayer.getLatLonBoundingBox().getSRSName() == null)
				retorno.put("GeneralDirectPosition", owsLayer.getLatLonBoundingBox());
			else if(!retorno.containsKey(owsLayer.getLatLonBoundingBox().getSRSName()))
				retorno.put(owsLayer.getLatLonBoundingBox().getSRSName(), owsLayer.getLatLonBoundingBox());
		}
			
		if(retorno.isEmpty())
			return null;
		else
			return retorno;
	}

	private void lookupWMSResponsable(Service service, WMSCapabilities wmsCapabilities)
	{
		Responsible resp = service.getResponsable();
		ResponsibleParty owsResponsible = wmsCapabilities.getService().getContactInformation();
		
		if(resp.getContact() == null)
			resp.setContact(owsResponsible.getIndividualName());
		
		if(resp.getOrganization() == null)
			resp.setOrganization(owsResponsible.getOrganisationName() == null?null:owsResponsible.getOrganisationName().toString());
		
		service.setResponsable(resp);
	}
	
	private void lookupServiceWMSInfo(Service service, org.geotools.data.ows.Service owsService)
	{
		if(service.getName() == null)
			service.setName(owsService.getName());
		
		if(service.getTitle() == null)
			service.setTitle(owsService.getTitle());
		
		if(service.getURLResource() == null)
			service.setURLResource(owsService.getOnlineResource());
		
		if(service.get_abstract() == null)
			service.set_abstract(owsService.get_abstract());
		
		if(service.getLayerLimit() == null)
			service.setLayerLimit(owsService.getLayerLimit());
	}

	private List<String> lookupKeyWords(String[] keywords) {
		if(keywords == null)
			return null;
		if(keywords.length == 0)
			return null;
		List<String> retorno = new ArrayList<String>();
		for (String string : keywords) {
			retorno.add(string);
		}		
		return retorno;
	}
	
}
