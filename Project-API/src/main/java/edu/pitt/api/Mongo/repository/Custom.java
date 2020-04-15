package edu.pitt.api.Mongo.repository;

public class Custom {
//    private String state;
//    private String county;
//    private int total;

    public Custom(){};



    public class CountState{
        private String state;
        private int value;
        public int getValue(){return value;}
        public void setValue(int value) { this.value = value; }
        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
//
    }

    public static class Map{
        private String id;
        private int value;
        public int getValue(){return value;}
        public void setValue(int value) { this.value = value; }
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
//
    }

    public static class RoadLocation {
        float latitude;
        float longitude;

        public float getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            if (latitude == null) return;
            this.latitude = Float.parseFloat(latitude);
        }

        public float getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            if (longitude == null) return;
            this.longitude = Float.parseFloat(longitude);
        }
    }

    public class CountVis{
        private String label;
        private long value;

        public long getValue() { return value; }
        public void setValue(long value) { this.value = value; }

        public String getLabel() { return label; }
        public void setLabel(String label){this.label = label;}
    }

//    public class CountHumid{
//        private String humidity;
//        private int total;
//        public int getTotal() { return total; }
//        public void setTotal(int total) { this.total = total; }
//
//        public String getHumidity() { return humidity; }
//        public void setHumidity(String humidity){this.humidity=humidity;}
//    }
//
//    public class CountWeather{
//        private String label;
//        private int total;
//        public int getTotal() { return total; }
//        public void setTotal(int total) { this.total = total; }
//
//        public String getWeather_condition() { return weather_condition; }
//        public void setWeather_condition(String weather_condition){this.weather_condition=weather_condition;}
//    }

}
