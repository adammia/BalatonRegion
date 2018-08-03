package miadesign.hu.best.balaton.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import miadesign.hu.best.balaton.models.databases.DataConverter;

@SuppressWarnings("unused")
@Entity
public class Attraction extends BaseObservable implements Parcelable {

    @SerializedName("name")
    @Expose
    @PrimaryKey
    @NonNull
    public String name = "";
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("latitude")
    @Expose
    public Double latitude;
    @SerializedName("longitude")
    @Expose
    public Double longitude;
    @SerializedName("main_image")
    @Expose
    public String mainImage;
    @SerializedName("images")
    @Expose
    @TypeConverters(DataConverter.class)
    public List<String> images = null;
    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("description")
    @Expose
    public String description;


    private boolean favorite;


    public Attraction() {
    }

    //Getters
    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getMainImage() {
        return mainImage;
    }

    public List<String> getImages() {
        return images;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public String getImageDescription(String string) {
        return string + " " + this.name;
    }


    //Setters
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }


    //Parcelable implementation
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
        dest.writeString(this.mainImage);
        dest.writeStringList(this.images);
        dest.writeString(this.category);
        dest.writeString(this.description);
        dest.writeByte(this.favorite ? (byte) 1 : (byte) 0);
    }

    protected Attraction(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.mainImage = in.readString();
        this.images = in.createStringArrayList();
        this.category = in.readString();
        this.description = in.readString();
        this.favorite = in.readByte() != 0;
    }

    public static final Creator<Attraction> CREATOR = new Creator<Attraction>() {
        @Override
        public Attraction createFromParcel(Parcel source) {
            return new Attraction(source);
        }

        @Override
        public Attraction[] newArray(int size) {
            return new Attraction[size];
        }
    };
}
