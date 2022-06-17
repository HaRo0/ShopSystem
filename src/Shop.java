
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Shop {

	private String shopId;
	private String name;

	private int size;

	private Map<String, Map<ItemStack, Integer>> items;
	private Map<String, Material> categories;

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Map<String, Map<ItemStack, Integer>> getItems() {
		return items;
	}

	public void setItems(Map<String, Map<ItemStack, Integer>> item) {
		items = item;
	}

	public Map<String, Material> getCategories() {
		return categories;
	}

	public void setCategories(Map<String, Material> categorie) {
		categories = categorie;
	}
}