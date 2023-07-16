package github.erb3.fabric.nohotbarlooping;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {

        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(NoHotbarLooping.translate("config.title"));

            ConfigCategory general = builder.getOrCreateCategory(
                    NoHotbarLooping.translate("config.category")
            );

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            general.addEntry(entryBuilder.startBooleanToggle(
                        NoHotbarLooping.translate("config.enabled.name"),
                        NoHotbarLooping.shouldLoopHotbar
                    )
                    .setDefaultValue(true)
                    .setTooltip(NoHotbarLooping.translate("config.enabled.description"))
                    .setSaveConsumer((newValue) -> NoHotbarLooping.config.toggle())
                    .build());

            return builder.build();
        };
    }
}
