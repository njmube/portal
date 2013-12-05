package mx.gob.sat.cfd._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "", propOrder = { "emisor", "receptor", "conceptos",
//		"impuestos", "complemento", "addenda" })
@XmlRootElement(name = "Comprobante")
public class ComprobanteModa extends Comprobante {
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ComprobanteModa [id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
	
	

}
