package Gym.Dto;

import java.util.List;

public class GymRequest {

        public String name;
        public String address;
        public String city;
        public String area;
        public String pin;
        public double latitude;
        public double longitude;
        public double hourlyRate;
        public int slotCapacity;

        public List<String> timings;
        public List<String> facilities;
        public List<String> aboutPoints;
        public List<String> gymPhotos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getSlotCapacity() {
        return slotCapacity;
    }

    public void setSlotCapacity(int slotCapacity) {
        this.slotCapacity = slotCapacity;
    }

    public List<String> getTimings() {
        return timings;
    }

    public void setTimings(List<String> timings) {
        this.timings = timings;
    }

    public List<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }

    public List<String> getAboutPoints() {
        return aboutPoints;
    }

    public void setAboutPoints(List<String> aboutPoints) {
        this.aboutPoints = aboutPoints;
    }

    public List<String> getGymPhotos() {
        return gymPhotos;
    }

    public void setGymPhotos(List<String> gymPhotos) {
        this.gymPhotos = gymPhotos;
    }
}
