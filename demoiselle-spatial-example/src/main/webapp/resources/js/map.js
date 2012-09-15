var map2,selectControl2,layer_vector2,selectedFeature;

			function onPopupClose(evt) {
			    selectControl2.unselect(selectedFeature);
			}
			
			function onFeatureSelect(feature) {
				
				selectedFeature = feature;
			    popup = new OpenLayers.Popup.FramedCloud("popup", 
			                             feature.geometry.getBounds().getCenterLonLat(),
			                             null,
			                             '<div>' + feature.data.name + '<br/>' + feature.data.address + '</div>',
			                             null, true, onPopupClose);
			    feature.popup = popup;
			    map2.addPopup(popup);

			}

			function onFeatureUnselect(feature) {
			    map2.removePopup(feature.popup);
			    feature.popup.destroy();
			    feature.popup = null;
			}

			function createEvents(layer_vector,map){
				map2 = map;
				layer_vector2 = layer_vector;
				selectControl = new OpenLayers.Control.SelectFeature(layer_vector, {onSelect: onFeatureSelect, onUnselect: onFeatureUnselect});
				map.addControl(selectControl);
	            selectControl.activate();

	            selectControl2 = selectControl;
			}