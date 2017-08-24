/*
 * Copyright 2015 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.xyztouristattractions.provider;

import android.net.Uri;

import com.example.android.xyztouristattractions.common.Attraction;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Static data content provider.
 */
public class TouristAttractions {

    public static final String CITY_SYDNEY = "Dhaka";

    public static final String CITY_KHULNA = "Khulna";

    public static final String TEST_CITY = CITY_SYDNEY;

    private static final float TRIGGER_RADIUS = 2000; // 2KM
    private static final int TRIGGER_TRANSITION = Geofence.GEOFENCE_TRANSITION_ENTER |
            Geofence.GEOFENCE_TRANSITION_EXIT;
    private static final long EXPIRATION_DURATION = Geofence.NEVER_EXPIRE;

    public static final Map<String, LatLng> CITY_LOCATIONS = new HashMap<String, LatLng>() {{
        put(CITY_SYDNEY, new LatLng(-33.873651, 151.2068896));
    }};

    /**
     * All photos used with permission under the Creative Commons Attribution-ShareAlike License.
     */
    public static final HashMap<String, List<Attraction>> ATTRACTIONS =
            new HashMap<String, List<Attraction>>() {{

        put(CITY_SYDNEY, new ArrayList<Attraction>() {{
            add(new Attraction(
                    "Bandarban",
                    "Bandarban (Bengali: বান্দরবান) is a district in South-Eastern Bangladesh, and a part of the Chittagong Division.[1] It is one of the three districts that make up the Chittagong Hill Tracts, the others being Rangamati District and Khagrachhari District..",
                    "Bandarban is regarded as one of the most attractive travel destinations in Bangladesh. Bandarban (meaning the dam of monkeys), or in Marma or Arakanese language as \"Rwa-daw Mro\" is also known as Arvumi or the Bohmong Circle (of the rest of the three hill districts Rangamati is the Chakma Circle, Raja Devasish Roy and Khagrachari is the Mong Circle, Raja Sachingprue Marma). Bandarban town is the home town of the Bohmong Chief (currently King, or Raja, U Cho Prue Marma) who is the head of the Marma population. It also is the administrative headquarters of Bandarban district, which has turned into one of the most exotic tourist attractions in Bangladesh..",
                    Uri.parse("https://upload.wikimedia.org/wikipedia/commons/thumb/1/19/Goodmorning_Keokaradang_%286830453822%29.jpg/1024px-Goodmorning_Keokaradang_%286830453822%29.jpg"),
                    Uri.parse("https://upload.wikimedia.org/wikipedia/commons/thumb/1/19/Goodmorning_Keokaradang_%286830453822%29.jpg/1024px-Goodmorning_Keokaradang_%286830453822%29.jpg"),
                    new LatLng(22.1924889,92.2185),
                    CITY_SYDNEY
            ));

            add(new Attraction(
                    "Noakhali Sciewnce and Technology University",
                    "Noakhali Science and Technology University is a newly established public university in the coastal terrain Noakhali of Bangladesh.-Wikipedia",
                    "Former Prime Minister Begum Khaleda Zia laid the foundation stone of Noakhali Science and Technology University on 11 October 2003.\n" +
                            "\n" +
                            "Earlier the Prime Minister Sheikh Hasina takes necessary steps to established this university. At 11 July 2001 her government passed a law in the parliament.\n" +
                            "\n" +
                            "Its construction work was formally inaugurated on 24 March 2005. It is the fifth of 12 such universities the government decided in 1997 to establish in the 12 erstwhile greater districts where there was no university.\n" +
                            "\n" +
                            "The aim of founding a science and technology university as defined in the project pro-forma is to mold merit into skilled work force and to develop centers of excellence to create and disseminate knowledge. Innovating new technologies and developing the old ones is one of the research and development (R&D) objectives of these universities.",
                    Uri.parse("http://nstu.edu.bd/images/phocagallery/Beauti%20Of%20NSTU/thumbs/phoca_thumb_l_nstu%20alll%20preview.jpg"),
                    Uri.parse("http://nstu.edu.bd/images/phocagallery/Beauti%20Of%20NSTU/thumbs/phoca_thumb_l_nstu%20alll%20preview.jpg"),
                    new LatLng(22.7931971,91.0980468),
                    CITY_SYDNEY
            ));

            add(new Attraction(
                    "Sundarbans",
                    "The Sundarbans (Bengali: সুন্দরবন, Shundôrbôn) is a natural region comprising southern Bangladesh and a small part in Eastern India. It is the largest single block of tidal halophytic mangrove forest in the world..",
                    "Sundarbans South, East and West are three protected forests in Bangladesh. This region is densely covered by mangrove forests, and is the largest reserves for the Bengal tiger. The Sundarbans National Park is a National Park, Tiger Reserve, and a Biosphere Reserve located in the Sundarbans delta in the Indian state of West Bengal..",
                    Uri.parse("https://upload.wikimedia.org/wikipedia/commons/5/58/Boat%2C_trees_and_water_in_Sundarbans.jpg"),
                    Uri.parse("https://upload.wikimedia.org/wikipedia/commons/5/58/Boat%2C_trees_and_water_in_Sundarbans.jpg"),
                    new LatLng(21.949722,89.18333),
                    CITY_SYDNEY
            ));

            add(new Attraction(
                    "Cox's Bazar Beach",
                    "Cox's Bazar (Bengali: কক্সবাজার Kôks bajar) is a town, a fishing port and district headquarters in Bangladesh. The beach in Cox's Bazar is an unbroken 125 km sandy sea beach with a gentle slope, one of the world's longest.[2][3][4] It is located 150 km south of the industrial port Chittagong.",
                    "Today, Cox's Bazar is one of the most-visited tourist destinations in Bangladesh, though it is not a major international tourist destination, and has no international hotel chains. In 2013, the Bangladesh Government formed the Tourist Police unit to better protect local and foreign tourists, as well as to look after the nature and wildlife in the tourist spots of Cox's Bazar.",
                    Uri.parse("https://lh4.googleusercontent.com/-wbNgVdUkBiE/VHe99hGVtNI/AAAAAAAAAFY/fAHfhchNLJw/w600-no/IMG_20141124_143747.jpg"),
                    Uri.parse("https://lh6.googleusercontent.com/-sjY_xlEOic4/VHe9-I4DD9I/AAAAAAAAAFI/Mt0VnjU7SxQ/w600-no/IMG_20141124_144008.jpg"),
                    new LatLng(21.4353765,91.9628865),
                    CITY_SYDNEY
            ));

            add(new Attraction(
                    "St. Martin's Island",
                    "St. Martin's Island (Bengali: সেন্ট মার্টিন্স দ্বীপ) is a small island (area only 8 km2) in the northeastern part of the Bay of Bengal, about 9 km south of the tip of the Cox's Bazar-Teknaf peninsula, and forming the southernmost part of Bangladesh. There is a small adjoining island that is separated at high tide, called Chhera island.",
                    "It is about 8 km west of the northwest coast of Myanmar, at the mouth of the Naf River. The first settlement started just 250 years ago by some Arabian sailors who named the island 'Zajira'. During British occupation the island was named St. Martin Island. During the First Anglo-Burmese War between the British and Burmese empires in 1824–1826, rival claims to the island were a major factor. The local names of the island are \"Narical Gingira\", also spelled \"Narikel Jinjira/Jinjera\", which means 'Coconut Island' in Bengali, and \"Daruchini Dip\". It is the only coral island in Bangladesh.",
                    Uri.parse("https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/St%27Martin%27s_Island.JPG/1024px-St%27Martin%27s_Island.JPG"),
                    Uri.parse("https://upload.wikimedia.org/wikipedia/commons/thumb/2/27/Saint_Martin_Island_Back_side_twilight_view.JPG/1024px-Saint_Martin_Island_Back_side_twilight_view.JPG"),
                    new LatLng(20.627256,92.322578),
                    CITY_SYDNEY
            ));
        }});

    }};

    /**
     * Creates a list of geofences based on the city locations
     */
    public static List<Geofence> getGeofenceList() {
        List<Geofence> geofenceList = new ArrayList<Geofence>();
        for (String city : CITY_LOCATIONS.keySet()) {
            LatLng cityLatLng = CITY_LOCATIONS.get(city);
            geofenceList.add(new Geofence.Builder()
                    .setCircularRegion(cityLatLng.latitude, cityLatLng.longitude, TRIGGER_RADIUS)
                    .setRequestId(city)
                    .setTransitionTypes(TRIGGER_TRANSITION)
                    .setExpirationDuration(EXPIRATION_DURATION)
                    .build());
        }
        return geofenceList;
    }

    public static String getClosestCity(LatLng curLatLng) {
        if (curLatLng == null) {
            // If location is unknown return test city so some data is shown
            return TEST_CITY;
        }

        double minDistance = 0;
        String closestCity = null;
        for (Map.Entry<String, LatLng> entry: CITY_LOCATIONS.entrySet()) {
            double distance = SphericalUtil.computeDistanceBetween(curLatLng, entry.getValue());
            if (minDistance == 0 || distance < minDistance) {
                minDistance = distance;
                closestCity = entry.getKey();
            }
        }
        return closestCity;
    }
}
