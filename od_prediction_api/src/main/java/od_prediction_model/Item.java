package od_prediction_model;

public class Item {

	private String id;
	private String name;

	public String getName() {
		return id;
	}

	public void setName(String name) {
		this.id = name;
	}

	public Item(String name, String value) {
		this.id = name;
		this.name = value;
	}

	public String getValue() {
		return name;
	}

	public void setValue(String value) {
		this.name = value;
	}

	public Item(String nome) {
		this.id = nome;
	}

}
