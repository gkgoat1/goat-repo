package at.gkgo.canon.bake;

import com.mojang.serialization.JsonOps;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.recipe.Recipe;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class BakerExporter implements RecipeExporter {
    public static Identifier getRecipeLocation(Identifier recipeId) {
        return new Identifier(recipeId.getNamespace(), String.join("", "recipes/", recipeId.getPath(), ".json"));
    }

    public static Identifier getAdvancementLocation(Identifier advancementId) {
        return new Identifier(advancementId.getNamespace(), String.join("", "advancements/", advancementId.getPath(), ".json"));
    }

    public static Identifier getTagLocation(String identifier, Identifier tagId) {
        return new Identifier(tagId.getNamespace(), String.join("", "tags/", identifier, "/", tagId.getPath(), ".json"));
    }
    public final Baker baker;

    public BakerExporter(Baker baker) {
        this.baker = baker;
    }

    @Override
    public void accept(Identifier recipeId, Recipe<?> recipe, @Nullable AdvancementEntry advancement) {
        baker.bake(getRecipeLocation(recipeId),Recipe.CODEC.encodeStart(JsonOps.INSTANCE,recipe).result().get().getAsJsonObject(), ResourceType.SERVER_DATA);
        if(advancement != null){
            baker.bake(getAdvancementLocation(recipeId),Advancement.CODEC.encodeStart(JsonOps.INSTANCE,advancement.value()).result().get().getAsJsonObject(), ResourceType.SERVER_DATA);
        }
    }

    @Override
    public Advancement.Builder getAdvancementBuilder() {
        return Advancement.Builder.createUntelemetered();
    }
}
