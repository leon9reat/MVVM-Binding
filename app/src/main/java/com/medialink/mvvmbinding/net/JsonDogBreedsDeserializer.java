package com.medialink.mvvmbinding.net;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.medialink.mvvmbinding.model.DogBreed;
import com.medialink.mvvmbinding.model.DogBreeds;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonDogBreedsDeserializer implements JsonDeserializer<DogBreeds> {
    private static final String TAG = "JsonDogBreedsDeserializer";

    @SuppressLint("LongLogTag")
    @Override
    public DogBreeds deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        DogBreeds breeds = new DogBreeds();
        if (json.isJsonObject()) {
            for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
                if (entry.getKey().equals("status")) {
                    Log.d(TAG, "Primitive: " + entry.getKey() + " = " + entry.getValue().getAsString());

                    breeds.setStatus(entry.getValue().getAsString());
                } else if (entry.getKey().equals("message")) {
                    Log.d(TAG, "Object: key: " + entry.getKey() + " = " + entry.getValue());

                    JsonObject jsonObject = entry.getValue().getAsJsonObject();
                    for (Map.Entry<String, JsonElement> subEntry : jsonObject.entrySet()) {
                        DogBreed db = new DogBreed();
                        db.setBreed(subEntry.getKey());
                        breeds.addBreed(db);
                    }
                }
            }
        }
        return breeds;
    }
}
