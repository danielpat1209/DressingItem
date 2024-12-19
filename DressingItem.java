package org.example;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



public class DressingItem {
    private DressingItemType type;
    private String color;
    private List<Season> seasons;
    private int size;
    private int amount;

    public DressingItemType getType() {
        return type;
    }

    public void setType(DressingItemType type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    //א.
    public Map<Season, List<DressingItem>> filterGroupBySeasons(List<DressingItem> items, int minSize) {
        Map<Season, List<DressingItem>> seasonItems = new HashMap<>();
        for (DressingItem item : items) {
            if (item.getSize() >= minSize) {

                for (Season season : item.getSeasons()) {
                    if (seasonItems.containsKey(season)) {
                        seasonItems.get(season);
                    } else {
                        System.out.println("season items not exist");
                    }
                }
            }
        }
        return seasonItems;
    }
    //א.
     public Map<Season,List<DressingItem>> filterGroupBySeasons1(List<DressingItem> items,int minSize) {
         return items.stream().filter(item -> item.getSize() >= minSize)
                 .flatMap(item -> item.getSeasons().stream().map(Season -> Map.entry(Season, item)))
                 .collect(Collectors.groupingBy(Map.Entry::getKey
                         , Collectors.mapping(Map.Entry::getValue
                                 , Collectors.toList())));

     }
    // ב.
    public int countItems(List<DressingItem> items, List<Season> seasons) {
        int sumItems = 0;
        for (DressingItem item : items) {
            for (Season season : seasons)
            if (item.getSeasons().contains(season)) {
                sumItems++;
                sumItems += item.getAmount();
                break;
            }

        }
        return sumItems;
    }
    //ב.
    public int countItems1(List<DressingItem> items, List<Season> seasons) {
        return  items.stream().filter(item->item.getSeasons().stream()
                .anyMatch(seasons::contains)).mapToInt(DressingItem::getAmount)
                .sum();
    }

    public String mostCommonColor(List<DressingItem> items, DressingItemType dressingItemType) {
        Map<String, Integer> colorCount = new HashMap<>();
        // הפונקציה צריכה להחזיר את הצבע הנפוץ ביותר של פריט הלבוש מסוג dressingItemType

        for (DressingItem item : items) {
            if (item.getType() == dressingItemType) { // בדוק אם סוג הפריט מתאים
                String color = item.getColor();
                colorCount.put(color, colorCount.getOrDefault(color, 0) + 1); // עדכן את מספר ההופעות
            }
        }

        // מציאת הצבע הנפוץ ביותר
        String mostCommonColor = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : colorCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommonColor = entry.getKey();
            }
        }

        return mostCommonColor; // החזר את הצבע הנפוץ ביותר
    }


    public String mostCommonColor1(List<DressingItem> items, DressingItemType dressingItemType) {
        return items.stream()
                .filter(item -> item.getType() == dressingItemType) // סינון פריטים לפי הסוג
                .collect(Collectors.groupingBy(DressingItem::getColor, Collectors.counting())) // סידור לפי צבע ומניה של כל צבע
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue)) // מציאת הצבע עם הכי הרבה הופעות
                .map(Map.Entry::getKey) // החזרת הצבע הנפוץ ביותר
                .orElse(null); // במקרה ואין צבעים, החזר null


}
    }
