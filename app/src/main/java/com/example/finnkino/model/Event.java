package com.example.finnkino.model;

import java.util.List;

public class Event {
    private String eventID, originalTitle, globalDistributorName, type, genres, synopsis, ratingImageUrl,
            largeImageLandscape, imdbRating, productionYear, lengthInMinutes;
    private List<Actor> cast;
    private List<Director> directors;
    private List<ContentDescriptor> contentDescriptors;

    public String getEventID() {
        return eventID;
    }
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getGlobalDistributorName() {
        return globalDistributorName;
    }
    public void setGlobalDistributorName(String globalDistributorName) {
        this.globalDistributorName = globalDistributorName;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getGenres() {
        return genres;
    }
    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getSynopsis() {
        return synopsis;
    }
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getRatingImageUrl() {
        return ratingImageUrl;
    }
    public void setRatingImageUrl(String ratingImageUrl) {
        this.ratingImageUrl = ratingImageUrl;
    }

    public String getLargeImageLandscape() {
        return largeImageLandscape;
    }
    public void setLargeImageLandscape(String largeImageLandscape) {
        this.largeImageLandscape = largeImageLandscape;
    }

    public String getProductionYear() {
        return productionYear;
    }
    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    public String getLengthInMinutes() {
        return lengthInMinutes;
    }
    public void setLengthInMinutes(String lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    public String getImdbRating() {
        return imdbRating;
    }
    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public List<Actor> getCast() {
        return cast;
    }
    public void setCast(List<Actor> cast) {
        this.cast = cast;
    }

    public List<Director> getDirectors() {
        return directors;
    }
    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public List<ContentDescriptor> getContentDescriptors() {
        return contentDescriptors;
    }
    public void setContentDescriptors(List<ContentDescriptor> contentDescriptors) {
        this.contentDescriptors = contentDescriptors;
    }





    public class Actor {
        private String actorFirstName, actorLastName;

        public String getActorFirstName() {
            return actorFirstName;
        }
        public void setActorFirstName(String actorFirstName) {
            this.actorFirstName = actorFirstName;
        }

        public String getActorLastName() {
            return actorLastName;
        }
        public void setActorLastName(String actorLastName) {
            this.actorLastName = actorLastName;
        }
    }



    public class Director {
        private String directorFirstName, directorLastName;

        public String getDirectorFirstName() {
            return directorFirstName;
        }
        public void setDirectorFirstName(String directorFirstName) {
            this.directorFirstName = directorFirstName;
        }

        public String getDirectorLastName() {
            return directorLastName;
        }
        public void setDirectorLastName(String directorLastName) {
            this.directorLastName = directorLastName;
        }
    }



    public class ContentDescriptor {
        private String contentDescriptorName, contentDescriptorImageURL;

        public String getContentDescriptorName() {
            return contentDescriptorName;
        }
        public void setContentDescriptorName(String contentDescriptorName) {
            this.contentDescriptorName = contentDescriptorName;
        }

        public String getContentDescriptorImageURL() {
            return contentDescriptorImageURL;
        }
        public void setContentDescriptorImageURL(String contentDescriptorImageURL) {
            this.contentDescriptorImageURL = contentDescriptorImageURL;
        }
    }
}