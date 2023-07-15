package github.erb3.fabric.nohotbarlooping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class Config {

    private static final File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "NoHotbarLooping.json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void saveConfig() {
        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(NoHotbarLooping.enabled, writer);
        } catch (IOException e) {
            NoHotbarLooping.LOGGER.error("Could not save NoHotbarLooping config!");
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try (Reader reader = new FileReader(configFile)) {
            NoHotbarLooping.enabled = gson.fromJson(reader, Boolean.class);
        } catch (Exception e) {
            if (configFile.exists()) {
                NoHotbarLooping.LOGGER.error("Config file exists, but could not load.");
                e.printStackTrace();
            } else {
                NoHotbarLooping.LOGGER.warn("Config file does not exist!");
            }
        }
    }

    public void toggle() {
        NoHotbarLooping.enabled = !NoHotbarLooping.enabled;
        this.saveConfig();
    }

}
