package software.experiment.qrcode.database;

import java.io.Serializable;

public class Item implements Serializable{
	/**
	 * ????????????????????
	 */
	private static final long serialVersionUID = 9205698121772728240L;
	protected String itemName;
	protected boolean lost;
	protected int price;
	protected String QRCodeFileName;
	
	public Item(String itemName, boolean lost, int price, String QRCodeFileName) {
		super();
		this.itemName = itemName;
		this.lost = lost;
		this.price = price;
		this.QRCodeFileName = QRCodeFileName;
	}

	public String getItemName() {
		return itemName;
	}

	public boolean isLost() {
		return lost;
	}

	public int getPrice() {
		return price;
	}

	public String getQRCodeFileName() {
		return QRCodeFileName;
	}
}
