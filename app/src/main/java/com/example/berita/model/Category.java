// Category.java
package com.example.berita.model;

public class Category {
    private String id;
    private String name;
    private String displayName;
    private int iconResId;
    private boolean isSelected;

    public Category(String id, String name, String displayName, int iconResId) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.iconResId = iconResId;
        this.isSelected = false;
    }

    // Getters & Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDisplayName() { return displayName; }
    public int getIconResId() { return iconResId; }
    public boolean isSelected() { return isSelected; }
    public void setSelected(boolean selected) { isSelected = selected; }
}