package miadesign.hu.best.balaton.models.networks;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import miadesign.hu.best.balaton.entities.Attraction;

public class AttractionDeserializer implements JsonDeserializer<Attraction[]> {

    private JsonElement output = new JsonArray();

    @Override
    public Attraction[] deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {

        JsonElement array = je.getAsJsonObject().getAsJsonArray("digitalAttractions");

        for (JsonElement element : array.getAsJsonArray()) {
            JsonObject flattenObj = new JsonObject();
            JsonObject originalObj = element.getAsJsonObject();
            flattenObj.add("latitude", originalObj.getAsJsonArray("geomap").get(1));
            flattenObj.add("longitude", originalObj.getAsJsonArray("geomap").get(0));
            flattenObj.add("main_image", originalObj.getAsJsonArray("mainImageResized").get(0));
            JsonArray images = new JsonArray();
            images.addAll(originalObj.getAsJsonArray("mainImageResized"));
            if (originalObj.has("imagesResized")) {
                images.addAll(originalObj.getAsJsonArray("imagesResized"));
            }
            flattenObj.add("images", images);

            //details
            JsonObject details = originalObj.getAsJsonObject("details");
            flattenObj.add("category", details.get("Shorttext"));
            flattenObj.add("id", details.get("AttractionID"));
            flattenObj.add("description", details.get("Descriptions"));
            flattenObj.add("name", details.get("Denomination"));
            output.getAsJsonArray().add(flattenObj);

        }
        return new Gson().fromJson(output, type);

    }

}
