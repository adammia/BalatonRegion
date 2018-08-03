package miadesign.hu.best.balaton.models.databases;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DataConverter {

    @TypeConverter
    public String fromList(List<String> images) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        return images != null ? gson.toJson(images, type) : null;
    }

    @TypeConverter
    public List<String> toList(String string) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        return string != null ? gson.fromJson(string, type) : null;
    }

}
