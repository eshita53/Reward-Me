package com.example.alfasunny.homeuser;

/**
 * Created by Tanmoy Krishna Das on 2/11/2018.
 */

class ReviewEach {
    String restaurantName, itemName, rating, authorName, reviewDescription;

    public ReviewEach() {
    }

    public ReviewEach(String restaurantName, String itemName, String rating, String authorName, String reviewDescription) {
        this.restaurantName = restaurantName;
        this.itemName = itemName;
        this.rating = rating;
        this.authorName = authorName;
        this.reviewDescription = reviewDescription;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getReviewDescription() {
        return reviewDescription;
    }

    public void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }
}
