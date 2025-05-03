package bloody.devmules.shearMaster;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ShearMasterTabCompleter implements TabCompleter {

    private ShearMaster plugin;

    public ShearMasterTabCompleter(ShearMaster plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("shearmaster")) {
            if (args.length == 1) {
                completions.add("reload");
                completions.add("toggle");
                completions.add("status");
            } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle")) {
                completions.addAll(plugin.getConfigManager().getAnimals()); // Suggestie voor dieren
            } else if (args.length == 3 && args[0].equalsIgnoreCase("toggle")) {
                completions.add("shear");
                completions.add("slice");
            }
        }

        return completions;
    }
}
