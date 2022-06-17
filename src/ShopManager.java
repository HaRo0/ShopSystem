import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopManager implements Listener {

	private Shop shop;

	private Map<String, Map<ItemStack, Integer>> items;
	private Map<String, Material> categories;
	private Map<String, Map<Integer, ItemStack>> sides = new HashMap<String, Map<Integer, ItemStack>>();
	private ArrayList<Player> isInInv = new ArrayList<Player>(), isInInv2 = new ArrayList<Player>();
	private int i;

	public ShopManager(Shop shop) {
		this.shop = shop;
		items = shop.getItems();
		categories = shop.getCategories();
		items.keySet().forEach(cat -> {
			i = 0;
			sides.put(cat, new HashMap<Integer, ItemStack>());
			items.get(cat).keySet().forEach(item -> {
				sides.get(cat).put(i, item.clone());
				i++;
			});
		});
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (isInInv.contains(e.getWhoClicked())) {
			e.setCancelled(true);
			if (e.getClickedInventory().getItem(e.getSlot()) != null) {
				if (e.getClickedInventory().getHolder() == null) {
					if (e.getSlot() % 9 == 0) {
						for (int i = 0; i < 6; i++) {
							ItemStack item = e.getClickedInventory().getItem(i * 9);
							if (item != null) {
								ItemMeta meta = new ItemStack(Material.BARRIER).getItemMeta();
								meta.setDisplayName(item.getItemMeta().getDisplayName());
								item.setItemMeta(meta);
							}
						}
						ItemStack cat = e.getCurrentItem();
						ItemMeta met = cat.getItemMeta();
						met.addEnchant(Enchantment.SILK_TOUCH, 1, false);
						cat.setItemMeta(met);
						for (int i = 2, j = 45; i < 36; i++, j++) {
							if (i % 9 == 0) {
								i += 2;
							}

							if (sides.get(met.getDisplayName().substring(2)).get(j) != null) {
								ItemStack ite = sides.get(met.getDisplayName().substring(2)).get(j).clone();
								ItemMeta meta = ite.getItemMeta();
								List<String> lore = new ArrayList<String>();
								lore.add(
										"§a" + items.get(e.getCurrentItem().getItemMeta().getDisplayName().substring(2))
												.get(ite) + "$ pro Item");
								meta.setLore(lore);
								ite.setItemMeta(meta);

								e.getClickedInventory().setItem(i, ite);
							} else {
								e.getClickedInventory().setItem(i, null);
							}
						}
					} else {
						for (int i = 2; i < 36; i++) {
							if (i % 9 == 0) {
								i += 2;
							}
							if (i == e.getSlot()) {
								openBuy((Player) e.getWhoClicked(), e.getCurrentItem());
							}
						}
						String cat = "";
						for (int i = 0; i < 6; i++) {
							if (e.getClickedInventory().getItem(i * 9) != null) {
								if (e.getClickedInventory().getItem(i * 9).getItemMeta()
										.hasEnchant(Enchantment.SILK_TOUCH)) {
									cat = e.getClickedInventory().getItem(i * 9).getItemMeta().getDisplayName()
											.substring(2);
								}
							}
						}

						ItemStack ite = e.getClickedInventory().getItem(2).clone();
						ItemMeta meta = ite.getItemMeta();
						meta.setLore(null);
						ite.setItemMeta(meta);
						int max = 1;
						for (int i = 1; i <= sides.get(cat).keySet().size(); i++) {
								if (sides.get(cat).get(i).equals(ite)) {
								max = i;
							}
						}
						if (e.getSlot() == 48 || (e.getSlot() == 52 
								&& sides.get(cat).keySet().contains(max + 28)
								)) {
							boolean ready = false;
							int j = 0;
							for (int i = 1; i <= sides.get(cat).keySet().size(); i++) {
								if (!ready) {
									if (sides.get(cat).get(i).equals(ite)) {
										ready = true;
										if (e.getSlot() == 48) {
											j = i;
										} if (e.getSlot() == 52) {
											j = i + 28;
										}
									}
								}
							}
							if (ready) {
								for (int i = j - 28, k = 2; i < j; i++,k++) {
									if (k < 36) {
										if (k % 9 == 0) {
											k += 2;
										}
										ItemStack it = sides.get(cat).get(i).clone();
										ItemMeta met = it.getItemMeta();
										List<String> lore = new ArrayList<String>();
										lore.add("§a" + items.get(cat).get(ite) + "$ pro Item");
										met.setLore(lore);
										ite.setItemMeta(met);
										e.getClickedInventory().setItem(k, it);
									}
								}
							}
						}
					}
				}
			}
		} else if (isInInv2.contains(e.getWhoClicked())) {
			e.setCancelled(true);
			if (e.getClickedInventory().getHolder() == null) {
				switch (e.getSlot()) {
				case (18):
					e.getClickedInventory().getItem(22).setAmount(1);
					break;
				case (19):
					if (e.getClickedInventory().getItem(22).getAmount() - 10 > 0) {
						e.getClickedInventory().getItem(22)
								.setAmount(e.getClickedInventory().getItem(22).getAmount() - 10);
					} else {
						e.getClickedInventory().getItem(22).setAmount(1);
					}
					break;
				case (20):
					if (e.getClickedInventory().getItem(22).getAmount() - 1 > 0) {
						e.getClickedInventory().getItem(22)
								.setAmount(e.getClickedInventory().getItem(22).getAmount() - 1);
					} else {
						e.getClickedInventory().getItem(22).setAmount(1);
					}
					break;
				case (26):
					e.getClickedInventory().getItem(22).setAmount(64);
					break;
				case (25):
					if (e.getClickedInventory().getItem(22).getAmount() + 10 < 64) {
						e.getClickedInventory().getItem(22)
								.setAmount(e.getClickedInventory().getItem(22).getAmount() + 10);
					} else {
						e.getClickedInventory().getItem(22).setAmount(64);
					}
					break;
				case (24):
					if (e.getClickedInventory().getItem(22).getAmount() + 1 < 64) {
						e.getClickedInventory().getItem(22)
								.setAmount(e.getClickedInventory().getItem(22).getAmount() + 1);
					} else {
						e.getClickedInventory().getItem(22).setAmount(64);
					}
					break;
				}
			}
		}
	}

	@EventHandler
	public void inventoryClose(InventoryCloseEvent e) {
		isInInv.remove(e.getPlayer());
		isInInv2.remove(e.getPlayer());
	}

	void openBuy(Player p, ItemStack item) {
		p.closeInventory();
		isInInv.remove(p);
		isInInv2.add(p);
		Inventory inv = Bukkit.createInventory(null, 9 * 6, "§bBUY " + item.getType());

		ItemStack item2 = new ItemStack(Material.BARRIER);
		ItemMeta meta = item2.getItemMeta();
		meta.setDisplayName(" ");
		item2.setItemMeta(meta);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 9; j++) {
				inv.setItem(i * 5 * 9 + j, item2);
			}
		}
		inv.setItem(22, item);

		item2.setType(Material.STAINED_GLASS_PANE);
		item2.setDurability((short) 14);
		meta.setDisplayName("§cNur 1");
		item2.setItemMeta(meta);
		inv.setItem(18, item2);

		meta.setDisplayName("§c-10");
		item2.setItemMeta(meta);
		inv.setItem(19, item2);

		meta.setDisplayName("§c-1");
		item2.setItemMeta(meta);
		inv.setItem(20, item2);

		item2.setDurability((short) 5);
		meta.setDisplayName("§a64");
		item2.setItemMeta(meta);
		inv.setItem(26, item2);

		meta.setDisplayName("§a+10");
		item2.setItemMeta(meta);
		inv.setItem(25, item2);

		meta.setDisplayName("§a+1");
		item2.setItemMeta(meta);
		inv.setItem(24, item2);

		p.openInventory(inv);
	}

	public void openGUI(Player p) {
		p.closeInventory();
		if (categories.size() < 6) {
			isInInv2.remove(p);
			isInInv.add(p);
			i = 0;
			Inventory inv = Bukkit.createInventory(null, 9 * 6, "§b" + shop.getName());
			categories.keySet().forEach(cat -> {
				ItemStack item = new ItemStack(categories.get(cat));
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§b" + cat);
				item.setItemMeta(meta);
				inv.setItem(i, item);
				i += 9;
			});
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
			ItemMeta meta = glass.getItemMeta();
			meta.setDisplayName("§3");
			glass.setItemMeta(meta);
			for (int j = 0; j < 6; j++) {
				inv.setItem(j * 9 + 1, glass);
			}
			glass.setType(Material.STAINED_GLASS);
			glass.setDurability((short) 14);
			meta.setDisplayName("§cZurueck");
			glass.setItemMeta(meta);
			inv.setItem(48, glass);
			glass.setDurability((short) 5);
			meta.setDisplayName("§aWeiter");
			glass.setItemMeta(meta);
			inv.setItem(52, glass);
			p.openInventory(inv);
		} else {
			p.sendMessage("§cBitte sag einem Admin bescheid, dass die Administration dumm ist");
		}
	}

	public void addItem(String categorie, ItemStack item, int price) {
		Map<ItemStack, Integer> cat;
		if (!items.containsKey(categorie)) {
			cat = new HashMap<ItemStack, Integer>();
			sides.put(categorie, new HashMap<Integer, ItemStack>());
			items.put(categorie, cat);
		} else {
			cat = items.get(categorie);
		}
		item.getItemMeta().setLore(null);
		cat.put(item, price);
		sides.get(categorie).put(sides.get(categorie).size(), item.clone());
	}

	public void setCategorieItem(String categorie, Material item) {

		if (!categories.containsKey(categorie)) {
			categories.put(categorie, item);
		}

	}

	public void removeCategorieItem() {

	}

	public void removeItem() {

	}

//	private void test() {
//
//		shop.setShopId("0");
//		shop.setName("Test");
//		Map<String, Map<ItemStack, Integer>> items = new HashMap<String, Map<ItemStack, Integer>>();
//		Map<ItemStack, Integer> item = new HashMap<ItemStack, Integer>(); // waepons
//		ItemStack i = new ItemStack(Material.DIAMOND_SWORD, 1, (short) 1);
//		ItemMeta meta = i.getItemMeta();
//		meta.setDisplayName("§6Lucky Sword");
//		i.setItemMeta(meta);
//		item.put(i, 200);
//		items.put("Waepons", item);
//
//		item = new HashMap<ItemStack, Integer>(); // Blocks
//		i = new ItemStack(Material.ACACIA_DOOR);
//		meta = i.getItemMeta();
//		meta.setDisplayName("§bFantastic Door");
//		i.setItemMeta(meta);
//		item.put(i, 50);
//		items.put("Blocks", item);
//
//	}
}
