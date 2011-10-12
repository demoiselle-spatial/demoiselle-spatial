package br.gov.frameworkdemoiselle.spatial.component.shapefile.sample;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe responsavel por implementar a abstração de um ResultSet especifico
 * para arquivos shapefile.
 * A classe agrega uma coleção de registros contidos em um arquivo shapefile
 * @author rafael.soto
 *
 */
public class ShapefileResultSet implements Iterable<ShapefileRow>{

	private int offset;
	
	private int maxElements;
	
	private List<ShapefileRow> rows = null;
	
	
	public ShapefileResultSet() {
	
		offset = 0;
		maxElements = 0;
	}

	public void addRow(ShapefileRow row)
	{
		if(rows == null)
		{
			rows = new ArrayList<ShapefileRow>();
		}
		rows.add(row);
		maxElements = rows.size();
	}
	
	
	public boolean hasNext() {

		if (offset >= maxElements) {
			return false;
		} else {
			return true;
		}
	}

	
	public ShapefileRow next() {
		
		
		return rows.get(offset++);
	}
		

	@Override
	public Iterator<ShapefileRow> iterator() {
		
		return rows.iterator();
	}
	
	public int size()
	{
		return maxElements;
	}
	
	
	
}
