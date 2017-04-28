
package br.edu.ifpb.ajudeMais.api.dto;


/**
 * 
 * <p>
 * <b> LatLng.java </b>
 * </p>
 *
 * <p>
 * Entidade Representa o objecto Geográfico Latitude e Longitude.
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class LatLng {

	private String latitude;
	private String longitude;
	
	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	
}
