package com.redspark.albiontools;

/**
 * Stores constant data
 */

public class Constants {

    //Enum of supported locations
    @SuppressWarnings("unused")
    public enum LOCATION {
        Caerleon("Caerleon"),
        Thetford("Thetford"),
        Lymhurst("Lymhurst"),
        Bridgewatch("Bridgewatch"),
        Martlock("Martlock");

        private String string;

        LOCATION(String string) {
            this.string = string;
        }

        public String getLocationString() {
            return string;
        }
    }

    //Enum of supported item qualities
    @SuppressWarnings("unused")
    public enum QUALITY {
        UNKNOWN(0),
        Normal(1),
        Good(2),
        Outstanding(3),
        Excellent(4),
        Masterpiece(5);

        private int qualityIndex;

        QUALITY(int qualityIndex) {
            this.qualityIndex = qualityIndex;
        }

        public int getQualityIndex() {
            return qualityIndex;
        }

        @SuppressWarnings("unused")
        public static QUALITY findQuality(int qualityIndex) {
            for (QUALITY q : values()) {
                if (q.qualityIndex == qualityIndex) {
                    return q;
                }
            }
            return null;
        }
    }

    //Crops and their seeds
    @SuppressWarnings("unused")
    public enum CROP{
        Carrot("Carrots", "Carrot Seeds"),
        Bean("Beans", "Bean Seeds"),
        Wheat("Sheaf of Wheat", "Wheat Seeds"),
        Turnip("Turnips", "Turnip Seeds"),
        Cabbage("Cabbage", "Cabbage Seeds"),
        Potato("Potatoes", "Potato Seeds"),
        Corn("Bundle of Corn", "Corn Seeds"),
        Pumpkin("Pumpkin", "Pumpkin Seeds");

        private String cropName, cropSeedName;
        CROP(String cropName, String cropSeedName){
            this.cropName = cropName;
            this.cropSeedName = cropSeedName;
        }
        public String getCropName(){
            return cropName;
        }
        public String getCropSeedName(){
            return cropSeedName;
        }

    }
}
