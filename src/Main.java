import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements CommandExecutor {

	Shop s = new Shop();
	ShopManager shop;

	@Override
	public void onEnable() {
		super.onEnable();
		s.setCategories(new HashMap<String, Material>());
		s.setItems(new HashMap<String, Map<ItemStack, Integer>>());
		s.setName("Shop");
		shop = new ShopManager(s);
		ItemStack test=new ItemStack(Material.GRASS);
		ItemMeta meta = test.getItemMeta();
		meta.addEnchant(Enchantment.DURABILITY, 501, true);
		test.setItemMeta(meta);
		shop.addItem("test",test,200);
		shop.addItem("test",new ItemStack(Material.STONE),200);
		shop.addItem("test",new ItemStack(Material.WOOD),200);
		shop.addItem("test",new ItemStack(Material.GLASS),200);
		shop.addItem("test",new ItemStack(Material.DIRT),200);
		shop.addItem("test",new ItemStack(Material.COBBLESTONE),200);
		shop.addItem("test",new ItemStack(Material.PRISMARINE),200);
		shop.addItem("test",new ItemStack(Material.GLOWSTONE),200);
		shop.addItem("test",new ItemStack(Material.ARROW),200);
		for(int i=0;i<50;i++) {
			meta.addEnchant(Enchantment.DURABILITY, i, true);
			test=new ItemStack(Material.GRASS);
			test.setItemMeta(meta);
			shop.addItem("test",test,200);
		}
		shop.setCategorieItem("test", Material.ANVIL);

		Bukkit.getPluginManager().registerEvents(shop, this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("shopgui")) {
			if (sender instanceof Player) {
				shop.openGUI((Player) sender);
				return true;
			}
		}
		return false;
	}

}
