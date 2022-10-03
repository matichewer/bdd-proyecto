package vuelos.modelo.empleado.beans;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//CLASE IMPLEMENTADA PROVISTA POR LA CATEDRA
//Representa como un objeto una tupla de la tabla "ubicaciones"
public class UbicacionesBeanImpl implements Serializable, UbicacionesBean {
	
	private static Logger logger = LoggerFactory.getLogger(UbicacionesBeanImpl.class);
	
	private static final long serialVersionUID = 1L;

	private String pais;
	private String estado;
	private String ciudad;
	private int huso;
	
	@Override
	public String getPais() {
		return this.pais;
	}

	@Override
	public void setPais(String pais) {
		this.pais = pais;		
	}

	@Override
	public String getEstado() {
		return this.estado;
	}

	@Override
	public void setEstado(String estado) {
		this.estado = estado;		
	}

	@Override
	public String getCiudad() {
		return this.ciudad;
	}

	@Override
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;		
	}

	@Override
	public int getHuso() {
		return this.huso;
	}

	@Override
	public void setHuso(int huso) {
		this.huso = huso;		
	}

	@Override
	public String toString() {
		return this.pais + " - " +  this.estado + " - " + this.ciudad;		
	}
	
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UbicacionesBeanImpl) {
        	UbicacionesBeanImpl ub = (UbicacionesBeanImpl) obj;
            if (	ub.getPais().equals(this.getPais()) && 
            		ub.getEstado().equals(this.getEstado()) &&
            		ub.getCiudad().equals(this.getCiudad()) 
            	){
                return true;
            }
        }
        return false;       
    }
}
