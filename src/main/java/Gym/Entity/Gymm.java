package Gym.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Gymm {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        private String address;
        private String city;
        private String area;
        private String pin;

        private Double latitude;
        private Double longitude;

        @Column(nullable = false)
        private Double hourlyRate;   // mandatory

        @Column(nullable = false)
        private Integer slotCapacity;  // mandatory

        private Boolean active = false;

        @ElementCollection
        private List<String> timings;

        @ElementCollection
        private List<String> facilities;

        @ElementCollection
        private List<String> aboutPoints;

        @ElementCollection
        private List<String> gymPhotos;

        @ManyToOne
        @JoinColumn(name = "owner_id", nullable = false)
        private Users owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Integer getSlotCapacity() {
        return slotCapacity;
    }

    public void setSlotCapacity(Integer slotCapacity) {
        this.slotCapacity = slotCapacity;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }
}

