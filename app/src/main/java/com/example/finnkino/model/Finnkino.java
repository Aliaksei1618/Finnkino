package com.example.finnkino.model;

import static com.example.finnkino.utils.NetworkUtils.generateStringURL;
import static com.example.finnkino.utils.NetworkUtils.getDocument;
import static com.example.finnkino.utils.OmdbApi.getRatingFromOmdbApi;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Finnkino {
    // Finnkino singleton
    private static Finnkino instance = new Finnkino();
    // events string constants
    private static final String EVENTS_URI = "https://www.finnkino.fi/xml/Events/?includeVideos=false";
    private static final String EVENT_TAG = "Event";
    private static final String EVEN_ID_TAG = "ID";
    private static final String ORIGINAL_TITLE_TAG = "OriginalTitle";
    private static final String GLOBAL_DISTRIBUTOR_NAME_TAG = "GlobalDistributorName";
    private static final String EVENT_TYPE_TAG = "EventType";
    private static final String GENRES_TAG = "Genres";
    private static final String SYNOPSIS_TAG = "Synopsis";
    private static final String RATING_IMAGE_URL_TAG = "RatingImageUrl";
    private static final String EVENT_LARGE_IMAGE_LANDSCAPE_TAG = "EventLargeImageLandscape";
    private static final String PRODUCTION_YEAR_TAG = "ProductionYear";
    private static final String LENGTH_MINUTES_TAG = "LengthInMinutes";
    private static final String ACTOR_TAG = "Actor";
    private static final String ACTOR_FIRST_NAME_TAG = "FirstName";
    private static final String ACTOR_LAST_NAME_TAG = "LastName";
    private static final String DIRECTORS_TAG = "Directors";
    private static final String DIRECTOR_FIRST_NAME_TAG = "FirstName";
    private static final String DIRECTOR_LAST_NAME_TAG = "LastName";
    private static final String CONTENT_DESCRIPTOR_TAG = "ContentDescriptor";
    private static final String CONTENT_DESCRIPTOR_NAME_TAG = "Name";
    private static final String CONTENT_DESCRIPTOR_IMAGE_TAG = "ImageURL";
    // theatre areas string constants
    private static final String THEATRE_AREAS_URL = "https://www.finnkino.fi/xml/TheatreAreas/";
    private static final String THEATRE_AREA_TAG = "TheatreArea";
    private static final String THEATRE_AREA_ID_TAG = "ID";
    private static final String THEATRE_AREA_NAME_TAG = "Name";
    // shows string constants
    private static final String SCHEDULE_URL = "https://www.finnkino.fi/xml/Schedule/";
    private static final String AREA = "area";
    private static final String NR_OF_DAYS = "nrOfDays";
    private static final String SHOW_TAG = "Show";
    private static final String SHOW_ID_TAG = "ID";
    private static final String SHOW_EVENT_ID_TAG = "EventID";
    private static final String THEATRE_ID_TAG = "TheatreID";
    private static final String TITLE_TAG = "Title";
    private static final String THEATRE_AND_AUDITORIUM_TAG = "TheatreAndAuditorium";
    private static final String RATING_TAG = "Rating";
    private static final String SPOKEN_LANGUAGE_TAG = "SpokenLanguage";
    private static final String SPOKEN_LANGUAGE_NAME_TAG = "Name";
    private static final String SPOKEN_LANGUAGE_ISO_TAG = "ISOTwoLetterCode";
    private static final String PRESENTATION_METHOD_TAG = "PresentationMethod";
    private static final String EVENT_SMALL_IMAGE_PORTRAIT_TAG = "EventSmallImagePortrait";
    private static final String DT_ACCOUNTING_TAG = "dtAccounting";
    private static final String DTTM_SHOW_START_TAG = "dttmShowStart";
    private static final String SUBTITLE_LANGUAGE1_TAG = "SubtitleLanguage1";
    private static final String SUBTITLE_LANGUAGE1_NAME_TAG = "Name";
    private static final String SUBTITLE_LANGUAGE2_TAG = "SubtitleLanguage2";
    private static final String SUBTITLE_LANGUAGE2_NAME_TAG = "Name";

    private User currentUser;
    private List<Show> showsList;
    private List<Event> eventList;
    private List<TheatreArea> theatreAreaList;
    private Map<String, String[]> theatresIdMap;

    {
        theatresIdMap = new HashMap<>();
        theatresIdMap.put("1029", new String[]{"1056", "1050", "1058", "1034", "1047", "1038", "1043", "1044", "1049", "1042", "1052", "1238", "1039", "1040", "1037", "1035"});
        theatresIdMap.put("1021", new String[]{"1040", "1037"});
        theatresIdMap.put("1014", new String[]{"1056", "1050", "1058", "1034", "1047", "1038", "1043"});
        theatresIdMap.put("1012", new String[]{"1056", "1050"});
        theatresIdMap.put("1002", new String[]{"1058", "1034", "1047", "1038", "1043"});
        theatresIdMap.put("1047", new String[]{"1035"});
        theatresIdMap.put("1039", new String[]{"1056"});
        theatresIdMap.put("1038", new String[]{"1050"});
        theatresIdMap.put("1045", new String[]{"1058"});
        theatresIdMap.put("1031", new String[]{"1034"});
        theatresIdMap.put("1032", new String[]{"1047"});
        theatresIdMap.put("1033", new String[]{"1038"});
        theatresIdMap.put("1013", new String[]{"1043"});
        theatresIdMap.put("1015", new String[]{"1044"});
        theatresIdMap.put("1016", new String[]{"1049"});
        theatresIdMap.put("1017", new String[]{"1042"});
        theatresIdMap.put("1041", new String[]{"1052"});
        theatresIdMap.put("1018", new String[]{"1036"});
        theatresIdMap.put("1019", new String[]{"1039"});
        theatresIdMap.put("1034", new String[]{"1040"});
        theatresIdMap.put("1035", new String[]{"1037"});
        theatresIdMap.put("1022", new String[]{"1035"});
        theatresIdMap.put("1046", new String[]{"1059"});
    }

    private Finnkino() {
    }

    public static Finnkino getInstance() {
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Map<String, String[]> getTheatresIdMap() {
        return theatresIdMap;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public List<Show> getShowsList() {
        return showsList;
    }

    public List<TheatreArea> getTheatreAreaList() {
        return theatreAreaList;
    }

    public void setShowList() {
        List<Show> showList = new ArrayList<>();
        String[] theatreAreas = {"1014", "1015", "1016", "1017", "1018", "1019", "1021", "1022", "1041", "1046"};
        for (String theatreArea : theatreAreas) {
            Document document = getDocument(generateStringURL(SCHEDULE_URL, AREA, theatreArea, NR_OF_DAYS, "93"));
            NodeList showNodeList = document.getElementsByTagName(SHOW_TAG);
            for (int i = 0; i < showNodeList.getLength(); i++) {
                Node showNode = showNodeList.item(i);
                if (showNode.getNodeType() != Node.ELEMENT_NODE) continue;
                Element showElement = (Element) showNode;
                Show show = new Show();
                show.setShowID(showElement.getElementsByTagName(SHOW_ID_TAG).item(0).getTextContent());
                show.setEventID(showElement.getElementsByTagName(SHOW_EVENT_ID_TAG).item(0).getTextContent());
                show.setTheatreID(showElement.getElementsByTagName(THEATRE_ID_TAG).item(0).getTextContent());
                show.setTitle(showElement.getElementsByTagName(TITLE_TAG).item(0).getTextContent());
                show.setTheatreAndAuditorium(showElement.getElementsByTagName(THEATRE_AND_AUDITORIUM_TAG).item(0).getTextContent());
                show.setRating(showElement.getElementsByTagName(RATING_TAG).item(0).getTextContent());
                NodeList spokenLanguageNodeList = showElement.getElementsByTagName(SPOKEN_LANGUAGE_TAG);
                for (int k = 0; k < spokenLanguageNodeList.getLength(); k++) {
                    Node spokenLanguageNode = spokenLanguageNodeList.item(k);
                    if (spokenLanguageNode.getNodeType() != Node.ELEMENT_NODE) continue;
                    Element spokenLanguageElement = (Element) spokenLanguageNode;
                    show.setSpokenLanguage(spokenLanguageElement.getElementsByTagName(SPOKEN_LANGUAGE_NAME_TAG).item(0).getTextContent());
                    show.setSpokenLanguageISO(spokenLanguageElement.getElementsByTagName(SPOKEN_LANGUAGE_ISO_TAG).item(0).getTextContent());
                }
                show.setPresentationMethod(showElement.getElementsByTagName(PRESENTATION_METHOD_TAG).item(0).getTextContent());
                if (showElement.getElementsByTagName(EVENT_SMALL_IMAGE_PORTRAIT_TAG).getLength() > 0) {
                    show.setSmallImagePortrait(showElement.getElementsByTagName(EVENT_SMALL_IMAGE_PORTRAIT_TAG).item(0).getTextContent());
                }
                show.setDtAccounting(LocalDateTime.parse(showElement.getElementsByTagName(DT_ACCOUNTING_TAG).item(0).getTextContent()));
                show.setDttmShowStart(LocalDateTime.parse(showElement.getElementsByTagName(DTTM_SHOW_START_TAG).item(0).getTextContent()));
                NodeList subtitleLanguage1NodeList = showElement.getElementsByTagName(SUBTITLE_LANGUAGE1_TAG);
                for (int k = 0; k < subtitleLanguage1NodeList.getLength(); k++) {
                    Node subtitleLanguage1Node = subtitleLanguage1NodeList.item(k);
                    if (subtitleLanguage1Node.getNodeType() != Node.ELEMENT_NODE) continue;
                    Element spokenLanguageElement = (Element) subtitleLanguage1Node;
                    show.setSubtitleLanguage1(spokenLanguageElement.getElementsByTagName(SUBTITLE_LANGUAGE1_NAME_TAG).item(0).getTextContent());
                }
                NodeList subtitleLanguage2NodeList = showElement.getElementsByTagName(SUBTITLE_LANGUAGE2_TAG);
                for (int k = 0; k < subtitleLanguage2NodeList.getLength(); k++) {
                    Node subtitleLanguage2Node = subtitleLanguage2NodeList.item(k);
                    if (subtitleLanguage2Node.getNodeType() != Node.ELEMENT_NODE) continue;
                    Element spokenLanguageElement = (Element) subtitleLanguage2Node;
                    show.setSubtitleLanguage2(spokenLanguageElement.getElementsByTagName(SUBTITLE_LANGUAGE2_NAME_TAG).item(0).getTextContent());
                }
                if (show.getDttmShowStart().isAfter(LocalDateTime.now(ZoneId.of("Europe/Helsinki")))) {
                    showList.add(show);
                }
            }
        }
        this.showsList = showList;
    }

    public void setTheatreAreaList() {
        List<TheatreArea> theatreAreaList = new ArrayList<>();
        String uriString = generateStringURL(THEATRE_AREAS_URL);
        Document document = getDocument(uriString);
        NodeList theatreNodeList = document.getElementsByTagName(THEATRE_AREA_TAG);
        for (int i = 0; i < theatreNodeList.getLength(); i++) {
            Node theatreNode = theatreNodeList.item(i);
            if (theatreNode.getNodeType() != Node.ELEMENT_NODE) continue;
            Element element = (Element) theatreNode;
            TheatreArea theatreArea = new TheatreArea();
            theatreArea.setTheatreAreaID(element.getElementsByTagName(THEATRE_AREA_ID_TAG).item(0).getTextContent());
            theatreArea.setTheatreAreaName(element.getElementsByTagName(THEATRE_AREA_NAME_TAG).item(0).getTextContent());
            theatreAreaList.add(theatreArea);
        }
        this.theatreAreaList = theatreAreaList;
    }

    public void setEventList() {
        List<Event> eventList = new ArrayList<>();
        String uriString = generateStringURL(EVENTS_URI);
        Document document = getDocument(uriString);
        NodeList eventNodeList = document.getElementsByTagName(EVENT_TAG);
        for (int i = 0; i < eventNodeList.getLength(); i++) {
            Node eventNode = eventNodeList.item(i);
            if (eventNode.getNodeType() != Node.ELEMENT_NODE) continue;
            Element eventElement = (Element) eventNode;
            Event event = new Event();
            event.setEventID(eventElement.getElementsByTagName(EVEN_ID_TAG).item(0).getTextContent());
            event.setOriginalTitle(eventElement.getElementsByTagName(ORIGINAL_TITLE_TAG).item(0).getTextContent());
            event.setGlobalDistributorName(eventElement.getElementsByTagName(GLOBAL_DISTRIBUTOR_NAME_TAG).item(0).getTextContent());
            event.setType(eventElement.getElementsByTagName(EVENT_TYPE_TAG).item(0).getTextContent());
            event.setGenres(eventElement.getElementsByTagName(GENRES_TAG).item(0).getTextContent());
            event.setSynopsis(eventElement.getElementsByTagName(SYNOPSIS_TAG).item(0).getTextContent());
            event.setRatingImageUrl(eventElement.getElementsByTagName(RATING_IMAGE_URL_TAG).item(0).getTextContent());
            if (eventElement.getElementsByTagName(EVENT_LARGE_IMAGE_LANDSCAPE_TAG).getLength() > 0) {
                event.setLargeImageLandscape(eventElement.getElementsByTagName(EVENT_LARGE_IMAGE_LANDSCAPE_TAG).item(0).getTextContent());
            }
            event.setProductionYear(eventElement.getElementsByTagName(PRODUCTION_YEAR_TAG).item(0).getTextContent());
            event.setLengthInMinutes(eventElement.getElementsByTagName(LENGTH_MINUTES_TAG).item(0).getTextContent());
            event.setImdbRating(getRatingFromOmdbApi(event.getOriginalTitle(), event.getProductionYear()));
            List<Event.Actor> cast = new ArrayList<>();
            NodeList actorNodeList = eventElement.getElementsByTagName(ACTOR_TAG);
            for (int j = 0; j < actorNodeList.getLength(); j++) {
                Node actorNode = actorNodeList.item(j);
                if (actorNode.getNodeType() != Node.ELEMENT_NODE) continue;
                Element castElement = (Element) actorNode;
                Event.Actor actor = event.new Actor();
                actor.setActorFirstName(castElement.getElementsByTagName(ACTOR_FIRST_NAME_TAG).item(0).getTextContent());
                actor.setActorLastName(castElement.getElementsByTagName(ACTOR_LAST_NAME_TAG).item(0).getTextContent());
                cast.add(actor);
            }
            event.setCast(cast);
            List<Event.Director> directors = new ArrayList<>();
            NodeList directorNodeList = eventElement.getElementsByTagName(DIRECTORS_TAG);
            for (int j = 0; j < directorNodeList.getLength(); j++) {
                Node directorNode = directorNodeList.item(j);
                if (directorNode.getNodeType() != Node.ELEMENT_NODE) continue;
                Element directorElement = (Element) directorNode;
                Event.Director director = event.new Director();
                if (directorElement.getElementsByTagName(DIRECTOR_FIRST_NAME_TAG).getLength() > 0) {
                    director.setDirectorFirstName(directorElement.getElementsByTagName(DIRECTOR_FIRST_NAME_TAG).item(0).getTextContent());
                }
                if (directorElement.getElementsByTagName(DIRECTOR_LAST_NAME_TAG).getLength() > 0) {
                    director.setDirectorLastName(directorElement.getElementsByTagName(DIRECTOR_LAST_NAME_TAG).item(0).getTextContent());
                }
                directors.add(director);
            }
            event.setDirectors(directors);
            List<Event.ContentDescriptor> descriptors = new ArrayList<>();
            NodeList descriptorNodeList = eventElement.getElementsByTagName(CONTENT_DESCRIPTOR_TAG);
            for (int j = 0; j < descriptorNodeList.getLength(); j++) {
                Node descriptorNode = descriptorNodeList.item(j);
                if (descriptorNode.getNodeType() != Node.ELEMENT_NODE) continue;
                Element descriptorElement = (Element) descriptorNode;
                Event.ContentDescriptor descriptor = event.new ContentDescriptor();
                descriptor.setContentDescriptorName(descriptorElement.getElementsByTagName(CONTENT_DESCRIPTOR_NAME_TAG).item(0).getTextContent());
                descriptor.setContentDescriptorImageURL(descriptorElement.getElementsByTagName(CONTENT_DESCRIPTOR_IMAGE_TAG).item(0).getTextContent());
                descriptors.add(descriptor);
            }
            event.setContentDescriptors(descriptors);
            eventList.add(event);
        }
        this.eventList = eventList;
    }
}
